package design;


import XMLDAO.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Client;


/**
 * Created by rask on 16.04.2017.
 */
public class ProfileController {

    @FXML
    private Label lblMessage;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtSurname;
    @FXML
    private TextField txtTelephone;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtWorkplace;
    @FXML
    private TextField txtExperience;

    public void registerButtonAction(ActionEvent actionEvent) throws Exception{

        Person person = new Person(txtName.getText(), txtSurname.getText(), "", txtTelephone.getText(), txtEmail.getText(),
                txtWorkplace.getText(), Integer.valueOf(txtExperience.getText()));

        Client client = new Client();
        Object o =  client.sendToServer(person);

        if(o instanceof String){
            String str = (String)o;
            if(str.equals("Profile created successfully")){
                ((Node) (actionEvent.getSource())).getScene().getWindow().hide();

                Parent parent = FXMLLoader.load(getClass().getResource("Catalog.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(parent);
                stage.setScene(scene);
                stage.setTitle("Archive");
                stage.show();
            }

        }

    }

}
