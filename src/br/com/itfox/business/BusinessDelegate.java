/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.itfox.business;

import br.com.itfox.beans.CollectionColumns;
import br.com.itfox.beans.LogFiles;
import br.com.itfox.beans.LogUpload;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author belchiorpalma
 */
public class BusinessDelegate {
    
    // insert method for class LogUpload
    public void insertLogUpload(LogUpload l){
        Connection conn = new DBase(true).getConnection();
        
        if(conn!=null && l!=null){
            try {
                String sql = "INSERT INTO LOGUPLOAD (FILENAME, PATH, DESCRIPTION, STATUS) VALUES (?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                Blob description = conn.createBlob();
                description.setBytes(1, l.getDescription().getBytes());

                ps.setString(1,l.getFilename() );
                ps.setString(2, l.getPath());
                ps.setBlob(3, description);
                ps.setString(4, l.getStatus());
                ps.executeUpdate();
                
                conn.close();
                
            } catch (SQLException ex) {
                Logger.getLogger(BusinessDelegate.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    // insert method for class LogUpload
    public LogUpload selectLogUpload(LogUpload l){
        Connection conn = new DBase(true).getConnection();
        
        if(conn!=null && l!=null){
            try {
                String sql = "SELECT LOGUPLOAD_ID, FILENAME, PATH, DESCRIPTION, STATUS, DATE FROM  LOGUPLOAD WHERE PATH=? ORDER BY LOGUPLOAD_ID DESC LIMIT 1";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, l.getPath());
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    
                    java.sql.Blob ablob = rs.getBlob("DESCRIPTION");
                    String strDescription = new String(ablob.getBytes(1l, (int) ablob.length()));

                    LogUpload log = new LogUpload();
                    log.setLoguploadId(rs.getInt("LOGUPLOAD_ID"));
                    log.setFilename(rs.getString("FILENAME"));
                    log.setPath(rs.getString("PATH"));
                    log.setDescription(strDescription);
                    log.setStatus(rs.getString("STATUS"));
                    log.setDate(rs.getTimestamp("DATE"));
                    
                    return log;
                }
                conn.close();
                
            } catch (SQLException ex) {
                Logger.getLogger(BusinessDelegate.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return l;
    }
    
    // insert method for class LogFiles
    public void insertLogFiles(LogFiles l){
        Connection conn = new DBase(true).getConnection();
        
        if(conn!=null && l!=null){
            try {
                String sql = "INSERT INTO LOGFILES (NAME, ICON, SIZE, STATUS, STATUS_ICON, URL, THUMBNAIL_URL, DELETE_URL, DELETE_TYPE, JSON) VALUES (?, ?, ?, ?, ?,?,?,?,?,?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                
                ps.setString(1,l.getName());
                ps.setString(2, l.getIcon());
                ps.setString(3, l.getSize());
                ps.setString(4,l.getStatus());
                ps.setString(5, l.getSatusIcon());
                ps.setString(6, l.getUrl());
                ps.setString(7, l.getThumbnailURL());
                ps.setString(8, l.getDeteleURL());
                ps.setString(9, l.getDeleteType());
                ps.setString(10, l.getJson());
                ps.executeUpdate();
                
                conn.close();
                
            } catch (SQLException ex) {
                Logger.getLogger(BusinessDelegate.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void deleteLogFiles(LogFiles l){
        Connection conn = new DBase(true).getConnection();
        
        if(conn!=null && l!=null){
            try {
                String sql = "DELETE FROM LOGFILES WHERE NAME = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                
                ps.setString(1,l.getName());
                ps.executeUpdate();
                
                conn.close();
                
            } catch (SQLException ex) {
                Logger.getLogger(BusinessDelegate.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public List<LogFiles> selectLogFiles(){
        Connection conn = new DBase(true).getConnection();
        List<LogFiles> listLogFiles = new ArrayList<LogFiles>();
        if(conn!=null){
            try {
                String sql = "SELECT * FROM LOGFILES";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    
                    LogFiles l = new LogFiles(rs.getString("NAME"), rs.getString("ICON"), rs.getString("SIZE"),
                                            rs.getString("STATUS"), rs.getString("STATUS_ICON"), rs.getString("URL"),
                                            rs.getString("THUMBNAIL_URL"), rs.getString("DELETE_URL"), rs.getString("DELETE_TYPE"), rs.getString("JSON"));
                    listLogFiles.add(l);
                }
                conn.close();
                
            } catch (SQLException ex) {
                Logger.getLogger(BusinessDelegate.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listLogFiles;
    }
    
    public int selectCountChassi(String ini, String fim, String seg){
        Connection conn = new DBase(true).getConnection();
        int count=0;
        if(conn!=null){
            try {
                String sql = "SELECT COUNT(*) AS COUNTCHASSI\n" +
"                            FROM EMPLACAMENTOS E, MODELOS M, SEGMENTO S\n" +
"                            WHERE E.MODELO=M.MODELO AND\n" +
"                            M.SEGMENTO=S.SEGMENTO AND\n" +
"                            E.DATA BETWEEN ? AND ? AND \n" +
"							S.SEGMENTO = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, ini);
                ps.setString(2, fim);
                ps.setString(3, seg);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    count=rs.getInt("COUNTCHASSI");
                }
                conn.close();
                
            } catch (SQLException ex) {
                Logger.getLogger(BusinessDelegate.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return count;
    }
    
    public List<CollectionColumns> selectDistribuicaoSeg(String ini, String fim, String seg){
        Connection conn = new DBase(true).getConnection();
        List<CollectionColumns> list = new ArrayList<CollectionColumns>();
        String count="";
        String desc = "";
        if(conn!=null){
            try {
                String sql = "SELECT COUNT(SS.SUBSEGMENTO) AS NUMVEIC, S.SEGMENTO, SS.DESCRICAO \n " +
                            "FROM EMPLACAMENTOS E, MODELOS M, SEGMENTO S, SUBSEGMENTO SS \n " +
                            "WHERE E.MODELO=M.MODELO AND\n " +
                            "M.SEGMENTO=S.SEGMENTO AND\n " +
                            "M.SUBSEGMENTO=SS.SUBSEGMENTO AND\n " +
                            "E.DATA BETWEEN ? AND ? AND\n " +
                            "S.SEGMENTO = ? \n " +
                            "GROUP BY SS.SUBSEGMENTO ";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, ini);
                ps.setString(2, fim);
                ps.setString(3, seg);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    count=rs.getString("NUMVEIC");
                    desc= rs.getString("DESCRICAO");
                    CollectionColumns c = new CollectionColumns(new String[]{count,"",desc});
                    list.add(c);
                }
                conn.close();
                
            } catch (SQLException ex) {
                Logger.getLogger(BusinessDelegate.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }
    
    public List<CollectionColumns> selectDistribuicaoMarca(String ini, String fim, String seg){
        Connection conn = new DBase(true).getConnection();
        List<CollectionColumns> list = new ArrayList<CollectionColumns>();
        String count="";
        String desc = "";
        if(conn!=null){
            try {
                String sql = "SELECT COUNT(*) AS TOTAL, MA.DESCRICAO \n " +
                            "FROM EMPLACAMENTOS E, MODELOS M, MARCAS MA, SEGMENTO S\n " +
                            "WHERE E.MODELO=M.MODELO AND\n " +
                            "M.FABRICANTE=MA.MARCA AND\n " +
                            "M.SEGMENTO=S.SEGMENTO AND\n " +
                            "E.DATA BETWEEN ? AND ? AND\n "+
                            "S.SEGMENTO=?  "+
                            "GROUP BY MA.MARCA";
                PreparedStatement ps = conn.prepareStatement(sql);
               
                ps.setString(1, ini);
                ps.setString(2, fim);
                ps.setString(3, seg);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    count=rs.getString("TOTAL");
                    desc= rs.getString("DESCRICAO");
                    CollectionColumns c = new CollectionColumns(new String[]{count,desc});
                    list.add(c);
                }
                conn.close();
                
            } catch (SQLException ex) {
                Logger.getLogger(BusinessDelegate.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }
    
    public List<CollectionColumns> selectRankMunic(String ini, String fim, String seg){
        Connection conn = new DBase(true).getConnection();
        List<CollectionColumns> list = new ArrayList<CollectionColumns>();
        String count="";
        String desc = "";
        if(conn!=null){
            try {
                String sql = "SELECT COUNT(*) AS TOTAL, MU.NOME\n" +
                            "FROM EMPLACAMENTOS E, MODELOS M, MARCAS MA, SEGMENTO S, MUNICIPIOS MU\n" +
                            "WHERE E.MODELO=M.MODELO AND\n" +
                            "M.FABRICANTE=MA.MARCA AND\n" +
                            "M.SEGMENTO=S.SEGMENTO AND\n" +
                            "E.MUNICIPIO=MU.MUNICIPIO AND\n" +
                            "E.DATA BETWEEN ? AND ? AND\n" +
                            "S.SEGMENTO=? \n" +
                            "GROUP BY MU.NOME\n" +
                            "ORDER BY TOTAL DESC\n" +
                            "LIMIT 10";
                                            PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, ini);
                ps.setString(2, fim);
                ps.setString(3, seg);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    count=rs.getString("TOTAL");
                    desc= rs.getString("NOME");
                    CollectionColumns c = new CollectionColumns(new String[]{count,desc});
                    list.add(c);
                }
                conn.close();
                
            } catch (SQLException ex) {
                Logger.getLogger(BusinessDelegate.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }
    
    public List<CollectionColumns> selectRankModel(String ini, String fim, String seg){
        Connection conn = new DBase(true).getConnection();
        List<CollectionColumns> list = new ArrayList<CollectionColumns>();
        String count="";
        String desc = "";
        if(conn!=null){
            try {
                String sql = "SELECT COUNT(*) AS TOTAL, M.DESCRICAO\n" +
                            "FROM EMPLACAMENTOS E, MODELOS M, MARCAS MA, SEGMENTO S, MUNICIPIOS MU\n" +
                            "WHERE E.MODELO=M.MODELO AND\n" +
                            "M.FABRICANTE=MA.MARCA AND\n" +
                            "M.SEGMENTO=S.SEGMENTO AND\n" +
                            "E.MUNICIPIO=MU.MUNICIPIO AND\n" +
                            "E.DATA BETWEEN ? AND ? AND\n" +
                            "S.SEGMENTO=? \n" +
                            "GROUP BY M.MODELO\n" +
                            "ORDER BY TOTAL DESC\n" +
                            "LIMIT 10";
                                            PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, ini);
                ps.setString(2, fim);
                ps.setString(3, seg);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    count=rs.getString("TOTAL");
                    desc= rs.getString("DESCRICAO");
                    CollectionColumns c = new CollectionColumns(new String[]{count,desc});
                    list.add(c);
                }
                conn.close();
                
            } catch (SQLException ex) {
                Logger.getLogger(BusinessDelegate.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }
    
    public List<CollectionColumns> selectSegmentos(){
        Connection conn = new DBase(true).getConnection();
        List<CollectionColumns> list = new ArrayList<CollectionColumns>();
        String seg= "";
        String desc = "";
        if(conn!=null){
            try {
                String sql = "SELECT SEGMENTO, DESCRICAO FROM SEGMENTO";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    seg=rs.getString("SEGMENTO");
                    desc= rs.getString("DESCRICAO");
                    CollectionColumns c = new CollectionColumns(new String[]{seg,desc});
                    list.add(c);
                }
                conn.close();
                
            } catch (SQLException ex) {
                Logger.getLogger(BusinessDelegate.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }
    
}
