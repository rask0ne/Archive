package client.design;

import client.Client;
import client.crypt.md5Crypt;
import client.models.User;
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
import xml.parser.Person;

/**
 * Created by rask on 16.04.2017.
 */
public class ProfileController {

    @FXML
    private Label lblMessage;
    @FXML
    private TextField txtName;
    @FXML
    private PasswordField txtSurname;
    @FXML
    private TextField txtTelephone;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtWorkplace;
    @FXML
    private TextField txtExperience;

    public void registerButtonAction(ActionEvent actionEvent) throws Exception{

        Person person = new Person(txtName.getText(), txtSurname.getText(), txtTelephone.getText(), txtEmail.getText(),
                txtWorkplace.getText(), txtExperience.getText());

        Client client = new Client();
        client.sendToServer(person);

        Object o = client.getFromServer();
        if(o instanceof String){
            String str = (String)o;
            if(str.equals("Registered Successfully")){
                ((Node) (actionEvent.getSource())).getScene().getWindow().hide();

                Parent parent = FXMLLoader.load(getClass().getResource("Archive.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(parent);
                stage.setScene(scene);
                stage.setTitle("Archive");
                stage.show();
            }

        }

    }

}
