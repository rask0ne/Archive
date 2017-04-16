package xml.parser.StAX;

import xml.parser.Parserable;
import xml.parser.Person;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;
import java.util.LinkedList;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 * Created by rask on 16.04.2017.
 */
public class StAX implements Parserable{

    @Override
    public Person[] parseFromXML(String path) {
        File inputFile = new File(path);
        if (!inputFile.exists()) {
            return null;
        }
        Handler handler = new Handler();
        handler.startParsing(path);
        return handler.getUsers();
    }

    @Override
    public Person parseFromXML(String path, int index) {
        File inputFile = new File(path);
        if (!inputFile.exists()) {
            return null;
        }
        Handler handler = new Handler();
        handler.startParsing(path);
        return handler.getUser(index);
    }
}
class Handler {

    LinkedList<Person> users = new LinkedList<Person>();

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

    public void startParsing(String path) {
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();

            XMLEventReader eventReader = factory.createXMLEventReader(new FileReader(path));
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                switch (event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT:
                        StartElement startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();
                        if (qName.equalsIgnoreCase("user")) {
                        } else if (qName.equalsIgnoreCase("FIO")) {
                            bFIO = true;
                        } else if (qName.equalsIgnoreCase("firstname")) {
                            bFirstName = true;
                        } else if (qName.equalsIgnoreCase("lastname")) {
                            bLastName = true;
                        } else if (qName.equalsIgnoreCase("contact")) {
                            bContact = true;
                        } else if (qName.equalsIgnoreCase("telephonenumber")) {
                            bTelephoneNumber = true;
                        } else if (qName.equalsIgnoreCase("mail")) {
                            bEmail = true;
                        } else if (qName.equalsIgnoreCase("work")) {
                            bWork = true;
                        } else if (qName.equalsIgnoreCase("workplace")) {
                            bWorkplace = true;
                        } else if (qName.equalsIgnoreCase("experience")) {
                            bExperience = true;
                        }
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        Characters characters = event.asCharacters();
                        if (bFirstName) {
                            FirstName = characters.getData();
                            bFirstName = false;
                        }
                        if (bLastName) {
                            LastName = characters.getData();
                            bLastName = false;
                        }
                        if (bTelephoneNumber) {
                            TelephoneNumber = characters.getData();
                            bTelephoneNumber = false;
                        }
                        if (bEmail) {
                            Email = characters.getData();
                            bEmail = false;
                        }
                        if (bWorkplace) {
                            Workplace = characters.getData();
                            bWorkplace = false;
                        }
                        if (bExperience) {
                            Experience = characters.getData();
                            bExperience = false;
                        }
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        EndElement endElement = event.asEndElement();
                        if (endElement.getName().getLocalPart().equalsIgnoreCase("user")) {
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
                        }
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    protected Person getUser(int index) {
        return users.get(index);
    }

    protected Person[] getUsers() {
        Person list[] = new Person[users.size()];
        int i = 0;
        for (Person user : users) {
            list[i++] = user;
        }
        return list;
    }

}
