package org.example;

import java.net.URI;
import java.net.http.*;

public class HttpMethodsDemo {
    public static void main(String[] args) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        // GET /users/42
        HttpRequest getReq = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/users/42"))
                .header("Accept", "application/json")
                .GET()
                .build();
        HttpResponse<String> getResp = client.send(getReq, HttpResponse.BodyHandlers.ofString());
        System.out.println("GET status=" + getResp.statusCode());
        System.out.println(getResp.body());

        // POST /users
        String newUserJson = "{\"name\":\"Ada\",\"email\":\"ada@example.com\"}";
        HttpRequest postReq = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/users"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(newUserJson))
                .build();
        HttpResponse<String> postResp = client.send(postReq, HttpResponse.BodyHandlers.ofString());
        System.out.println("POST status=" + postResp.statusCode());
        System.out.println(postResp.body());
    }
}
