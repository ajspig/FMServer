package result;

import com.google.gson.JsonObject;

/**
 * the result of clearing the database and adding loading user, person, and event data into the database
 */

public class LoadResult {
    /**
     * this two data members will be the result of adding these objects to our database
     */
    String message;
    Boolean success;

    /**
     * constructor will set message and success equal to error and false or success true
     * @param message
     * @param success
     */
    public LoadResult(Boolean success, String message) {
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
