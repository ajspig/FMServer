package MyTests;
import dataAccess.AuthtokenDAO;
import dataAccess.DataAccessException;
import dataAccess.Database;
import model.Authtoken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class AuthtokenDAOTest {
    private Database db;
    private Authtoken bestAuthtoken;
    private AuthtokenDAO aDao;

    @BeforeEach
    public void setUp() throws DataAccessException{
        db = new Database();
        bestAuthtoken = new Authtoken("authtoken1","username1");
        Connection conn = db.getConnection();
        aDao = new AuthtokenDAO(conn);
        aDao.clear();
    }
    @AfterEach
    public void tearDown(){
        db.closeConnection(false);
    }
    @Test
    public void insertPass() throws DataAccessException{
        aDao.insert(bestAuthtoken);
        String compareTest = aDao.findUser(bestAuthtoken.getAuthtoken());
        assertEquals(bestAuthtoken.getUsername(), compareTest);
    }
    @Test
    public void insertFail() throws DataAccessException{
        aDao.insert(bestAuthtoken);
        assertThrows(DataAccessException.class, () -> aDao.insert(bestAuthtoken));
    }
    @Test
    public void retrievePass() throws DataAccessException{
        //returns username based off authtoken
        aDao.insert(bestAuthtoken);
        String compareTest = aDao.findUser(bestAuthtoken.getAuthtoken());
        assertEquals(bestAuthtoken.getUsername(), compareTest);
    }
    @Test
    public void retrieveFail() throws DataAccessException{
        assertThrows(DataAccessException.class, () -> aDao.findUser(bestAuthtoken.getAuthtoken()));
    }

    @Test
    public void clearPass() throws DataAccessException{
        aDao.insert(bestAuthtoken);
        String compareTest = aDao.findUser(bestAuthtoken.getAuthtoken());
        assertEquals(bestAuthtoken.getUsername(), compareTest); //check if "successful" task produces what we want/expect
        aDao.clear();
        assertThrows(DataAccessException.class, () -> aDao.findUser(bestAuthtoken.getAuthtoken()));
    }
    @Test
    public void clearPassPartTwo() throws DataAccessException{
        //checking if clear works on empty database
        aDao.clear();
        assertThrows(DataAccessException.class, () -> aDao.findUser(bestAuthtoken.getAuthtoken()));
    }
}
