package MyTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import registerService.ClearService;
import registerService.FillService;
import registerService.RegisterService;
import request.RegisterRequest;
import result.FillResult;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FillServiceTest {
    private String username;
    private String password;
    @BeforeEach
    public void setUp() {
        username = "ab23";
        password = "password";
    }
    @Test
    public void fillPass(){
        RegisterRequest request = new RegisterRequest(username, password, "a@mail.com",
                "a", "b", "f");
        RegisterService registerService = new RegisterService();
        registerService.register(request);

        FillService fillService = new FillService(username);
        FillResult fillResult = fillService.fill(4);
        assertEquals(true, fillResult.getSuccess());
        //check that object is filled
        //check that there are the correct number of generations filled for user
    }

    @Test
    public void FillFail(){
        ClearService clearService = new ClearService();
        clearService.clear();

        FillService fillService = new FillService(username);
        FillResult fillResult = fillService.fill(4);
        assertEquals(false, fillResult.getSuccess());

    }
}
