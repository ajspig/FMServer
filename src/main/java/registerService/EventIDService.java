package registerService;

import dataAccess.*;
import model.Event;
import model.Person;
import result.EventIDResult;
import result.PersonIDResult;

/**
 * Returns the single Event object with the specified ID (if the event is associated with the current user).
 * The current user is determined by the provided authtoken.
 */

public class EventIDService {
    /**
     * Returns the single Event object with the specified ID (if the event is associated with the current user).
     * The current user is determined by the provided authtoken.
     * @return
     * possible errors: invalid auth token, invalid eventID parameter, Requested event does not belong to this user,
     * Internal server error
     */
    public EventIDResult eventID(String authtoken, String eventID){
        Database db = new Database();
        try {
            db.openConnection();
            AuthtokenDAO authtokenDAO = new AuthtokenDAO(db.getConnection());
            String username = authtokenDAO.findUser(authtoken);

            if(username == null){
                throw new DataAccessException("Invalid Authtoken");
            }

            EventDAO eventDAO = new EventDAO(db.getConnection());
            Event eventToReturn = eventDAO.find(eventID);

            if(eventToReturn == null){
                throw new DataAccessException("Invalid EventID parameter");
            }

            if(!username.equals(eventToReturn.getAssociatedUsername())){
                throw new DataAccessException("Requested event does not belong to this user, Internal server error");
            }

            EventIDResult eventIDResult = new EventIDResult(eventToReturn.getAssociatedUsername(),eventToReturn.getEventID(),
                    eventToReturn.getPersonID(), eventToReturn.getLatitude(),
                    eventToReturn.getLongitude(), eventToReturn.getCountry(),
                    eventToReturn.getCity(), eventToReturn.getEventType(),
                    eventToReturn.getYear(),true);
            db.closeConnection(true);
            return eventIDResult;
        }catch(DataAccessException e){
            e.printStackTrace();
            db.closeConnection(false);
            return new EventIDResult(false, "Error:" + e.getMessage());
        }

    }
}
