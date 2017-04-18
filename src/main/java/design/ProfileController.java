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
import models.UserSingleton;
import org.apache.log4j.Logger;


/**
 * Created by rask on 16.04.2017.
 */

/**
 * Controller of Profile.fxml
 */
public class ProfileController {

    private final Logger logger = Logger.getLogger(Login.class);

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

    /**
     * Getting text from all textfields, creating new object of Person model and sending request
     * to the server to create new profile and add info to xml
     * @param actionEvent
     * @throws Exception
     */
    public void registerButtonAction(ActionEvent actionEvent) throws Exception{

        Person person;
        if(UserSingleton.getInstance().getProfile() == "") {
            person = new Person(UserSingleton.getInstance().getLogin(), txtName.getText(), txtSurname.getText(), txtTelephone.getText(), txtEmail.getText(),
                    txtWorkplace.getText(), Integer.valueOf(txtExperience.getText()));
        }
        else{
            person = new Person(UserSingleton.getInstance().getProfile(), txtName.getText(), txtSurname.getText(), txtTelephone.getText(), txtEmail.getText(),
                    txtWorkplace.getText(), Integer.valueOf(txtExperience.getText()));
        }

        Client client = new Client();
        logger.info("Requesting to create/edit profile");
        Object o =  client.sendToServer(person);

        if(o instanceof String){
            String str = (String)o;
            if(str.equals("Profile created successfully")){
                ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
                logger.info("Created new profile");
                Parent parent = FXMLLoader.load(getClass().getResource("Catalog.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(parent);
                stage.setScene(scene);
                stage.setTitle("Archive");
                stage.show();
            }
            if(str.equals("profile edited")){
                logger.info("Profile got edited");
                UserSingleton.getInstance().setProfile("");
                ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
            }

        }

    }

}
