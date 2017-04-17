package design;


import XMLDAO.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import models.Client;
import models.User;
import models.UserSingleton;
import org.apache.log4j.Logger;


import java.io.IOException;

import repositories.XMLRepository;

import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Created by rask on 01.03.2017.
 */

/**
 * Controller of 'Catalog' window.
 */
public class CatalogController {

    /**
     * Username from current session is shown here
     */
    @FXML
    private Label lblTextMessage;
    /**
     * Field to imput string for search
     */
    @FXML
    private TextField srchText;
    /**
     * Table where files are shown
     */
    @FXML
    private TableView<XMLRepository> tableView;
    @FXML
    /**
     * Button to upload file
     */
    private Button uplButton;
    @FXML
    /**
     * Button to delete file
     */
    private Button dltButton;

    /**
     * Method to initialize table with all files first time and
     * change Label username info to current user
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @FXML
    public void initialize() throws SQLException, ClassNotFoundException, IOException {

        lblTextMessage.setText(UserSingleton.getInstance().getLogin());

        updateTableView();

    }

    /**
     * Logger class initialize
     */
    private final Logger logger = Logger.getLogger(CatalogController.class);

    /**
     * Method to search files by entering string in Search TextField.
     * This string imputs in overloaded method updateTableView(String);
     *
     * @param actionEvent
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void searchAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String text = srchText.getText();

       // updateTableView(text);

    }



    /**
     * Algorithm of deleting file from database by its name and username, who uploaded this file.
     * Also there is a user role check, because admin has rights to delete all files, user
     * may delete only his files, guest has not such rights.
     * @param actionEvent
     * @throws SQLException
     * @throws ClassNotFoundException
     */


    /**
     * Method to refresh data in table after some manipulations with files database. Works
     * after any changes such as uploading or deleting.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void updateTableView() throws SQLException, ClassNotFoundException, IOException {

        User user = new User(UserSingleton.getInstance().getLogin(), "", "");
        user.setAction("Get Profiles");
        Client client = new Client();
        ArrayList<Person> list = (ArrayList<Person>)client.sendToServer(user);
       // FileDataAccessor dataAccessor = new FileDataAccessor("jdbc:mysql://localhost:3306/catalogdb?useSSL=false", "root", "root"); // provide driverName, dbURL, user, password...
        ArrayList<XMLRepository> username = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            username.add(new XMLRepository(list.get(i).getName()));
            System.out.println(list.get(i).getName());
        }

        TableColumn<XMLRepository, String> usernameCol = new TableColumn<>("User Profile");
        usernameCol.setCellValueFactory(new PropertyValueFactory<XMLRepository, String>("username"));


        tableView.getItems().clear();
        if(tableView.getColumns().isEmpty())
            tableView.getColumns().addAll(usernameCol);


        tableView.getItems().addAll(username);

        logger.info("TableView updated");


    }

    /**
     * Overloaded method to refresh data after searching for some files. Gets string and then
     * forms list according to this inquiry.
     * @param name
     * @throws SQLException
     * @throws ClassNotFoundException
     */

    /**
     * Button to change current user. Creates window 'Login'.
     * @param actionEvent
     * @throws IOException
     */
    public void changeUserButton(ActionEvent actionEvent) throws IOException {

        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

        Parent parent = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();

        logger.info("Created window 'Login' from changeUserButton");

    }

    public void getDOM(ActionEvent actionEvent) throws IOException, ClassNotFoundException {

        User user = new User(UserSingleton.getInstance().getLogin(), "", "");
        user.setAction("Get DOM parser");
        Client client = new Client();
        String str = (String)client.sendToServer(user);

    }

    public void getJDOM(ActionEvent actionEvent) throws IOException, ClassNotFoundException {

        User user = new User(UserSingleton.getInstance().getLogin(), "", "");
        user.setAction("Get JDOM parser");
        Client client = new Client();
        String str = (String)client.sendToServer(user);

    }

    public void getSAX(ActionEvent actionEvent) throws IOException, ClassNotFoundException {

        User user = new User(UserSingleton.getInstance().getLogin(), "", "");
        user.setAction("Get SAX parser");
        Client client = new Client();
        String str = (String)client.sendToServer(user);
    }

    public void getStAX(ActionEvent actionEvent) throws IOException, ClassNotFoundException {

        User user = new User(UserSingleton.getInstance().getLogin(), "", "");
        user.setAction("Get StAX parser");
        Client client = new Client();
        String str = (String)client.sendToServer(user);
    }

    public void changeRolesButton(ActionEvent actionEvent) throws IOException, ClassNotFoundException {

        User user = new User(UserSingleton.getInstance().getLogin(), "", "");
        boolean check = true;
        String query;

        user.setAction("Change Roles");

        Client client = new Client();
        Object o = client.sendToServer(user);

        if(o instanceof String){
            String str = (String)o;
            if(str.equals("Open Change Table")){

                //((Node)(actionEvent.getSource())).getScene().getWindow().hide();
                Parent parent = FXMLLoader.load(getClass().getResource("ChangeRoles.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(parent);
                stage.setScene(scene);
                stage.setTitle("Change Roles");
                stage.show();
            }
        }


    }

    public void showProfileButton(ActionEvent actionEvent) throws IOException, ClassNotFoundException {

        XMLRepository xml = (XMLRepository) tableView.getSelectionModel().getSelectedItem();

        User user = new User(UserSingleton.getInstance().getLogin(), "", "");
        user.setAction("Show user profile");
        user.setUserProfile(xml.getUsername());

        Client client = new Client();
        Person profile =  (Person)client.sendToServer(user);
        UserSingleton.getInstance().setProfile(profile.toString());

        Parent parent = FXMLLoader.load(getClass().getResource("ShowProfile.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Profile");
        stage.show();

    }


    public void changeProfileAction(ActionEvent actionEvent) throws IOException, ClassNotFoundException, SQLException {

        XMLRepository xml = (XMLRepository) tableView.getSelectionModel().getSelectedItem();

        User user = new User(UserSingleton.getInstance().getLogin(), "", "");
        user.setAction("Change user profile");
        user.setUserProfile(xml.getUsername());
        UserSingleton.getInstance().setProfile(xml.getUsername());

        Client client = new Client();
        String str =  (String)client.sendToServer(user);

        if(str.equals("You have rights")) {

            System.out.println("Ok");
            Parent parent = FXMLLoader.load(getClass().getResource("Profile.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.setTitle("Profile");
            stage.show();
        }

    }


    public void deleteProfileAction(ActionEvent actionEvent) throws IOException, ClassNotFoundException, SQLException {

        XMLRepository xml = (XMLRepository) tableView.getSelectionModel().getSelectedItem();

        User user = new User(UserSingleton.getInstance().getLogin(), "", "");
        user.setAction("Delete user profile");
        user.setUserProfile(xml.getUsername());
        UserSingleton.getInstance().setProfile(xml.getUsername());

        Client client = new Client();
        String str =  (String)client.sendToServer(user);

        if(str.equals("deleted")){
            updateTableView();
        }

    }
}


