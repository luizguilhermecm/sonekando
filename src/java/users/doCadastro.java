package users;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author snk
 * Servlet chamada no formulario de cadastro, 
 * Essa Servlet cria um objeto Users e insere os dados do formulário no objeto.
 * NewUser(_user) eh a funcao em Users que faz o meio de campo para insersão no
 * banco
 */
public class doCadastro extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        //<editor-fold defaultstate="collapsed" desc="comment">
        try {
            //</editor-fold>
            Users _user = new Users();

            if (request.getParameter("first_name").isEmpty()
                    || request.getParameter("last_name").isEmpty()
                    || request.getParameter("city").isEmpty()
                    || request.getParameter("email").isEmpty()
                    || request.getParameter("pass").isEmpty()) {
                response.sendRedirect("erroCadastro.jsp");
            }

            _user.setFirstName(request.getParameter("first_name"));
            _user.setLastName(request.getParameter("last_name"));
            _user.setSex(request.getParameter("sex"));
            _user.setCity(request.getParameter("city"));
            _user.setEmail(request.getParameter("email"));
            _user.setPass(request.getParameter("pass"));


            if (_user.NewUser(_user)) {
                UserDao _login = new UserDao();
                boolean existe;
                existe = _login.LoginDao(_user);

                if (existe) {
                    HttpSession session = request.getSession();
                    session.setAttribute("user_id", _login.getIdDao(_user.getEmail()));
                    session.setAttribute("login", true);
                    response.sendRedirect("profile.jsp");
                } else {
                    HttpSession session = request.getSession();
                    session.setAttribute("login", false);
                    response.sendRedirect("erroLogin.jsp");
                }
            }
            else {
                out.println("<h2>O Servidor estava tirando uma soneka!</h2>");
                out.println("<a href=erroCadastro.jsp> Tente Novamente </a>");
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(doCadastro.class.getName()).log(Level.SEVERE, null, ex);
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
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(doCadastro.class.getName()).log(Level.SEVERE, null, ex);
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
