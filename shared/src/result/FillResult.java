package result;

/**
 * the result of filling the database with generated data for a specified username
 */

public class FillResult {
    /**
     * this two data members will be the result of adding these objects to our database
     * either a failure or success
     */
    String message;
    Boolean success;

    /**
     * constructor will set message and success equal to error and false or success true
     * @param message
     * @param success
     */
    public FillResult(Boolean success, String message) {
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
