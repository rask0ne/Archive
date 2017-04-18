import design.Login;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Server;

/**
 * Created by rask on 16.04.2017.
 */

/**
 * Starting server
 */
public class Main{

      public static void main(String[] args) throws Exception {

          Server server = new Server();
          new Thread(server).start();
    }

}
