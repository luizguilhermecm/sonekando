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
//        pstm.close();
    }

    //That methos is responsible to insert new users in DB.
    //Its receives a Users object and conect with DB and Insert all the datas
    //in DB, except ID, because that number is gives for DB.
    //For execute a query is used a "pstm.executeUpdate" what return a integer
    //That return 1 if the query was good and return 0 if couldn't finish the
    //query. Using that returns of DB this method return a boolean true if DB
    //return 1 and false if DB return 0.
    public boolean InsertUser(Users _user) {
        int executeUpdate = 0;
        try {
            Conectar();

            query = "INSERT INTO users (ufname, ulname, usex, ucity, uemail, upass) VALUES (?, ?, ?, ?, ?, ?);";
            //TODO: send upass with md5(?)

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
    
    public void EditUser(int _uid, Users _user) throws SQLException {
        int executeUpdate = 0;

            Conectar();

            query = "UPDATE users SET ufname=?, ulname=?, usex=?, ucity=?, uemail=?, upass=? WHERE uid=?;";
            //TODO: send upass with md5(?)

            pstm = conn.prepareStatement(query);
            pstm.setString(1, _user.getFirstName());
            pstm.setString(2, _user.getLastName());
            pstm.setString(3, _user.getSex());
            pstm.setString(4, _user.getCity());
            pstm.setString(5, _user.getEmail());
            pstm.setString(6, _user.getPass());
            pstm.setInt(7, _uid);
            
            executeUpdate = pstm.executeUpdate();

            Desconectar();
    }


    //LoginDao is used to verify if the user put in login area correct datas.
    //That method receives an object Users and make a query in DB, looking for
    //uemail and upass and compare with Users. If both is equal its return true
    //if not equal return false.
    public boolean LoginDao(Users _user) {
        try {
            Conectar();
            query = "SELECT uemail,upass FROM users WHERE uemail=? AND upass=?";
            pstm = conn.prepareStatement(query);
            pstm.setString(1, _user.getEmail());
            pstm.setString(2, _user.getPass());

            ResultSet rs = pstm.executeQuery();
            Desconectar();

            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    //That method receives a String _uemail like parameter, and _uemail is the
    //login the user uses to enter in sonekando. That _uemail is unique in DB.
    //This method receive that unique email and make a query looking for your
    //row in DB e return a ResultSet with uid.
    //This method just can be used after the user did login in sonekando.
    //If the ResultSet didn't has a uid that is because something wrong happens.
    //In that case that method return 0 because not exist a user in DB with id 0.
    public int getIdDao(String _uemail) {
        try {
            Conectar();
            query = "SELECT uid FROM users WHERE uemail=?;";
            pstm = conn.prepareStatement(query);
            pstm.setString(1, _uemail);

            ResultSet rs = pstm.executeQuery();
            Desconectar();

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
            Desconectar();

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
            Desconectar();

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
            Desconectar();

            if (rs.next()) {
                return rs.getString("fullname");
            }
            return "Error Select";
        } catch (Exception e) {
            return "Error Try";
        }
    }
    
    public String getEmailDao(int _uid) {
        try {
            Conectar();
            query = "SELECT uemail FROM users WHERE uid=?;";
            pstm = conn.prepareStatement(query);
            pstm.setInt(1, _uid);

            ResultSet rs = pstm.executeQuery();
            Desconectar();

            if (rs.next()) {
                return rs.getString("uemail");
            }
            return "Error Select";
        } catch (Exception e) {
            return "Error Try";
        }
    }
    
       public String getPassDao(int _uid) {
        try {
            Conectar();
            query = "SELECT upass FROM users WHERE uid=?;";
            pstm = conn.prepareStatement(query);
            pstm.setInt(1, _uid);

            ResultSet rs = pstm.executeQuery();
            Desconectar();

            if (rs.next()) {
                return rs.getString("upass");
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
            Desconectar();

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
        Desconectar();

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

    //This method receives a uid of user what is logged in sonekando.
    //Its receives a uid and make a query looking for asks of friendship with
    //him and didn't was accept yet.
    public ResultSet getPendenciasDao(int _uid) throws Exception {
        ResultSet rs = null;
        Conectar();
        query = "SELECT fid,fuser_id FROM friends WHERE ffriend_id=? AND faccept = 'false';";
        pstm = conn.prepareStatement(query);
        pstm.setInt(1, _uid);
        rs = pstm.executeQuery();
        Desconectar();

        return rs;
    }

    public ResultSet SearchUsers(String _name) throws SQLException {
        Conectar();
        //FIX: retornar se ocorrer parte do nome, nao lembro como!
        String _query_name = "%" + _name + "%";
        query = "SELECT uid, (ufname || ' ' || ulname) AS full_name FROM users WHERE LOWER(ufname) LIKE LOWER(?) OR LOWER(ulname) LIKE LOWER(?);";
        pstm = conn.prepareStatement(query);
        pstm.setString(1, _query_name);
        pstm.setString(2, _query_name);
        ResultSet rs = pstm.executeQuery();
        Desconectar();

        return rs;
    }
}