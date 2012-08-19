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
 * Servlet chamada no formulario de doLogin,
 * Logica meio deturpada ainda nessa Servlet mas funcionando,
 * Cria um objeto User e insere os valores do formulario,
 * Chama a funcao LoginDao(_user) em UserDao para validar os dados,
 * Se usuario existe cria-se uma sessao para ele com seu email/nome e faz uma
 * gracinha de soneka e tal
 * Se usuario nao existe ou errou login, redireciona para pagina de erroLogin.jsp
 * e la ele se vira nos 30.
 * 
 */
public class doLogin extends HttpServlet {

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
        loop:
        try {
            
            Users _user = new Users();
            _user.setEmail(request.getParameter("email_login"));
            _user.setPass(request.getParameter("pass_login"));

            UserDao _login = new UserDao();
            boolean existe;
            existe = _login.LoginDao(_user);
            
            if(existe){
                HttpSession session = request.getSession();
                session.setAttribute("user_id", _login.getIdDao(_user.getEmail()));
                session.setAttribute("login", true);
                response.sendRedirect("profile.jsp");
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("login", false);
                response.sendRedirect("erroLogin.jsp");
            }               

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
