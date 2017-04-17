package XMLDAO.Parsers;

import XMLDAO.Person;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class XMLStAXParser implements Parserable {

    /**
     * Parse XML file for getting array of people, saving in file
     * @param path the path of xml file for parse
     * @return the array of people in xml file (path)
     */
    public Person[] parseFromXML(String path) {
        File inputFile = new File(path);
        if (!inputFile.exists()) {
            return null;
        }
        Handler handler = new Handler();
        handler.startParsing(path);
        return handler.getPeople();
    }

    /**
     * Parse XML file for getting user with @param index from this xml file
     * @param path the path of xml file for parsing
     * @param index the index of getting user
     * @return the user with @param index
     */
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

        LinkedList<Person> people = new LinkedList<Person>();

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
                                name = startElement.getAttributeByName(new QName("name")).getValue();
                            } else if (qName.equalsIgnoreCase("FIO")) {
                                bFIO = true;
                            } else if (qName.equalsIgnoreCase("firstname")) {
                                bFirstName = true;
                            } else if (qName.equalsIgnoreCase("lastname")) {
                                bLastName = true;
                           /* } else if (qName.equalsIgnoreCase("fathername")) {
                                bFatherName = true;*/
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
                            /*if (bFatherName) {
                                FatherName = characters.getData();
                                bFatherName = false;
                            }*/
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
                                people.addLast(new Person(name, FirstName, LastName/*, FatherName*/, TelephoneNumber, Email, Workplace, Integer.valueOf(Experience)));
                                bFirstName = false;
                                bLastName = false;
                                //bFatherName = false;
                                bTelephoneNumber = false;
                                bEmail = false;
                                bWorkplace = false;
                                bExperience = false;

                                bFIO = false;
                                bContact = false;
                                bWork = false;

                                FirstName = null;
                                LastName = null;
                                //FatherName = null;
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
            if (index < 0 || index >= people.size()) {
                return null;
            }
            else {
                return people.get(index);
            }
        }

        protected Person[] getPeople() {
            Person list[] = new Person[people.size()];
            int i = 0;
            for (Person person : people) {
                list[i++] = person;
            }
            return list;
        }
    }
