/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.*;

/**
 *
 * @author snk
 */
public class Factory {
 
    
        private static String url = "jdbc:postgresql://localhost/postgres";
        private static String user = "postgres";
        private static String password = "soneka";
        
        public Connection getCon() throws SQLException {
                return DriverManager.getConnection(url, user, password);
        }


}
