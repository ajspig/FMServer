package MyTests;

import dataAccess.DataAccessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import registerService.ClearService;
import registerService.LoginService;
import registerService.RegisterService;
import request.LoginRequest;
import request.RegisterRequest;
import result.ClearResult;
import result.LoginResult;
import result.RegisterResult;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginServiceTest {
    private String username;
    private String password;
    @BeforeEach
    public void setUp() throws DataAccessException {
        username = "ab23";
        password = "password";
    }

    @Test
    public void loginPass(){

        RegisterRequest request = new RegisterRequest(username, password, "a@mail.com",
                "a", "b", "f");
        RegisterService registerService = new RegisterService();
        registerService.register(request);

        LoginRequest loginRequest = new LoginRequest(username, password);
        LoginService loginService = new LoginService();
        LoginResult loginResult = loginService.login(loginRequest);
        assertEquals(true, loginResult.getSuccess());
        assertEquals(username, loginResult.getUsername());

    }
    @Test
    public void loginFail(){
        ClearService clearService = new ClearService();
        clearService.clear();

        LoginRequest loginRequest = new LoginRequest(username, password);
        LoginService loginService = new LoginService();
        LoginResult loginResult = loginService.login(loginRequest);
        assertEquals(false, loginResult.getSuccess());
    }
}
