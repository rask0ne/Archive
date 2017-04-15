package server;

import client.Client;
import client.models.User;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Person;
import models.UsersEntity;
import xml.parser.PersonDOM;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by rask on 15.04.2017.
 */
public class Server implements Runnable{

    ServerSocket listener = new ServerSocket(8000);
    Socket client;


    ObjectInputStream deserializer = new ObjectInputStream(client.getInputStream());
    ObjectOutputStream serializer = new ObjectOutputStream(client.getOutputStream());

    Person c = (Person)deserializer.readObject();

    public Server() throws IOException, ClassNotFoundException {
    }

    public void run() {
        while(true){
            try {
                client = listener.accept();
                Object o = getFromClient();
                checkObject(o);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendToClient(Object o) throws IOException {

        this.client = new Socket("",8000);
        ObjectOutputStream serializer = new ObjectOutputStream(client.getOutputStream());
        serializer.writeObject(o);
        serializer.flush();
    }

    public Object getFromClient() throws IOException, ClassNotFoundException {

        client = new Socket("",8000);
        ObjectInputStream deserializer = new ObjectInputStream(client.getInputStream());
        Object object = deserializer.readObject();
        return object;
    }

    void checkObject(Object o) throws SQLException {
        if(o instanceof User){
            User user = (User)o;
            actionForUser(user);
        }
        if(o instanceof PersonDOM){
            actionForPersonDOM();
        }
    }

    void actionForUser(User user) throws SQLException, IOException, ClassNotFoundException {

        if(user.getAction().equals("Check if registered")){

        }
        if(user.getAction().equals("Login")){

            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/archive", "root", "root");
            Statement stmt = (Statement) con.createStatement();
            String query = "SELECT Id, Username, Password, Role FROM users;";
            stmt.executeQuery(query);
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {

                String dbUsername = rs.getString("username");
                String dbPassword = rs.getString("password");

                if (user.getLogin().equals(dbUsername) && user.getPassword().equals(dbPassword)) {

                    String message = "Loggined Successfully";
                    Server server = new Server();
                    server.sendToClient(message);
                    User serverUser = new UsersEntity(username, password, 2);
                    int id = rs.getInt(("id"));
                    user.setId(id);
                    user.setUsername(username);
                    user.setPassword(password);
                    int role = rs.getInt(("role"));
                    user.setRole(role);

                    ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
                    Parent parent = FXMLLoader.load(getClass().getResource("Archive.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(parent);
                    stage.setScene(scene);
                    stage.setTitle("Catalog");
                    stage.show();
                    break;
                }
            }

        }
    }

}
