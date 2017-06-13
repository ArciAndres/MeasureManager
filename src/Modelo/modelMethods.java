/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Controlador.NewHibernateUtil;
import Controlador.customMethods;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;


/**
 *
 * @author ANDRES ARCINIEGAS
 */
public class modelMethods {
    public static Object getbyElementName (Class classe, String columnName, String element){ //Se toma como argumento la clase, el nombre de la columna, y el valor a buscar
        SessionFactory sessionFactory = NewHibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(classe);
        Object obj = criteria.add(Restrictions.eq(columnName, element)).uniqueResult();
        session.close();
        return obj;
    }

    public static boolean getComparativeTime(Date dte) {
        Calendar now = customMethods.toCalendar(dte);
        Config recordtime = (Config)modelMethods.getbyElementName(Config.class,"element", "recordTime"); //recordTime es un registro de la tabla de Configuración
        Calendar recordTime = customMethods.toCalendar(recordtime.getTimespan());
        
        int nowToSeconds = now.get(Calendar.MINUTE) * 60 + now.get(Calendar.SECOND);
        int recordTimeSeconds = recordTime.get(Calendar.MINUTE) * 60 + recordTime.get(Calendar.SECOND);
        return nowToSeconds % recordTimeSeconds == 0 ; 
    }
    
    public static int getDelayToStartReadings(Date dte, long period) {
        Calendar now = customMethods.toCalendar(dte);
        int delay = 10-now.get(Calendar.SECOND)%10;
        return delay;
    }
}
