package MyTests;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.junit.jupiter.api.Test;
import registerService.*;
import request.LoadRequest;
import request.LoginRequest;
import result.EventResult;
import result.LoadResult;
import result.LoginResult;
import result.PersonResult;

import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PersonServiceTest {

    @Test
    public void personPass() throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(new FileReader("passoffFiles/LoadData.json"));
        LoadRequest loadRequest = gson.fromJson(jsonReader, LoadRequest.class);
        LoadService loadService= new LoadService();
        LoadResult loadResult = loadService.load(loadRequest);

        LoginRequest loginRequest = new LoginRequest("sheila","parker");
        LoginResult loginResult = new LoginService().login(loginRequest);

        PersonResult personResult = new PersonService().person(loginResult.getAuthtoken());
        //this will only be the data with the associated login
        assertEquals(true, personResult.isSuccess());
    }
    @Test
    public void personFail(){
        //check for invalid authtoken fail
        new ClearService().clear();
        PersonResult personResult = new PersonService().person("giberish");
        assertFalse(personResult.isSuccess());
    }

}
