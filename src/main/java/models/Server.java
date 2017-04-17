package models;

import XMLDAO.Parsers.XMLDOMParser;
import XMLDAO.Parsers.XMLJDOMParser;
import XMLDAO.Parsers.XMLSAXParser;
import XMLDAO.Parsers.XMLStAXParser;
import XMLDAO.Person;
import XMLDAO.XMLEditor;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import hibernate.Util.UsersDataAccessor;
import repositories.UserRepository;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by rask on 15.04.2017.
 */
public class Server implements Runnable{

    ServerSocket listener = new ServerSocket(8000);
    Socket client;
    private XMLEditor xmlEditor = new XMLEditor("archive.xml", new XMLDOMParser(), "archive.zip");

    public Server() throws IOException, ClassNotFoundException {
    }

    public void run() {

        while(true){
            try {
                client = listener.accept();
                System.out.println("Got signal");
                getFromClient();


            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendToClient(Object o) throws IOException {

        this.client = new Socket("",8000);

    }

    public void getFromClient() throws IOException, ClassNotFoundException, SQLException {

        System.out.println("In fun");
        //client = new Socket("",8000);
        ObjectInputStream deserializer = new ObjectInputStream(client.getInputStream());
        Object object = deserializer.readObject();
        System.out.println("Got object");
        object = checkObject(object);
        ObjectOutputStream serializer = new ObjectOutputStream(client.getOutputStream());
        serializer.writeObject(object);
        serializer.flush();

    }

    Object checkObject(Object o) throws SQLException {
        if(o instanceof User){

            try {
                System.out.println("instance of user");
                User user = (User)o;
                Object obj = actionForUser(user);
                return obj;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if(o instanceof Person){
            System.out.println("instance of Person");
            Person person = (Person)o;
            String obj = actionForPerson(person);
            return obj;
        }
        return o;
    }

    private String actionForPerson(Person person) {

        int index = xmlEditor.findIndexAsName(person.getName());
        if(index != -1){
            xmlEditor.edit(person, index);
            return "profile edited";
        }
        else {
            xmlEditor.addInEnd(person);
            return "Profile created successfully";
        }

    }

    Object actionForUser(User user) throws SQLException, IOException, ClassNotFoundException {

        Connection con;
        Statement stmt;
        System.out.println(user.getAction());
        if(user.getAction().equals("Check if registered")) {

            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/archive", "root", "root");

            stmt = (Statement) con.createStatement();
            String query = "SELECT Username FROM users;";
            stmt.executeQuery(query);
            ResultSet rs = stmt.getResultSet();
            boolean check = true;
            while (rs.next()) {
                String DBusername = rs.getString("Username");
                if (user.getLogin().equals(DBusername)) {

                    check = false;
                    String message = "Login is already used";

                    return message;

                }
            }

            if (check == true) {

                UsersEntity userBD = new UsersEntity(user.getLogin(), user.getPassword(), "user");

                query = "insert into users (username, password, role)"
                        + " values (?, ?, ?)";

                PreparedStatement preparedStmt = (PreparedStatement) con.prepareStatement(query);
                preparedStmt.setString (1, user.getLogin());
                preparedStmt.setString (2, user.getPassword());
                preparedStmt.setString(3, "user");
                preparedStmt.execute();

                String message = "Registered Successfully";
                System.out.println("registered");
                return message;
            }
        }
        if(user.getAction().equals("Login")){

            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/archive", "root", "root");
            stmt = (Statement) con.createStatement();
            String query = "SELECT Id, Username, Password, Role FROM users;";
            stmt.executeQuery(query);
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {

                String dbUsername = rs.getString("username");
                String dbPassword = rs.getString("password");

                if (user.getLogin().equals(dbUsername) && user.getPassword().equals(dbPassword)) {

                    String message = "Loggined Successfully";

                    return message;

                }
            }
        }
        if(user.getAction().equals("Change Roles")){

            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/archive", "root", "root");
            stmt = (Statement) con.createStatement();
            String query = "SELECT Id, Username, Role FROM users;";
            stmt.executeQuery(query);
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {

                String dbUsername = rs.getString("username");
                String dbRole = rs.getString("role");

                if (user.getLogin().equals(dbUsername) && dbRole.equals("admin")) {


                    String message = "Open Change Table";

                    return message;

                }
            }
            return "You are not an admin";
        }
        if(user.getAction().equals("Get DOM parser")){

            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/archive", "root", "root");
            stmt = (Statement) con.createStatement();
            String query = "SELECT Id, Username, Role FROM users;";
            stmt.executeQuery(query);
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {

                String dbUsername = rs.getString("username");
                String dbRole = rs.getString("role");

                if (user.getLogin().equals(dbUsername) && dbRole.equals("admin")) {

                    xmlEditor.setParser(new XMLDOMParser());
                    String message = "DOM setted";

                    return message;

                }
            }
            return "You are not an admin";
        }
        if(user.getAction().equals("Get JDOM parser")){

            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/archive", "root", "root");
            stmt = (Statement) con.createStatement();
            String query = "SELECT Id, Username, Role FROM users;";
            stmt.executeQuery(query);
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {

                String dbUsername = rs.getString("username");
                String dbRole = rs.getString("role");

                if (user.getLogin().equals(dbUsername) && dbRole.equals("admin")) {

                    xmlEditor.setParser(new XMLJDOMParser());
                    String message = "JDOM setted";

                    return message;

                }
            }
            return "You are not an admin";
        }
        if(user.getAction().equals("Get SAX parser")){

            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/archive", "root", "root");
            stmt = (Statement) con.createStatement();
            String query = "SELECT Id, Username, Role FROM users;";
            stmt.executeQuery(query);
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {

                String dbUsername = rs.getString("username");
                String dbRole = rs.getString("role");

                if (user.getLogin().equals(dbUsername) && dbRole.equals("admin")) {

                    xmlEditor.setParser(new XMLSAXParser());
                    String message = "SAX setted";

                    return message;

                }
            }
            return "You are not an admin";
        }
        if(user.getAction().equals("Get StAX parser")){

            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/archive", "root", "root");
            stmt = (Statement) con.createStatement();
            String query = "SELECT Id, Username, Role FROM users;";
            stmt.executeQuery(query);
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {

                String dbUsername = rs.getString("username");
                String dbRole = rs.getString("role");

                if (user.getLogin().equals(dbUsername) && dbRole.equals("admin")) {

                    xmlEditor.setParser(new XMLStAXParser());
                    String message = "StAX setted";

                    return message;

                }
            }
            return "You are not an admin";
        }
        if(user.getAction().equals("Change to admin")){

            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/archive", "root", "root");
            stmt = (Statement) con.createStatement();
            String query = "SELECT Id, Username, Role FROM users;";
            stmt.executeQuery(query);
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {

                String dbUsername = rs.getString("username");

                if (user.getLogin().equals(dbUsername)) {

                    query = "UPDATE users SET role= ? WHERE username = ? ";

                    PreparedStatement preparedStmt = (PreparedStatement) con.prepareStatement(query);
                    preparedStmt.setString (1, user.getRole());
                    preparedStmt.setString (2, user.getLogin());
                    preparedStmt.execute();
                    String message = "Changed to admin. Welcome!";
                    UsersDataAccessor access = new UsersDataAccessor("jdbc:mysql://localhost:3306/archive?useSSL=false", "root", "root");

                    return access.getFileList();

                }
            }
            return "You are not an admin";
        }
        if(user.getAction().equals("Change to user")){

            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/archive", "root", "root");
            stmt = (Statement) con.createStatement();
            String query = "SELECT Id, Username, Role FROM users;";
            stmt.executeQuery(query);
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {

                String dbUsername = rs.getString("username");
                String dbRole = rs.getString("role");

                if (user.getLogin().equals(dbUsername)) {

                    query = "UPDATE users SET role= ? WHERE username = ? ";

                    PreparedStatement preparedStmt = (PreparedStatement) con.prepareStatement(query);
                    preparedStmt.setString (1, user.getRole());
                    preparedStmt.setString (2, user.getLogin());
                    preparedStmt.execute();
                    String message = "Changed to user. Welcome!";

                    UsersDataAccessor access = new UsersDataAccessor("jdbc:mysql://localhost:3306/archive?useSSL=false", "root", "root");

                    return access.getFileList();

                }
            }
            return "You are not an admin";
        }
        if(user.getAction().equals("Update Table of Users")){

            System.out.println("Update Users");
            UsersDataAccessor access = new UsersDataAccessor("jdbc:mysql://localhost:3306/archive?useSSL=false", "root", "root");

            return access.getFileList();
        }
        if(user.getAction().equals("Get Profiles")){
            return xmlEditor.getList();
        }
        if(user.getAction().equals("Show user profile")){

            return xmlEditor.get(xmlEditor.findIndexAsName(user.getUserProfile()));
        }
        if(user.getAction().equals("Change user profile")) {
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/archive", "root", "root");
            stmt = (Statement) con.createStatement();
            String query = "SELECT Username, Role FROM users;";
            stmt.executeQuery(query);
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {

                String dbUsername = rs.getString("username");
                String dbRole = rs.getString("role");

                if (user.getLogin().equals(user.getUserProfile()) || (user.getLogin().equals(dbUsername)
                        && dbRole.equals("admin"))) {

                    String message = "You have rights";
                    return message;
                }
            }
            return "you dont have rights";
        }

        return null;
    }

}
