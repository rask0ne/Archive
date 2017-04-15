package client.models;

import java.io.Serializable;

/**
 * Created by rask on 15.04.2017.
 */
public class User implements Serializable{

    private String login;
    private String password;
    private int role;
    private String action;

    public User(String login, String password, int role){
        this.setLogin(login);
        this.setPassword(password);
        this.setRole(role);
    }

    public User(){}

    public int getRole(){
        return this.role;
    }

    public String getAction(){
        return this.action;
    }

    public String getLogin(){
        return this.login;
    }

    public String getPassword(){
        return this.password;
    }

    public void setLogin(String login){
        this.login = login;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setRole(int role){
        this.role = role;
    }

    public void setAction(String action){
        this.action = action;
    }
}
