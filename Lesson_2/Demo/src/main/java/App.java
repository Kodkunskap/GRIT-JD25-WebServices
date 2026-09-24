import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class App {

        /*

        https://jsonplaceholder.typicode.com
        https://jsonplaceholder.typicode.com/posts/6

         */

        public static void main(String[] args) {
            try {
                App.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private static void run() throws IOException, InterruptedException {

            /*
                    Exempel 1 - GET-request
             */
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(10))
                    .version(HttpClient.Version.HTTP_1_1)
                    .followRedirects(HttpClient.Redirect.NORMAL)
                    .build();

            HttpRequest getRequest = HttpRequest.newBuilder()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .uri(URI.create("https://jsonplaceholder.typicode.com/posts/6"))
                    .GET()
                    .build();

            HttpResponse<String> getResponse = client.send(
                    getRequest,
                    HttpResponse.BodyHandlers.ofString()
            );

            System.out.println("Status code: " + getResponse.statusCode());
            System.out.println("Body: " + getResponse.body());

            /*
                Exempel 2 - POST-request
             */
            HttpRequest postRequest = HttpRequest.newBuilder()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .uri(URI.create("https://jsonplaceholder.typicode.com/posts"))
                    .POST(HttpRequest.BodyPublishers.ofString(
                            """
                                    {
                                      "userId": 1,
                                      "title": "dolorem eum magni eos aperiam quia",
                                      "body": "ut aspernatur corporis harum nihil quis provident sequi\\nmollitia nobis aliquid molestiae\\nperspiciatis et ea nemo ab reprehenderit accusantium quas\\nvoluptate dolores velit et doloremque molestiae"
                                    }
                                 """
                    ))
                    .build();

            HttpResponse<String> postResponse = client.send(
                    postRequest,
                    HttpResponse.BodyHandlers.ofString()
            );

            System.out.println("Status code: " + postResponse.statusCode());
            System.out.println("Body: " + postResponse.body());

            /*
                Exempel 3 - Deserialisera JSON -> POJO
             */
            ObjectMapper mapper = new ObjectMapper();
            Post post = mapper.readValue(
                    postResponse.body(),
                    Post.class
            );
            System.out.println(post);


            /*
                Exempel 4 - Serialisera POJO -> JSON, Skicka till WS
             */
            Post newPost = new Post(
                    101,
                    "The Legend of Martin",
                    "Lorem Ipsum 22",
                    0);
            String jsonStr = mapper.writeValueAsString(newPost);
            System.out.println(jsonStr);        // Vi bör se att id inte finns med i JSON p.g.a. @JsonInclude
            HttpRequest postRequest2 = HttpRequest.newBuilder()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .uri(URI.create("https://jsonplaceholder.typicode.com/posts"))
                    .POST(HttpRequest.BodyPublishers.ofString(jsonStr))
                    .build();

            HttpResponse<String> postResponse2 = client.send(
                    postRequest2,
                    HttpResponse.BodyHandlers.ofString()
            );

            System.out.println("Status code: " + postResponse2.statusCode());
            System.out.println("Body: " + postResponse2.body());

        }

}
