package result;
/**
 * this class returns the result of registering a new user in the database
 */
public class RegisterResult {
    /**
     * if successful our RegisterResult object will return the authoken for the user,
     * their username and personID and return success = true
     * if not successful (success = false) our RegisterResult object will return a message
     * explaining the error
     */
    String authtoken;
    String username;
    String personID;
    boolean success;
    String  message;

    /**
     * this constructor is for our success state
     * it doesnt ask for the message object because it wont need it
     * it creates a new RegisterResult object
     * @param authtoken
     * @param username
     * @param personID
     * @param success
     */

    public RegisterResult(String authtoken, String username, String personID, boolean success) {
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
        this.success = success;
    }

    /**
     * this constructor is for our failed state,
     * it will only take the success and the message data member
     * the message will return "Error: " followed by a description of the error
     * it creates a new RegisterResult object
     * @param success
     * @param message
     */
    public RegisterResult(boolean success, String message) {
        this.success = success;
        this.message = message;
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

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
