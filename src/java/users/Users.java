/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package users;

/**
 *
 * @author snk
 * 
 * Classe Users eh a classe dos senhores usuarios.
 */
public class Users {

    private int id;
    private String first_name;
    private String last_name;
    private String sex;
    private String city;
    private String email;
    private String pass;

    public int getId() {
        return this.id;
    }

    public void setId(int _id) {
        this.id = _id;
    }

    public String getFirstName() {
        return this.first_name;
    }

    public void setFirstName(String _first_name) {
        this.first_name = _first_name;
    }

    public String getLastName() {
        return this.last_name;
    }

    public void setLastName(String _last_name) {
        this.last_name = _last_name;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String _sex) {
        this.sex = _sex;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String _city) {
        this.city = _city;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String _email) {
        this.email = _email;
    }

    public String getPass() {
        return this.pass;
    }

    public void setPass(String _pass) {
        this.pass = _pass;
    }
    
    //caso ocorra algum erro na insersão no banco retornará false
    public boolean NewUser(Users _user) {
        UserDao aux = new UserDao();
        return aux.InsertUser(_user);
    }
}