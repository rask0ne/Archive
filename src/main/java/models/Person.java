package models;

/**
 * Created by rask on 14.04.2017.
 */
public class Person {

    /*(String firstName, String lastName, String fatherName, String telephoneNumber, String mail, String workPlace, int workExperience){*/

    String firstname;
    String lastname;
    String telephone;
    String email;
    String workPlace;
    String workExpirience;
    String skills;

    public Person(String firstName, String lastName, String telephoneNumber, String mail, String workPlace, String workExperience){

        this.firstname = firstName;
        this.lastname = lastName;
        this.telephone = telephoneNumber;
        this.email = mail;
        this.workPlace = workPlace;
        this.workExpirience = workExperience;

    }

}
