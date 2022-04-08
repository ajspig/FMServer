package result;

import model.Person;

/**
 * this class will return the result of querying all  the family members of the current user
 *
 */

public class PersonResult {
    /**
     * data is all the family members of user
     * success is where the action was successful or not
     * and message explains the failure
     */
    Person[] data;
    boolean success;
    String message;

    /**
     * if this action proved unsuccessful we will use this constructor
     * and use our message object to explain the error
     * @param success
     * @param message
     */

    public PersonResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    /**
     * this constructor is for our success state
     * creates a new RegisterResult object based on the parameters passed it
     * @param data
     * @param success
     */

    public PersonResult(Person[] data, boolean success) {
        this.data = data;
        this.success = success;
    }

    public Person[] getData() {
        return data;
    }

    public void setData(Person[] data) {
        this.data = data;
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
