package model;

import java.util.Objects;

/**
 * Our person model class is our Java representation of our SQL database objects
 * its data members directly connect with the columns in our SQL dataset
 * its functions (the getters and setters) will be used by the associated
 * person DAO class
 */
public class Person {
    /**
     * The below fields are the associated columns in our SQL database
     */
    private String personID;
    private String associatedUsername;
    private String firstName;
    private String lastName;
    private String gender;
    private String fatherID;
    private String motherID;
    private String spouseID;

    /**
     * This constructor initializes a Person object using the required parameters
     * and optional parameters FatherID, MotherID and SpouseID
     * @param personID
     * @param associatedUsername
     * @param firstName
     * @param lastName
     * @param gender
     * @param fatherID
     * @param motherID
     * @param spouseID
     */

    public Person(String personID, String associatedUsername, String firstName, String lastName,
                  String gender, String fatherID, String motherID, String spouseID) {
        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    /**
     * This constructor sets Father, Mother and spouse id= null
     * can update them later with an associated setter functions
     * @param personID
     * @param associatedUsername
     * @param firstName
     * @param lastName
     * @param gender
     */
    public Person(String personID, String associatedUsername, String firstName, String lastName,
                  String gender) {
        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = null;
        this.motherID = null;
        this.spouseID = null;
    }
    @Override
    public boolean equals(Object o){
        //check if we are comparing it with itself
        if(o == this){ return true;}
        Person p = (Person) o;
        return (Objects.equals(p.getPersonID(), this.getPersonID())) &&
                (Objects.equals(p.getAssociatedUsername(), this.getAssociatedUsername())) &&
                (Objects.equals(p.getFirstName(), this.getFirstName())) &&
                (Objects.equals(p.getLastName(), this.getLastName())) &&
                (Objects.equals(p.getGender(), this.getGender())) &&
                (Objects.equals(p.getFatherID(), this.getFatherID())) &&
                (Objects.equals(p.getMotherID(), this.getMotherID())) &&
                (Objects.equals(p.getSpouseID(), this.getSpouseID()));
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }
}
