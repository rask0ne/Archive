package XMLDAO;

import java.io.Serializable;

import static XMLDAO.XMLConstants.XMLPARSERCONST.*;


/**
 * Created by rask on 07.04.2017.
 * Class of user, that was saving in the XML file on Server
 */
public class Person implements Serializable {
    private String name;
    private String firstName;
    private String lastName;
    private String fatherName;
    private String telephoneNumber;
    private String mail;
    private String workPlace;
    private int workExperience;

    /**
     * Constructor of this class. Check inputs Strings for correct work and initialize fields of object
     * @param firstName First Name of Person [a-Z]
     * @param lastName Last Name of Person [a-Z]
    // * @param fatherName Father Name of Person [a-Z]
     * @param telephoneNumber Telephone Number of Person '+'[0-9]
     * @param mail Mail adress of Person [0-9a-Z]@[gmail,mail,...]'.'[com,ru,...]$
     * @param workPlace The name of the workPlace of Person. Don't have checking
     * @param workExperience The work experience of Person. Don't have checking
     */
    public Person(String name, String firstName, String lastName/*, String fatherName*/, String telephoneNumber, String mail, String workPlace, int workExperience){
        this.name = name;
        if (checkName(firstName)) {
            this.firstName = firstName;
        }
        else {
            this.firstName = null;
        }
        if (checkName(lastName)) {
            this.lastName = lastName;
        }
        else {
            this.lastName = null;
        }
        /*if (checkName(fatherName)) {
            this.fatherName = fatherName;
        }
        else {
            this.fatherName = null;
        }*/
        if (checkNumber(telephoneNumber)) {
            this.telephoneNumber = telephoneNumber;
        }
        else {
            this.telephoneNumber = null;
        }
        if (checkMail(mail)) {
            this.mail = mail;
        }
        else {
            this.mail = null;
        }
        this.workPlace = workPlace;
        this.workExperience = workExperience;
    }

    /**
     * getting First Name of this Person
     * @return First Name of this Person
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * setting First Name of this Person
     * @param firstName First Name of this Person
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * getting Last Name of this Person
     * @return Last Name of this Person
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * setting Last Name of this Person
     * @param lastName Last Name of this Person
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * getting Father Name of this Person
     * @return Father Name of this Person
     */
  //  public String getFatherName() {
  //      return fatherName;
  //  }

    /**
     * setting Father Name of this Person
     * @param fatherName Father Name of this Person
     */
   // public void setFatherName(String fatherName) {
   //     this.fatherName = fatherName;
   // }

    /**
     * getting Telephone Number of this Person
     * @return Telephone Number of this Person
     */
    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    /**
     * setting Telephone Number of this Person
     * @param telephoneNumber Telephone Number of this Person
     */
    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    /**
     * getting mail adress of this Person
     * @return mail adress of this Person
     */
    public String getMail() {
        return mail;
    }

    /**
     * setting mail adress of this Person
     * @param mail mail adress of this Person
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * getting Work Place of this Person
     * @return Work Place of this Person
     */
    public String getWorkPlace() {
        return workPlace;
    }

    /**
     * setting Work Place of this Person
     * @param workPlace Work Place of this Person
     */
    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    /**
     * getting Work experience of this Person
     * @return Work experience of this Person
     */
    public int getWorkExperience() {
        return workExperience;
    }

    /**
     * setting Work experience of this Person
     * @param workExperience Work experience of this Person
     */
    public void setWorkExperience(int workExperience) {
        this.workExperience = workExperience;
    }


    /**
     * check correct of the Name (for FirstName, LastName, FatherName)
     * @param name the String with name
     * @return true - this String is name
     *        false - this String isn't name
     */
    private boolean checkName(String name) {
        return (name.matches("^[a-zA-Z ]+$"));
    }

    /**
     * check correct of the Telephone Number (for Telephone Number)
     * @param number the String with Telephone Number
     * @return true - this String is Telephone Number
     *        false - this String isn't Telephone Number
     */
    private boolean checkNumber(String number) {
        return (number.matches("^[+][0-9]+$"));
    }

    /**
     * check correct of the mail adress (for Mail)
     * @param mail the String with mail adress
     * @return true - this String is mail adress
     *        false - this String isn't mail adress
     */
    private boolean checkMail(String mail) {
        return (mail.matches(".*@([a-z0-9]([-a-z0-9]{0,61}[a-z0-9])?\\.)*(ru|aero|arpa|asia|biz|cat|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|net|org|pro|tel|travel|[a-z][a-z])$"));
    }

    /**
     * method for transform object of Person into XML format
     * @param mask int value for adding bounders:
     *             XMLBEGIN - up bound
     *             XMLEND   - down bound
     *             XMLNONE  - none of the above
     * @return the result of transform to XML
     */
    public String parseToXML(int mask) {
        String result = "";
        if ((mask & XMLBEGIN) == XMLBEGIN){
            result += XMLBegin();
        }
        result += ("  <user name=\"" + this.name + "\">\n");
        result += ("    <FIO>\n");
        result += ("      <firstname>" + this.firstName + "</firstname>\n");
        result += ("      <lastname>" + this.lastName + "</lastname>\n");
      //  result += ("      <fathername>" + this.fatherName + "</fathername>\n");
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

    /**
     * static method for transform the array of objects into XML format
     * @param people the array with Person objects
     * @param mask int value for adding bounders:
     *             XMLBEGIN - up bound
     *             XMLEND   - down bound
     *             XMLNONE  - none of the above
     * @return the result of transforming into XML format
     */
    public static String parseToXML(Person[] people, int mask) { //vtrusevich
        String result = "";
        if ((mask & XMLBEGIN) == XMLBEGIN) {
            result += XMLBegin();
        }
        if (people != null) {
            for (Person person : people) {
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


    /**
     * method for transforming Person into String
     * @return the result of this transforming
     */
    @Override
    public String toString() {
        String result = "";
        result += "name - " + this.name + "\n";
        result += "first Name - " + this.firstName + "\n";
        result += "last Name - " + this.lastName + "\n";
   //     result += "father Name - " + this.fatherName + "\n";
        result += "telephone Number - " + this.telephoneNumber + "\n";
        result += "Mail - " + this.mail + "\n";
        result += "Workplace - " + this.workPlace + "\n";
        result += "Experience - " + this.workExperience + "\n";
        return result;
    }

    public String toShow(){
        return (this.name + "\t" + this.firstName + "\t" + this.lastName + "\t" + this.telephoneNumber + "\t" + this.mail + "\t" + this.workPlace + "\t" + this.workExperience);
    }

    /**
     * method for equals with other Person
     * @param person the other object of the Person
     * @return true - if this Person equals other Person
     *        false - if this Person not equals other Person
     */
    public boolean equals(Person person){
        return (this.getFirstName().equals(person.getFirstName()) &&
                this.getLastName().equals(person.getLastName()) &&
        //        this.getFatherName().equals(person.getFatherName()) &&
                this.getTelephoneNumber().equals(person.getTelephoneNumber()) &&
                this.getMail().equals(person.getMail()) &&
                this.getWorkPlace().equals(person.getWorkPlace()) &&
                this.getWorkExperience() == person.getWorkExperience());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
