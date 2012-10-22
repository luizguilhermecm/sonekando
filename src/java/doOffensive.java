/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import estatistica.estatisticaDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author snk
 */
public class doOffensive extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession session = request.getSession();
            int user_id = Integer.parseInt(session.getAttribute("user_id").toString());
            
            estatisticaDao _estatisticaDao = new estatisticaDao();
            
            String offensiveWord = request.getParameter("offensiveWord");
            
            String inicio;
            inicio = request.getParameter("deAno") + "/"
                    + request.getParameter("deMes") + "/"
                    + request.getParameter("deDia");
            
            String fim;
            fim = request.getParameter("ateAno") + "/"
                    + request.getParameter("ateMes") + "/"
                    + request.getParameter("ateDia");
            
            
            DateFormat formatar = new SimpleDateFormat("yyyy/MM/dd");
            
            
            try  
            {
                java.sql.Date start = new java.sql.Date(formatar.parse(inicio).getTime());
                java.sql.Date end = new java.sql.Date(formatar.parse(fim).getTime());
                
                if (inicio.equals(fim) && inicio.equals("2012/1/1")){
                    try {
                    start = _estatisticaDao.WeekLess();
                    end = _estatisticaDao.Now();
                    } catch (Exception e){
                        out.println(e.getMessage());
                    }
                }
      
                try{
                ResultSet _offensive = _estatisticaDao.offensiveContent(start, end, offensiveWord);
                
                request.setAttribute("ResultSet", _offensive);
                RequestDispatcher rd = request.getRequestDispatcher("offensiveContent.jsp");
                rd.forward(request,response);

               
                }catch (Exception e){
                    out.println(e.getMessage());
                }
                
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }           

            //response.sendRedirect("estatisticas.jsp");
        
        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            try {
                processRequest(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(doOffensive.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ParseException ex) {
            Logger.getLogger(doOffensive.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            try {
                processRequest(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(doOffensive.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ParseException ex) {
            Logger.getLogger(doOffensive.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
