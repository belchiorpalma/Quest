/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.itfox.beans;

import java.sql.Timestamp;

/**
 *
 * @author belchiorpalma
 */
public class LogUpload {
    private int loguploadId;
    private String filename;
    private String path;
    private String description;
    private String status;
    private Timestamp date;
    
    public LogUpload(){}

    public LogUpload(String filename, String path, String description, String status) {
        this.filename = filename;
        this.path = path;
        this.description = description;
        this.status = status;
    }
    

    public int getLoguploadId() {
        return loguploadId;
    }

    public void setLoguploadId(int loguploadId) {
        this.loguploadId = loguploadId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
    
    
}
