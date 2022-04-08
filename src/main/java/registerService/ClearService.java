package registerService;
import dataAccess.*;
import result.ClearResult;
import result.RegisterResult;

/**
 * clears all data from database
 * including user, authtoken, person and event data
 */
public class ClearService {

    /**
     * clears all data from database
     * possible errors: internal server error
     */
    public ClearResult clear(){
        Database db = new Database();
        try{
            db.openConnection();
            EventDAO eventDAO = new EventDAO(db.getConnection());
            PersonDAO personDAO = new PersonDAO(db.getConnection());
            UserDAO userDAO = new UserDAO(db.getConnection());
            AuthtokenDAO authtokenDAO = new AuthtokenDAO(db.getConnection());

            eventDAO.clear();
            personDAO.clear();
            userDAO.clear();
            authtokenDAO.clear();


            db.closeConnection(true);
            return new ClearResult(true, "yay clear succeeded");
        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
            return new ClearResult(false, "Error" +e.getMessage());

        }
    }

}
