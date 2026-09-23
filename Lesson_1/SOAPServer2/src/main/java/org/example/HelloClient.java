package org.example;

import jakarta.xml.soap.*;

public class HelloClient {
    public static void main(String[] args) throws Exception {
        // Skapa SOAP-anslutning
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection soapConnection = soapConnectionFactory.createConnection();

        String url = "http://localhost:8080/hello";
        SOAPMessage response = soapConnection.call(createRequest("Java 25"), url);

        // Skriv ut svaret
        System.out.println("Svar från servern:");
        response.writeTo(System.out);
        soapConnection.close();
    }

    private static SOAPMessage createRequest(String name) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        SOAPPart soapPart = soapMessage.getSOAPPart();
        SOAPEnvelope envelope = soapPart.getEnvelope();
        // ns - kan ha vilket namn jag vill, det går också att använda flera olika
        //      namnrymder
        envelope.addNamespaceDeclaration("ns", "http://example.org/");

        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("sayHello", "ns");
        soapBodyElem.addChildElement("arg0").addTextNode(name);

        soapMessage.saveChanges();
        return soapMessage;
    }
}