/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.itfox.config;

/**
 *
 * @author belchiorpalma
 */
public class Preferences {
    private static final String USER="root";
    private static final String PASS="";
    private static final String DATABASE="jdbc:mysql://localhost:3306/quest?generateSimpleParameterMetadata=true";

    public static String getUSER() {
        return USER;
    }

    public static String getPASS() {
        return PASS;
    }

    public static String getDATABASE() {
        return DATABASE;
    }
    
}
