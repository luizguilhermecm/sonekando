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
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
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
        factory.setSizeThreshold(40969);

        ServletFileUpload upload = new ServletFileUpload(factory);
        
        try
        {
            HttpSession session = request.getSession();
            ImageDao _image = new ImageDao();
            PostDao _postDao = new PostDao();
            int user_id = Integer.parseInt(session.getAttribute("user_id").toString());
            String content = " ";
            byte[] imageUpload = null;
            int size = 0;
            int post_id = 0;
            List items = upload.parseRequest(request);
            Iterator iter = items.iterator();
            while (iter.hasNext()){
                FileItem item = (FileItem) iter.next();
                
                if (item.isFormField()){
                        content = item.getString();
                        post_id = _postDao.NewPost(user_id, content);

                } 
                if (!item.isFormField()) {
                    size = (int) item.getSize();
                    if (size > 0) {
                        imageUpload = item.get();
                        _postDao.UploadImage(user_id, post_id, imageUpload, size);

                    }
                }
            }
            response.sendRedirect("profile.jsp");
            
            
        } catch (Exception ex) {
            out.println(ex.getMessage());
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
