package MyTests;

import dataAccess.*;
import model.Authtoken;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import registerService.ClearService;
import registerService.LoginService;
import registerService.RegisterService;
import request.LoginRequest;
import request.RegisterRequest;
import result.ClearResult;
import result.EventIDResult;
import result.LoginResult;
import result.RegisterResult;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClearServiceTest {
    private String username;
    private String password;

    @BeforeEach
    public void setUp() throws DataAccessException{
        username = "ajspig";
        password = "password";
    }

    @Test
    public void clearPassEmpty(){
        ClearResult result = new ClearService().clear();
        assertTrue(result.isSuccess()); //check if it's true
    }
    @Test
    public void clearPass() throws DataAccessException{
        RegisterRequest request = new RegisterRequest(username, password, "a@mail.com",
                "a", "b", "f");
        RegisterService registerService = new RegisterService();
        registerService.register(request);

        ClearResult result = new ClearService().clear();
        assertTrue(result.isSuccess()); //check if it's true

        //try logging in with the user we registered
        LoginRequest loginRequest = new LoginRequest(username, password);
        LoginResult loginResult = new LoginService().login(loginRequest);
        assertFalse(loginResult.getSuccess());

    }

}
