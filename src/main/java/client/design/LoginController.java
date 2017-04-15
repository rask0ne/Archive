package client.design;

import client.Client;
import client.models.User;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import client.crypt.md5Crypt;
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
import models.UsersEntity;

import java.sql.DriverManager;
import java.sql.ResultSet;

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

        User user = new User(txtUsername.getText(), "", 2);
        boolean check = true;
        String query;

        String password = new md5Crypt().md5Apache(txtPassword.getText());
        user.setPassword(password);
        Client client = new Client();
        client.sendToServer(user);


        lblMessage.setText("Wrong username or password!");
        lblMessage.setStyle("-fx-color: red");
    }

    public void usernameTextButtonAction(ActionEvent actionEvent) {
    }

    public void guestAction(ActionEvent actionEvent) throws Exception{

        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("Archive.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Catalog");
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


