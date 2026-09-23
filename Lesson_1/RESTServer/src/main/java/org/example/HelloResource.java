package org.example;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Map;

@Path("/api")
public class HelloResource {

    @GET
    @Path("/hello")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> hello(@QueryParam("name") String name) {
        String who = (name == null || name.isBlank()) ? "World" : name;
        return Map.of("message", "Hej " + who + "!");
    }

    @POST
    @Path("/echo")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response echo(String body) {
        // Minsta möjliga: returnera inkommande JSON som den är
        return Response.ok(body).build();
    }
}