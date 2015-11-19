/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.itfox.business;

/**
 *
 * @author belchiorpalma
 */
import br.com.itfox.beans.LogUpload;
import br.com.itfox.beans.Table;
import br.com.itfox.config.Preferences;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DataTruncation;
import java.sql.DriverManager;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBase
{
    private Connection connection = null;
    private static String strError="";
    public DBase()
    {
    }
    public DBase(boolean connect){
        if(connect==true){
            this.connect();
        }
    }
    public void connect(){
        try{
                this.connection = this.connect(Preferences.getDATABASE(),Preferences.getUSER(),Preferences.getPASS());
            }catch(Exception e){
                e.printStackTrace();
            }
    }
    
    public Connection getConnection(){
        return this.connection;
    }
 
    public Connection connect(String db_connect_str, 
  String db_userid, String db_password)
    {
        Connection conn;
        try
        {
            Class.forName( "com.mysql.jdbc.Driver").newInstance();
 
            conn = DriverManager.getConnection(db_connect_str, 
            db_userid, db_password);
         
        }
        catch(Exception e)
        {
            e.printStackTrace();
            conn = null;
        }
 
        return conn;    
    }
     
    public void importData(Connection conn,String filename, String absolutePath)
    {
        Statement stmt;
        String query;
        String tablename="";
        String fields = "";
        // filename equals .txt filename imported
 
        try
        {
            stmt = conn.createStatement(
            ResultSet.TYPE_SCROLL_SENSITIVE,
            ResultSet.CONCUR_UPDATABLE);
            
           // check if filename exists in list
            Table t = new Table();
            t = t.search(filename);
            if(t!=null && t.getFilename().length()>0){
                tablename=t.getTablename();
                fields = t.getFields();
            }
           

            query = "LOAD DATA LOCAL INFILE '"+absolutePath+
            "'"+
            "REPLACE\n " +
            "INTO TABLE "+
            tablename +
            " FIELDS TERMINATED BY '|' \n" +
            "LINES \n" +
            "TERMINATED BY '\\n' \n" +
            "IGNORE 1 lines\n " +
            fields;
 
            stmt.executeUpdate(query);
            
                 
        }
        catch(Exception e)
        {
            e.printStackTrace();
            stmt = null;
        }
    }
    
    public int importData(String filename, String absolutePath)
    {
        Statement stmt;
        String query;
        String tablename="";
        String fields = "";
        BusinessDelegate bd = new BusinessDelegate();
        int status = 0;
        // filename equals .txt filename imported
        if(connection==null){this.connect();}
        if(connection!=null){
            try
            {

                stmt = connection.createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

               // check if filename exists in list
                Table t = new Table();
                t = t.search(filename);
                if(t!=null && t.getFilename()!=null && t.getFilename().length()>0){
                    tablename=t.getTablename();
                    fields = t.getFields();
                }else{
                    return -1;
                }


                query = "LOAD DATA LOCAL INFILE '"+absolutePath+
                "'"+
                "REPLACE\n " +
                "INTO TABLE "+
                tablename +
                " FIELDS TERMINATED BY '|' \n" +
                "LINES \n" +
                "TERMINATED BY '\\n' \n" +
                "IGNORE 1 lines\n " +
                fields;

                status =stmt.executeUpdate(query);
                LogUpload l = new LogUpload(filename, absolutePath, fields, String.valueOf(status));
                bd.insertLogUpload(l);

            }
            catch(Exception e)
            {
                // get the error details
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                String details=sw.toString(); // stack trace as a string
                
                LogUpload l = new LogUpload(filename, absolutePath, details, String.valueOf(status));
                bd.insertLogUpload(l);
                e.printStackTrace();
                stmt = null;
            }
        }
        return status;
    }
    
    public int importDataCSV(String filename, String absolutePath)
    {
        PreparedStatement stmt;
        String query;
        String tablename="";
        String fields = "";
        String fieldsOnDuplicate="";
        BusinessDelegate bd = new BusinessDelegate();
        int status1 = 0, status2=0, status3=0, status4=0, status5=0;
        // filename equals .txt filename imported
        if(connection==null){this.connect();}
        if(connection!=null){
            try
            {

                //stmt = connection.createStatement(
                //ResultSet.TYPE_SCROLL_SENSITIVE,
                //ResultSet.CONCUR_UPDATABLE);

               // check if filename exists in list
                Table t = new Table();
                t = t.search(filename);
                if(t!=null && t.getFilename()!=null && t.getFilename().length()>0){
                    tablename=t.getTablename();
                    fields = t.getFields();
                    fieldsOnDuplicate = t.getFieldsOnDuplicate();
                }else{
                    return -1;
                }
                // 1- Create a new temporary table
                stmt = connection.prepareStatement("CREATE TEMPORARY TABLE temporary_table LIKE PJ_RETORNO");
                status1 = stmt.executeUpdate();
                this.logWarning(stmt);
                // -- 2 - Optionally, drop all indices from the temporary table to speed things up.
                //SHOW INDEX FROM temporary_table;
                //DROP INDEX `PRIMARY` ON temporary_table;
                
                // -- 3 - Load the CSV into the temporary table
                query = "LOAD DATA LOCAL INFILE '"+absolutePath+
                "'"+
                "REPLACE\n " +
                "INTO TABLE "+
                "temporary_table " +
                " FIELDS TERMINATED BY ';' \n" +
                " OPTIONALLY ENCLOSED BY '\"' ESCAPED BY '\\\\' \n"+
                "LINES \n" +
                "TERMINATED BY '\\r' \n" +
                "IGNORE 1 lines \n" +
                fields;
                
                stmt = connection.prepareStatement(query);
                status3 = stmt.executeUpdate();
                this.logWarning(stmt);
                // -- 4 - Copy the data using ON DUPLICATE KEY UPDATE
                query = 
                "INSERT INTO "+tablename+"\n" +
                "SELECT * FROM temporary_table\n" +
                "ON DUPLICATE KEY UPDATE \n"+
                 ""+fieldsOnDuplicate; 
                
                stmt = connection.prepareStatement(query);
                status4 = stmt.executeUpdate();
                this.logWarning(stmt);
                
                // -- 5 - Remove the temporary table
                query = "DROP TEMPORARY TABLE temporary_table";
                stmt = connection.prepareStatement(query);
                status5 = stmt.executeUpdate();
                this.logWarning(stmt);
                //status =stmt.executeUpdate(query,Statement.RETURN_GENERATED_KEYS);
                //stmt.getMetaData();
                //stmt.getWarnings();
                /*
                ResultSet rs = stmt.getGeneratedKeys();
                long key = -1L;
                StringBuilder s = new StringBuilder();
                if (rs != null && rs.next()) {
                    s.append("\ns"+rs.getString(1));
                    key = rs.getLong(1);
                    s.append("key"+key);
                }
                */
                

                
                
                LogUpload l = new LogUpload(filename, absolutePath, strError, String.valueOf(status3));
                bd.insertLogUpload(l);

            }
            catch(Exception e)
            {
                // get the error details
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                String details=sw.toString(); // stack trace as a string
                
                LogUpload l = new LogUpload(filename, absolutePath, details, String.valueOf(status3));
                bd.insertLogUpload(l);
                e.printStackTrace();
                stmt = null;
            }
        }
        return status3;
    }
    public void logWarning(PreparedStatement stmt){
        try {
            // Get warnings on PreparedStatement object
            SQLWarning warning = stmt.getWarnings();
            while (warning != null) {
                // Process statement warnings...
                String message = warning.getMessage();
                String sqlState = warning.getSQLState();
                int errorCode = warning.getErrorCode();
                strError+="\nmessage:"+message+" sqlState:"+sqlState+" errorCode:"+errorCode;
                warning = warning.getNextWarning();
            }
            strError+="\nupdateCount:"+stmt.getUpdateCount();
            strError+="\nfetchDirection:"+stmt.getFetchDirection()+"-"+stmt.getFetchSize()+"-"+stmt.getLargeMaxRows()+"-\nfieldSize:"+stmt.getMaxFieldSize()+"-"+stmt.getMaxRows()+"-"+stmt.getQueryTimeout()+"-\nResultType:"+stmt.getResultSetType()+"-\nConcorrencia:"+stmt.getResultSetConcurrency()+"-\nHoldability:"+stmt.getResultSetHoldability();
            ResultSet rs = stmt.getResultSet();
            if(rs!=null){
                while(rs.next()){
                    strError+="\nResultSet:"+stmt.getResultSet().getFetchSize();
                }
            }
            strError+="\nstmt:"+stmt.toString();
            //displayError(stmt.getWarnings());
            ParameterMetaData paramMetaData = stmt.getParameterMetaData();
            if (paramMetaData == null) {
                System.out.println("db vendor does NOT support ParameterMetaData");
                strError+="\ndb vendor does NOT support ParameterMetaData";
            } else {
                System.out.println("db vendor supports ParameterMetaData");
                // find out the number of dynamic parameters
                
                int paramCount = paramMetaData.getParameterCount();
                System.out.println("paramCount=" + paramCount);
                strError+="\nparamCount=" + paramCount;
                System.out.println("-------------------");
                strError+="-------------------";
                for (int param = 1; param <= paramCount; param++) {
                    System.out.println("param number=" + param);
                    strError+="param number=" + param;
                    String paramTypeName = paramMetaData.getParameterTypeName(param);
                    System.out.println("param SQL type name=" + paramTypeName);
                    strError+="param SQL type name=" + paramTypeName;
                    
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void displayError(SQLWarning warning) {
    while (warning != null) {
      if (warning instanceof DataTruncation) {
        displayError((DataTruncation) warning);
      } else {
        System.out.println(" Warning: " + warning.getMessage());
        strError+=warning.getMessage();
      }
      warning = warning.getNextWarning();
    }
    
  }
};
