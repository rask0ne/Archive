package XMLDAO.Parsers;

import XMLDAO.Person;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Alexey on 15.04.2017.
 * The DOM parser for XML file. Uses as tool in XMLEditor
 */
public class XMLDOMParser implements Parserable {

    /**
     * Parse XML file for getting array of people, saving in file
     * @param path the path of xml file for parse
     * @return the array of people in xml file (path)
     */
    @Nullable
    public Person[] parseFromXML(String path) {
        try {
            File inputFile = new File(path);
            if (!inputFile.exists()){
                System.out.println("NOT EXIST!");
                return null;
            }
            System.out.println("EXIST");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("user");
            System.out.println(nList.getLength());
            Person[] people;
            if (nList.getLength() > 0) {
                people = new Person[nList.getLength()];
            }
            else {
                people = null;
            }
            for (int temp = 0; temp < nList.getLength(); temp++) {
                    people[temp] = parseFromXML(path, temp);
            }
            return people;
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
    @Nullable
    public Person parseFromXML(String path, int index) {
        try {
            String firstName = null;
            String lastName = null;
            String fatherName = null;
            String telephoneNumber = null;
            String mail = null;
            String workPlace = null;
            int workExperience = 0;

            File inputFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("user");
            if (index < 0 || index >= nList.getLength()){
                return null;
            }
            Node nNode = nList.item(index);
            Element user = (Element) nNode;
            NodeList fioList = doc.getElementsByTagName("FIO");
            Node fioNode = fioList.item(index);
            if (fioNode.getNodeType() == Node.ELEMENT_NODE) {
                firstName = getXMLArgument(fioNode, "firstname");
                lastName = getXMLArgument(fioNode, "lastname");
               // fatherName = getXMLArgument(fioNode, "fathername");
            }
            NodeList contactList = doc.getElementsByTagName("contact");
            Node contactNode = contactList.item(index);
            if (contactNode.getNodeType() == Node.ELEMENT_NODE) {
                telephoneNumber = getXMLArgument(contactNode, "telephonenumber");
                mail = getXMLArgument(contactNode, "mail");
            }
            NodeList workList = doc.getElementsByTagName("work");
            Node workNode = workList.item(index);
            if (workNode.getNodeType() == Node.ELEMENT_NODE) {
                workPlace = getXMLArgument(workNode, "workplace");
                workExperience = Integer.valueOf(getXMLArgument(nNode, "experience"));
            }
            System.out.println(nNode.getAttributes().getNamedItem("name").getLocalName());
            return new Person(((Element) nNode).getAttribute("name"),firstName, lastName/*, fatherName*/, telephoneNumber, mail, workPlace, workExperience);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * private method for getting value of attribute from Node in XML file
     * @param nNode the Node in XML file
     * @param attribute the name of attribute to getting it value
     * @return the value of this argument
     */
    @Nullable
    private static String getXMLArgument(Node nNode, String attribute) {
        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) nNode;
            return eElement.getElementsByTagName(attribute).item(0).getTextContent();
        }
        return null;
    }
    }
