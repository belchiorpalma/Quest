package br.com.itfox.servlet;

import br.com.itfox.beans.LogFiles;
import br.com.itfox.beans.LogUpload;
import br.com.itfox.business.BusinessDelegate;
import br.com.itfox.business.DBase;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.DefaultFileItemFactory;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.imgscalr.Scalr;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;

public class UploadServlet extends HttpServlet {
        
    /**
        * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
        * 
        */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        
        if (request.getParameter("getfile") != null && !request.getParameter("getfile").isEmpty()) {
            File file = new File(request.getServletContext().getRealPath("/")+"imgs/"+request.getParameter("getfile"));
            if (file.exists()) {
                int bytes = 0;
                ServletOutputStream op = response.getOutputStream();

                response.setContentType(getMimeType(file));
                response.setContentLength((int) file.length());
                response.setHeader( "Content-Disposition", "inline; filename=\"" + file.getName() + "\"" );

                byte[] bbuf = new byte[1024];
                DataInputStream in = new DataInputStream(new FileInputStream(file));

                while ((in != null) && ((bytes = in.read(bbuf)) != -1)) {
                    op.write(bbuf, 0, bytes);
                }

                in.close();
                op.flush();
                op.close();
            }
        } else if (request.getParameter("delfile") != null && !request.getParameter("delfile").isEmpty()) {
            File file = new File(request.getServletContext().getRealPath("/")+"imgs/"+ request.getParameter("delfile"));
            if (file.exists()) {
                file.delete(); // TODO:check and report success
            } 
            BusinessDelegate bd = new BusinessDelegate();
            try{
                LogFiles logFiles = new LogFiles();
                logFiles.setName(request.getParameter("delfile"));
                bd.deleteLogFiles(logFiles);
            }catch(Exception e){
                e.printStackTrace();
            }
        }else if (request.getParameter("getlog") != null && !request.getParameter("getlog").isEmpty()) {
            String path = (request.getServletContext().getRealPath("/")+"imgs/"+ request.getParameter("getlog"));
            
            BusinessDelegate bd = new BusinessDelegate();
            PrintWriter writer = response.getWriter();
             response.setContentType("application/json");
            JSONArray json = new JSONArray();
            try{
                LogUpload logUpload = new LogUpload();
                logUpload.setPath(path);
                logUpload = bd.selectLogUpload(logUpload);
                JSONObject jsono = new JSONObject();
                jsono.put("LOG",path);
                jsono.put("LOGUPLOAD_ID", logUpload.getLoguploadId());
                jsono.put("FILENAME",logUpload.getFilename());
                jsono.put("PATH",logUpload.getPath());
                jsono.put("DESCRIPTION",logUpload.getDescription());
                jsono.put("STATUS",logUpload.getStatus());
                jsono.put("DATE", logUpload.getDate());
                json.put(jsono);
            }catch(Exception e){
                e.printStackTrace();
            }finally {
            writer.write(json.toString());
            writer.close();
            }
        }
         else if (request.getParameter("getthumb") != null && !request.getParameter("getthumb").isEmpty()) {
            File file = new File(request.getServletContext().getRealPath("/")+"imgs/"+request.getParameter("getthumb"));
                if (file.exists()) {
                    System.out.println(file.getAbsolutePath());
                    String mimetype = getMimeType(file);
                    if (mimetype.endsWith("png") || mimetype.endsWith("jpeg")|| mimetype.endsWith("jpg") || mimetype.endsWith("gif")) {
                        BufferedImage im = ImageIO.read(file);
                        if (im != null) {
                            BufferedImage thumb = Scalr.resize(im, 75); 
                            ByteArrayOutputStream os = new ByteArrayOutputStream();
                            if (mimetype.endsWith("png")) {
                                ImageIO.write(thumb, "PNG" , os);
                                response.setContentType("image/png");
                            } else if (mimetype.endsWith("jpeg")) {
                                ImageIO.write(thumb, "jpg" , os);
                                response.setContentType("image/jpeg");
                            } else if (mimetype.endsWith("jpg")) {
                                ImageIO.write(thumb, "jpg" , os);
                                response.setContentType("image/jpeg");
                            } else {
                                ImageIO.write(thumb, "GIF" , os);
                                response.setContentType("image/gif");
                            }
                            ServletOutputStream srvos = response.getOutputStream();
                            response.setContentLength(os.size());
                            response.setHeader( "Content-Disposition", "inline; filename=\"" + file.getName() + "\"" );
                            os.writeTo(srvos);
                            srvos.flush();
                            srvos.close();
                        }
                    }
            } // TODO: check and report success
        }else if (request.getParameter("list") != null && !request.getParameter("list").isEmpty() && request.getParameter("keyword") == null) {
            //List<FileItem> items = this.listFilesForFolder(request.getServletContext().getRealPath("/")+"imgs/");
            //this.printFiles(request, response, items);
            this.listFilesDatabase(request, response);
        }else if (request.getParameter("list") != null && !request.getParameter("list").isEmpty() && request.getParameter("keyword") != null && !request.getParameter("keyword").isEmpty()) {
            List<FileItem> items = this.listFilesForFolder(request.getServletContext().getRealPath("/")+"imgs/");
            this.printFilesSearch(request, response, items,request.getParameter("keyword"));
        }  
        else {
            PrintWriter writer = response.getWriter();
            writer.write("call POST with multipart form data: "+ request.getServletContext().getRealPath("/"));
        }
    }
    
    /**
        * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
        * 
        */
    @SuppressWarnings("unchecked")
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!ServletFileUpload.isMultipartContent(request)) {
            throw new IllegalArgumentException("Request is not multipart, please 'multipart/form-data' enctype for your form.");
        }

        ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());
        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        JSONArray json = new JSONArray();
        BusinessDelegate bd = new BusinessDelegate();
        try {
            List<FileItem> items = uploadHandler.parseRequest(request);
            for (FileItem item : items) {
                if (!item.isFormField()) {
                        DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");
                        Date date = new Date();
                        String strData = dateFormat.format(date);
                        String fileName = item.getName();
                        String suffix = getSuffix(item.getName());
                        String strFileName="";
                        String strIcon="";
                        String absolutePath="";
                        int status=-2;
                        if(fileName.contains(".")) fileName=fileName.substring(0, fileName.lastIndexOf('.')); // removendo a extensão
                        strFileName = fileName+"_"+strData+"."+suffix;
                        strIcon =this.getIcon(strFileName);
                        
                        absolutePath = request.getServletContext().getRealPath("/")+"imgs/"+strFileName;
                        File file = new File(request.getServletContext().getRealPath("/")+"imgs/", strFileName);
                        item.write(file);
                        
                        // check if file is database file
                        if(suffix.equalsIgnoreCase("txt")){
                            DBase db = new DBase(true);
                            status=db.importData(fileName,absolutePath);
                        }else if(suffix.equalsIgnoreCase("csv")){
                            DBase db = new DBase(true);
                            status=db.importDataCSV(fileName,absolutePath);
                        }
                        
                        JSONObject jsono = new JSONObject();
                        jsono.put("name", strFileName);
                        jsono.put("icon", strIcon);
                        jsono.put("size", item.getSize());
                        jsono.put("status", status);
                        jsono.put("statusIcon", this.getStatus(status));
                        jsono.put("url", "UploadServlet?getfile=" + strFileName);
                        jsono.put("thumbnail_url", "UploadServlet?getthumb=" + strFileName);
                        jsono.put("delete_url", "UploadServlet?delfile=" + strFileName);
                        jsono.put("delete_type", "GET");
                        json.put(jsono);
                        System.out.println(json.toString());
                        
                        // importing files inside database
                        // save on database characteristics of file
                        
                        try{
                            LogFiles logFiles = new LogFiles(strFileName, strIcon, String.valueOf(item.getSize()), String.valueOf(status), 
                                                    this.getStatus(status),"UploadServlet?getfile=" + strFileName, "UploadServlet?getthumb=" + strFileName, 
                                                     "UploadServlet?delfile=" + strFileName, "GET", jsono.toString());
                                                    
                            bd.insertLogFiles(logFiles);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                }
            }
        } catch (FileUploadException e) {
                throw new RuntimeException(e);
        } catch (Exception e) {
                throw new RuntimeException(e);
        } finally {
            writer.write(json.toString());
            writer.close();
        }

    }

    private String getMimeType(File file) {
        String mimetype = "";
        if (file.exists()) {
            if (getSuffix(file.getName()).equalsIgnoreCase("png")) {
                mimetype = "image/png";
            }else if(getSuffix(file.getName()).equalsIgnoreCase("jpg")){
                mimetype = "image/jpg";
            }else if(getSuffix(file.getName()).equalsIgnoreCase("jpeg")){
                mimetype = "image/jpeg";
            }else if(getSuffix(file.getName()).equalsIgnoreCase("gif")){
                mimetype = "image/gif";
            }else {
                javax.activation.MimetypesFileTypeMap mtMap = new javax.activation.MimetypesFileTypeMap();
                mimetype  = mtMap.getContentType(file);
            }
        }
        return mimetype;
    }



    private String getSuffix(String filename) {
        String suffix = "";
        int pos = filename.lastIndexOf('.');
        if (pos > 0 && pos < filename.length() - 1) {
            suffix = filename.substring(pos + 1);
        }
        return suffix;
    }
    
    public void listFilesDatabase(HttpServletRequest request,HttpServletResponse response){
        PrintWriter writer = null;
        JSONArray json = new JSONArray();
        String aux="";
        try {
            writer = response.getWriter();
            response.setContentType("application/json");
            
        
            try{
                BusinessDelegate bd = new BusinessDelegate();
                List<LogFiles> listLF = bd.selectLogFiles();
                
                for(LogFiles l: listLF){
                    JSONObject jsono = new JSONObject(l.getJson());
                   // jsono.getJSONObject(l.getJson());
                    //aux=l.getJson();
                    //String j = l.getJson().replaceAll("\\\\", "");
                  // jsono.get(j);
                    json.put(jsono);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch (Exception e) {
                writer.write(e.getLocalizedMessage());
                throw new RuntimeException(e);
            } finally {
               // writer.write(aux);
                writer.write(json.toString());
                writer.close();
            }
    }
    
    public List<FileItem> listFilesForFolder(String path){
        // Java8 listFiles
        List<FileItem> listFiles = new ArrayList<FileItem>();
        try {
            Files.walk(Paths.get(path)).forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    System.out.println(filePath);
                    if(!filePath.getFileName().toFile().canExecute()){
                    FileItem item = this.createFileItem(filePath.getFileName().toString(), this.getMimeType(filePath.toFile()), true, filePath.getFileName().toString());
                    listFiles.add(item);
                    }
                }
            });
           
        } catch (IOException ex) {
            Logger.getLogger(UploadServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
         return listFiles;
    }
    public void printFiles(HttpServletRequest request,HttpServletResponse response, List<FileItem> items ){
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            response.setContentType("application/json");
            JSONArray json = new JSONArray();
            try {
                //List<FileItem> items = uploadHandler.parseRequest(request);
                for (FileItem item : items) {
                    
                //if (!item.isFormField()) {
                    
                        //File file = new File(request.getServletContext().getRealPath("/")+"imgs/", item.getName());
                        //item.write(file);
                     if(item.isFormField()){  
                        JSONObject jsono = new JSONObject();
                        
                        if(!item.getName().contains("icon")){ // exibe apenas se não for icone
                        
                        jsono.put("name", item.getName());
                        jsono.put("icon", this.getIcon(item.getName()));
                        jsono.put("size", 0);
                        jsono.put("url", "UploadServlet?getfile=" + item.getName());
                        jsono.put("thumbnail_url", "UploadServlet?getthumb=" + item.getName());
                        jsono.put("delete_url", "UploadServlet?delfile=" + item.getName());
                        jsono.put("delete_type", "GET");
                        json.put(jsono);
                        }
                        //System.out.println(json.toString());
                        //writer.write(json.toString());
                     }
               // }
                }
            } catch (Exception e) {
                writer.write(e.getLocalizedMessage());
                throw new RuntimeException(e);
            } finally {
                writer.write(json.toString());
                writer.close();
            }
        } catch (IOException ex) {
            writer.write(ex.getLocalizedMessage());
            Logger.getLogger(UploadServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            writer.close();
        }
    }
     public void printFilesSearch(HttpServletRequest request,HttpServletResponse response, List<FileItem> items , String keyword){
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            response.setContentType("application/json");
            JSONArray json = new JSONArray();
            try {
                //List<FileItem> items = uploadHandler.parseRequest(request);
                for (FileItem item : items) {
                    
                //if (!item.isFormField()) {
                    
                        //File file = new File(request.getServletContext().getRealPath("/")+"imgs/", item.getName());
                        //item.write(file);
                     if(item.isFormField()){  
                        JSONObject jsono = new JSONObject();
                        
                        if(!item.getName().contains("icon")){ // exibe apenas se não for icone
                            
                            if(item.getName().toLowerCase().contains(keyword.toLowerCase())){
                            jsono.put("name", item.getName());
                            jsono.put("icon", this.getIcon(item.getName()));
                            jsono.put("size", 0);
                            jsono.put("url", "UploadServlet?getfile=" + item.getName());
                            jsono.put("thumbnail_url", "UploadServlet?getthumb=" + item.getName());
                            jsono.put("delete_url", "UploadServlet?delfile=" + item.getName());
                            jsono.put("delete_type", "GET");
                            json.put(jsono);
                            }
                            
                        }
                        //System.out.println(json.toString());
                        //writer.write(json.toString());
                     }
               // }
                }
            } catch (Exception e) {
                writer.write(e.getLocalizedMessage());
                throw new RuntimeException(e);
            } finally {
                writer.write(json.toString());
                writer.close();
            }
        } catch (IOException ex) {
            writer.write(ex.getLocalizedMessage());
            Logger.getLogger(UploadServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            writer.close();
        }
    }
     /**
     * Very low threshold for testing memory versus disk options.
     */
    private static final int threshold = 16;
    private static final String fileContentType = "application/octet-stream";
    protected FileItemFactory createFactory(File repository)
    {
        return new DefaultFileItemFactory(threshold, repository);
    }
    
    public FileItem createFileItem(String fileFieldName,String fileContentType, boolean isFormField, String fileName){
        FileItemFactory factory = createFactory(null);
        FileItem item = factory.createItem(
                fileFieldName,
                fileContentType,
                isFormField,
                fileName
        );
        return item;
    }
    
    public String getIcon(String fileName){
        String suffix = getSuffix(fileName);
        String strIcon="";
        if(suffix.equalsIgnoreCase("txt")){
            strIcon="iconTxt.png";
        }else if(suffix.equalsIgnoreCase("xls")){
            strIcon="iconXls.png";
        }else if(suffix.equalsIgnoreCase("xlsx")){
            strIcon="iconXls.png";
        }else if(suffix.equalsIgnoreCase("csv")){
            strIcon="iconXls.png";
        }
        else if(suffix.equalsIgnoreCase("doc")){
            strIcon="iconDoc.png";
        }else if(suffix.equalsIgnoreCase("docx")){
            strIcon="iconDoc.png";
        }
        else if(suffix.equalsIgnoreCase("pdf")){
            strIcon="iconPdf.png";
        }
        else{
            strIcon=fileName;
        }
        return strIcon;
    }
    
    public String getStatus(int status){
        String strIcon="";
        switch(status){
            case 0: 
                strIcon="iconError.png";
                break;
            case -1:
                strIcon="iconInvalid.png";
                break;
            case -2:
                strIcon="iconNull.png";
                break;
            default:
                strIcon="iconOK.png";
                break;
        }
        return strIcon;
    }
    
}