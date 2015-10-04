/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Peter
 */
public class HomePortalUtil {
    
    public static int convertTimeIntoNumeral(String time){
        int timeNumeral = 0;
        if(time.equals("01:00 AM")){
            timeNumeral = 1;
        }else if(time.equals("02:00 AM")){
            timeNumeral = 2;
        }else if(time.equals("03:00 AM")){
            timeNumeral = 3;
        }else if(time.equals("04:00 AM")){
            timeNumeral = 4;
        }else if(time.equals("05:00 AM")){
            timeNumeral = 5;
        }else if(time.equals("06:00 AM")){
            timeNumeral = 6;
        }else if(time.equals("06:00 AM")){
            timeNumeral = 7;
        }else if(time.equals("08:00 AM")){
            timeNumeral = 8;
        }else if(time.equals("09:00 AM")){
            timeNumeral = 9;
        }else if(time.equals("10:00 AM")){
            timeNumeral = 10;
        }else if(time.equals("11:00 AM")){
            timeNumeral = 11;
        }else if(time.equals("12:00 PM")){
            timeNumeral = 12;
        }else if(time.equals("01:00 PM")){
            timeNumeral = 13;
        }else if(time.equals("02:00 PM")){
            timeNumeral = 14;
        }else if(time.equals("03:00 PM")){
            timeNumeral = 15;
        }else if(time.equals("04:00 PM")){
            timeNumeral = 16;
        }else if(time.equals("05:00 PM")){
            timeNumeral = 17;
        }else if(time.equals("06:00 PM")){
            timeNumeral = 18;
        }else if(time.equals("07:00 PM")){
            timeNumeral = 19;
        }else if(time.equals("08:00 PM")){
            timeNumeral = 20;
        }else if(time.equals("09:00 PM")){
            timeNumeral = 21;
        }else if(time.equals("10:00 PM")){
            timeNumeral = 22;
        }else if(time.equals("11:00 PM")){
            timeNumeral = 23;
        }else if(time.equals("12:00 AM")){
            timeNumeral = 24;
        }
        return timeNumeral;
    }
    
    
    public static String getparsedDate(String date) throws Exception {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00");
        String s1 = date;
        String s2 = null;
        Date d;
        try {
            d = sdf.parse(s1);
            s2 = (new SimpleDateFormat("MM/dd/yyyy")).format(d);

        } catch (ParseException e) {

            e.printStackTrace();
        }
        return s2;
    }
    
    public static String convertToDate(String date) {
        Date date1 = null;
        String date2 = null;
        try {
           date1 = new SimpleDateFormat("MM/dd/yyyy").parse(date);
           System.out.println("date 1 :" + date1);
           date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date1);
           System.out.println(date2);
          
        } catch (ParseException ex) {
            Logger.getLogger(HomePortalUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
         System.out.println("date 2 :" + date2);
         return date2;
    }
    
    public static double formatAmount(double amount)
    {
        DecimalFormat decim = new DecimalFormat("0.00");
        Double amt = Double.parseDouble(decim.format(amount));
        return amt;   
    }
}
