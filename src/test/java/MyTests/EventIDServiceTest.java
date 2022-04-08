package MyTests;

import dataAccess.DataAccessException;
import dataAccess.Database;
import dataAccess.EventDAO;
import model.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import registerService.ClearService;
import registerService.EventIDService;
import registerService.RegisterService;
import request.RegisterRequest;
import result.EventIDResult;
import result.RegisterResult;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventIDServiceTest {
    private Database db;
    private EventDAO eventDAO;
    private Event testEvent;
    private String eventID;
    private String username;

    @BeforeEach
    public void setUp() throws DataAccessException{

        //clear database
        new ClearService().clear();
        eventID = "Biking_123A";
        username = "gale";
    }
    @AfterEach
    public void tearDown() {
        new ClearService().clear();
    }
    @Test
    public void eventIDPass() throws DataAccessException {
        //registering a user, and adding event then checking that our EventID result is a success
        RegisterRequest request = new RegisterRequest(username, "password", "a@mail.com",
                "a", "b", "f");
        RegisterService registerService = new RegisterService();
        RegisterResult registerResult = registerService.register(request);
        db = new Database();
        Connection conn = db.getConnection();
        eventDAO = new EventDAO(conn);
        eventDAO.clear();
        testEvent = new Event(eventID, username, "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);

        eventDAO.insert(testEvent);
        db.closeConnection(true);

        EventIDService eventIDService= new EventIDService();
        EventIDResult eventIDResult = eventIDService.eventID(registerResult.getAuthtoken(),eventID);
        assertTrue(eventIDResult.isSuccess());
    }
    @Test
    public void eventIDFail () {
        //the database is cleared so there won't be an authtoken or an eventID
        new ClearService().clear();
        EventIDService eventIDService= new EventIDService();
        EventIDResult eventIDResult = eventIDService.eventID("coolcoolnotreal",eventID);
        assertFalse(eventIDResult.isSuccess());
    }

}
