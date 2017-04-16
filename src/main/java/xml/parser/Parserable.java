package xml.parser;

/**
 * Created by rask on 16.04.2017.
 */
public interface Parserable {
    Person[] parseFromXML(String path);
    Person parseFromXML(String path, int index);
}
