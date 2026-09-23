package org.example;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.xml.ws.Endpoint;

// Definiera en SOAP-tjänst
@WebService
public class HelloService {

    @WebMethod
    public String sayHello(String name) {
        return "Hej " + name + ", välkommen till min SOAP-tjänst!";
    }

    public static void main(String[] args) {
        // Publicera tjänsten lokalt på http://localhost:8080/hello
        Endpoint.publish("http://localhost:8080/hello", new HelloService());
        System.out.println("SOAP-servern körs på http://localhost:8080/hello?wsdl");
    }
}