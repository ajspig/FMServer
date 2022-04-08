package MyTests;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import dataAccess.DataAccessException;
import org.junit.jupiter.api.Test;
import registerService.ClearService;
import registerService.EventService;
import registerService.LoadService;
import registerService.LoginService;
import request.LoadRequest;
import request.LoginRequest;
import result.EventResult;
import result.LoadResult;
import result.LoginResult;

import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class EventServiceTest {

    @Test
    public void retrievePass () throws DataAccessException, FileNotFoundException {
        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(new FileReader("passoffFiles/LoadData.json"));
        LoadRequest loadRequest = gson.fromJson(jsonReader, LoadRequest.class);
        LoadService loadService= new LoadService();
        loadService.load(loadRequest);
        //load in a set of data

        LoginRequest loginRequest = new LoginRequest("sheila","parker");
        LoginResult loginResult = new LoginService().login(loginRequest);

        EventResult eventResult = new EventService().event(loginResult.getAuthtoken());
        //this will only be the data with the associated login
        assertEquals(true, eventResult.isSuccess());
    }
    @Test
    public void retrieveFail () throws DataAccessException{
        //check for invalid authtoken fail
        new ClearService().clear();
        EventResult eventResult = new EventService().event("giberish");
        assertFalse(eventResult.isSuccess());
    }



}
