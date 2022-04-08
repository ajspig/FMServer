package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import registerService.PersonService;
import result.PersonResult;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;

public class PersonHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        Gson gson = new Gson();
        PersonResult result = new PersonResult(false, "Internal Server Error");//not a get request
        try{
            if(exchange.getRequestMethod().equalsIgnoreCase("get")){
                Headers reqHeaders = exchange.getRequestHeaders();
                if(reqHeaders.containsKey("Authorization")){
                    String authToken = reqHeaders.getFirst("Authorization");

                    PersonService service = new PersonService();
                    result = service.person(authToken);
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
