package server;
import java.io.*;
import java.net.*;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.*;
import registerService.RegisterService;
import request.RegisterRequest;
import result.EventResult;
import result.RegisterResult;

public class RegisterHandler implements HttpHandler{

    // Handles HTTP requests containing the /user/register URL path
    //the HTTPExchange object gives the handler access to all the details of the HTTP request
    //also give handler ability to construct an HTTP response and send it back to client
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        Gson gson = new Gson();
        RegisterResult result = new RegisterResult(false, "Request property missing or has invalid value");

        try{
            if(exchange.getRequestMethod().equalsIgnoreCase("post")){
                //Extract the JSON string from HTTP request body
                InputStream reqBody = exchange.getRequestBody();
                String reqData = readString(reqBody);
                //display/log the request JSON data

                RegisterRequest request = gson.fromJson(reqData, RegisterRequest.class);
                RegisterService service = new RegisterService();
                result = service.register(request);
                success = result.isSuccess();
            }
            if(!success){
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            }else{
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            }
            Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
            gson.toJson(result, resBody);

            resBody.close();
            exchange.getResponseBody().close();
        }catch (IOException e){
            //internal error occurred inside server
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            result = new RegisterResult(false, e.getMessage());
            Writer resBody = new OutputStreamWriter(exchange.getResponseBody());

            gson.toJson(result, resBody);
            resBody.close();
            exchange.getResponseBody().close();
            e.printStackTrace();
        }catch (JsonSyntaxException e){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
            result = new RegisterResult(false, "Error: Request property missing or has invalid value");
            gson.toJson(result, resBody);
            resBody.close();
            exchange.getResponseBody().close();
        }
    }
    private String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }
}
