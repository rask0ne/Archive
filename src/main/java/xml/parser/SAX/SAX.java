package xml.parser.SAX;

import java.io.File;
import java.util.LinkedList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import xml.parser.Parserable;
import xml.parser.Person;

/**
 * Created by rask on 16.04.2017.
 */
public class SAX implements Parserable{

    @Override
    public Person[] parseFromXML(String path) {
        try {
            File inputFile = new File(path);
            if (!inputFile.exists()) {
                return null;
            }
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            UserHandler userhandler = new UserHandler();
            saxParser.parse(inputFile, userhandler);
            return userhandler.getUsers();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Person parseFromXML(String path, int index) {
        try {
            File inputFile = new File(path);
            if (!inputFile.exists()) {
                return null;
            }
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            UserHandler userhandler = new UserHandler();
            saxParser.parse(inputFile, userhandler);
            return userhandler.getUser(index);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

class UserHandler extends DefaultHandler {

    protected LinkedList <Person> users = new LinkedList<Person>();

    String FirstName = null;
    String LastName = null;
    String TelephoneNumber = null;
    String Email = null;
    String Workplace = null;
    String Experience = null;

    boolean bFirstName = false;
    boolean bLastName = false;
    boolean bTelephoneNumber = false;
    boolean bEmail = false;
    boolean bWorkplace = false;
    boolean bExperience = false;

    boolean bFIO = false;
    boolean bContact = false;
    boolean bWork = false;

    int count = 0;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        System.out.println("start checking - " + qName);
        if (qName.equalsIgnoreCase("user")) {
        } else if (qName.equalsIgnoreCase("FIO")) {
            bFIO = true;
        } else if (qName.equalsIgnoreCase("firstname")) {
            bFirstName = true;
            count++;
        } else if (qName.equalsIgnoreCase("lastname")) {
            bLastName = true;
            count++;
        } else if (qName.equalsIgnoreCase("contact")) {
            bContact = true;
        } else if (qName.equalsIgnoreCase("telephonenumber")) {
            bTelephoneNumber = true;
            count++;
        } else if (qName.equalsIgnoreCase("mail")) {
            bEmail = true;
            count++;
        } else if (qName.equalsIgnoreCase("work")) {
            bWork = true;
        } else if (qName.equalsIgnoreCase("workplace")) {
            bWorkplace = true;
            count++;
        } else if (qName.equalsIgnoreCase("experience")) {
            bExperience = true;
            count++;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("user")) {

        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {

        System.out.println("checking - " + new String(ch,start,length));
        if (bFirstName) {
            FirstName = new String(ch, start, length);
            bFirstName = false;
        } else if (bLastName) {
            LastName = new String(ch, start, length);
            bLastName = false;
        } else if (bTelephoneNumber) {
            TelephoneNumber = new String(ch, start, length);
            bTelephoneNumber = false;
        } else if (bEmail) {
            Email = new String(ch, start, length);
            bEmail = false;
        } else if (bWorkplace) {
            Workplace = new String(ch, start, length);
            bWorkplace = false;
        } else if (bExperience) {
            Experience = new String(ch, start, length);
            bExperience = false;
        } else if (bFIO) {
            bFIO = false;
        } else if (bContact) {
            bContact = false;
        } else if (bWork) {
            bWork = false;
        }
        if (count == 7) {
            users.addLast(new Person(FirstName, LastName, TelephoneNumber, Email, Workplace, Experience));
            bFirstName = false;
            bLastName = false;
            bTelephoneNumber = false;
            bEmail = false;
            bWorkplace = false;
            bExperience = false;

            bFIO = false;
            bContact = false;
            bWork = false;

            FirstName = null;
            LastName = null;
            TelephoneNumber = null;
            Email = null;
            Workplace = null;
            Experience = null;

            count = 0;
        }
    }

    protected Person getUser (int index){
        return users.get(index);
    }

    protected Person[] getUsers(){
        Person list[] = new Person[users.size()];
        int i = 0;
        for (Person user : users){
            list[i++] = user;
        }
        return list;
    }

}
