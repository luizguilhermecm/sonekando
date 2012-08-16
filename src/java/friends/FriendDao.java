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
                pstm.close();
        }

        public int getFuserIdDao (int _fid){
            try{
                Conectar();
                query = "SELECT fuiser_id FROM friends WHERE fid=?;";
                pstm = conn.prepareStatement(query);
                pstm.setInt(1, _fid);
                
                ResultSet rs = pstm.executeQuery();
                                
                if(rs.next()) {
                    return rs.getInt("fuser_id");
                }
                return 0;
            } catch (Exception e){
                return 0;
            }
            
        }
        public int getFfriendIdDao (int _fid){
            try{
                Conectar();
                query = "SELECT ffriend_id FROM friends WHERE fid=?;";
                pstm = conn.prepareStatement(query);
                pstm.setInt(1, _fid);
                
                ResultSet rs = pstm.executeQuery();
                                
                if(rs.next()) {
                    return rs.getInt("ffriend_id");
                }
                return 0;
            } catch (Exception e){
                return 0;
            }
            
        }  
        
        // FIX: funcao traiçoeira, arrumar forma de melhorar isso
        public void AceitarAmigoDao (int _fuser_id, int _ffriend_id) throws Exception {
                Conectar();
                query = "INSERT INTO friends (fuser_id, ffriend_id, faccept) VALUES (?, ?, 'true');";
                pstm = conn.prepareStatement(query);
                pstm.setInt(1, _fuser_id);
                pstm.setInt(2, _ffriend_id);
            int executeUpdate = pstm.executeUpdate();
        }
}
