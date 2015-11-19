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
public class CollectionColumns {
    private String[] columns;
    public CollectionColumns(String args[]){
        this.columns=args;
    }

    public String[] getColumns() {
        return columns;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }
    
    
}
