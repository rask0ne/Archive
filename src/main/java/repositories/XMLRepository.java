package repositories;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by rask on 17.04.2017.
 */
public class XMLRepository {

    private final StringProperty xmlName = new SimpleStringProperty(this, "fileName");
    public StringProperty xmlNameProperty() {
        return xmlName ;
    }
    public final String getFileName() {
        return xmlNameProperty().get();
    }
    public final void setXmlName(String fileName) {
        xmlNameProperty().set(fileName);
    }

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

    public XMLRepository(String fileName, String username) {
        setXmlName(fileName);
        setUsername(username);
    }

}
