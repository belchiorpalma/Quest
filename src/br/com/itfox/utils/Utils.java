package br.com.itfox.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author belchiorpalma
 */
public class Utils {
    public static String dateFormat(String date_s){
        String dateFormated="";
        try {
            //String date_s = "2011-01-18 00:00:00.0";
            
            // *** note that it's "yyyy-MM-dd hh:mm:ss" not "yyyy-mm-dd hh:mm:ss"
            SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yy");
            Date date = dt.parse(date_s);
            
            // *** same for the format String below
            SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
            dateFormated=dt1.format(date);
            //System.out.println(dateFormated);
        } catch (ParseException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dateFormated;
    }
    public static String dateFormatPt(String date_s){
        String dateFormated="";
        try {
            //String date_s = "2011-01-18 00:00:00.0";
            
            // *** note that it's "yyyy-MM-dd hh:mm:ss" not "yyyy-mm-dd hh:mm:ss"
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dt.parse(date_s);
            
            // *** same for the format String below
            SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yy");
            dateFormated=dt1.format(date);
            //System.out.println(dateFormated);
        } catch (ParseException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dateFormated;
    }
    
}
