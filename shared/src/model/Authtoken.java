package model;

/**
 * The auth token model class is just how Java will store the
 * objects it gets from sql
 * it has two data members, authtoken and username (these directly associate
 * with columns from SQL table)
 * These functions will be used by the associated authtoken DAO class
 */

public class Authtoken {
    /**
     * The below fields are the associated columns in our SQL dataset
     */
    private String authtoken;
    private String username;

    /**
     * This is our constructor for the Authtoken class
     * We create an Authtoken class by giving it our authtoken and username data members
     * Authtoken and a username are the two required objects for Authtoken objects
     * @param authtoken
     * @param username
     */

    public Authtoken(String authtoken, String username) {
        this.authtoken = authtoken;
        this.username = username;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
