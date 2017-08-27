package Controlador;

import ADE.*;
import ModeloInterno.FormatMeasure;
import ModeloInterno.Fmeasure;
import ModeloInterno.FPhase;
import Modelo.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import Vista.MeasureManagerGUI;
import com.pi4j.io.i2c.I2CFactory;
import java.io.IOException;
import java.net.InetAddress;
import java.sql.Time;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.stream.Collectors;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.Transaction;

public class Operaciones {
    private MeasureManagerGUI measureManagerGUI;
    String jsonAddress ;
    public Operaciones(MeasureManagerGUI i){
        measureManagerGUI = i;
        ADERegister.initRegisterlist();
    }
    
    int[] quantitiesIDtoMeasureInst = new int[]{18,19,13,32}; // 18 y 19 = V e I RMS, 13 = Factor de Potencia, 32 =Potencia activa instantánea
    ScheduledExecutorService executor;
    
    public void loopInfinite(double voltage, double current, double powerFactor, double frequency, double phaseValue,long period, int id_meter, boolean[] phases, int numMeasurements, int numMeasures, int tolerance, boolean aleatorios, int iterationsForMeanValue) throws I2CFactory.UnsupportedBusNumberException, IOException, InterruptedException
    {        
        Meter meter = new Meter();
        meter.setId(id_meter);
        jsonAddress = customMethods.getJsonAddress();
        List<ADERegister> instRegisters = ADEController.getRegistersByQuantityID(quantitiesIDtoMeasureInst);
        if (!aleatorios) {
            ADEController.inicializacionADE();
        }
        
        
        Runnable scriptRunnable = new Runnable() {    
            // Se puede determinar el período en el que se actualizan las medidas, pero a la base de datos solamente
            // se cargan con el período que se especifica en la fila de configuración recordTime de esta.
            int conta = 0, conta2 = 0, contaErrores = 0;
            public void run() { // Las clases que comienzan por f en esta parte, son utilizadas para gestionar más fácil la información desde el archivo de json. Para cargarlas a la base de datos ya se hace la respectiva conversión
                
                System.out.println("Ejecuta la funcion run()");
                
                Date now = new Date();
                FormatMeasure formatmeasure = new FormatMeasure();
                formatmeasure.setDate(now);

                List<FPhase> fphases = new ArrayList<FPhase>();
                try{
                    for (int phase = 0; phase < phases.length; phase++) {
                        FPhase fphase = new FPhase();
                        fphase.setPhase(phase);
                        List<Fmeasure> fmeasures = new ArrayList<>();
                        
                        if (aleatorios){ 
                            fmeasures = customMethods.getRandomValues(voltage,current,powerFactor,numMeasures,tolerance,quantitiesIDtoMeasureInst);
                        }
                        else 
                        {
                            final int phaseFinal = phase;
                            fmeasures = ADEController.getValuesFromRegisters(instRegisters.stream().filter(r -> r.getPhase() == phaseFinal).collect(Collectors.toList()), iterationsForMeanValue);
                            int g = 0;
                        }
                        fphase.setFmeasures(fmeasures);
                        fphases.add(fphase);
                    }
                    formatmeasure.setFPhases(fphases);
                    customMethods.toJson(formatmeasure,jsonAddress); //Guarda los datos en formato json para ser leídos por la página web
                    System.out.println("Medidas registradas en " + jsonAddress);
                    Thread.sleep(10);
                    if (!customMethods.isAnyTrue(phases)) {
                        System.out.println("Ninguna fase seleccionada. Revise la pestaña Measurement");
                    }
                }
                catch(Exception ex){
                    Logger.getLogger(Operaciones.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Hubo en error en la ejecución. Error: " + ex.toString());
                    contaErrores++;
                    System.out.println("Errores en esta ejecución: " + String.valueOf(contaErrores));
                    
                }
                
                if(modelMethods.getComparativeTime(now) )
                {
                    List<Measure> measures = new ArrayList<Measure>();
                    List<List<Measure>> measuresTotal = new ArrayList<List<Measure>>();
                    List<Measurement> measurements = new ArrayList<Measurement>();
                    
                    for (int i = 0; i < fphases.size(); i++) {
                        FPhase phase = fphases.get(i);
                        measures = new ArrayList<Measure>();
                        Measurement measurement = new Measurement(meter, now, phase.getPhase());
                        measurement = insertSingleMeasurement(measurement);
                        
                        List<Fmeasure> fmeasures = phase.getFmeasures();
                        
                        for (int j = 0; j < fmeasures.size(); j++) {
                            Quantity quantity = new Quantity();
                            quantity.setId(fmeasures.get(j).getQuantityID());
                            measures.add(new Measure(measurement, quantity, fmeasures.get(j).getValue()));
                            conta++;
                            measureManagerGUI.getTextField_insertedMeasures().setText(String.valueOf(conta));
                        }
                        conta2++;
                        insertListMeasures(measures);
                        measureManagerGUI.getTextField_insertedMeasurements().setText(String.valueOf(conta2));
                        System.out.println("Medidas insertadas");               
                    }
                }   
            }
        };
        
        executor = Executors.newScheduledThreadPool(1);
        int delay = modelMethods.getDelayToStartReadings(new Date(),period);
        executor.scheduleAtFixedRate(scriptRunnable, delay, period, TimeUnit.SECONDS);
    }
    
    public Measurement insertSingleMeasurement(Measurement measurement)
    {
        SessionFactory sessionFactory = NewHibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        int id = (int)session.save(measurement);
        tx.commit();
        measurement = (Measurement)session.get(Measurement.class, id);
        session.close();
        //JOptionPane.showMessageDialog(null, "Single Measurement inserted correctly");
        return measurement;
    }
    
    public void insertListMeasures(List<Measure> Measures)
    {
        SessionFactory sessionFactory = NewHibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        for (Iterator<Measure> iterator = Measures.iterator(); iterator.hasNext();) {
            Measure measure = iterator.next();
            session.save(measure);
        }        
        tx.commit();
        session.close();
        //JOptionPane.showMessageDialog(null, "List of Measures inserted correctly");
    }

    
    
   

    public void stopInfiniteLoop() {
        System.out.println("Entró al stopInfiniteLoop");
        executor.shutdown();
    }   
    
    
}
