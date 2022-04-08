package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import registerService.EventIDService;
import result.EventIDResult;
import result.PersonIDResult;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;

public class EventIDHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        Gson gson = new Gson();
        EventIDResult result = new EventIDResult(false, "Internal Server Error");//dont have get request
        try{
            if(exchange.getRequestMethod().equalsIgnoreCase("get")){
                Headers reqHeaders = exchange.getRequestHeaders();
                String eventID = exchange.getRequestURI().toString();
                eventID = eventID.substring(7);

                if(reqHeaders.containsKey("Authorization")){
                    String authToken = reqHeaders.getFirst("Authorization");

                    EventIDService service = new EventIDService();
                    result = service.eventID(authToken, eventID);
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
