/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.itfox.servlet;

import br.com.itfox.beans.CollectionColumns;
import br.com.itfox.beans.Colors;
import br.com.itfox.business.BusinessDelegate;
import br.com.itfox.utils.Utils;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author belchiorpalma
 */
@WebServlet(name = "DashboardServlet", urlPatterns = {"/DashboardServlet"})
public class DashboardServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        BusinessDelegate bd = new BusinessDelegate();
        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        JSONArray json = new JSONArray();
        String ini = "";
                   ini = request.getParameter("ini");
        String fim="";
                   fim = request.getParameter("fim");
        String seg="";
                    seg = request.getParameter("seg");
        int count=0;           
        if (request.getParameter("countChassi") != null && !request.getParameter("countChassi").isEmpty()) {
            
            if(ini != null && !ini.isEmpty() && fim!=null & !fim.isEmpty() && seg !=null && !seg.isEmpty()){
                 try {
                     ini = (Utils.dateFormat(ini));
                     fim = (Utils.dateFormat(fim));
                     count = bd.selectCountChassi(ini, fim, seg);
                     JSONObject jsono = new JSONObject();
                     jsono.put("countChassi", count);
                     jsono.put("ini", Utils.dateFormatPt(ini));
                     jsono.put("fim", Utils.dateFormatPt(fim));
                     json.put(jsono);
                     
                 }catch(JSONException ex){
                     Logger.getLogger(DashboardServlet.class.getName()).log(Level.SEVERE, null, ex);
                 }finally {
                    writer.write(json.toString());
                    writer.close();
                }
            }
        }else if (request.getParameter("distSeg") != null && !request.getParameter("distSeg").isEmpty()) {
             if(ini != null && !ini.isEmpty() && fim!=null & !fim.isEmpty() && seg !=null && !seg.isEmpty()){
                 try {
                     ini = (Utils.dateFormat(ini));
                     fim = (Utils.dateFormat(fim));
                     List<CollectionColumns> listColumns= bd.selectDistribuicaoSeg(ini, fim, seg);
                     int i=0;
                     for(CollectionColumns c: listColumns){
                        JSONObject jsono = new JSONObject();
                        jsono.put("label", c.getColumns()[2]);
                        jsono.put("data", Integer.parseInt(c.getColumns()[0]));
                        jsono.put("color", new Colors().getColors()[i]);
                      //  jsono.put("ini", Utils.dateFormatPt(ini));
                      //  jsono.put("fim", Utils.dateFormatPt(fim));
                        json.put(jsono);
                        i++;
                     }
                     
                 }catch(JSONException ex){
                     Logger.getLogger(DashboardServlet.class.getName()).log(Level.SEVERE, null, ex);
                 }finally {
                    writer.write(json.toString());
                    writer.close();
                }
             }
        }else if (request.getParameter("distMarca") != null && !request.getParameter("distMarca").isEmpty()) {
             if(ini != null && !ini.isEmpty() && fim!=null & !fim.isEmpty() && seg !=null && !seg.isEmpty()){
                 try {
                     ini = (Utils.dateFormat(ini));
                     fim = (Utils.dateFormat(fim));
                     List<CollectionColumns> listColumns= bd.selectDistribuicaoMarca(ini, fim, seg);
                     int i=0;
                     for(CollectionColumns c: listColumns){
                        JSONObject jsono = new JSONObject();
                        jsono.put("label", c.getColumns()[1]);
                        jsono.put("data", Integer.parseInt(c.getColumns()[0]));
                        jsono.put("color", new Colors().getColors()[i]);
                      //  jsono.put("ini", Utils.dateFormatPt(ini));
                      //  jsono.put("fim", Utils.dateFormatPt(fim));
                        json.put(jsono);
                        i++;
                     }
                     
                 }catch(JSONException ex){
                     Logger.getLogger(DashboardServlet.class.getName()).log(Level.SEVERE, null, ex);
                 }finally {
                    writer.write(json.toString());
                    writer.close();
                }
             }
        }else if (request.getParameter("rankMunic") != null && !request.getParameter("rankMunic").isEmpty()) {
             if(ini != null && !ini.isEmpty() && fim!=null & !fim.isEmpty() && seg !=null && !seg.isEmpty()){
                 try {
                     ini = (Utils.dateFormat(ini));
                     fim = (Utils.dateFormat(fim));
                     List<CollectionColumns> listColumns= bd.selectRankMunic(ini, fim, seg);
                     int i=0;
                     for(CollectionColumns c: listColumns){
                        JSONObject jsono = new JSONObject();
                        jsono.put("label", c.getColumns()[1]);
                        jsono.put("data", Integer.parseInt(c.getColumns()[0]));
                        jsono.put("color", new Colors().getColors()[i]);
                      //  jsono.put("ini", Utils.dateFormatPt(ini));
                      //  jsono.put("fim", Utils.dateFormatPt(fim));
                        json.put(jsono);
                        i++;
                     }
                     
                 }catch(JSONException ex){
                     Logger.getLogger(DashboardServlet.class.getName()).log(Level.SEVERE, null, ex);
                 }finally {
                    writer.write(json.toString());
                    writer.close();
                }
             }
        }else if (request.getParameter("rankModel") != null && !request.getParameter("rankModel").isEmpty()) {
             if(ini != null && !ini.isEmpty() && fim!=null & !fim.isEmpty() && seg !=null && !seg.isEmpty()){
                 try {
                     ini = (Utils.dateFormat(ini));
                     fim = (Utils.dateFormat(fim));
                     List<CollectionColumns> listColumns= bd.selectRankModel(ini, fim, seg);
                     int i=0;
                     for(CollectionColumns c: listColumns){
                        JSONObject jsono = new JSONObject();
                        jsono.put("label", c.getColumns()[1]);
                        jsono.put("data", Integer.parseInt(c.getColumns()[0]));
                        jsono.put("color", new Colors().getColors()[i]);
                      //  jsono.put("ini", Utils.dateFormatPt(ini));
                      //  jsono.put("fim", Utils.dateFormatPt(fim));
                        json.put(jsono);
                        i++;
                     }
                     
                 }catch(JSONException ex){
                     Logger.getLogger(DashboardServlet.class.getName()).log(Level.SEVERE, null, ex);
                 }finally {
                    writer.write(json.toString());
                    writer.close();
                }
             }
        }else if (request.getParameter("selectSeg") != null && !request.getParameter("selectSeg").isEmpty()) {
             
                 try {
                     List<CollectionColumns> listColumns= bd.selectSegmentos();
                     int i=0;
                     for(CollectionColumns c: listColumns){
                        JSONObject jsono = new JSONObject();
                        jsono.put("segmento", c.getColumns()[0]);
                        jsono.put("descricao", c.getColumns()[1]);
                       
                        json.put(jsono);
                        i++;
                     }
                     
                 }catch(JSONException ex){
                     Logger.getLogger(DashboardServlet.class.getName()).log(Level.SEVERE, null, ex);
                 }finally {
                    writer.write(json.toString());
                    writer.close();
                }
             
        }
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DashboardServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DashboardServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
