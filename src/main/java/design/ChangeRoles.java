package design;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import static javafx.application.Application.launch;

/**
 * Created by rask on 17.04.2017.
 */

/**
 * Logic of ChangeRoles window
 */
public class ChangeRoles extends Application{

    private final Logger logger = Logger.getLogger(Login.class);

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ChangeRoles.fxml"));

        primaryStage.setTitle("Change Roles");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();

        logger.info("Window 'ChangeRoles' created");
    }


    public static void main(String[] args) {
        launch(args);
    }

}
