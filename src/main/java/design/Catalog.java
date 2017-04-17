package design;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

/**
 * Created by rask on 01.03.2017.
 */

/**
 * Logic of 'Catalog' window.
 */
public class Catalog extends Application {

    private final Logger logger = Logger.getLogger(Catalog.class);

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Catalog.fxml"));

        primaryStage.setTitle("Catalog");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        logger.info("Window 'Catalog' created");

    }

    public Catalog(Stage stage) throws Exception {

        //lblMessage.setText(UserRepository.getInstance().getName());
        start(stage);

    }



}
