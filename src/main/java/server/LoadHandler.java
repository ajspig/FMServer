package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import registerService.LoadService;
import request.LoadRequest;
import request.LoginRequest;
import result.ClearResult;
import result.LoadResult;

import java.io.*;
import java.net.HttpURLConnection;

public class LoadHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        Gson gson = new Gson();
        LoadResult result = new LoadResult(false, "Internal Server");
        try{
            if(exchange.getRequestMethod().equalsIgnoreCase("post")){
                InputStream reqBody = exchange.getRequestBody();
                String reqData = readString(reqBody);
                System.out.println(reqData);
                LoadRequest request = gson.fromJson(reqData, LoadRequest.class);


                LoadService service = new LoadService();
                result = service.load(request);
                success = result.getSuccess();
            }
            if(!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            }else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

            }
            Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
            gson.toJson(result, resBody);

            resBody.close();
            exchange.getResponseBody().close();
        }catch (IOException e){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            result = new LoadResult(false, e.getMessage());
            Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
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
