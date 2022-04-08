package registerService;

import dataAccess.AuthtokenDAO;
import dataAccess.DataAccessException;
import dataAccess.Database;
import dataAccess.PersonDAO;
import model.Person;
import result.PersonIDResult;
import result.PersonResult;

/**
 * grabs a single person object based off ID
 */
public class PersonIDService {
    /**
     * Returns the single Person object with the specified ID (if the person is associated with the current user).
     * The current user is determined by the provided authtoken.
     * possible errors: Invalid auth token, Invalid personID parameter, Requested person does not belong to this user,
     * Internal server error
     * @return
     */
    public PersonIDResult personID(String authtoken, String personID){
        Database db = new Database();
        try {
            db.openConnection();
            AuthtokenDAO authtokenDAO = new AuthtokenDAO(db.getConnection());
            String username = authtokenDAO.findUser(authtoken);
            if(username == null){
                throw new DataAccessException("Invalid Authtoken");
            }

            PersonDAO personDAO = new PersonDAO(db.getConnection());
            Person personToReturn = personDAO.find(personID);
            if(personToReturn == null){
                throw new DataAccessException("Invalid PersonId parameter");
            }
            if(!username.equals(personToReturn.getAssociatedUsername())){
                throw new DataAccessException("Requested person does not belong to this user, Internal server error");
            }

            PersonIDResult personIDResult = new PersonIDResult(personToReturn.getAssociatedUsername(), personToReturn.getPersonID(),
                    personToReturn.getFirstName(), personToReturn.getLastName(), personToReturn.getGender(),
                    personToReturn.getFatherID(), personToReturn.getMotherID(), personToReturn.getSpouseID(),
                    true);
            db.closeConnection(true);
            return personIDResult;
        }catch(DataAccessException e){
            e.printStackTrace();
            db.closeConnection(false);
            return new PersonIDResult(false, "Error: "+e.getMessage());
        }
    }
}
