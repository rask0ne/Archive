package XMLDAO;

import java.io.Serializable;

import static XMLDAO.XMLConstants.XMLPARSERCONST.XMLBEGIN;
import static XMLDAO.XMLConstants.XMLPARSERCONST.XMLEND;
import static XMLDAO.XMLConstants.XMLPARSERCONST.XMLNONE;
import static XMLDAO.Parsers.XMLDOMParser.XMLBegin;
import static XMLDAO.Parsers.XMLDOMParser.XMLEnd;


/**
 * Created by Alexey on 03.04.2017.
 */
public class User implements Serializable {
    private String firstName;
    private String lastName;
    private String fatherName;
    private String telephoneNumber;
    private String mail;
    private String workPlace;
    private int workExperience;

    public User(String firstName, String lastName, String fatherName, String telephoneNumber, String mail, String workPlace, int workExperience){
        this.firstName = firstName;
        this.lastName = lastName;
        this.fatherName = fatherName;
        this.telephoneNumber = telephoneNumber;
        this.mail = mail;
        this.workPlace = workPlace;
        this.workExperience = workExperience;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFatherName() {
        return fatherName;
    }
    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }
    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getWorkPlace() {
        return workPlace;
    }
    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public int getWorkExperience() {
        return workExperience;
    }
    public void setWorkExperience(int workExperience) {
        this.workExperience = workExperience;
    }


    public String parseToXML(int mask) {
        String result = "";
        if ((mask & XMLBEGIN) == XMLBEGIN){
            result += XMLBegin();
        }
        result += ("  <user>\n");
        result += ("    <FIO>\n");
        result += ("      <firstname>" + this.firstName + "</firstname>\n");
        result += ("      <lastname>" + this.lastName + "</lastname>\n");
        result += ("      <fathername>" + this.fatherName + "</fathername>\n");
        result += ("    </FIO>\n");
        result += ("    <contact>\n");
        result += ("      <telephonenumber>" + this.telephoneNumber + "</telephonenumber>\n");
        result += ("      <mail>" + this.mail + "</mail>\n");
        result += ("    </contact>\n");
        result += ("    <work>\n");
        result += ("      <workplace>" + this.workPlace + "</workplace>\n");
        result += ("      <experience>" + this.workExperience + "</experience>\n");
        result += ("    </work>\n");
        result += ("  </user>\n");
        if ((mask & XMLEND) == XMLEND){
            result += XMLEnd();
        }
        return result;
    }

    public static String parseToXML(User[] users, int mask) { //vtrusevich
        String result = "";
        if ((mask & XMLBEGIN) == XMLBEGIN) {
            result += XMLBegin();
        }
        if (users != null) {
            for (User user : users) {
                if (user != null) {
                    result += user.parseToXML(XMLNONE);
                }
            }
        }
        if ((mask & XMLEND) == XMLEND) {
            result += XMLEnd();
        }
        return result;
    }


    @Override
    public String toString() {
        String result = "";
        result += "first Name - " + this.firstName + "\n";
        result += "last Name - " + this.lastName + "\n";
        result += "father Name - " + this.fatherName + "\n";
        result += "telephone Number - " + this.telephoneNumber + "\n";
        result += "Mail - " + this.mail + "\n";
        result += "Workplace - " + this.workPlace + "\n";
        result += "Experience - " + this.workExperience + "\n";
        return result;
    }

}
