package design;


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
    public void initialize() throws SQLException, ClassNotFoundException {

        lblTextMessage.setText(UserSingleton.getInstance().getLogin());

        //updateTableView();

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
   /* public void deleteActionButton(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        FileRepository file = (FileRepository) tableView.getSelectionModel().getSelectedItem();

        logger.info("Selected file to delete");

        if(file.getUsername().equals(UserRepository.getInstance().getName())
                || UserRepository.getInstance().getName().equals("admin")) {

            Connection con = (Connection) DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/catalogdb?autoReconnect=true&useSSL=false", "root", "root");

            logger.info("Connection to DB established");

            String query = "delete from files where filename = ? and username = ?";
            PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(query);
            pstmt.setString(1, file.getFileName());
            pstmt.setString(2, file.getUsername());
            pstmt.execute();
            pstmt = (PreparedStatement) con.prepareStatement("alter table files auto_increment = 1");
            pstmt.execute();

            logger.info("File deleted from DB");

            con.close();

            logger.info("Connection to DB closed");

            updateTableView();

        }

    }*/

    /**
     * Method to refresh data in table after some manipulations with files database. Works
     * after any changes such as uploading or deleting.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void updateTableView() throws SQLException, ClassNotFoundException {


       // FileDataAccessor dataAccessor = new FileDataAccessor("jdbc:mysql://localhost:3306/catalogdb?useSSL=false", "root", "root"); // provide driverName, dbURL, user, password...

        TableView<XMLRepository> personTable = new TableView<>();
        TableColumn<XMLRepository, String> firstNameCol = new TableColumn<>("Profile");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<XMLRepository, String>("xmlName"));
        TableColumn<XMLRepository, String> usernameCol = new TableColumn<>("User");
        usernameCol.setCellValueFactory(new PropertyValueFactory<XMLRepository, String>("username"));


        tableView.getItems().clear();
        if(tableView.getColumns().isEmpty())
            tableView.getColumns().addAll(firstNameCol, usernameCol);


        //tableView.getItems().addAll(dataAccessor.getFileList());

        logger.info("TableView updated");


    }

    /**
     * Overloaded method to refresh data after searching for some files. Gets string and then
     * forms list according to this inquiry.
     * @param name
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    /*public void updateTableView(String name) throws SQLException, ClassNotFoundException {


        FileDataAccessor dataAccessor = new FileDataAccessor("jdbc:mysql://localhost:3306/catalogdb?useSSL=false", "root", "root"); // provide driverName, dbURL, user, password...

        TableView<FileRepository> personTable = new TableView<>();
        TableColumn<FileRepository, String> firstNameCol = new TableColumn<>("File Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<FileRepository, String>("fileName"));
        TableColumn<FileRepository, String> usernameCol = new TableColumn<>("Person");
        usernameCol.setCellValueFactory(new PropertyValueFactory<FileRepository, String>("username"));


        tableView.getItems().clear();
        if(tableView.getColumns().isEmpty())
            tableView.getColumns().addAll(firstNameCol, usernameCol);


        tableView.getItems().addAll(dataAccessor.getSearchFileList(name));

        logger.info("TableView with found files updated");
    }*/

    /**
     * Button to open file.
     * @param actionEvent
     * @throws IOException
     * @throws SQLException
     */
   /* public void openButtonAction(ActionEvent actionEvent) throws IOException, SQLException {

        FileRepository file = (FileRepository) tableView.getSelectionModel().getSelectedItem();
        file.execution();

    }*/

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

    public void showProfileButton(ActionEvent actionEvent) {
    }
}


