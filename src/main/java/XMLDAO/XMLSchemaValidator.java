package XMLDAO;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by Alexey on 16.04.2017.
 * The class for checking validation of XML file with XML schema
 */
public class XMLSchemaValidator {

    /**
     * checking validation of XML file with XML schema
     * @param pathXml the path of the checking XML file
     * @param pathXsd the path of the XML schema file
     * @return true - if this XML file is validate with this XML schema
     *        false - if this XML file isn't validate with this XML schema
     * @throws FileNotFoundException if one of this files wasn't founded
     */
    public static boolean checkXMLforXSD(String pathXml, String pathXsd) throws FileNotFoundException {

        File xml = new File(pathXml);
        File xsd = new File(pathXsd);

        if (!xml.exists()) {
            throw new FileNotFoundException("Can't find xml file");
        }

        if (!xsd.exists()) {
            throw new FileNotFoundException("Can't find xsd schema file");
        }

        try {
            SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(xsd).newValidator().validate(new StreamSource(xml));
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
        return true;
    }
}
