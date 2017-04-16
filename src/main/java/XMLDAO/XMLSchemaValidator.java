package XMLDAO;

import jdk.internal.org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;

/**
 * Created by Alexey on 16.04.2017.
 */
public class XMLSchemaValidator {

    public static boolean checkXMLforXSD(String pathXml, String pathXsd)
            throws Exception {

        File xml = new File(pathXml);
        File xsd = new File(pathXsd);

        if (!xml.exists()) {
            System.out.println("can't find xnl " + pathXml);
        }

        if (!xsd.exists()) {
            System.out.println("can't find xsd " + pathXsd);
        }

        if (!xml.exists() || !xsd.exists()) {
            return false;
        }

        SchemaFactory factory = SchemaFactory
                .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = factory.newSchema(new StreamSource(pathXsd));
        javax.xml.validation.Validator validator = schema.newValidator();
        validator.validate(new StreamSource(pathXml));
        return true;
    }
}
