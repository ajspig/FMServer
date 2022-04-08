package MyTests;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import dataAccess.DataAccessException;
import org.junit.jupiter.api.Test;
import registerService.*;
import request.LoadRequest;
import request.LoginRequest;
import request.RegisterRequest;
import result.*;

import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoadServiceTest {
    @Test
    public void loadPass() throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(new FileReader("passoffFiles/LoadData.json"));
        LoadRequest loadRequest = gson.fromJson(jsonReader, LoadRequest.class);
        LoadService loadService= new LoadService();
        LoadResult loadResult = loadService.load(loadRequest);

        assertEquals(true, loadResult.getSuccess());

        LoginRequest loginRequest = new LoginRequest("sheila","parker");
        LoginResult loginResult = new LoginService().login(loginRequest);

        //test if we can login successfully
        assertTrue(loginResult.getSuccess());
        //check that there are events in table
        EventResult eventResult = new EventService().event(loginResult.getAuthtoken());
        assertTrue(eventResult.isSuccess());
        //check that there are persons in table
        PersonResult personResult = new PersonService().person(loginResult.getAuthtoken());
        assertTrue(personResult.isSuccess());
    }
    @Test
    public void loadFail() throws DataAccessException, FileNotFoundException {
        String username = "ajspig";
        String password = "password";
        //check that load clears the database before inserting
        //register a new user, run load then check that login doesnt work
        RegisterRequest request = new RegisterRequest(username, password, "a@mail.com",
                "a", "b", "f");
        RegisterService registerService = new RegisterService();
        RegisterResult registerResult = registerService.register(request);

        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(new FileReader("passoffFiles/LoadData.json"));
        LoadRequest loadRequest = gson.fromJson(jsonReader, LoadRequest.class);
        LoadService loadService= new LoadService();
        LoadResult loadResult = loadService.load(loadRequest);
        assertEquals(true, loadResult.getSuccess());

        LoginRequest loginRequest = new LoginRequest(username, password);
        LoginService loginService = new LoginService();
        LoginResult loginResult = loginService.login(loginRequest);
        assertEquals(false, loginResult.getSuccess());


    }
}
