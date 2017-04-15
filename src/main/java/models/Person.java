package models;

import java.io.Serializable;

/**
 * Created by rask on 14.04.2017.
 */
public class Person implements Serializable {

    String firstname;
    String lastname;
    String telephone;
    String email;
    String workPlace;
    String workExpirience;

    public Person(String firstName, String lastName, String telephoneNumber, String mail, String workPlace, String workExperience){

        this.firstname = firstName;
        this.lastname = lastName;
        this.telephone = telephoneNumber;
        this.email = mail;
        this.workPlace = workPlace;
        this.workExpirience = workExperience;

    }

}
