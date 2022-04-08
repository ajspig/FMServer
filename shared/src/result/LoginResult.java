package result;

/**
 * the result of logging a user in
 */
public class LoginResult {
    /**
     * the data fields for both a successfully user login
     * and a failed user login
     */
    String authtoken;
    String username;
    String personID;
    Boolean success;
    String message;

    /**
     * constructor for the success state
     * creates a new loginResult object with the required parameters
     * @param authtoken
     * @param username
     * @param personID
     * @param success
     */
    public LoginResult(String authtoken, String username, String personID, Boolean success) {
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
        this.success = success;
    }

    /**
     * constructor for the failed state
     * message will have an explanation for the error
     * @param success
     * @param message
     */

    public LoginResult(Boolean success, String message) {
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

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
