package dataAccess;

/**
 * as we handle our sql dm in our DAO classes if we get any bad data access errors
 * we will throw a DataAccessException
 * this class is where we will handle that error
 */
public class DataAccessException extends Exception{
    /**
     * constructor that extends Exception
     * @param message
     */
    public DataAccessException(String message) {
        super(message);
    }
    /**
     * another constructor that doesn't take a parameter
     */
    DataAccessException() {
        super();
    }
}
