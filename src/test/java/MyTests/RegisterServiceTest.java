package MyTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import registerService.LoginService;
import registerService.RegisterService;
import request.LoginRequest;
import request.RegisterRequest;
import result.LoginResult;
import result.RegisterResult;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegisterServiceTest {
    private String username;
    private String password;

    @BeforeEach
    public void setUp(){
        username = "ajspig";
        password = "password";
    }
    @Test
    public void registerPass(){
        RegisterRequest request = new RegisterRequest(username, password, "a@mail.com",
                "a", "b", "f");
        RegisterService registerService = new RegisterService();
        registerService.register(request);

        LoginRequest loginRequest = new LoginRequest(username,password);
        LoginResult loginResult = new LoginService().login(loginRequest);

        assertTrue(loginResult.getSuccess());
    }
    @Test
    public void registerFail(){
        RegisterRequest request = new RegisterRequest(username, password, "a@mail.com",
                "a", "b", "f");
        RegisterService registerService = new RegisterService();
        registerService.register(request);

        RegisterResult result = registerService.register(request);

        assertFalse(result.isSuccess());
        //username already taken by another user
        //need to make sure register won't let us register a user all ready registered

    }
}
