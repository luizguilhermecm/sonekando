/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.*;

/**
 *
 * @author snk
 * Classe Factory sera a fabrica de objetos "Connection" usadas para se conectar
 * ao banco, nesse caso Postgresql.
 * 
 * String url -> eh a url necessaria para se conectar ao banco
 * String user -> nome de usuario do Postgresql
 * String password -> senha do usuario do Postgresql
 */
public class Factory {
 
    
        private static String url = "jdbc:postgresql://localhost/postgres";
        private static String user = "postgres";
        private static String password = "soneka";
        
        /*Funcao getCon() retorna um objeto do tipo "Connection" necessario
         * para comunicacao com o banco de dados, toda vez que alguma funcao 
         * precisar realizar algo no banco chamara essa funcao para obter um
         * Connection. Futuramente dados como user e password com varios
         * previlegios diferentes devera ser incluido e salvo em arquivo para
         * facilitar a vida.
         */

        // TODO: criar conexões com diferentes permissoes no banco
        public Connection getCon() throws SQLException {
                return DriverManager.getConnection(url, user, password);
        }


}
