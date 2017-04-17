package XMLDAO.Parsers;

import XMLDAO.Person;

/**
 * Created by Alexey on 15.04.2017.
 * The Interface of Parser. Using in XMLEditor
 */
public interface Parserable {
    /**
     * Parse XML file for getting array of people, saving in file
     * @param path the path of xml file for parse
     * @return the array of people in xml file (path)
     */
    Person[] parseFromXML(String path);

    /**
     * Parse XML file for getting user with @param index from this xml file
     * @param path the path of xml file for parsing
     * @param index the index of getting user
     * @return the user with @param index
     */
    Person parseFromXML(String path, int index);
}
