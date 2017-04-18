package hibernate.Util;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import design.Register;
import org.apache.log4j.Logger;
import repositories.UserRepository;

import java.io.Serializable;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rask on 17.04.2017.
 */

/**
 * Connector between database of users and GUI interface. Gets a list of current users
 */
public class UsersDataAccessor implements Serializable{

    private final Logger logger = Logger.getLogger(Register.class);
    private Connection connection ;


    /**
     * Method to establish connection to the database.
     * @param dbURL URL to database.
     * @param user  username
     * @param password password
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    /**
     * Connecing to the database
     * @param dbURL adress
     * @param user login
     * @param password password
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public UsersDataAccessor(String dbURL, String user, String password) throws SQLException, ClassNotFoundException, SQLException {
        //Class.forName(driverClassName);
        connection = (Connection) DriverManager.getConnection(dbURL, user, password);
        logger.info("Connected to the database from UserDataAccessor");

    }

    /**
     * Method to close connection to the database.
     * @throws SQLException
     */
    public void shutdown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    /**
     * Creating list of all files to import it into table in 'ChangeRoles' window.
     *
     * @return list of files to integrate.
     * @throws SQLException
     */
    public ArrayList<UserRepository> getFileList() throws SQLException {

        Statement stmnt = connection.createStatement();
        ResultSet rs = stmnt.executeQuery("select username, role from users");
        {
            ArrayList<UserRepository> filesList = new ArrayList<>();
            while (rs.next()) {
                String username = rs.getString("username");
                String role = rs.getString("role");
                UserRepository file = new UserRepository(username, role);
                filesList.add(file);
            }
            logger.info("Returning list of users");
            return filesList ;
        }
    }

    /**
     * Creating list of files which comparable with search string.
     * @param name string to compare
     * @return list of files to integrate.
     * @throws SQLException
     */
    public ArrayList<UserRepository> getSearchFileList(String name) throws SQLException {

        String task = "select username, role from users where username like ? or role like ?";
        PreparedStatement stmnt = (PreparedStatement) connection.prepareStatement(task);
        stmnt.setString(1, "%" + name + "%");
        stmnt.setString(2, "%" + name + "%");
        ResultSet rs = stmnt.executeQuery();
        {
            ArrayList<UserRepository> filesList = new ArrayList<>();
            while (rs.next()){
                String fileName = rs.getString("username");
                String username = rs.getString("role");
                UserRepository file = new UserRepository(fileName, username);
                filesList.add(file);
            }
            return filesList ;
        }
    }
    
}
