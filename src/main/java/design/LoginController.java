package design;

import crypt.md5Crypt;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Client;
import models.User;
import models.UserSingleton;

/**
 * Controller of 'Login' window.
 */
public class LoginController {

    @FXML
    private Label lblMessage;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;

    public void passwordTextButtonAction(ActionEvent actionEvent) {


    }

    public void loginButtonAction(ActionEvent actionEvent) throws Exception {

        User user = new User(txtUsername.getText(), "", "");
        boolean check = true;
        String query;

        String password = new md5Crypt().md5Apache(txtPassword.getText());
        user.setPassword(password);
        user.setAction("Login");

        Client client = new Client();
        Object o = client.sendToServer(user);

        if(o instanceof String){
            String str = (String)o;
            if(str.equals("Loggined Successfully")){

                UserSingleton.getInstance().setLogin(txtUsername.getText());
                UserSingleton.getInstance().setPassword(password);
                ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
                Parent parent = FXMLLoader.load(getClass().getResource("Catalog.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(parent);
                stage.setScene(scene);
                stage.setTitle("Archive");
                stage.show();
            }
        }

        lblMessage.setText("Wrong username or password!");
        lblMessage.setStyle("-fx-color: red");
    }

    public void usernameTextButtonAction(ActionEvent actionEvent) {
    }

    public void guestAction(ActionEvent actionEvent) throws Exception{

        UserSingleton.getInstance().setLogin("guest");
        UserSingleton.getInstance().setRole("guest");
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("Catalog.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Archive");
        stage.show();

    }

    public void registerButtonAction(ActionEvent actionEvent) throws Exception {

        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("Register.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Registration Form");
        stage.show();

    }

};
