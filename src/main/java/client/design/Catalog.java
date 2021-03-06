package client.design;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by rask on 01.03.2017.
 */
public class Catalog extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Archive.fxml"));

        primaryStage.setTitle("Catalog");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }
}
