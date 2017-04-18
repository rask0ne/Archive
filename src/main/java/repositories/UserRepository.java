package repositories;

import com.mysql.jdbc.Connection;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;
import java.sql.DriverManager;

/**
 * Created by rask on 17.04.2017.
 */

/**
 * Repository for table of users in ChangeRoles.fxml
 */
public class UserRepository implements Serializable{

    private String username;
    private String role;

   public void setRole(String role){
       this.role = role;
   }

   public void setUsername(String username){
       this.username = username;
   }

   public String getUsername(){
       return this.username;
   }

   public String getRole(){
       return  this.role;
   }

    public UserRepository() {}

    public UserRepository(String username, String role) {
        setUsername(username);
        setRole(role);
    }

}
