/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package estatistica;

import database.Factory;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

/**
 *
 * @author snk
 */
public class estatisticaDao {
    
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
    
    public ResultSet getPosts (Date data) throws SQLException{
        Conectar();
        ResultSet rs = null;
        query = "SELECT * FROM posts WHERE post_data > ?;";
        pstm = conn.prepareStatement(query);
        pstm.setDate(1, data);
        rs = pstm.executeQuery();
        Desconectar();
        return rs;    
    }

    public ResultSet morePerGroup(Date inicio, Date fim, int _gid) throws SQLException {
        Conectar();
        ResultSet rs = null;
        query = "SELECT j.uid, j.ufname, j.ulname, j.gid, j.gname, Count(*) AS total "
                + "FROM   posts p "
                + "JOIN (SELECT * "
                + "FROM   users u "
                + "JOIN (SELECT * "
                + "FROM   friends f "
                + "JOIN (SELECT * "
                + "FROM   friend_group f "
                + "JOIN groups g "
                + "ON f.group_id = gid "
                + "AND gid = ?) j "
                + "ON f.fid = j.friendship_id) j "
                + "ON uid = ffriend_id) j "
                + "ON p.post_user_id = j.uid "
                + "AND post_data > ? "
                + "AND post_data < ? "
                + "GROUP  BY j.uid, j.ufname, j.gid, j.gname, j.ulname "
                + "ORDER  BY total DESC "
                + "LIMIT  3 ;";
        pstm = conn.prepareStatement(query);
        pstm.setInt(1,_gid);
        pstm.setDate(2, inicio);
        pstm.setDate(3, fim);
        rs = pstm.executeQuery();
        Desconectar();
        return rs;    

    }
    
    public ResultSet moreInfluent(Date inicio, Date fim, int _user_id) throws SQLException{
        Conectar();
        ResultSet rs = null;
        query = "SELECT post_user_id, SUM(post_sigma_comment*5 + post_sigma_like) AS total "
                + "FROM posts "
                + "WHERE post_data > ? AND post_data < ? AND post_user_id IN ( "
                + "SELECT ffriend_id FROM friends WHERE fuser_id = ?) "
                + "group by post_user_id "
                + "ORDER BY total DESC "
                + "LIMIT 10;";
        pstm = conn.prepareStatement(query);
        pstm.setDate(1, inicio);
        pstm.setDate(2, fim);
        pstm.setInt(3, _user_id);
        rs = pstm.executeQuery();
        Desconectar();
        return rs;    

    }
    
    public ResultSet moreInfluentExcept(Date inicio, Date fim, int _user_id) throws SQLException{
        Conectar();
        ResultSet rs = null;
        query = "SELECT post_user_id, SUM(post_sigma_comment*5 + post_sigma_like) AS total "
                + "FROM posts "
                + "WHERE post_data > ? AND post_data < ? AND post_user_id IN ( "
                + "SELECT uid FROM users "
                + "EXCEPT "
                + "SELECT ffriend_id FROM friends WHERE fuser_id = ?) "
                + "group by post_user_id "
                + "ORDER BY total DESC "
                + "LIMIT 10;";
        pstm = conn.prepareStatement(query);
        pstm.setDate(1, inicio);
        pstm.setDate(2, fim);
        pstm.setInt(3, _user_id);
        rs = pstm.executeQuery();
        Desconectar();
        return rs;    

    }
    
}
