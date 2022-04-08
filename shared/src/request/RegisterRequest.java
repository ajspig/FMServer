package request;

/**
 * request objects give what are service classes need
 * this gives our registerService class what it needs
 * our RegisterService is registering a user in our database
 */

public class RegisterRequest {
    /**
     * these are all the data members required for a RegisterRequest object
     * they are the required information fields for creating a new user in our database
     */
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;

    /**
     * constructor for our class
     * creates a register object and declares all of our private data members
     * @param username
     * @param password
     * @param email
     * @param firstName
     * @param lastName
     * @param gender
     */

    public RegisterRequest(String username, String password, String email,
                           String firstName, String lastName, String gender) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
