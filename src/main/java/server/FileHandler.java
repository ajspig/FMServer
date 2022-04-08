package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        File file = null;
        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
                String urlPath = exchange.getRequestURI().toString();
                if (urlPath == null || urlPath.equals("/")) {
                    urlPath = "/index.html";
                }
                String filePath = "web" + urlPath;
                file = new File(filePath);

                if (file.exists()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                    OutputStream respBody = exchange.getResponseBody();
                    Files.copy(file.toPath(), respBody);
                    respBody.close();
                    success = true;
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    Files.copy(Path.of("web/HTML/404.html"), respBody);
                    respBody.close();
                    exchange.getResponseBody().close();
                    return;
                }
            }
            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                OutputStream respBody = exchange.getResponseBody();
                Files.copy(file.toPath(), respBody);

                respBody.close();
                exchange.getResponseBody().close();
            }
        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
