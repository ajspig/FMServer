package MyTests;

import dataAccess.DataAccessException;
import dataAccess.Database;
import dataAccess.PersonDAO;
import model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import registerService.ClearService;
import registerService.PersonIDService;
import registerService.RegisterService;
import request.RegisterRequest;
import result.PersonIDResult;
import result.RegisterResult;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PersonIDServiceTest {
    private String username;
    private Database db;
    private PersonDAO personDAO;
    private Person testPerson;
    private String personID;


    @BeforeEach
    public void setUp() throws DataAccessException{
        username = "gale";
        personID = "ajspig123";
    }

    @Test
    public void personIDPass() throws DataAccessException{
        RegisterRequest request = new RegisterRequest(username, "password", "a@mail.com",
                "a", "b", "f");
        RegisterService registerService = new RegisterService();
        RegisterResult registerResult = registerService.register(request);
        db = new Database();
        Connection conn = db.getConnection();
        personDAO = new PersonDAO(conn);
        personDAO.clear();
        testPerson = new Person(personID, username, "julia", "pete", "f",
                "dad123", "mom123", "spouse123");
        personDAO.insert(testPerson);
        db.closeConnection(true);

        PersonIDService personIDService = new PersonIDService();
        PersonIDResult personIDResult = personIDService.personID(registerResult.getAuthtoken(),personID);
        assertTrue(personIDResult.isSuccess());
    }
    @Test
    public void personID (){
        new ClearService().clear();
        PersonIDService personIDService = new PersonIDService();
        PersonIDResult personIDResult = personIDService.personID("coolcoolnotreal", personID);
        assertFalse(personIDResult.isSuccess());
    }

}
