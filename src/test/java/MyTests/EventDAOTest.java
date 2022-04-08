package MyTests;

import dataAccess.DataAccessException;
import dataAccess.Database;
import dataAccess.EventDAO;
import model.Event;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//We will use this to test that our insert method is working and failing in the right ways
public class EventDAOTest {
    private Database db;
    private Event bestEvent;
    private EventDAO eDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        // Here we can set up any classes or variables we will need for each test
        // lets create a new instance of the Database class
        db = new Database();
        // and a new event with random data
        bestEvent = new Event("Biking_123A", "Gale", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);

        // Here, we'll open the connection in preparation for the test case to use it
        Connection conn = db.getConnection();
        //Then we pass that connection to the EventDAO, so it can access the database.
        eDao = new EventDAO(conn);
        //Let's clear the database as well so any lingering data doesn't affect our tests
        eDao.clear();
    }

    @AfterEach
    public void tearDown() {
        // Here we close the connection to the database file, so it can be opened again later.
        // We will set commit to false because we do not want to save the changes to the database
        // between test cases.
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException {
        eDao.insert(bestEvent);
        Event compareTest = eDao.find(bestEvent.getEventID());
        assertNotNull(compareTest);
        assertEquals(bestEvent, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        eDao.insert(bestEvent);
        assertThrows(DataAccessException.class, () -> eDao.insert(bestEvent));
    }
    @Test
    public void findForUserPass() throws DataAccessException{
        eDao.insert(bestEvent);
        List<Event> compareTest = eDao.findForUser(bestEvent.getAssociatedUsername());
        assertNotNull(compareTest); //check for fatal error
        assertEquals(bestEvent, compareTest.get(0)); //check if "successful" task produces what we want/expect
    }
    @Test
    public void findForUserFail () throws DataAccessException{
        eDao.insert(bestEvent);
        List<Event> compareTest = eDao.findForUser("abcdefgh");
        assertTrue(compareTest.isEmpty());
    }
    @Test
    public void clearUserPassPartOne() throws DataAccessException{
        //look in an empty database
        eDao.clearUserEvents(bestEvent.getAssociatedUsername());
        //now check that the user is not there
        assertNull(eDao.find(bestEvent.getEventID())); }
    @Test
    public void clearUserPassPartTwo () throws DataAccessException{
        eDao.insert(bestEvent);
        assertNotNull(eDao.find(bestEvent.getEventID()));
        //first check it is inserted correctly
        eDao.clearUserEvents(bestEvent.getAssociatedUsername());
        //now check that the user is not there
        assertNull(eDao.find(bestEvent.getEventID()));
    }

    @Test
    public void retrievePass() throws DataAccessException{
        eDao.insert(bestEvent);
        Event compareTest = eDao.find(bestEvent.getEventID());
        assertNotNull(compareTest); //check for fatal error
        assertEquals(bestEvent, compareTest); //check if "successful" task produces what we want/expect
    }
    @Test
    public void retrieveFail () throws DataAccessException{
        eDao.insert(bestEvent);
        Event compareTest = eDao.find("abcdefgh");
        assertNull(compareTest);
    }
    @Test
    public void clearPassPartOne() throws DataAccessException{
        eDao.insert(bestEvent);
        //check if insert actually inserted
        Event compareTest = eDao.find(bestEvent.getEventID());
        assertNotNull(compareTest); //check for fatal error
        assertEquals(bestEvent, compareTest); //check if "successful" task produces what we want/expect
        eDao.clear();
        Event compareTestAfter = eDao.find(bestEvent.getEventID());
        assertNull(compareTestAfter);
    }
    @Test
    public void clearPassPartTwo() throws DataAccessException{
        //checking clear on an empty database
       eDao.clear();
        Event compareTestAfter = eDao.find(bestEvent.getEventID());
        assertNull(compareTestAfter);
    }
}
