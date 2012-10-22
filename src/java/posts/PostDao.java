/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package posts;

import database.Factory;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author snk
 */
public class PostDao {
    
    private Connection conn;
    private PreparedStatement pstm;
    Statement stmt;
    private String query;

    public void Conectar() throws SQLException {
        Factory fac = new Factory();
        conn = fac.getCon();
    }

    public void Desconectar() throws SQLException {
        conn.close();
       // pstm.close();
    }
    
    public int NewPost(int _uid, String _content) throws SQLException {
        Conectar();
        ResultSet rs = null;
        query = "INSERT INTO posts (post_user_id, post_content) VALUES (?,?) RETURNING post_id;";
        pstm = conn.prepareStatement(query);
        pstm.setInt(1, _uid);
        pstm.setString(2, _content);
        rs = pstm.executeQuery();
        Desconectar();
        if (rs.next()){
            return rs.getInt("post_id");
        }
        else 
            return 0;
    }

    
    public void EditPost(int _post_id, String _edited_content) throws SQLException {
        Conectar();
        query = "UPDATE posts SET post_content=? WHERE post_id=?;";
        pstm = conn.prepareStatement(query);
        pstm.setString(1, _edited_content);
        pstm.setInt(2, _post_id);
        int executeUpdate = pstm.executeUpdate();
        Desconectar();
    }
    
    public void DeletePost(int _post_id) throws SQLException {
        Conectar();
        query = "DELETE FROM posts WHERE post_id=?;";
        pstm = conn.prepareStatement(query);
        pstm.setInt(1, _post_id);
        int executeUpdate = pstm.executeUpdate();
        Desconectar();
    }
    
    
    public void LikePost(int _user_id, int _post_id) throws SQLException {
        Conectar();
        query = "INSERT INTO likes(like_user_id, like_post_id) VALUES (?,?);";
        pstm = conn.prepareStatement(query);
        pstm.setInt(1, _user_id);
        pstm.setInt(2, _post_id);
        int executeUpdate = pstm.executeUpdate();
        pstm.close();
        if(executeUpdate == 1){
        query = "UPDATE posts SET post_sigma_like=post_sigma_like+1 WHERE post_id=?;";
        pstm = conn.prepareStatement(query);
        pstm.setInt(1, _post_id);
        executeUpdate = pstm.executeUpdate();
        }
        Desconectar();
    }
    
    public ResultSet SelectPosts(int _uid) throws SQLException {
        Conectar();
        ResultSet rs = null;
        query = "SELECT * FROM posts WHERE post_user_id = ? OR post_user_id IN (" 
                    + "SELECT fuser_id FROM friends WHERE ffriend_id = ?"
                    + ")"
                + " ORDER BY post_data DESC;";
        pstm = conn.prepareStatement(query);
        pstm.setInt(1, _uid);
        pstm.setInt(2, _uid);
        rs = pstm.executeQuery();
        Desconectar();
        //if (rs.next()) return rs;
        return rs;        
    }

    public ResultSet SelectPostsPublicProfile(int _uid) throws SQLException {
        Conectar();
        ResultSet rs = null;
        query = "SELECT * FROM posts WHERE post_user_id = ?"
                + " ORDER BY post_data DESC;";
        pstm = conn.prepareStatement(query);
        pstm.setInt(1, _uid);
        rs = pstm.executeQuery();
        Desconectar();
        //if (rs.next()) return rs;
        return rs;        
    }
    
    
    public void NewComment(int _uid, int _post_id, String _content) throws SQLException {
        Conectar();
        query = "INSERT INTO posts_comments (comment_user_id, comment_post_id, comment_content) VALUES (?,?,?);";
        pstm = conn.prepareStatement(query);
        pstm.setInt(1, _uid);
        pstm.setInt(2, _post_id);
        pstm.setString(3, _content);
        int executeUpdate = pstm.executeUpdate();
        pstm.close();
        if (executeUpdate == 1){
        query = "UPDATE posts SET post_sigma_comment=post_sigma_comment+1 WHERE post_id=?;";
        pstm = conn.prepareStatement(query);
        pstm.setInt(1, _post_id);
        executeUpdate = pstm.executeUpdate();
        }
        Desconectar();

    }    
    
    public ResultSet SelectComments(int _post_id) throws SQLException {
        Conectar();
        ResultSet rs = null;
        query = "SELECT * FROM posts_comments WHERE comment_post_id=? ORDER BY comment_data;";
        pstm = conn.prepareStatement(query);
        pstm.setInt(1, _post_id);
        rs = pstm.executeQuery();
        Desconectar();
        //if (rs.next()) return rs;
        return rs;
        
    }
    
    public void UploadImage(int _user_id, int _post_id, byte[] _image, int size) throws SQLException {
            Conectar();
            query = "INSERT INTO image_post (image_user_id, image_post_id, image_image, size) VALUES (?, ?, ?, ?);";
            pstm = conn.prepareStatement(query);
            pstm.setInt(1, _user_id);
            pstm.setInt(2, _post_id);
            pstm.setBytes(3, _image);
            pstm.setInt(4, size);
            int executeUpdate = pstm.executeUpdate();
            Desconectar();
    }
    
    public ResultSet listImages(int _post_id) throws SQLException {
            Conectar();
            ResultSet rs = null;
            query = "SELECT image_id FROM image_post WHERE image_post_id = ?;";
            pstm = conn.prepareStatement(query);
            pstm.setInt(1, _post_id);
            rs = pstm.executeQuery();
            Desconectar();
            return rs;
    }    
    
    public byte[] DownloadImage(int _image_id) throws SQLException {
            Conectar();
            ResultSet rs = null;
            query = "SELECT image_image FROM image_post WHERE image_id=?;";
            pstm = conn.prepareStatement(query);
            pstm.setInt(1, _image_id);
            rs = pstm.executeQuery();
            Desconectar();
            if (rs.next()){
                return rs.getBytes("image_image");
            }
            else 
                return null;
    }

    
}
