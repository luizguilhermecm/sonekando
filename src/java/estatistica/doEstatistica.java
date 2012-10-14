/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package estatistica;

import friends.FriendDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import users.UserDao;

/**
 *
 * @author snk
 */
public class doEstatistica extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession session = request.getSession();
            int user_id = Integer.parseInt(session.getAttribute("user_id").toString());
            
            estatisticaDao _estatisticaDao = new estatisticaDao();
            FriendDao _friendDao = new FriendDao();
            UserDao _userDao = new UserDao();
            
            String inicio;
            inicio = request.getParameter("deAno") + "/"
                    + request.getParameter("deMes") + "/"
                    + request.getParameter("deDia");
            
            String fim;
            fim = request.getParameter("ateAno") + "/"
                    + request.getParameter("ateMes") + "/"
                    + request.getParameter("ateDia");
            
            if (inicio.equals(fim) && inicio.equals("2012/1/1")){
                out.println("iguais e padrao");
                //chamar funcao que retorna intervalo de 1 semana
            }
            
            DateFormat formatar = new SimpleDateFormat("yyyy/MM/dd");
            
            
            try  
            {
                java.sql.Date start = new java.sql.Date(formatar.parse(inicio).getTime());
                java.sql.Date end = new java.sql.Date(formatar.parse(fim).getTime());
                
                ResultSet todos = _estatisticaDao.getPosts(end);
                while (todos.next()){
                    out.println(todos.getString("post_content"));
                }
                
                request.setAttribute("startDate", start);
                request.setAttribute("endDate", end);
                RequestDispatcher rd = request.getRequestDispatcher("resultadoEstatistica.jsp");
                rd.forward(request,response);


            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }           

            //response.sendRedirect("estatisticas.jsp");
        
        }finally {            
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
            } catch (ParseException ex) {
                Logger.getLogger(doEstatistica.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(doEstatistica.class.getName()).log(Level.SEVERE, null, ex);
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
            } catch (ParseException ex) {
                Logger.getLogger(doEstatistica.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(doEstatistica.class.getName()).log(Level.SEVERE, null, ex);
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
