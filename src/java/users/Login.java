/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package users;

import database.*;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author snk
 */
public class Login extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            
            Users _user = new Users();
            _user.setEmail(request.getParameter("emaillogin"));
            _user.setPass(request.getParameter("passlogin"));

            UserDao _login = new UserDao();
            boolean existe;
            existe = _login.LoginDao(_user);
            
            if(existe) out.println("Entrou");
            else {
                String site = "erroLogin.jsp";
                response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
                response.setHeader("Location", site);
            }
            
            HttpSession session = request.getSession();
            session.setAttribute("nome", _user.getEmail());

            String nome = (String) (session.getAttribute("nome"));
            response.getWriter().println(nome);
            out.println(session.getId());
            
            String soneka = "\"soneka.jsp\"";
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Sonekando</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Tire uma soneka " + _user.getEmail() + " enquanto terminamos aqui :)</h1><br />");
            out.println("<a href=" + soneka + ">Tirar uma soneka</a>");
            out.println("</body>");
            out.println("</html>");

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
        processRequest(request, response);
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
        processRequest(request, response);
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
