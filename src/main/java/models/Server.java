package models;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

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
                String obj = actionForUser(user);
                return obj;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if(o instanceof Person){
            //actionForPersonDOM();
        }
        return o;
    }

    String actionForUser(User user) throws SQLException, IOException, ClassNotFoundException {

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

                UsersEntity userBD = new UsersEntity(user.getLogin(), user.getPassword(), 2);

                query = "insert into users (username, password, role)"
                        + " values (?, ?, ?)";

                PreparedStatement preparedStmt = (PreparedStatement) con.prepareStatement(query);
                preparedStmt.setString (1, user.getLogin());
                preparedStmt.setString (2, user.getPassword());
                preparedStmt.setInt(3, 2);
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
        return null;
    }

}
