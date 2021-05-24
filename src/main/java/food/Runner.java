package food;

import food.model.ModelFacade;
import food.model.ModelFacadeImpl;
import food.model.input.FoodDatabase;
import food.model.input.FoodDatabaseOffline;
import food.model.input.FoodDatabaseOnline;
import food.model.output.Twilio;
import food.model.output.TwilioOffline;
import food.model.output.TwilioOnline;
import food.controller.Controller;
import food.controller.ControllerImpl;
import food.view.FoodWindow;
import food.view.FoodWindowImpl;
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

/**
 * The Runner class for the Application.
 */
public class Runner extends Application {

    /**
     * The Window for JavaFX.
     */
    private FoodWindow window;

    /**
     * The mode the application is running in.
     */
    private static String mode;

    /**
     * The map of credentials.
     */
    private static Map<String, String> credentials;

    /**
     * Runs the program.
     * @param args Command line arguments.
     */
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

            String twilioSID = Runner.credentials.get("twilio-sid");
            String twilioKey = Runner.credentials.get("twilio-token");
            String twilioTo = Runner.credentials.get("twilio-phone-to");
            String twilioFrom = Runner.credentials.get("twilio-phone-from");
            Twilio twilio = new TwilioOnline(twilioSID, twilioKey, twilioFrom, twilioTo);

            ModelFacade facade = new ModelFacadeImpl(online, twilio);

            Controller controller = new ControllerImpl(facade);
            this.window = new FoodWindowImpl(controller);
        } else if (Runner.mode.equals("offline")) {

            FoodDatabase offline = new FoodDatabaseOffline();
            Twilio twilio = new TwilioOffline();

            ModelFacade facade = new ModelFacadeImpl(offline, twilio);

            Controller controller = new ControllerImpl(facade);
            this.window = new FoodWindowImpl(controller);
        } else {
            throw new IllegalStateException("No mode specified");
        }

        primaryStage.setTitle("Food Database");
        primaryStage.setScene(this.window.getScene());
        primaryStage.show();
    }

    /**
     * Parses the credentials file.
     * @param file The credentials file.
     * @return The map of credentials.
     */
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
