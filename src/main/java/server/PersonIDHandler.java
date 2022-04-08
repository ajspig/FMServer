package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import registerService.PersonIDService;
import result.PersonIDResult;
import result.PersonResult;

import java.io.*;
import java.net.HttpURLConnection;

public class PersonIDHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        Gson gson = new Gson();
        PersonIDResult result = new PersonIDResult(false, "Internal Server Error"); //Was not a get request
        try{
            if(exchange.getRequestMethod().equalsIgnoreCase("get")){
                Headers reqHeaders = exchange.getRequestHeaders();
                String personID = exchange.getRequestURI().toString();
                personID = personID.substring(8);

                if(reqHeaders.containsKey("Authorization")){
                    String authToken = reqHeaders.getFirst("Authorization");

                    PersonIDService service = new PersonIDService();
                    result = service.personID(authToken, personID);
                    success = result.isSuccess();
                }
            }
            if(!success){
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            }else{
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            }
            Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
            gson.toJson(result, resBody);
            resBody.close();
            OutputStream respBody = exchange.getResponseBody();
            respBody.close();
            exchange.getResponseBody().close();
        }catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
