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

        public void InsertUser(Users _user){
                try{
                        Conectar();
                        query = "INSERT INTO users (uname, usex, uemail, pass) VALUES (?, ?, ?, ?);";
                        pstm = conn.prepareStatement(query);
                        pstm.setString(1, _user.getName());
                        pstm.setString(2, _user.getSex());
                        pstm.setString(3, _user.getEmail());
                        pstm.setString(4, _user.getPass());
                        pstm.executeUpdate();
                        Desconectar();
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }
        
        public boolean LoginDao (Users _user){
            try{
                Conectar();
                query = "SELECT uemail,pass FROM users WHERE uemail=? AND pass=?";
                pstm = conn.prepareStatement(query);
                pstm.setString(1, _user.getEmail());
                pstm.setString(2, _user.getPass());
                
                ResultSet rs = pstm.executeQuery();
                                
                if(rs.next()) return true;
                else return false;
              
            } catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }

}
