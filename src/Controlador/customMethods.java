/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.util.Calendar;
import java.util.Date;

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
    
}
