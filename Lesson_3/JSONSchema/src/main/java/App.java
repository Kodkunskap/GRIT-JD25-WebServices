import com.networknt.schema.Error;
import com.networknt.schema.InputFormat;
import com.networknt.schema.Schema;
import com.networknt.schema.SchemaRegistry;
import com.networknt.schema.dialect.Dialects;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

public class App {

    public static void main(String[] args) {
        App.run();
    }

    public static void run() {

        String validJSON = """
               {
                "id": 123,
                "name": "Martin",
                "email": "martin.haagen@gritacademy.se"
               }
                """;

        String invalidJSON = """
               {
                "id": 123,
                "name": "Martin"
               }
                """;

        SchemaRegistry schemaRegistry = SchemaRegistry.withDialect(
            Dialects.getDraft202012()
        );
        Schema schema = null;
        try(InputStream in = new FileInputStream(new File("user-schema.json"))) {
            schema = schemaRegistry.getSchema(in);
        } catch(Exception e) {
            e.printStackTrace();
            return;
        }

        if(App.validateJSON(schema, validJSON)) {
            System.out.println("Is valid JSON:");
            System.out.println(validJSON);
        }

        if(!App.validateJSON(schema, invalidJSON)) {
            System.out.println("Is invalid JSON:");
            System.out.println(invalidJSON);
        }


    }

    private static boolean validateJSON(Schema schema, String json) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(json);
        List<Error> errors = schema.validate(node);

        // schema.validate(json, InputFormat.JSON)

        if(errors.isEmpty()) {
            return true;
        } else {
            errors.forEach(error -> System.out.println(error.getMessage()));
            return false;
        }

    }


}
