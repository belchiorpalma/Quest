/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.itfox.test;

import br.com.itfox.beans.Table;
import br.com.itfox.business.DBase;
import br.com.itfox.config.Preferences;
import java.sql.Connection;

/**
 *
 * @author belchiorpalma
 */
public class AutomateImport {
    public static void main(String[] args) 
    {
        //DBase db = new DBase();
        //Connection conn = db.connect(Preferences.getDATABASE(),Preferences.getUSER(),Preferences.getPASS());
        DBase db = new DBase(true);
        String filename="";
        Table table = new Table();
        for(Table t: table.listTable()){
            filename=t.getFilename();
            db.importData(filename,"/Users/belchiorpalma/Desktop/Quest-Wireframe/Emplacamentos/"+filename+".txt");
        }
    }
}
