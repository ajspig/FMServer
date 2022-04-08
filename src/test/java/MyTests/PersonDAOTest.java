package MyTests;
import dataAccess.DataAccessException;
import dataAccess.Database;
import dataAccess.PersonDAO;
import model.Event;
import model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PersonDAOTest {
    private Database db;
    private Person bestPerson;
    private PersonDAO pDao;

    @BeforeEach
    public void setUp() throws DataAccessException{
        db = new Database();
        bestPerson = new Person("ajspig123", "ajspig", "julia", "pete", "f",
                "dad123", "mom123", "spouse123");
        Connection conn = db.getConnection();
        pDao = new PersonDAO(conn);
        pDao.clear(); //database clear all method
    }

    @AfterEach
    public void tearDown(){
        db.closeConnection(false);
    }
    @Test
    public void insertPass() throws DataAccessException{
        pDao.insert(bestPerson);
        Person compareTest = pDao.find(bestPerson.getPersonID());
        assertNotNull(compareTest);
        assertEquals(bestPerson, compareTest);
    }
    @Test
    public void insertFail() throws DataAccessException{
        pDao.insert(bestPerson);
        //personID should be unique and this should throw an error since this personId is already in there
        assertThrows(DataAccessException.class, () -> pDao.insert(bestPerson));
    }
    //update
    @Test
    public void updatePass() throws DataAccessException{
        pDao.insert(bestPerson);
        Person compareTest = pDao.find(bestPerson.getPersonID()); //store the method's return value
        assertNotNull(compareTest); //check for fatal error
        assertEquals(bestPerson, compareTest); //check if "successful" task produces what we want/expect

        Person betterPerson = new Person("ajspig123", "notajspig", "julia", "pete", "f",
                "dad123", "mom123", "spouse123");
        pDao.update(betterPerson);

        Person compareBetterPerson = pDao.find(betterPerson.getPersonID()); //store the method's return value
        assertNotNull(compareBetterPerson); //check for fatal error
        assertEquals(betterPerson, compareBetterPerson); //check if "successful" task produces what we want/expect
    }
    @Test
    public void updateFail() throws DataAccessException{
        //make the function fail by changing the personID
        pDao.insert(bestPerson);
        Person compareTest = pDao.find(bestPerson.getPersonID()); //store the method's return value
        assertNotNull(compareTest); //check for fatal error
        assertEquals(bestPerson, compareTest); //check if "successful" task produces what we want/expect

        Person betterPerson = new Person("coolcool", "ajspig", "julia", "pete", "f",
                "dad123", "mom123", "spouse123");
        pDao.update(betterPerson);

        Person compareBetterPerson = pDao.find(bestPerson.getPersonID()); //store the method's return value
        assertNotEquals(betterPerson,compareBetterPerson); //check for fatal error
    }
    @Test
    public void retrievePass () throws DataAccessException{
        pDao.insert(bestPerson);
        Person compareTest = pDao.find(bestPerson.getPersonID()); //store the method's return value
        assertNotNull(compareTest); //check for fatal error
        assertEquals(bestPerson, compareTest); //check if "successful" task produces what we want/expect
    }
    @Test
    //trying to get our test to fail
    public void retrieveFail () throws DataAccessException{
        pDao.insert(bestPerson);
        Person compareTest = pDao.find("abcdefgh");
        assertNull(compareTest);
    }
    @Test
    public void findForUserPass() throws DataAccessException{
        pDao.insert(bestPerson);
        List<Person> compareTest = pDao.findForUser(bestPerson.getAssociatedUsername());
        assertNotNull(compareTest); //check for fatal error
        assertEquals(bestPerson, compareTest.get(0)); //check if "successful" task produces what we want/expect
    }
    @Test
    public void findForUserFail () throws DataAccessException{
        pDao.insert(bestPerson);
        List<Person> compareTest = pDao.findForUser("abcdefgh");
        assertTrue(compareTest.isEmpty());
    }
    @Test
    public void clearUserPassPartOne() throws DataAccessException{
        //look in an empty database
        pDao.clearUserPersons(bestPerson.getAssociatedUsername());
        //now check that the user is not there
        assertNull(pDao.find(bestPerson.getPersonID())); }
    @Test
    public void clearUserPassPartTwo () throws DataAccessException{
        pDao.insert(bestPerson);
        //first check it is inserted correctly
        Person compareTest = pDao.find(bestPerson.getPersonID());
        assertNotNull(compareTest);
        assertEquals(bestPerson, compareTest);

        pDao.clearUserPersons(bestPerson.getAssociatedUsername());
        //now check that the user is not there
        assertNull(pDao.find(bestPerson.getPersonID()));
    }
    @Test
    public void clearPassPartOne() throws DataAccessException{
        //we insert the same user twice, once before a clear fun and once after
        pDao.insert(bestPerson);
        //check if insert actually inserted
        Person compareTest = pDao.find(bestPerson.getPersonID()); //store the method's return value
        assertNotNull(compareTest); //check for fatal error
        assertEquals(bestPerson, compareTest); //check if "successful" task produces what we want/expect
        pDao.clear();
        Person compareTestAfter = pDao.find(bestPerson.getPersonID());
        assertNull(compareTestAfter);
    }
    @Test
    public void clearPassPartTwo() throws DataAccessException{
        //checking clear on an empty database
        pDao.clear();
        Person compareTestAfter = pDao.find(bestPerson.getPersonID());
        assertNull(compareTestAfter);
    }

}
