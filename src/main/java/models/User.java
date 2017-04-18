package models;

import java.io.Serializable;

/**
 * Created by rask on 15.04.2017.
 */

/**
 * Model of user
 */
public class User implements Serializable{

    private String login;
    private String password;
    private String role;
    private String action;
    private String userProfile;

    public User(String login, String password, String role){
        this.setLogin(login);
        this.setPassword(password);
        this.setRole(role);
    }

    public User(){}

    public String getUserProfile(){
        return this.userProfile;
    }

    public void setUserProfile(String userProfile){
        this.userProfile = userProfile;
    }

    public String getRole(){
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

    public void setRole(String role){
        this.role = role;
    }

    public void setAction(String action){
        this.action = action;
    }
}
