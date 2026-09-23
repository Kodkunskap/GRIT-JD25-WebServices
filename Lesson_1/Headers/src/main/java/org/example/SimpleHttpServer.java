package org.example;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class SimpleHttpServer {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", (HttpExchange exchange) -> {
            System.out.println("Metod: " + exchange.getRequestMethod());
            System.out.println("Path: " + exchange.getRequestURI());
            exchange.getRequestHeaders().forEach((k, v) -> System.out.println(k + ": " + v));

            String response = "OK";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        });
        server.start();
        System.out.println("Server startad på http://localhost:8080/");
    }
}
