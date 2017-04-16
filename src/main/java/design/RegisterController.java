package design;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import crypt.md5Crypt;
import hibernate.Util.HibernateUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Client;
import models.User;
import models.UsersEntity;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;

/**
 * Created by rask on 01.03.2017.
 */


/**
 * Controller of 'Register' window.
 */
public class RegisterController{

    @FXML
    private Label lblMessage;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;

    public void passwordTextButtonAction(ActionEvent actionEvent) {
    }

    public void usernameTextButtonAction(ActionEvent actionEvent) {
    }

    public void registerButtonAction(ActionEvent actionEvent) throws Exception{

        boolean check = true;
        String query;
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        password = new md5Crypt().md5Apache(password);

        User user = new User(txtUsername.getText(), password, 2);
        user.setAction("Check if registered");
        Client client = new Client();
        System.out.println("sent to server");
        Object o = client.sendToServer(user);

        System.out.println("Got signal");
        if(o instanceof String){
            String str = (String)o;
            if(str.equals("Registered Successfully")){
                ((Node) (actionEvent.getSource())).getScene().getWindow().hide();

                Parent parent = FXMLLoader.load(getClass().getResource("Profile.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(parent);
                stage.setScene(scene);
                stage.setTitle("Profile");
                stage.show();
            }
            lblMessage.setText("This username already exists!");
        }

    }

}
