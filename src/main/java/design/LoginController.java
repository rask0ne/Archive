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
import org.apache.log4j.Logger;

/**
 * Controller of 'Login' window.
 */
public class LoginController {

    private final Logger logger = Logger.getLogger(Login.class);

    @FXML
    private Label lblMessage;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;

    /**
     * Sending request to the server if user is registered, checking it. If user doesn't have
     * a profile, he should create it.
     * @param actionEvent
     * @throws Exception
     */
    public void loginButtonAction(ActionEvent actionEvent) throws Exception {

        User user = new User(txtUsername.getText(), "", "");
        boolean check = true;
        String query;

        String password = new md5Crypt().md5Apache(txtPassword.getText());
        user.setPassword(password);
        user.setAction("Login");

        Client client = new Client();
        logger.info("Trying to authorizate");
        Object o = client.sendToServer(user);

        if(o instanceof String){
            String str = (String)o;
            if(str.equals("Loggined Successfully")){
                logger.info("Loggined succesfully, user has profile");
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
            if(str.equals("Create new profile")){
                logger.info("Loggined successfully, user doesn't have profile");
                UserSingleton.getInstance().setLogin(txtUsername.getText());
                UserSingleton.getInstance().setProfile(txtUsername.getText());
                UserSingleton.getInstance().setPassword(password);
                ((Node) (actionEvent.getSource())).getScene().getWindow().hide();

                Parent parent = FXMLLoader.load(getClass().getResource("Profile.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(parent);
                stage.setScene(scene);
                stage.setTitle("Profile");
                stage.show();
            }
        }

        lblMessage.setText("Wrong username or password!");
        lblMessage.setStyle("-fx-color: red");
    }

    /**
     * Entering as a guest
     * @param actionEvent
     * @throws Exception
     */
    public void guestAction(ActionEvent actionEvent) throws Exception{
        logger.info("User entered as a guest");
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

    /**
     * Registering new user
     * @param actionEvent
     * @throws Exception
     */
    public void registerButtonAction(ActionEvent actionEvent) throws Exception {

        logger.info("Registering new user");
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("Register.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Registration Form");
        stage.show();

    }

};
