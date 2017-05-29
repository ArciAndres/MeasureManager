/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Measure;
import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;
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

    static String toJson(Object obj, String file){ //Convierte a un archivo json el objeto que llegue, a la dirección especificada i.e: D:\\file.json
        Gson gson = new Gson();
        String jsonInString = gson.toJson(obj);
        System.out.println(jsonInString);
        
        try {
            FileWriter writer = new FileWriter(file);
            gson.toJson(obj,writer);
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(customMethods.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jsonInString;
    }
    
}
