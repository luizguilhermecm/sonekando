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

    public ResultSet getPosts(Date data) throws SQLException {
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
        pstm.setInt(1, _gid);
        pstm.setDate(2, inicio);
        pstm.setDate(3, fim);
        rs = pstm.executeQuery();
        Desconectar();
        return rs;

    }

    public ResultSet moreInfluent(Date inicio, Date fim, int _user_id) throws SQLException {
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

    public ResultSet moreInfluentExcept(Date inicio, Date fim, int _user_id) throws SQLException {
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

    public ResultSet datesBetween(Date inicio, Date fim, int _user_id) throws SQLException {
        Conectar();
        ResultSet rs = null;
        query = "SELECT DISTINCT date_trunc('day', post_data) AS datas "
                + "FROM posts WHERE post_data > ? AND post_data < ? "
                + "AND post_user_id IN( "
                + "SELECT fuser_id FROM friends WHERE ffriend_id = ?)";
        pstm = conn.prepareStatement(query);
        pstm.setDate(1, inicio);
        pstm.setDate(2, fim);
        pstm.setInt(3, _user_id);
        rs = pstm.executeQuery();
        Desconectar();
        return rs;
    }

    public ResultSet threePerDay(Date dia, int _user_id) throws SQLException {
        Conectar();
        ResultSet rs = null;
        query = "SELECT post_user_id, "
                + "       post_content, "
                + "       post_data, "
                + "       post_id, "
                + "       Sum(post_sigma_comment * 5 + post_sigma_like) AS total "
                + "FROM   (SELECT post_id, "
                + "               post_data, "
                + "               post_user_id, "
                + "               post_content, "
                + "               post_sigma_comment, "
                + "               post_sigma_like "
                + "        FROM   posts "
                + "        WHERE  Date_trunc('day', post_data) = ? "
                + "               AND post_user_id IN (SELECT fuser_id "
                + "                                    FROM   friends "
                + "                                    WHERE  ffriend_id = ?)) j "
                + "GROUP  BY post_data, "
                + "          post_user_id, "
                + "          post_id, "
                + "          post_content "
                + "ORDER  BY total DESC "
                + "LIMIT  3";
        pstm = conn.prepareStatement(query);
        pstm.setDate(1, dia);
        pstm.setInt(2, _user_id);
        rs = pstm.executeQuery();
        Desconectar();
        return rs;
    }

    public ResultSet myPosts(Date inicio, Date fim, int _user_id) throws SQLException {
        Conectar();
        ResultSet rs = null;
        query = "SELECT post_user_id, "
                + "       post_content, "
                + "       post_data, "
                + "       post_id, "
                + "       Sum(post_sigma_comment * 5 + post_sigma_like) AS total "
                + "FROM   posts "
                + "WHERE  post_user_id = ? "
                + "       AND post_data > ? "
                + "       AND post_data < ? "
                + "GROUP  BY post_data, "
                + "          post_user_id, "
                + "          post_id, "
                + "          post_content "
                + "ORDER  BY total DESC ";
        pstm = conn.prepareStatement(query);
        pstm.setDate(2, inicio);
        pstm.setDate(3, fim);
        pstm.setInt(1, _user_id);
        rs = pstm.executeQuery();
        Desconectar();
        return rs;
    }

    public ResultSet allTimeAvg(int _user_id) throws SQLException {
        Conectar();
        ResultSet rs = null;
        query = "SELECT Avg(post_sigma_comment * 5 + post_sigma_like) AS media_geral "
                + "FROM   posts "
                + "WHERE  post_user_id = ? ";
        pstm = conn.prepareStatement(query);
        pstm.setInt(1, _user_id);
        rs = pstm.executeQuery();
        Desconectar();
        return rs;
    }

    public ResultSet periodAvg(Date inicio, Date fim, int _user_id) throws SQLException {
        Conectar();
        ResultSet rs = null;
        query = "SELECT Avg(post_sigma_comment * 5 + post_sigma_like) AS media_periodo "
                + "FROM   posts "
                + "WHERE  post_user_id = ? "
                + "       AND post_data > ? "
                + "       AND post_data < ?";
        pstm = conn.prepareStatement(query);
        pstm.setDate(2, inicio);
        pstm.setDate(3, fim);
        pstm.setInt(1, _user_id);
        rs = pstm.executeQuery();
        Desconectar();
        return rs;
    }

    public ResultSet postsThatDay(Date dia, int _user_id) throws SQLException {
        Conectar();
        ResultSet rs = null;
        query = "SELECT Count (*) AS total_posts "
                + "FROM   posts "
                + "WHERE  post_user_id = ? "
                + "       AND date_trunc('day', post_data) = ? ";
        pstm = conn.prepareStatement(query);
        pstm.setInt(1, _user_id);
        pstm.setDate(2, dia);
        rs = pstm.executeQuery();
        Desconectar();
        return rs;
    }

    public ResultSet likesThatDay(Date dia, int _user_id) throws SQLException {
        Conectar();
        ResultSet rs = null;
        query = "SELECT Count (*) AS total_likes "
                + "FROM   likes "
                + "WHERE  like_user_id = ? "
                + "       AND Date_trunc('day', like_data) = ? ";
        pstm = conn.prepareStatement(query);
        pstm.setInt(1, _user_id);
        pstm.setDate(2, dia);
        rs = pstm.executeQuery();
        Desconectar();
        return rs;
    }

    public ResultSet commentsThatDay(Date dia, int _user_id) throws SQLException {
        Conectar();
        ResultSet rs = null;
        query = "SELECT Count (*) AS total_comments "
                + "FROM   posts_comments "
                + "WHERE  comment_user_id = ? "
                + "       AND Date_trunc('day', comment_data) = ? ";
        pstm = conn.prepareStatement(query);
        pstm.setInt(1, _user_id);
        pstm.setDate(2, dia);
        rs = pstm.executeQuery();
        Desconectar();
        return rs;
    }

    public ResultSet commentsToMeThatDay(Date dia, int _user_id, int _me) throws SQLException {
        Conectar();
        ResultSet rs = null;
        query = "SELECT Count (*) AS comments_me "
                + "FROM   posts_comments "
                + "WHERE  comment_user_id = ? "
                + "       AND Date_trunc('day', comment_data) = ? "
                + "       AND comment_post_id IN (SELECT post_id "
                + "                            FROM   posts "
                + "                            WHERE  post_user_id = ?) ";
        pstm = conn.prepareStatement(query);
        pstm.setInt(1, _user_id);
        pstm.setDate(2, dia);
        pstm.setInt(3, _me);
        rs = pstm.executeQuery();
        Desconectar();
        return rs;
    }    
    
    public ResultSet likesToMeThatDay(Date dia, int _user_id, int _me) throws SQLException {
        Conectar();
        ResultSet rs = null;
        query = "SELECT Count (*) AS likes_me "
                + "FROM   likes "
                + "WHERE  like_user_id = ? "
                + "       AND Date_trunc('day', like_data) = ? "
                + "       AND like_post_id IN (SELECT post_id "
                + "                            FROM   posts "
                + "                            WHERE  post_user_id = ?) ";
        pstm = conn.prepareStatement(query);
        pstm.setInt(1, _user_id);
        pstm.setDate(2, dia);
        pstm.setInt(3, _me);
        rs = pstm.executeQuery();
        Desconectar();
        return rs;
    }

    public ResultSet datesBetweenForAll(Date inicio, Date fim, int _user_id) throws SQLException {
        Conectar();
        ResultSet rs = null;
        query = "SELECT DISTINCT Date_trunc('day', post_data) AS datas "
                + "FROM   posts "
                + "WHERE  post_user_id = ? "
                + "       AND post_data > ? "
                + "       AND post_data < ? "
                + "UNION "
                + "SELECT DISTINCT Date_trunc('day', like_data) AS datas "
                + "FROM   likes "
                + "WHERE  like_user_id = ? "
                + "       AND like_data > ? "
                + "       AND like_data < ? "
                + "UNION "
                + "SELECT DISTINCT Date_trunc('day', comment_data) AS datas "
                + "FROM   posts_comments "
                + "WHERE  comment_user_id = ? "
                + "       AND comment_data > ? "
                + "       AND comment_data < ? ";
        pstm = conn.prepareStatement(query);
        pstm.setInt(1, _user_id);
        pstm.setDate(2, inicio);
        pstm.setDate(3, fim);
        pstm.setInt(4, _user_id);
        pstm.setDate(5, inicio);
        pstm.setDate(6, fim);
        pstm.setInt(7, _user_id);
        pstm.setDate(8, inicio);
        pstm.setDate(9, fim);
        rs = pstm.executeQuery();
        Desconectar();
        return rs;
    }
    
    public ResultSet offensiveContent(Date inicio, Date fim, String palavra) throws SQLException {
        Conectar();
        ResultSet rs = null;
        query = "SELECT * FROM offensive(?,?,?);";
        pstm = conn.prepareStatement(query);
        pstm.setString(1, palavra);
        pstm.setDate(2, inicio);
        pstm.setDate(3, fim);
        rs = pstm.executeQuery();
        Desconectar();        
        return rs;
    }

    public ResultSet getAllOffensiveContent() throws SQLException {
        Conectar();
        ResultSet rs = null;
        query = "SELECT * FROM log;";
        pstm = conn.prepareStatement(query);
        rs = pstm.executeQuery();
        Desconectar();        
        return rs;
    }    
    
    public  java.sql.Date Now() throws SQLException {
        Conectar();
        ResultSet rs = null;
        query = "SELECT (date 'now()' + integer '1') AS hoje;";

        pstm = conn.prepareStatement(query);
        rs = pstm.executeQuery();
        Desconectar();
        rs.next();
        return rs.getDate("hoje");
    }
    
    public java.sql.Date WeekLess() throws SQLException{
        Conectar();
        ResultSet rs = null;
        query = "SELECT date_trunc('day', (date 'now()' - integer '7')) AS week;";
        pstm = conn.prepareStatement(query);
        rs = pstm.executeQuery();
        Desconectar();
        rs.next();
        return rs.getDate("week");
    }
}
