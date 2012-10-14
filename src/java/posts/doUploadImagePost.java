/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package posts;

import images.ImageDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author snk
 */
public class doUploadImagePost extends HttpServlet {

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
            /* TODO output your page here
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet doUploadImagePost</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet doUploadImagePost at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
             */
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
        
        PrintWriter out = response.getWriter();
            
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(4096);

        ServletFileUpload upload = new ServletFileUpload(factory);
        try
        {
            HttpSession session = request.getSession();
            PostDao _postDao = new PostDao();
            
            int user_id = Integer.parseInt(session.getAttribute("user_id").toString());
            int post_id = Integer.parseInt(request.getParameter("post_id_image"));
            List fileItems = upload.parseRequest(request);
            Iterator it = fileItems.iterator();
            FileItem fi = (FileItem)it.next();
            byte[] imageUpload = fi.get();
            
            _postDao.UploadImage(user_id, post_id, imageUpload);
            request.getRequestDispatcher("profile.jsp").forward(request, response);
        }
        catch (Exception ex)
        {

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