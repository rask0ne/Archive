package design;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import models.UserSingleton;

import java.io.IOException;

/**
 * Created by rask on 18.04.2017.
 */
public class ShowProfile {

    @FXML
    private Label lblProfile;

    public void initialize() throws ClassNotFoundException, IOException {

        lblProfile.setText(UserSingleton.getInstance().getProfile());

    }
}
