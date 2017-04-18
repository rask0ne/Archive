package models;

/**
 * Created by rask on 17.04.2017.
 */

/**
 * Client singleton. Stores the most important information about user and his actions to server
 */
public class UserSingleton {

    private static UserSingleton instance = null;

    private String login;
    private String password;
    private String role;
    private String action;
    private String profile = "";

    public UserSingleton(String login, String password, String role){
        this.setLogin(login);
        this.setPassword(password);
        this.setRole(role);
    }

    public UserSingleton(){}

    public static final UserSingleton getInstance(){

        if(instance == null){
            instance = new UserSingleton();

        }
        return instance;
    }

    public String getProfile(){
        return this.profile;
    }

    public void setProfile(String profile){
        this.profile = profile;
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
