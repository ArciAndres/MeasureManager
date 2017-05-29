package Controlador;

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
import Vista.InsertMeasure;
import java.sql.Time;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Calendar;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.Transaction;

public class Operaciones {
    private InsertMeasure insertMeasureView;
    
    public Operaciones(InsertMeasure i){
        insertMeasureView = i;
    }
    
    double[] quantityValues = new double[]{120,30,360,10,365,60,0.7};
    ScheduledExecutorService executor;
    
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
    public void insertSingleMeasure(Measurement measurement, Measure measure)
    {
        SessionFactory sessionFactory = NewHibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        measure.setMeasurement(measurement);
        session.save(measure);
        tx.commit();
        session.close();
        //JOptionPane.showMessageDialog(null, "Single Measure inserted correctly");
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
    
    public void insertMeasureNow(Measurement measurement, List<Measure> Measures)
    {
        measurement = insertSingleMeasurement(measurement);
        for (Iterator<Measure> iterator = Measures.iterator(); iterator.hasNext();) {
            Measure measure = iterator.next();
            insertSingleMeasure(measurement, measure);
        }

        JOptionPane.showMessageDialog(null, "Whole inserting operation went well!!");
    }
    
    public void loopInsertMeasures(Measurement measurement, int numMeasurements, int numMeasures , double value, double tolerance)
    {
        int conta = 0;
        List<Measure> measures = new ArrayList<Measure>();
        Quantity quantity = new Quantity();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < numMeasurements; i++) {
           measurement.setTime(new Date());
           measurement = insertSingleMeasurement(measurement);
            for (int j = 1; j <= numMeasures; j++) {
                quantity = new Quantity();
                quantity.setId(j);
                measures.add(new Measure(measurement, quantity, value + tolerance*(Math.random()*2-1)));
                conta++;
            }
            insertListMeasures(measures);
        }
        long estimatedTime = System.currentTimeMillis() - startTime;
        JOptionPane.showMessageDialog(null, "Whole List Operation was hell Ok!!" + " Conta = " + Integer.toString(conta) + " time = " + Long.toString(estimatedTime));
    }

    public void loopInfinite(double voltage, double current, double powerFactor, double frequency, double phaseValue,long period, int id_meter, boolean[] phases, int numMeasurements, int numMeasures, int tolerance) 
    {
        double powerReal = voltage*current;
        double powerAparent = powerReal/powerFactor;
        double powerReactive = Math.sqrt(Math.pow(powerAparent,2) - Math.pow(powerReal,2));
        Meter meter = new Meter();
        meter.setId(id_meter);
        phaseValue = Math.acos(powerFactor)*180/Math.PI;
        double[] measureValues = new double[]{voltage,current,powerReal,powerReactive,powerAparent,phaseValue,powerFactor};
        
        
        Runnable scriptRunnable = new Runnable() {    
            // Se puede determinar el período en el que se actualizan las medidas, pero a la base de datos solamente
            //se cargan con el período que se especifica en la fila de configuración recordTime de esta.
            
            public void run() {
                System.out.println("Entró a la función run");
                
                Date now = new Date();
                FormatMeasure formatmeasure = new FormatMeasure();
                formatmeasure.setDate(now);

                List<FPhase> fphases = new ArrayList<FPhase>();
                try{
                    for (int phase = 0; phase < phases.length; phase++) {
                        FPhase fphase = new FPhase();
                        fphase.setPhase(phase);
                        List<Fmeasure> fmeasures = new ArrayList<>();
                        for (int j = 1; j <= numMeasures; j++) {
                            Fmeasure fmeasure = new Fmeasure();
                            fmeasure.setQuantityID(j);
                            fmeasure.setValue(measureValues[j-1]*(1 + tolerance*(Math.random()*2-1)/100));
                            fmeasures.add(fmeasure);
                        }
                        fphase.setFmeasures(fmeasures);
                        fphases.add(fphase);
                    }
                    formatmeasure.setFPhases(fphases);
                    customMethods.toJson(formatmeasure,"D:\\file.json");
                    if (!customMethods.isAnyTrue(phases)) {
                        System.out.println("Ninguna fase seleccionada. Revise la pestaña Measurement");
                    }
                }
                catch(Exception ex){
                    System.out.println("Hubo en error en la ejecución. Error: " + ex.toString());
                }



//                    try{
//                        for (int phase = 0; phase < phases.length; phase++) {
//                            if (phases[phase] == true) {
//                                measures = new ArrayList<Measure>();
//                                measurements.add(new Measurement(meter, now, phase));
//                                for (int j = 1; j <= numMeasures; j++) {
//                                    Quantity quantity = new Quantity();
//                                    quantity.setId(j);
//                                    measures.add(new Measure(measurements.get(measurements.size()-1), quantity, measureValues[j-1]*(1 + tolerance*(Math.random()*2-1)/100)));
//                                }
//                                measuresTotal.add(measures);
//                            }
//                            List<Object> activity = new ArrayList<Object>();
//                            activity.add(measurements);
//                            activity.add(measures);
//                            
//                            customMethods.toJson(measures,"D:\\file.json");
//                            
//                        }
//                        if (!customMethods.isAnyTrue(phases)) {
//                            System.out.println("Ninguna fase seleccionada. Revise la pestaña Measurement");
//                        }
//                    }
//                    catch(Exception ex){
//                        System.out.println("Hubo en error en la ejecución. Error: " + ex.toString());
//                    }
//                    

                if(modelMethods.getComparativeTime(now))
                {
                    int conta = 0, conta2 = 0;
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
                            insertMeasureView.getTextField_insertedMeasures().setText(String.valueOf(conta));
                        }
                        conta2++;
                        insertListMeasures(measures);
                        insertMeasureView.getTextField_insertedMeasurements().setText(String.valueOf(conta2));
                        System.out.println("Medidas insertadas");               
                    }
                }   
            }
        };
        
        executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(scriptRunnable, 0, period, TimeUnit.SECONDS);
        
        
    }
   

    public void stopInfiniteLoop() {
        System.out.println("Entró al stopInfiniteLoop");
        executor.shutdown();
    }   
    
}
