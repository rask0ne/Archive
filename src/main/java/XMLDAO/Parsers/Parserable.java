package XMLDAO.Parsers;

import XMLDAO.User;

/**
 * Created by Alexey on 15.04.2017.
 */
public interface Parserable {
    User[] parseFromXML(String path);
    User parseFromXML(String path, int index);
}
