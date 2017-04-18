package design;

import com.mysql.jdbc.Connection;
import hibernate.Util.UsersDataAccessor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Client;
import models.User;
import models.UserSingleton;
import org.apache.log4j.Logger;
import repositories.UserRepository;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rask on 17.04.2017.
 */

/**
 * Controller of ChangeRoles.fxml
 */
public class ChangeRolesController {

    private final Logger logger = Logger.getLogger(Login.class);
    @FXML
    private TableView<UserRepository> tableView;

    /**
     * Initializing table with all users registered in DB
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public void initialize() throws SQLException, ClassNotFoundException, IOException {

        TableColumn<UserRepository, String> firstNameCol = new TableColumn<>("User");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<UserRepository, String>("username"));
        TableColumn<UserRepository, String> usernameCol = new TableColumn<>("Role");
        usernameCol.setCellValueFactory(new PropertyValueFactory<UserRepository, String>("role"));

        tableView.getItems().clear();
        if(tableView.getColumns().isEmpty())
            tableView.getColumns().addAll(firstNameCol, usernameCol);

        User user = new User(UserSingleton.getInstance().getLogin(), "", "");
        user.setAction("Update Table of Users");
        Client client = new Client();
        ArrayList<UserRepository> list = (ArrayList<UserRepository>)client.sendToServer(user);
        tableView.getItems().addAll(list);
        logger.info("Table of users initialized");

    }

    /**
     * Setting user from table as an admin
     * @param actionEvent
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void setAsAdmin(ActionEvent actionEvent) throws IOException, ClassNotFoundException, SQLException {

        UserRepository file = (UserRepository) tableView.getSelectionModel().getSelectedItem();


        User user = new User(file.getUsername(), "", "admin");
        user.setAction("Change to admin");
        Client client = new Client();
        ArrayList<UserRepository> list = (ArrayList<UserRepository>)client.sendToServer(user);

        TableColumn<UserRepository, String> firstNameCol = new TableColumn<>("User");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<UserRepository, String>("username"));
        TableColumn<UserRepository, String> usernameCol = new TableColumn<>("Role");
        usernameCol.setCellValueFactory(new PropertyValueFactory<UserRepository, String>("role"));

        tableView.getItems().clear();
        if(tableView.getColumns().isEmpty())
            tableView.getColumns().addAll(firstNameCol, usernameCol);

        tableView.getItems().addAll(list);
        logger.info("User " + file.getUsername() + " became an admin");

    }

    /**
     * Setting user from a table as a user
     * @param actionEvent
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void setAsUser(ActionEvent actionEvent) throws IOException, ClassNotFoundException, SQLException {

        UserRepository file = (UserRepository) tableView.getSelectionModel().getSelectedItem();


        User user = new User(file.getUsername(), "", "user");
        user.setAction("Change to user");
        Client client = new Client();
        ArrayList<UserRepository> list = (ArrayList<UserRepository>)client.sendToServer(user);

        TableColumn<UserRepository, String> firstNameCol = new TableColumn<>("User");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<UserRepository, String>("username"));
        TableColumn<UserRepository, String> usernameCol = new TableColumn<>("Role");
        usernameCol.setCellValueFactory(new PropertyValueFactory<UserRepository, String>("role"));

        tableView.getItems().clear();
        if(tableView.getColumns().isEmpty())
            tableView.getColumns().addAll(firstNameCol, usernameCol);

        tableView.getItems().addAll(list);
        logger.info("User " + file.getUsername() + " became a user");
    }

}
