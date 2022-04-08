package server;

import java.io.*;
import java.net.*;

import com.google.gson.Gson;
import com.sun.net.httpserver.*;

import registerService.FillService;
import result.EventResult;
import result.FillResult;

public class FillHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        Gson gson = new Gson();
        FillResult result = new FillResult(false, "Internal Server Error");
        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
                String URI = exchange.getRequestURI().toString();
                URI = URI.substring(6);
                int generations = 4;
                String username;
                if (URI.contains("/")) {
                    username = URI.split("/")[0];
                    generations = Integer.parseInt(URI.split("/")[1]);
                } else {
                    username = URI;
                }
                InputStream reqBody = exchange.getRequestBody();
                String reqData = readString(reqBody);
                System.out.println(reqData);

                FillService service = new FillService(username);
                result = service.fill(generations);
                success = result.getSuccess();
            }
            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            }else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            }
            Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
            gson.toJson(result, resBody);
            resBody.close();
            exchange.getResponseBody().close();
        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
            result = new FillResult(false, e.getMessage());
            gson.toJson(result, resBody);
            resBody.close();
            exchange.getResponseBody().close();
            e.printStackTrace();
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
