package registerService;

import dataAccess.AuthtokenDAO;
import dataAccess.DataAccessException;
import dataAccess.Database;
import dataAccess.EventDAO;
import model.Event;
import result.EventResult;
import result.PersonResult;

/**
 * returns all events for all Family members of current user
 * current user is determined from the provided authtoken
 */

public class EventService {
    /**
     * returns all events for all Family members of current user
     * current user is determined from the provided authtoken
     * possible errors: inalid auth token, internal server error
     */
    public EventResult event(String authtoken){
        Database db = new Database();
        try{
            db.openConnection();
            AuthtokenDAO authDAO = new AuthtokenDAO(db.getConnection());
            String username = authDAO.findUser(authtoken);

            EventDAO eventDAO = new EventDAO(db.getConnection());
            Event[] listOfEvents = eventDAO.findForUser(username).toArray(new Event[0]);

            db.closeConnection(true);
            return new EventResult(listOfEvents, true);
        }catch(DataAccessException e){
            e.printStackTrace();
            db.closeConnection(false);
            return new EventResult(false, "Error" + e.getMessage());
        }
    }
}
