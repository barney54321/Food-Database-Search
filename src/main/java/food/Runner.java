package food;

import food.view.FoodWindow;
import javafx.application.Application;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class Runner extends Application {

    private FoodWindow window;

    public static void main(String[] args) {
        Map<String, String> credentials = credentialsParser("credentials.json");

        if (credentials == null) {
            return;
        }

        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.window = new FoodWindow();

        primaryStage.setTitle("Food Database");
        primaryStage.setScene(this.window.getScene());
        primaryStage.show();
    }

    private static Map<String, String> credentialsParser(String file) {
        try {
            JSONParser parser = new JSONParser();
            Reader reader = new FileReader(file);

            JSONObject json = (JSONObject) parser.parse(reader);

            Map<String, String> res = new HashMap<>();

            res.put("food-id", (String) json.get("food-id"));
            res.put("food-key", (String) json.get("food-key"));
            res.put("twilio-sid", (String) json.get("twilio-sid"));
            res.put("twilio-token", (String) json.get("twilio-token"));

            if (res.containsValue(null)) {
                System.err.println("Credentials file missing field");
                return null;
            }

            return res;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return null;
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}
