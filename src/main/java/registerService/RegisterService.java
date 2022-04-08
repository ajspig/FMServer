package registerService;

import dataAccess.AuthtokenDAO;
import dataAccess.Database;
import dataAccess.UserDAO;
import model.Authtoken;
import model.User;
import request.RegisterRequest;
import result.RegisterResult;

import java.util.UUID;

/**
 * The Service classes actually do what the client is asking
 * Here we will create a new user in database, generate new family tree and create a new authoken
 */

public class RegisterService {

    /**
     * @param r
     * our input is a RegisterRequest object
     * creates new user account
     * Generates 4 generations of ancestor data for the new user (just like the /fill endpoint if called with a
     * generations value of 4 and this new user’s username as parameters)
     * logs user in
     * returns an authtoken the result of registering our object
     * it could fail if there is already a user with the same userName
     * @return RegisterResult
     */
    public RegisterResult register(RegisterRequest r){
        Database db = new Database();
        try{
            db.openConnection();
            //use DAOs to do requested operations
            UserDAO userDAO = new UserDAO(db.getConnection());
            if(userDAO.find(r.getUsername()) != null){
                db.closeConnection(false);
                return new RegisterResult(false, "error username already taken by another user");
            }

            User user = new User(r.getUsername(),r.getPassword(),r.getEmail(),r.getFirstName(),
                    r.getLastName(),r.getGender(), UUID.randomUUID().toString() );
            userDAO.insert(user);

            AuthtokenDAO authtokenDAO = new AuthtokenDAO(db.getConnection());
            String token = UUID.randomUUID().toString();
            Authtoken authtoken = new Authtoken(token, user.getUsername());
            authtokenDAO.insert(authtoken);

            FillService fillService = new FillService(r.getUsername());
            fillService.setUser(user);
            //set fillService generations
            fillService.setGenerations(5);
            fillService.generatePerson(r.getGender(),5,2000, db);

            db.closeConnection(true);
            return new RegisterResult(token,user.getUsername() , user.getPersonID(), true);
        }catch(Exception ex){
            ex.printStackTrace();
            db.closeConnection(false);
            return new RegisterResult(false, ex.getMessage());
        }
    }
}
