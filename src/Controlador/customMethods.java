/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Measure;
import ModeloInterno.Fmeasure;
import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ANDRES ARCINIEGAS
 */
public class customMethods {
    public static Calendar toCalendar(Date date){ 
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
    
    public static boolean isAnyTrue(boolean[] array)
    {
        for(boolean b : array) if(b) return true;
        return false;
    }

    static String toJson(Object obj, String file) throws IOException{ //Convierte a un archivo json el objeto que llegue, a la dirección especificada i.e: D:\\file.json
        Gson gson = new Gson();
        String jsonInString = gson.toJson(obj);
        System.out.println(jsonInString);
                
        FileWriter writer = new FileWriter(file);
        gson.toJson(obj,writer);
        writer.close();
        
        return jsonInString;
    }
    
    static String getHost(){
        String hostname = "Unknown";
        try{
            InetAddress addr;
            addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
            System.out.println(hostname);

        }
        catch(Exception ex){
            System.out.println("Host could not be resolved");
        }
        return hostname;
    }

    static String getJsonAddress() {
         if (customMethods.getHost().equals("raspberrypi")) { //Se asigna la ruta para leerse desde la página web
             return("/home/pi/webApp/SGMUdenarApp/public/json/db.json");
             //jsonAddress = "db.json";
         }
         else{
             return("D:\\Dropbox\\Tesis\\SGMUdenarApp\\public\\json\\db.json"); //Debería ser algo más general, pero para este caso la dejaremos así
         }
    }

    static List<Fmeasure> getRandomValues(double voltage, double current, double powerFactor, int numMeasures, int tolerance, int[] quantityIDs) {
        double powerReal = voltage*current;
        double powerAparent = powerReal/powerFactor;
        double powerReactive = Math.sqrt(Math.pow(powerAparent,2) - Math.pow(powerReal,2));
        double phaseValue = Math.acos(powerFactor)*180/Math.PI;
        
        double[] simulatedValues = new double[]{voltage,current,powerReal,powerReactive,powerAparent,phaseValue,powerFactor};
        
        List<Fmeasure> fmeasures = new ArrayList<>();
        
        for (int j = 0; j < quantityIDs.length; j++) {
            Fmeasure fmeasure = new Fmeasure();
            fmeasure.setQuantityID(quantityIDs[j]);
            fmeasure.setValue(simulatedValues[j]*(1 + tolerance*(Math.random()*2-1)/100));
            fmeasures.add(fmeasure);
        }
        return fmeasures;
    }


    

            
            
    
}
