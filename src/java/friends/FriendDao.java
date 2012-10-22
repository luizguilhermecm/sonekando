/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package friends;

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
public class FriendDao {

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

    public int getFuserIdDao(int _fid) {
        try {
            Conectar();
            query = "SELECT fuser_id FROM friends WHERE fid=?;";
            pstm = conn.prepareStatement(query);
            pstm.setInt(1, _fid);

            ResultSet rs = pstm.executeQuery();
            Desconectar();

            if (rs.next()) {
                return rs.getInt("fuser_id");
            }
            return 0;
        } catch (Exception e) {
            return 0;
        }

    }

    public ResultSet getAmigosDao(int _uid) throws Exception {
        ResultSet rs = null;
        Conectar();
        query = "SELECT fid, fuser_id, ffriend_id FROM friends WHERE fuser_id=? AND faccept = 'true';";
        pstm = conn.prepareStatement(query);
        pstm.setInt(1, _uid);
        rs = pstm.executeQuery();
        Desconectar();

        return rs;
    }

    public int getFID(int _fuser_id, int _ffriend_id) throws SQLException {
        Conectar();
        query = "SELECT fid FROM friends WHERE fuser_id=? AND ffriend_id=?;";
        pstm = conn.prepareStatement(query);
        pstm.setInt(1, _fuser_id);
        pstm.setInt(2, _ffriend_id);
        ResultSet rs = pstm.executeQuery();
        Desconectar();

        if (rs.next()) {
            return rs.getInt("fid");
        }
        return 0;
    }

    public int getFfriendIdDao(int _fid) {
        try {
            Conectar();
            query = "SELECT ffriend_id FROM friends WHERE fid=?;";
            pstm = conn.prepareStatement(query);
            pstm.setInt(1, _fid);

            ResultSet rs = pstm.executeQuery();
            Desconectar();

            if (rs.next()) {
                return rs.getInt("ffriend_id");
            }
            return 0;
        } catch (Exception e) {
            return 0;
        }

    }

    public void AceitarAmigoDao(int _fid) throws Exception {
        Conectar();
        query = "UPDATE friends SET faccept = 'true' WHERE faccept = 'false' AND fid = ?;";
        pstm = conn.prepareStatement(query);
        pstm.setInt(1, _fid);
        int executeUpdate = pstm.executeUpdate();
        Desconectar();
    }
    // FIX: funcao traiçoeira, arrumar forma de melhorar isso

    public void AdicionarAmigoDao(int _fuser_id, int _ffriend_id) throws Exception {
        Conectar();
        query = "INSERT INTO friends (fuser_id, ffriend_id, faccept) VALUES (?, ?, 'true');";
        pstm = conn.prepareStatement(query);
        pstm.setInt(1, _fuser_id);
        pstm.setInt(2, _ffriend_id);
        int executeUpdate = pstm.executeUpdate();
        Desconectar();
    }

    public void RequisitarAmizadeDao(int _fuser_id, int _ffriend_id) throws Exception {
        Conectar();
        query = "INSERT INTO friends (fuser_id, ffriend_id, faccept) VALUES (?, ?, 'false');";
        pstm = conn.prepareStatement(query);
        pstm.setInt(1, _fuser_id);
        pstm.setInt(2, _ffriend_id);
        int executeUpdate = pstm.executeUpdate();
        Desconectar();
    }

    public void RejeitarAmigo(int _fid) throws SQLException {
        Conectar();
        query = "DELETE FROM friends WHERE fid=?;";
        pstm = conn.prepareStatement(query);
        pstm.setInt(1, _fid);
        int executeUpdate = pstm.executeUpdate();
        Desconectar();
    }

    public boolean VerificarAmizade(int _fuser_id, int _ffriend_id) throws SQLException {
        Conectar();
        query = "SELECT * FROM friends WHERE fuser_id=? AND ffriend_id=?;";
        pstm = conn.prepareStatement(query);
        pstm.setInt(1, _fuser_id);
        pstm.setInt(2, _ffriend_id);
        ResultSet rs = pstm.executeQuery();
        Desconectar();
        if (rs.next()) {
            return true;
        } else {
            return false;
        }

    }

    public void DeletarAmigo(int _fuser_id, int _ffriend_id) throws SQLException {
        Conectar();
        query = "DELETE FROM friends WHERE fuser_id=? AND ffriend_id=?;";
        pstm = conn.prepareStatement(query);
        pstm.setInt(1, _fuser_id);
        pstm.setInt(2, _ffriend_id);
        int executeUpdate = pstm.executeUpdate();
        pstm.close();
        Desconectar();
        Conectar();
        query = "DELETE FROM friends WHERE fuser_id=? AND ffriend_id=?;";
        pstm = conn.prepareStatement(query);
        pstm.setInt(1, _ffriend_id);
        pstm.setInt(2, _fuser_id);
        executeUpdate = pstm.executeUpdate();
        Desconectar();
    }

    public void NewGroup(int _guser_id, String _gname) throws SQLException {
        Conectar();
        query = "INSERT INTO groups (guser_id, gname) VALUES (?, ?);";
        pstm = conn.prepareStatement(query);
        pstm.setInt(1, _guser_id);
        pstm.setString(2, _gname);
        int executeUpdate = pstm.executeUpdate();
        Desconectar();
    }

    public void DeleteGroup(int _gid) throws SQLException {
        Conectar();
        query = "DELETE FROM friend_group WHERE group_id=?;";
        pstm = conn.prepareStatement(query);
        pstm.setInt(1, _gid);
        int executeUpdate = pstm.executeUpdate();
        pstm.close();
        query = "DELETE FROM groups WHERE gid=?;";
        pstm = conn.prepareStatement(query);
        pstm.setInt(1, _gid);
        executeUpdate = pstm.executeUpdate();

        Desconectar();
    }

    public ResultSet getGroups(int _guser_id) throws SQLException {
        Conectar();
        query = "SELECT gid, gname FROM groups WHERE guser_id=?;";
        pstm = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        pstm.setInt(1, _guser_id);
        ResultSet rs = pstm.executeQuery();
        Desconectar();
        return rs;
    }

    public ResultSet getGroupsForUser(int _guser_id, int _fid) throws SQLException {
        Conectar();
        query = "SELECT gid, gname FROM groups WHERE gid IN (SELECT gid "
                + " FROM groups WHERE guser_id = ? EXCEPT "
                + "(SELECT group_id FROM friend_group WHERE friendship_id = ?));";
        pstm = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        pstm.setInt(1, _guser_id);
        pstm.setInt(2, _fid);
        ResultSet rs = pstm.executeQuery();
        Desconectar();
        return rs;
    }

    public void setUserInGroup(int _fid, int _gid) throws SQLException {
        Conectar();
        query = "INSERT INTO friend_group (friendship_id, group_id) VALUES (?,?);";
        pstm = conn.prepareStatement(query);
        pstm.setInt(1, _fid);
        pstm.setInt(2, _gid);
        int executeUpdate = pstm.executeUpdate();
        Desconectar();

    }

    public ResultSet getAllUsersInGroup(int _gid) throws SQLException {
        Conectar();
        query = "SELECT uid, ufname, ulname FROM users WHERE uid IN(SELECT ffriend_id FROM friends WHERE fid IN (SELECT friendship_id FROM friend_group WHERE group_id = ?));";
        pstm = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        pstm.setInt(1, _gid);
        ResultSet rs = pstm.executeQuery();
        Desconectar();

        return rs;
    }

    public void GetOutOfGroup(int _gid, int _fid) throws SQLException {
        Conectar();
        query = "DELETE FROM friend_group WHERE group_id=? AND friendship_id=?";
        pstm = conn.prepareStatement(query);
        pstm.setInt(1, _gid);
        pstm.setInt(2, _fid);
        int executeUpdate = pstm.executeUpdate();
    }

    public String getGroupName(int _gid) throws SQLException {
        Conectar();
        query = "SELECT gname FROM groups WHERE gid = ?";
        pstm = conn.prepareStatement(query);
        pstm.setInt(1, _gid);
        ResultSet rs = pstm.executeQuery();

        if (rs.next()) {
            return rs.getString("gname");
        } else {
            return "Try Again";
        }
    }

    public ResultSet RecomendsFriends(int _uid) throws SQLException {
        Conectar();
        query = ""
                + "SELECT ffriend_id, "
                + "       Count(*) "
                + "FROM   friends "
                + "WHERE  fuser_id IN (SELECT ffriend_id "
                + "                     FROM   friends "
                + "                     WHERE  fuser_id = ?) "
                + "       AND ffriend_id NOT IN (SELECT ffriend_id "
                + "                            FROM   friends "
                + "                            WHERE  fuser_id = ?) "
                + "       AND ffriend_id <> ? "
                + "GROUP  BY ffriend_id "
                + "ORDER  BY Count(*) DESC "
                + "LIMIT  10 ";

        pstm = conn.prepareStatement(query);
        pstm.setInt(1, _uid);
        pstm.setInt(2, _uid);
        pstm.setInt(3, _uid);

        ResultSet rs = pstm.executeQuery();
        return rs;

    }
}
