package repositories;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by rask on 17.04.2017.
 */

/**
 * Repository of profiles for table in main window of the archive
 */
public class XMLRepository {

    private final StringProperty username = new SimpleStringProperty(this, "username");
    public StringProperty usernameProperty() {
        return username ;
    }
    public final String getUsername() {
        return usernameProperty().get();
    }
    public final void setUsername(String username) {
        usernameProperty().set(username);
    }

    public XMLRepository() {}

    public XMLRepository(String username) {
        setUsername(username);
    }

}
