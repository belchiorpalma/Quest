/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.itfox.business;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author belchiorpalma
 */
public class ImportTxt {
    public void importTxt(){
        
    }
    
    public void restoreTable(){
        try {
            /******************************************************/
            //Database Properties
            /******************************************************/
            String dbName = "dbName";
            String dbUser = "dbUser";
            String dbPass = "dbPass";
            
            /***********************************************************/
            // Execute Shell Command
            /***********************************************************/
            String executeCmd = "";
            
            executeCmd = new String[]{"/bin/sh", "-c", "mysql -u" + dbUser+ " -p"+dbPass+" " + dbName+ " < backup.sql" }.toString();
            
            
            Process runtimeProcess =Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();
            if(processComplete == 0){
                System.out.println("success");
            } else {
                System.out.println("restore failure");
            }
        } catch (IOException ex) {
            Logger.getLogger(ImportTxt.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ImportTxt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
