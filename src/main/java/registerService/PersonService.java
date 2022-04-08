package registerService;

import dataAccess.*;
import model.Authtoken;
import model.Person;
import model.User;
import result.PersonResult;

/**
 * returns all family members of current user
 */

public class PersonService {
    /**
     * Returns ALL family members of the current user. The current user is determined by the provided authtoken.
     * @return
     * possible errors: invalid auth token, internal server error
     */
    public PersonResult person(String authtoken){
        Database db = new Database();
        try{
            db.openConnection();
            AuthtokenDAO authDAO = new AuthtokenDAO(db.getConnection());
            String username = authDAO.findUser(authtoken);
            if(username == null){
                throw new DataAccessException("Invalid Authtoken");
            }

            PersonDAO personDAO = new PersonDAO(db.getConnection());
            Person[] listOfPersons = personDAO.findForUser(username).toArray(new Person[0]);

            db.closeConnection(true);
            return new PersonResult(listOfPersons,true);
        }catch(DataAccessException e){
            e.printStackTrace();
            db.closeConnection(false);
            return new PersonResult(false, "Error: "+e.getMessage());
        }
    }

}
