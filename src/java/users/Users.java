/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package users;

/**
 *
 * @author snk
 */
public class Users {
            private int id;
        private String name;
        private String sex;
        private String email;
        private String pass;

        public int getId(){
                return this.id;
        }
        public void setId(int _id){
                this.id = _id;
        }
        public String getName(){
                return this.name;
        }
        public void setName(String _name){
                this.name = _name;
        }
        public String getSex(){
                return this.sex;
        }
        public void setSex(String _sex){
                this.sex = _sex;
        }
        public String getEmail(){
                return this.email;
        }
        public void setEmail(String _email){
                this.email = _email;
        }
        public String getPass(){
                return this.pass;
        }
        public void setPass(String _pass){
                this.pass = _pass;
        }
        
        public void NewUser(Users _user){
                UserDao aux = new UserDao();
                aux.InsertUser(_user);
        }

}
