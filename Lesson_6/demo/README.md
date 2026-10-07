# Demo

## Autentisering och auktorisering

Projektet använder nu Spring Security. Följande beroende har lagts till i POM:en:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

Det finns två nya modeller, `AppUser` och `AppRole`, som skapar tabeller
som är länkade till varandra i databasen. Det finns också två repositories,
`AppUserRepository` och `AppRoleRepository`, för CRUD-operationer på tabellerna.

Lösenorden lagras hashade i databasen. Därför finns `DataSeeder.java`, som körs
automatiskt när applikationen startar och skapar användare och roller om
tabellerna är tomma. Se `DataSeeder.java` för användarnamn och lösenord.

`SecurityConfig.java` innehåller konfigurationen för CORS och för åtkomst till
olika endpoints. CORS-konfigurationen i `WebConfig.java` är avstängd med
`@ConditionalOnProperty` och inställningen `app.cors.mvc.enabled=false` i
`application.properties`, eftersom CORS nu hanteras av Spring Security.

`JpaUserDetailsService.java` hämtar användare från databasen. Spring Security
använder den för att kontrollera användarnamn och lösenord vid inloggning
och för att se vilka roller användaren har.

## Swagger (OpenAPI)

Projektet använder springdoc för att generera API-dokumentation och Swagger UI.
Följande beroende har lagts till i POM:en:

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>3.1.1</version>
</dependency>
```

springdoc läser controllers och modeller automatiskt, så ingen extra
konfiguration behövs i koden. När applikationen körs finns:

- Swagger UI: http://localhost:5500/swagger-ui.html
- OpenAPI-dokumentet (JSON): http://localhost:5500/v3/api-docs

Sökvägarna `/swagger-ui.html`, `/swagger-ui/**` och `/v3/api-docs/**` är öppna
utan inloggning i `SecurityConfig.java`. Endpoints under `/api/**` kräver
däremot inloggning med HTTP Basic. Swagger UI är ännu inte konfigurerat för
inloggning, så anrop mot `/api/**` från Swagger UI får svaret 401.
