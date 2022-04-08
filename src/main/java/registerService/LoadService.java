package registerService;

import dataAccess.*;
import model.User;
import request.LoadRequest;
import result.ClearResult;
import result.LoadResult;

/**
 * clears the data and then loads new data in
 */

public class LoadService {

    /**
     * @param l
     * input is a registered load object
     * clears all data from database (just like the /clear API)
     * loads the user, person and event data from request body into the database
     * @return
     * possible errorsL invalid request data, internal server error
     */
    public LoadResult load(LoadRequest l){
        Database db = new Database();

        try{
            db.openConnection();
            EventDAO eventDAO = new EventDAO(db.getConnection());
            PersonDAO personDAO = new PersonDAO(db.getConnection());
            UserDAO userDAO = new UserDAO(db.getConnection());
            AuthtokenDAO authtokenDAO = new AuthtokenDAO(db.getConnection());
            //do we want to clear the whole tables? or should we just clear for that user
            eventDAO.clear();
            personDAO.clear();
            userDAO.clear();
            authtokenDAO.clear();

            int numAddedUsers = l.getUsers().length;
            int numAddedPersons = l.getPersons().length;
            int numAddedEvents = l.getEvents().length;

            for(int i =0; i < numAddedUsers; i++){
                userDAO.insert(l.getUsers()[i]);
            }
            for(int i =0; i < numAddedPersons; i++){
                personDAO.insert(l.getPersons()[i]);
            }
            for(int i =0; i < numAddedEvents; i++){
                eventDAO.insert(l.getEvents()[i]);
            }
            db.closeConnection(true);
            return new LoadResult(true, "Successfully added "+ numAddedUsers + " users, "
                    + numAddedPersons + " persons, and " + numAddedEvents + " events to the database.");
        } catch (DataAccessException e){
            e.printStackTrace();
            db.closeConnection(false);
            return new LoadResult(false,"internal Server Error");
        }
    }
}
