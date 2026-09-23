# SOAPServer2

Ett enkelt demo av en SOAP-server skriven med beroenden (dependencies) från Jakarta-projektet. 

1. Starta projektet genom att skapa ett nytt Maven Archetype-projekt som använde quickstart-arketypen.
2. Efter detta behöver pom.xml uppdateras med rätt beroenden, olika versioner för detta kan hittas i [MVN-repository](mvnrepository.com).
3. När projektet körs kan man komma åt WSDL-filen genom att gå till [http://localhost:8080/hello?wsdl](http://localhost:8080/hello?wsdl).
4. Klienten behöver inga extra paket. Men det kan behöva skapas flera "Run / Debug Configurations" så att det går att köra båda två samtidigt.