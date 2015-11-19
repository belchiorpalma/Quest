/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.itfox.beans;

/**
 *
 * @author belchiorpalma
 */
public class LogFiles {
    private int logFilesId;
    private String name;
    private String icon;
    private String size;
    private String status;
    private String satusIcon;
    private String url;
    private String thumbnailURL;
    private String deteleURL;
    private String deleteType;
    private String json;
    
    public LogFiles(){}

    public LogFiles(String name, String icon, String size, String status, String satusIcon, String url, String thumbnailURL, String deteleURL, String deleteType, String json) {
        this.name = name;
        this.icon = icon;
        this.size = size;
        this.status = status;
        this.satusIcon = satusIcon;
        this.url = url;
        this.thumbnailURL = thumbnailURL;
        this.deteleURL = deteleURL;
        this.deleteType = deleteType;
        this.json=json;
    }
    
    

    public int getLogFilesId() {
        return logFilesId;
    }

    public void setLogFilesId(int logFilesId) {
        this.logFilesId = logFilesId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSatusIcon() {
        return satusIcon;
    }

    public void setSatusIcon(String satusIcon) {
        this.satusIcon = satusIcon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public String getDeteleURL() {
        return deteleURL;
    }

    public void setDeteleURL(String deteleURL) {
        this.deteleURL = deteleURL;
    }

    public String getDeleteType() {
        return deleteType;
    }

    public void setDeleteType(String deleteType) {
        this.deleteType = deleteType;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
    
    
}
