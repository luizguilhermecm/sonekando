/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package users;

import database.*;
import java.sql.*;

/**
 *
 * @author snk
 * Classe que manipula o objeto Users e o joga no banco.
 * Conectar() -> pega um objeto Connection da Factory.
 * Desconectar() -> fecha a conexão com o banco.
 * InsertUser(_user) -> faz a persistencia dos dados do objeto _user no banco
 * LoginDao (_user) -> faz uma busca no banco para validar o login do usuario.
 * 
 *
 */
//TODO: comentar as outras funcoes de UserDao.java
public class UserDao {

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
        pstm.close();
    }

    //TODO: enviar upass com md5()
    public boolean InsertUser(Users _user) {
        int executeUpdate = 0;
        try {
            Conectar();

            query = "INSERT INTO users (ufname, ulname, usex, ucity, uemail, upass) VALUES (?, ?, ?, ?, ?, ?);";

            pstm = conn.prepareStatement(query);
            pstm.setString(1, _user.getFirstName());
            pstm.setString(2, _user.getLastName());
            pstm.setString(3, _user.getSex());
            pstm.setString(4, _user.getCity());
            pstm.setString(5, _user.getEmail());
            pstm.setString(6, _user.getPass());

            executeUpdate = pstm.executeUpdate();

            Desconectar();

            if (executeUpdate == 0) {
                return false;
            } else if (executeUpdate == 1) {
                return true;
            }

        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public boolean LoginDao(Users _user) {
        try {
            Conectar();
            query = "SELECT uemail,upass FROM users WHERE uemail=? AND upass=?";
            pstm = conn.prepareStatement(query);
            pstm.setString(1, _user.getEmail());
            pstm.setString(2, _user.getPass());

            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public int getIdDao(String _uemail) {
        try {
            Conectar();
            query = "SELECT uid FROM users WHERE uemail=?;";
            pstm = conn.prepareStatement(query);
            pstm.setString(1, _uemail);

            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                return rs.getInt("uid");
            }
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }

    public String getNomeDao(int _uid) {
        try {
            Conectar();
            query = "SELECT ufname FROM users WHERE uid=?;";
            pstm = conn.prepareStatement(query);
            pstm.setInt(1, _uid);

            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                return rs.getString("ufname");
            }
            return "Error Select";
        } catch (Exception e) {
            return "Error Try";
        }
    }

    public String getSobrenomeDao(int _uid) {
        try {
            Conectar();
            query = "SELECT ulname FROM users WHERE uid=?;";
            pstm = conn.prepareStatement(query);
            pstm.setInt(1, _uid);

            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                return rs.getString("ulname");
            }
            return "Error Select";
        } catch (Exception e) {
            return "Error Try";
        }
    }

    public String getNomeCompletoDao(int _uid) {
        try {
            Conectar();
            query = "SELECT (ufname || ' ' || ulname) AS fullname FROM users WHERE uid=?;";
            pstm = conn.prepareStatement(query);
            pstm.setInt(1, _uid);

            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                return rs.getString("fullname");
            }
            return "Error Select";
        } catch (Exception e) {
            return "Error Try";
        }
    }

    public String getCityDao(int _uid) {
        try {
            Conectar();
            query = "SELECT ucity FROM users WHERE uid=?;";
            pstm = conn.prepareStatement(query);
            pstm.setInt(1, _uid);

            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                return rs.getString("ucity");
            }
            return "Error Select";
        } catch (Exception e) {
            return "Error Try";
        }
    }

    public Users getUserDao(int _uid) throws Exception {

        Conectar();
        Users _user = null;

        query = "SELECT * FROM users WHERE uid=?;";
        pstm = conn.prepareStatement(query);
        pstm.setInt(1, _uid);

        ResultSet rs = pstm.executeQuery();

        if (rs.next()) {
            _user.setId(rs.getInt("uid"));
            _user.setFirstName(rs.getString("ufname"));
            _user.setLastName(rs.getString("ulname"));
            _user.setSex(rs.getString("usex"));
            _user.setCity(rs.getString("ucity"));
            _user.setEmail(rs.getString("ucity"));

            return _user;
        }
        return null;
    }

    public ResultSet getAmigosDao(int _uid) throws Exception {
        ResultSet rs = null;
        Conectar();
        query = "SELECT fuser_id,ffriend_id FROM friends WHERE fuser_id=? AND faccept = 'true';";
        pstm = conn.prepareStatement(query);
        pstm.setInt(1, _uid);
        rs = pstm.executeQuery();
        return rs;
    }

    public ResultSet getPendenciasDao(int _uid) throws Exception {
        ResultSet rs = null;
        Conectar();
        query = "SELECT fid,fuser_id FROM friends WHERE ffriend_id=? AND faccept = 'false';";
        pstm = conn.prepareStatement(query);
        pstm.setInt(1, _uid);
        rs = pstm.executeQuery();
        return rs;
    }
   
    public ResultSet SearchUsers (String _name) throws SQLException{
        Conectar();
        //FIX: retornar se ocorrer parte do nome, nao lembro como!
        String _query_name = "%" + _name + "%";
        query = "SELECT uid, (ufname || ' ' || ulname) AS full_name FROM users WHERE ufname LIKE ? OR ulname LIKE ?;";
        pstm = conn.prepareStatement(query);
        pstm.setString(1, _query_name);
        pstm.setString(2, _query_name);
        ResultSet rs = pstm.executeQuery();
        return rs;
    }
}