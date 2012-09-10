
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package images;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;


/**
 *
 * @author snk
 */
public class doUploadProfileImage extends HttpServlet {

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
        try {
      
            
        } finally {            
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
            Logger.getLogger(doDownloadProfileImage.class.getName()).log(Level.SEVERE, null, ex);
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
        
        PrintWriter out = response.getWriter();
            
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(4096);

        //pasta que vai salvar os arquivos temporarios
        //factory.setRepository(new File("D:/"));
        
        
        
        int test2e = request.getContentLength();
        ServletFileUpload upload = new ServletFileUpload(factory);
        try
        {
            int usuario;
            HttpSession session = request.getSession();
            ImageDao imagemDAO = new ImageDao();

            usuario = Integer.parseInt(session.getAttribute("user_id").toString());
            
            List fileItems = upload.parseRequest(request);
            Iterator i = fileItems.iterator();
            FileItem fi = (FileItem)i.next();
            byte[] imagem_to_upload = fi.get();
            
            imagemDAO.setImageUserProfile(usuario, imagem_to_upload);
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
