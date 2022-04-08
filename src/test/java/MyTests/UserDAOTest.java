package MyTests;

import dataAccess.DataAccessException;
import dataAccess.Database;
import dataAccess.UserDAO;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {
    private Database db;
    private User bestUser;
    private UserDAO uDAO;

    @BeforeEach
    public void setUp() throws DataAccessException{
        db = new Database();
        bestUser = new User("as2273", "coolcool", "d@gmail.com",
                "abi", "spig", "f", "ajspig123");
        Connection conn = db.getConnection();
        uDAO = new UserDAO(conn);
        uDAO.clear();
    }
    @AfterEach
    public void tearDown(){
        //this is where we close the connection with the database
        db.closeConnection(false);
    }
    @Test
    public void insertPass() throws DataAccessException{
        uDAO.insert(bestUser);
        User compareTest = uDAO.find(bestUser.getUsername());
        assertNotNull(compareTest);
        assertEquals(bestUser, compareTest);
    }
    @Test
    public void insertFail() throws DataAccessException{
        uDAO.insert(bestUser);
        assertThrows(DataAccessException.class, () -> uDAO.insert(bestUser));
    }
    @Test
    public void validatePass() throws DataAccessException{
        uDAO.insert(bestUser);
        User compareTest = uDAO.find(bestUser.getUsername());
        assertNotNull(compareTest);
        assertEquals(bestUser, compareTest);
        //after confirming a user is entered in
        //call the validate function, it should return true
        assertTrue(uDAO.validate(bestUser.getUsername(), bestUser.getPassword()));
    }
    @Test
    public void validateFail() throws DataAccessException{
        uDAO.insert(bestUser);
        User compareTest = uDAO.find(bestUser.getUsername());
        assertNotNull(compareTest);
        assertEquals(bestUser, compareTest);

        assertFalse(uDAO.validate(bestUser.getUsername(), "gibberish"));
    }
    @Test
    public void retrievePass() throws DataAccessException{
        uDAO.insert(bestUser);
        User compareTest = uDAO.find(bestUser.getUsername());
        assertNotNull(compareTest); //check for fatal error
        assertEquals(bestUser, compareTest); //check if "successful" task produces what we want/expect
    }
    @Test
    public void retrieveFail () throws DataAccessException{
        uDAO.insert(bestUser);
        User compareTest = uDAO.find("abcdefgh");
        assertNull(compareTest);
    }
    @Test
    public void clearPass() throws DataAccessException{
        uDAO.insert(bestUser);
        //check if insert actually inserted
        User compareTest = uDAO.find(bestUser.getUsername());
        assertNotNull(compareTest); //check for fatal error
        assertEquals(bestUser, compareTest); //check if "successful" task produces what we want/expect
        uDAO.clear();
        User compareTestAfter = uDAO.find(bestUser.getUsername());
        assertNull(compareTestAfter);
    }
    @Test
    public void clearPassPartTwo() throws DataAccessException{
        uDAO.clear();
        User compareTestAfter = uDAO.find(bestUser.getUsername());
        assertNull(compareTestAfter);
    }

}
