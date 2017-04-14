package xml.parser;

import models.Person;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by rask on 14.04.2017.
 */
public class PersonDOM {

    public static int XMLNONE = 0;
    public static int XMLBEGIN = 2;
    public static int XMLEND = 1;

    private String firstName;
    private String lastName;
    private String telephone;
    private String email;
    private String workPlace;
    private String workExperience;

    public PersonDOM(String firstName, String lastName, String telephoneNumber, String mail, String workPlace, String workExperience){
        this.firstName = firstName;
        this.lastName = lastName;
        this.telephone = telephoneNumber;
        this.email = mail;
        this.workPlace = workPlace;
        this.workExperience = workExperience;
    }

    @Override
    public String toString() {
        String result = "";
        result += "first Name - " + this.firstName + "\n";
        result += "last Name - " + this.lastName + "\n";
        result += "telephone Number - " + this.telephone + "\n";
        result += "e-mail - " + this.email + "\n";
        result += "Workplace - " + this.workPlace + "\n";
        result += "Experience - " + this.workExperience + "\n";
        return result;
    }

    @Nullable
    public static PersonDOM[] parseFromXML() {
        try {
            File inputFile = new File("input.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("user");
            PersonDOM[] persons = new PersonDOM[nList.getLength()];
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    String firstName = getXMLArgument(nNode,"firstname",XMLCONST.XMLNAME);
                    String lastName =  getXMLArgument(nNode,"lastname",XMLCONST.XMLNAME);

                    String telephoneNumber = getXMLArgument(nNode,"telephonenumber",XMLCONST.XMLPHONENUMBER);
                    String mail =  getXMLArgument(nNode,"mail",XMLCONST.XMLMAIl);
                    String workPlace =  getXMLArgument(nNode,"workplace",XMLCONST.XMLNONE);
                    String workExperience = getXMLArgument(nNode, "experience", XMLCONST.XMLEXPERIENCE);
                    persons[temp] = new PersonDOM(firstName,lastName, telephoneNumber,mail,workPlace, workExperience);
                }
            }
            return persons;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String parseToXML(int mask) {
        String result = "";
        if ((mask & XMLBEGIN) == XMLBEGIN){
            result += XMLBegin();
        }
        result += ("  <user>\n");
        result += ("      <firstname>" + this.firstName + "</firstname>\n");
        result += ("      <lastname>" + this.lastName + "</lastname>\n");
        result += ("      <telephonenumber>" + this.telephone + "</telephonenumber>\n");
        result += ("      <mail>" + this.email + "</mail>\n");
        result += ("      <workplace>" + this.workPlace + "</workplace>\n");
        result += ("      <experience>" + this.workExperience + "</experience>\n");
        result += ("  </user>\n");
        if ((mask & XMLEND) == XMLEND){
            result += XMLEnd();
        }
        return result;
    }

    public static String parseToXML(PersonDOM[] persons , int mask) {
        String result = "";
        if ((mask & XMLBEGIN) == XMLBEGIN) {
            result += XMLBegin();
        }
        if (persons != null) {
            for (PersonDOM person : persons) {
                if (person != null) {
                    result += person.parseToXML(XMLNONE);
                }
            }
        }
        if ((mask & XMLEND) == XMLEND) {
            result += XMLEnd();
        }
        return result;
    }

    private static class XMLCONST {
        public static int XMLNONE = 0;
        public static int XMLNAME = 1;
        public static int XMLMAIl = 2;
        public static int XMLPHONENUMBER = 4;
        public static int XMLEXPERIENCE = 8;
    }

    @Nullable
    private static String getXMLArgument(Node nNode, String attribute, int mask) {
        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) nNode;
            String result = eElement.getElementsByTagName(attribute).item(0).getTextContent();
            if ((mask & XMLCONST.XMLNAME) == XMLCONST.XMLNAME) {
                if (result.matches("^[a-zA-Z ]+$")) {
                    return result;
                } else {
                    return null;
                }
            }
            if ((mask & XMLCONST.XMLPHONENUMBER) == XMLCONST.XMLPHONENUMBER) {
                if (result.matches("^[+][0-9]+$")) {
                    return result;
                } else {
                    return null;
                }
            }
            if ((mask & XMLCONST.XMLMAIl) == XMLCONST.XMLMAIl) {
                if (result.matches(".*@([a-z0-9]([-a-z0-9]{0,61}[a-z0-9])?\\.)*(ru|aero|arpa|asia|biz|cat|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|net|org|pro|tel|travel|[a-z][a-z])$")) {
                    return result;
                } else {
                    return null;
                }
            }
            if ((mask & XMLCONST.XMLEXPERIENCE) == XMLCONST.XMLEXPERIENCE) {
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
