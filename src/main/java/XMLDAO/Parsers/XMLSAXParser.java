package XMLDAO.Parsers;

import XMLDAO.Person;

import java.io.File;
import java.util.LinkedList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLSAXParser implements Parserable{

    /**
     * Parse XML file for getting array of people, saving in file
     * @param path the path of xml file for parse
     * @return the array of people in xml file (path)
     */
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
            return userhandler.getPeople();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Parse XML file for getting user with @param index from this xml file
     * @param path the path of xml file for parsing
     * @param index the index of getting user
     * @return the user with @param index
     */
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

    protected LinkedList <Person> people = new LinkedList<Person>();

    String name = null;
    String FirstName = null;
    String LastName = null;
    //String FatherName = null;
    String TelephoneNumber = null;
    String Email = null;
    String Workplace = null;
    String Experience = null;

    boolean bFirstName = false;
    boolean bLastName = false;
    //boolean bFatherName = false;
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
            name = attributes.getValue("name");
        } else if (qName.equalsIgnoreCase("FIO")) {
            bFIO = true;
        } else if (qName.equalsIgnoreCase("firstname")) {
            bFirstName = true;
            count++;
        } else if (qName.equalsIgnoreCase("lastname")) {
            bLastName = true;
            count++;
       /* } else if (qName.equalsIgnoreCase("fathername")) {
            bFatherName = true;
            count++;*/
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
        /*} else if (bFatherName) {
            FatherName = new String(ch, start, length);
            bFatherName = false;*/
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
        if (count == /*7*/ 6) {
            people.addLast(new Person(name, FirstName, LastName/*, FatherName*/, TelephoneNumber, Email, Workplace, Integer.valueOf(Experience)));
            bFirstName = false;
            bLastName = false;
           // bFatherName = false;
            bTelephoneNumber = false;
            bEmail = false;
            bWorkplace = false;
            bExperience = false;

            bFIO = false;
            bContact = false;
            bWork = false;

            FirstName = null;
            LastName = null;
            // FatherName = null;
            TelephoneNumber = null;
            Email = null;
            Workplace = null;
            Experience = null;

            count = 0;
        }
    }

    protected Person getUser (int index){
        if (index < 0 || index >= people.size()) {
            return null;
        }
        else {
            return people.get(index);
        }
    }

    protected Person[] getPeople(){
        Person list[] = new Person[people.size()];
        int i = 0;
        for (Person person : people){
            list[i++] = person;
        }
        return list;
    }
}
