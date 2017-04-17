package XMLDAO.Parsers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import XMLDAO.Person;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;


public class XMLJDOMParser implements Parserable {

    /**
     * Parse XML file for getting array of people, saving in file
     * @param path the path of xml file for parse
     * @return the array of people in xml file (path)
     */
    public Person[] parseFromXML(String path) {
        try {
            File inputFile = new File(path);
            if (!inputFile.exists()){
                return null;
            }
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(inputFile);
            Element classElement = document.getRootElement();
            List<Element> userList = classElement.getChildren();
            Person[] people = null;
            if (userList.size() > 0){
                people = new Person[userList.size()];
                for (int i = 0; i < userList.size(); i++){
                    people[i] = parseFromXML(path, i);
                }
            }
            return people;
        } catch (Exception e){
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
            String firstName = null;
            String lastName = null;
            String fatherName = null;
            String telephoneNumber = null;
            String mail = null;
            String workPlace = null;
            int workExperience = 0;

            File inputFile = new File(path);
            if (!inputFile.exists()){
                return null;
            }
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(inputFile);
            Element classElement = document.getRootElement();
            List<Element> userList = classElement.getChildren();
            if (index < 0 || index >= userList.size()){
                return null;
            }
            Element user = userList.get(index);
            Element FIO =  user.getChild("FIO");

            firstName = FIO.getChild("firstname").getText();
            lastName = FIO.getChild("lastname").getText();
            //fatherName = FIO.getChild("fathername").getText();

            Element contact = user.getChild("contact");

            telephoneNumber = contact.getChild("telephonenumber").getText();
            mail = contact.getChild("mail").getText();

            Element work = user.getChild("work");

            workPlace = work.getChild("workplace").getText();
            workExperience = Integer.valueOf(work.getChild("experience").getText());

            return new Person(user.getAttribute("name").getValue(),firstName,lastName/*,fatherName*/,telephoneNumber,mail,workPlace,workExperience);
        }catch(JDOMException e){
            e.printStackTrace();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        return null;
    }
}