package registerService;

import dataAccess.AuthtokenDAO;
import dataAccess.Database;
import dataAccess.UserDAO;
import model.Authtoken;
import model.User;
import request.LoginRequest;
import result.LoginResult;
import result.RegisterResult;

import java.util.UUID;

/**
 * logs user in
 * return an authtoken
 */

public class LoginService {

    /**
     * @param l using l (a LoginRequest) object it logs the user in
     *          an authtoken the result of registering our object
     *          can give an internal server error
     */
    public LoginResult login(LoginRequest l) {
        Database db = new Database();
        try {
            db.openConnection();
            UserDAO userDAO = new UserDAO(db.getConnection());
            if (userDAO.validate(l.getUsername(), l.getPassword())) {
                String randToken = UUID.randomUUID().toString();
                Authtoken authtoken = new Authtoken(randToken, l.getUsername());
                AuthtokenDAO authDAO = new AuthtokenDAO(db.getConnection());
                authDAO.insert(authtoken);
                LoginResult result = new LoginResult(randToken, l.getUsername(), userDAO.find(l.getUsername()).getPersonID(), true);
                db.closeConnection(true);
                return result;
            } else {
                db.closeConnection(false);
                return new LoginResult(false, "Error User is not in database");
            }
        } catch (Exception ex) {
            db.closeConnection(false);
            return new LoginResult(false, "Error: " + ex.getMessage());
        }
    }
}
