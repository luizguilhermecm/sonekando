/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package images;

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
public class ImageDao {
    
    private Connection conn;
    private PreparedStatement pstm;
    Statement stmt;
    private String query;

    private byte[] image_byte;

    
    public void Conectar() throws SQLException {
        Factory fac = new Factory();
        conn = fac.getCon();
    }

    public void Desconectar() throws SQLException {
        conn.close();
       // pstm.close();
    }

    //before add image verify if images exist, if exist go to method ChangeImage...
    //If don't exist keep in this method.
    public void setImageUserProfile(int _uid, byte[] _image) throws SQLException {
        if (getImageUserProfile(_uid) == null) {
            Conectar();
            query = "INSERT INTO profile_image (piuser_id, piimage) VALUES (?, ?);";
            pstm = conn.prepareStatement(query);
            pstm.setInt(1, _uid);
            pstm.setBytes(2, _image);
            int executeUpdate = pstm.executeUpdate();
            Desconectar();
        }
        else ChangeImageUserProfile(_uid, _image);
    }
    
    public void ChangeImageUserProfile(int _uid, byte[] _image) throws SQLException{
        Conectar();
        query = "UPDATE profile_image SET piimage=? WHERE piuser_id=?;";
        pstm = conn.prepareStatement(query);
        pstm.setBytes(1, _image);
        pstm.setInt(2, _uid);
        int executeUpdate = pstm.executeUpdate();
        Desconectar();
    }
    
    public byte[] getImageUserProfile(int _uid) throws SQLException{
        Conectar();
        query = "SELECT piimage FROM profile_image WHERE piuser_id=?;";
        pstm = conn.prepareStatement(query);
        pstm.setInt(1, _uid);
        
        ResultSet rs = pstm.executeQuery();
        Desconectar();
        
        if (rs.next())
            return rs.getBytes("piimage");
        
        else return null;
    }
}