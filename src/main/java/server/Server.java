package server;

import client.models.User;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import models.UsersEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import xml.parser.Person;

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

    models.Person c = (models.Person)deserializer.readObject();

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
            } catch (SQLException e) {
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

            try {
                User user = (User)o;
                actionForUser(user);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if(o instanceof Person){
            //actionForPersonDOM();
        }
    }

    void actionForUser(User user) throws SQLException, IOException, ClassNotFoundException {

        Connection con;
        Statement stmt;
        if(user.getAction().equals("Check if registered")) {

            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/catalogdb", "root", "root");
            ;
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
                    Server server = new Server();
                    server.sendToClient(message);
                    break;

                }
            }

            if (check == true) {

                UsersEntity userDB = new UsersEntity(user.getLogin(), user.getPassword(), 2);

                SessionFactory sessionFactory = server.Util.HibernateUtil.getSessionFactory();
                Session session = sessionFactory.openSession();
                session.beginTransaction();

                //Save the employee in database
                session.save(user);

                //Commit the transaction
                session.getTransaction().commit();
                session.close();

                String message = "Registered Successfully";
                Server server = new Server();
                server.sendToClient(message);
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
                    Server server = new Server();
                    server.sendToClient(message);

                }
            }

        }
    }

}
