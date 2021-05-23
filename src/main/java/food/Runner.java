package food;

import food.backend.input.FoodDatabase;
import food.backend.input.FoodDatabaseOffline;
import food.backend.input.FoodDatabaseOnline;
import food.backend.output.Twilio;
import food.backend.output.TwilioOnline;
import food.controller.Facade;
import food.controller.FacadeImpl;
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

    private static String mode;
    private static Map<String, String> credentials;

    public static void main(String[] args) {
        Runner.credentials = credentialsParser("credentials.json");

        if (Runner.credentials == null) {
            return;
        } else if (args.length == 0) {
            return;
        }

        Runner.mode = args[0];

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        if (Runner.mode.equals("online")) {
            String appID = Runner.credentials.get("food-id");
            String appKey = Runner.credentials.get("food-key");
            FoodDatabase online = new FoodDatabaseOnline(appID, appKey);
            Facade controller = new FacadeImpl(online);
            this.window = new FoodWindow(controller);
        } else if (Runner.mode.equals("offline")) {
            FoodDatabase offline = new FoodDatabaseOffline();
            Facade controller = new FacadeImpl(offline);
            this.window = new FoodWindow(controller);
        } else {
            throw new IllegalStateException("No mode specified");
        }

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
            res.put("twilio-phone-from", (String) json.get("twilio-phone-from"));
            res.put("twilio-phone-to", (String) json.get("twilio-phone-to"));

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
