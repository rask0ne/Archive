package XMLDAO.Parsers;

import XMLDAO.User;

import java.io.File;
import java.util.LinkedList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLSAXParser implements Parserable{

    @Override
    public User[] parseFromXML(String path) {
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
    public User parseFromXML(String path, int index) {
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

    protected LinkedList <User> users = new LinkedList<User>();

    String FirstName = null;
    String LastName = null;
    String FatherName = null;
    String TelephoneNumber = null;
    String Email = null;
    String Workplace = null;
    String Experience = null;

    boolean bFirstName = false;
    boolean bLastName = false;
    boolean bFatherName = false;
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
        } else if (qName.equalsIgnoreCase("fathername")) {
            bFatherName = true;
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
        } else if (bFatherName) {
            FatherName = new String(ch, start, length);
            bFatherName = false;
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
            users.addLast(new User(FirstName, LastName, FatherName, TelephoneNumber, Email, Workplace, Integer.valueOf(Experience)));
            bFirstName = false;
            bLastName = false;
            bFatherName = false;
            bTelephoneNumber = false;
            bEmail = false;
            bWorkplace = false;
            bExperience = false;

            bFIO = false;
            bContact = false;
            bWork = false;

            FirstName = null;
            LastName = null;
            FatherName = null;
            TelephoneNumber = null;
            Email = null;
            Workplace = null;
            Experience = null;

            count = 0;
        }
    }

    protected User getUser (int index){
        return users.get(index);
    }

    protected User[] getUsers(){
        User list[] = new User[users.size()];
        int i = 0;
        for (User user : users){
            list[i++] = user;
        }
        return list;
    }
}
