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
import design.Register;
import hibernate.Util.UsersDataAccessor;
import org.apache.log4j.Logger;
import repositories.UserRepository;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rask on 15.04.2017.
 */

/**
 * Server model in client-server app
 */
public class Server implements Runnable{

    private final Logger logger = Logger.getLogger(Register.class);

    ServerSocket listener = new ServerSocket(8000);
    Socket client;
    private XMLEditor xmlEditor = new XMLEditor("archive.xml", new XMLDOMParser(), "archive.zip");

    public Server() throws IOException, ClassNotFoundException {
    }

    /**
     * Listening to signals from server
     */
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

    /**
     * Getting object from client, processing it and sending result object back
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void getFromClient() throws IOException, ClassNotFoundException, SQLException {

        System.out.println("In fun");
        ObjectInputStream deserializer = new ObjectInputStream(client.getInputStream());
        Object object = deserializer.readObject();
        System.out.println("Got object");
        object = checkObject(object);
        ObjectOutputStream serializer = new ObjectOutputStream(client.getOutputStream());
        serializer.writeObject(object);
        serializer.flush();

    }

    /**
     * Method of process of object
     * @param o - object server got
     * @return
     * @throws SQLException
     */
    Object checkObject(Object o) throws SQLException {
        if(o instanceof User){

            try {
                System.out.println("instance of user");
                logger.info("Object is an instance of User class");
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
            logger.info("Object is an instanse of Person class");
            System.out.println("instance of Person");
            Person person = (Person)o;
            String obj = actionForPerson(person);
            return obj;
        }
        return o;
    }

    /**
     * Editing or adding new profile to xml file
     * @param person
     * @return
     */
    private String actionForPerson(Person person) {

        int index = xmlEditor.findIndexAsName(person.getName());
        if(index != -1){
            xmlEditor.edit(person, index);
            logger.info("Profile got edited");
            return "profile edited";
        }
        else {
            xmlEditor.addInEnd(person);
            logger.info("New profile added");
            return "Profile created successfully";
        }

    }

    /**
     * Getting actions from user object
     * @param user
     * @return
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     */
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
                    logger.info("Login is used");
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
                logger.info("new user registered");
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

                    if(xmlEditor.findIndexAsName(user.getLogin()) == -1) {
                        logger.info("User need to create new profile");
                        return "Create new profile";
                    }
                    else{
                        logger.info("Loggined successfully");
                        return "Loggined Successfully";
                    }

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

                    logger.info("User has rights to open Change Table");
                    String message = "Open Change Table";

                    return message;

                }
            }
            return "You are not an admin";
        }
        if(user.getAction().equals("Get DOM parser")){

            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/archive", "root", "root");
            stmt = (Statement) con.createStatement();
            String query = "SELECT username, role FROM users;";
            stmt.executeQuery(query);
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {

                String dbUsername = rs.getString("username");
                String dbRole = rs.getString("role");

                if (user.getLogin().equals(dbUsername) && dbRole.equals("admin")) {

                    xmlEditor.setParser(new XMLDOMParser());
                    String message = "DOM setted";
                    logger.info("DOM parser setted");

                    return message;

                }
            }
            return "You are not an admin";
        }
        if(user.getAction().equals("Get JDOM parser")){

            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/archive", "root", "root");
            stmt = (Statement) con.createStatement();
            String query = "SELECT username, role FROM users;";
            stmt.executeQuery(query);
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {

                String dbUsername = rs.getString("username");
                String dbRole = rs.getString("role");

                if (user.getLogin().equals(dbUsername) && dbRole.equals("admin")) {

                    xmlEditor.setParser(new XMLJDOMParser());
                    String message = "JDOM setted";
                    logger.info("JDOM parser setted");

                    return message;

                }
            }
            return "You are not an admin";
        }
        if(user.getAction().equals("Get SAX parser")){

            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/archive", "root", "root");
            stmt = (Statement) con.createStatement();
            String query = "SELECT id, username, role FROM users;";
            stmt.executeQuery(query);
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {

                String dbUsername = rs.getString("username");
                String dbRole = rs.getString("role");

                if (user.getLogin().equals(dbUsername) && dbRole.equals("admin")) {

                    xmlEditor.setParser(new XMLSAXParser());
                    String message = "SAX setted";
                    logger.info("SAX parser setted");

                    return message;

                }
            }
            return "You are not an admin";
        }
        if(user.getAction().equals("Get StAX parser")){

            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/archive", "root", "root");
            stmt = (Statement) con.createStatement();
            String query = "SELECT username, role FROM users;";
            stmt.executeQuery(query);
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {

                String dbUsername = rs.getString("username");
                String dbRole = rs.getString("role");

                if (user.getLogin().equals(dbUsername) && dbRole.equals("admin")) {

                    xmlEditor.setParser(new XMLStAXParser());
                    String message = "StAX setted";
                    logger.info("StAX parser setted");

                    return message;

                }
            }
            return "You are not an admin";
        }
        if(user.getAction().equals("Change to admin")){

            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/archive", "root", "root");
            stmt = (Statement) con.createStatement();
            String query = "SELECT username FROM users;";
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
                    logger.info("User changed to admin");

                    UsersDataAccessor access = new UsersDataAccessor("jdbc:mysql://localhost:3306/archive?useSSL=false", "root", "root");

                    return access.getFileList();

                }
            }
            return "You are not an admin";
        }
        if(user.getAction().equals("Change to user")){

            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/archive", "root", "root");
            stmt = (Statement) con.createStatement();
            String query = "SELECT username FROM users;";
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
                    logger.info("User changed to user");

                    UsersDataAccessor access = new UsersDataAccessor("jdbc:mysql://localhost:3306/archive?useSSL=false", "root", "root");

                    return access.getFileList();

                }
            }
            return "You are not an admin";
        }
        if(user.getAction().equals("Update Table of Users")){

            System.out.println("Update Users");
            UsersDataAccessor access = new UsersDataAccessor("jdbc:mysql://localhost:3306/archive?useSSL=false", "root", "root");
            logger.info("updating profiles");
            return access.getFileList();
        }
        if(user.getAction().equals("Get Profiles")){
            //xmlEditor.uncompress();
            //xmlEditor.validate("archive.xml");
            ArrayList<Person> list = xmlEditor.getList();
            //xmlEditor.compress(1);
            logger.info("Getting all profiles");
            return list;
        }
        if(user.getAction().equals("Show user profile")){

            //xmlEditor.uncompress();
           // xmlEditor.validate("archive.xml");
            logger.info("Getting one profile");
            Person person = xmlEditor.get(xmlEditor.findIndexAsName(user.getUserProfile()));
            //xmlEditor.compress(1);
            return person;
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
                    logger.info("Server accepted change of profile");
                    String message = "You have rights";
                    return message;
                }
            }
            return "you dont have rights";
        }
        if(user.getAction().equals("Delete user profile")) {
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/archive", "root", "root");
            stmt = (Statement) con.createStatement();
            String query = "SELECT username, role FROM users;";
            stmt.executeQuery(query);
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {

                String dbUsername = rs.getString("username");
                String dbRole = rs.getString("role");

                if (user.getLogin().equals(user.getUserProfile()) || (user.getLogin().equals(dbUsername)
                        && dbRole.equals("admin"))) {

                    //xmlEditor.uncompress();
                   // xmlEditor.validate("archive.xml");
                    xmlEditor.delete(xmlEditor.findIndexAsName(user.getUserProfile()));
                    logger.info("User profile got deleted");
                    //xmlEditor.validate("archive.xml");
                    //xmlEditor.compress(1);
                    String message = "deleted";
                    return message;
                }
            }
            return "you dont have rights";
        }

        return null;
    }

}
