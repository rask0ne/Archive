package xml.parser.DOM;

import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import xml.parser.CONST;
import xml.parser.Parserable;
import xml.parser.Person;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * Created by rask on 16.04.2017.
 */


public class DOM implements Parserable {

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
            Person[] users;
            if (nList.getLength() > 0) {
                users = new Person[nList.getLength()];
            }
            else {
                users = null;
            }
            for (int temp = 0; temp < nList.getLength(); temp++) {
                users[temp] = parseFromXML(path, temp);
            }
            return users;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    public Person parseFromXML(String path, int index) {
        try {
            String firstName = null;
            String lastName = null;
            String fatherName = null;
            String telephoneNumber = null;
            String mail = null;
            String workPlace = null;
            String workExperience = null;

            File inputFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("user");
            Node nNode = nList.item(index);
            NodeList fioList = doc.getElementsByTagName("FIO");
            Node fioNode = fioList.item(index);
            if (fioNode.getNodeType() == Node.ELEMENT_NODE) {
                firstName = getXMLArgument(fioNode, "firstname", CONST.XMLNAME);
                lastName = getXMLArgument(fioNode, "lastname", CONST.XMLNAME);
            }
            NodeList contactList = doc.getElementsByTagName("contact");
            Node contactNode = contactList.item(index);
            if (contactNode.getNodeType() == Node.ELEMENT_NODE) {
                telephoneNumber = getXMLArgument(contactNode, "telephonenumber", CONST.XMLPHONENUMBER);
                mail = getXMLArgument(contactNode, "mail", CONST.XMLMAIl);
            }
            NodeList workList = doc.getElementsByTagName("work");
            Node workNode = workList.item(index);
            if (workNode.getNodeType() == Node.ELEMENT_NODE) {
                workPlace = getXMLArgument(workNode, "workplace", CONST.XMLNONE);
                workExperience = getXMLArgument(nNode, "experience", CONST.XMLEXPERIENCE);
            }
            return new Person(firstName, lastName, telephoneNumber, mail, workPlace, workExperience);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    @Nullable
    private static String getXMLArgument(Node nNode, String attribute, int mask) {
        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) nNode;
            String result = eElement.getElementsByTagName(attribute).item(0).getTextContent();
            if ((mask & CONST.XMLNAME) == CONST.XMLNAME) {
                if (result.matches("^[a-zA-Z ]+$")) {
                    return result;
                } else {
                    return null;
                }
            }
            if ((mask & CONST.XMLPHONENUMBER) == CONST.XMLPHONENUMBER) {
                if (result.matches("^[+][0-9]+$")) {
                    return result;
                } else {
                    return null;
                }
            }
            if ((mask & CONST.XMLMAIl) == CONST.XMLMAIl) {
                if (result.matches(".*@([a-z0-9]([-a-z0-9]{0,61}[a-z0-9])?\\.)*(ru|aero|arpa|asia|biz|cat|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|net|org|pro|tel|travel|[a-z][a-z])$")) {
                    return result;
                } else {
                    return null;
                }
            }
            if ((mask & CONST.XMLEXPERIENCE) == CONST.XMLEXPERIENCE) {
                if (result.matches("^[0-9]+$")) {
                    return result;
                } else {
                    return "0";
                }
            }
            return result;
        }
        return null;
    }

    public static String XMLBegin(){
        return ("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n") + ("<class>\n");
    }

    public static String XMLEnd(){
        return ("</class>");
    }
}
