package XMLDAO.XMLConstants;

/**
 * Created by rask on 15.04.2017.
 * Class with Constants for transform Person into XML format
 */
public class XMLPARSERCONST{
    public static final int XMLNONE = 0;
    public static final int XMLBEGIN = 2;
    public static final int XMLEND = 1;

    /**
     * getting the String of Begin of the XML file
     * @return the String of Begin of the XML file
     */
    public static String XMLBegin(){
        return ("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n") + ("<class>\n");
    }

    /**
     * getting the String of End of the XML file
     * @return the String of End of the XML file
     */
    public static String XMLEnd(){
        return ("</class>");
    }
}
