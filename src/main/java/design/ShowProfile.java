package design;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import models.UserSingleton;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created by rask on 18.04.2017.
 */

/**
 * Simple class just to show profile info
 */
public class ShowProfile {

    private final Logger logger = Logger.getLogger(Register.class);

    @FXML
    private Label lblProfile;

    /**
     * Initializing ShowProfile object
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public void initialize() throws ClassNotFoundException, IOException {

        logger.info("Here is chosen profile!");
        lblProfile.setText(UserSingleton.getInstance().getProfile());

    }
}
