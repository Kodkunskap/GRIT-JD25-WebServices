package org.example;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

import java.net.URI;

public class RestServer {
    public static void main(String[] args) {
        String baseUri = "http://0.0.0.0:8082/";
        ResourceConfig rc = new ResourceConfig()
                .register(HelloResource.class)
                .packages("org.glassfish.jersey.jackson"); // aktivera JSON/Jackson

        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(baseUri), rc);
        System.out.println("JAX-RS server igång på " + baseUri + "  (ex: /api/hello, /api/echo)");
        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdownNow));
    }
}