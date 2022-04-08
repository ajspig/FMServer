package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import registerService.LoginService;
import request.LoginRequest;
import result.EventResult;
import result.LoginResult;

import java.io.*;
import java.net.HttpURLConnection;

public class LoginHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        Gson gson = new Gson();
        LoginResult result = new LoginResult(false, "Internal Server error");//was not a get request
        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
                InputStream reqBody = exchange.getRequestBody();
                String reqData = readString(reqBody);
                System.out.println(reqData);
                LoginRequest request = gson.fromJson(reqData, LoginRequest.class);

                LoginService service = new LoginService();
                result = service.login(request);
                success = result.getSuccess();
            }
            if(!success){
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            } else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            }
            Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
            gson.toJson(result, resBody);
            resBody.close();
            exchange.getResponseBody().close();
        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
            result = new LoginResult(false, "Error: Request property missing or has invalid value");
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
