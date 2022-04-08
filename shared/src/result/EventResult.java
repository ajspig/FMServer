package result;

import model.Event;
/**
 * if the query is successful use associated success or failure constructors
 */

public class EventResult {
    /**
     * data will be all events for ALL family members of current user
     * success and message are connected with the success or failure of the query
     */
    private Event[] data;
    private boolean success;
    private String message;

    /**
     * constructor for if our event creation succeeded
     * creates a EventResult object
     * @param data
     * @param success
     */
    public EventResult(Event[] data, boolean success) {
        this.data = data;
        this.success = success;
    }

    /**
     * if our creation of our result objects failed we use this constructor
     * message will give an explination for the failure
     * @param success
     * @param message
     */

    public EventResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Event[] getData() {
        return data;
    }

    public void setData(Event[] data) {
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
