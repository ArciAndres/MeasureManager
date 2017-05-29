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
import java.util.Arrays;
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
            int conta = 0;
            int conta2 = 0;

            public void run() {
                    System.out.println("Entró a la función run");
                    List<Measure> measures = new ArrayList<Measure>();
                    try{
                        for (int phase = 0; phase < phases.length; phase++) {
                            if (phases[phase] == true) {
                                Measurement measurement = new Measurement(meter, new Date(), phase);
//                          JTextField TextField_insertedMeasurements = InsertMeasure.insertMeasure.getTextField_insertedMeasurements();
                                measurement = insertSingleMeasurement(measurement);
                                for (int j = 1; j <= numMeasures; j++) {
                                    Quantity quantity = new Quantity();
                                    quantity.setId(j);
                                    measures.add(new Measure(measurement, quantity, measureValues[j-1]*(1 + tolerance*(Math.random()*2-1)/100)));
                                    conta++;
                                    insertMeasureView.getTextField_insertedMeasures().setText(String.valueOf(conta));
                                }
                                conta2++;
                                insertMeasureView.getTextField_insertedMeasurements().setText(String.valueOf(conta2));
                                insertListMeasures(measures);
                                System.out.println("Medidas de fase " + phase + " insertadas");
                            }
                        }
                        if (!isAnyTrue(phases)) {
                            System.out.println("Ninguna fase seleccionada. Revise la pestaña Measurement");
                        }
                    }
                    catch(Exception ex){
                        System.out.println("Hubo en error en la ejecución. Error: " + ex.toString());
                    }
            }
        };
        
        executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(scriptRunnable, 0, period, TimeUnit.SECONDS);
        
        
    }
    
    public static boolean isAnyTrue(boolean[] array)
    {
        for(boolean b : array) if(b) return true;
        return false;
    }

    public void stopInfiniteLoop() {
        System.out.println("Entró al stopInfiniteLoop");
        executor.shutdown();
    }   
    
}
