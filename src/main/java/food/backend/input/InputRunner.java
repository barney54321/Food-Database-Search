package food.backend.input;

import food.model.Food;
import food.model.FoodImpl;
import food.model.Nutrition;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/*
    Note that this is just for testing the input API.
    Should be deleted before final submission
 */
public class InputRunner {

    public static void main(String[] args) throws Exception {
//        get();
//        nutrition();
        Map<String, String> credentials = credentialsParser("credentials.json");
        FoodDatabase food = new FoodDatabaseOnline(credentials.get("food-id"), credentials.get("food-key"));

        List<Food> res = food.search("Red Apple");
        System.out.println(res.get(0).getLabel());

        Nutrition nutrition = food.getNutrition(res.get(0).getID(), "http://www.edamam.com/ontologies/edamam.owl#Measure_unit");
        System.out.println(nutrition.getCalories());
    }

    @SuppressWarnings("unchecked")
    public static void nutrition() throws Exception {
        JSONObject ingredientOne = new JSONObject();
        ingredientOne.put("quantity", 1);
        ingredientOne.put("measureURI", "http://www.edamam.com/ontologies/edamam.owl#Measure_unit");
        ingredientOne.put("foodId", "food_al1do7ybtx5mz5b85twtradho1lj");

        JSONObject body = new JSONObject();

        JSONArray ingredients = new JSONArray();

        ingredients.add(ingredientOne);

        body.put("ingredients", ingredients);

        StringEntity entity = new StringEntity(body.toString(), ContentType.APPLICATION_JSON);

        CloseableHttpClient client = HttpClients.createDefault();

        Map<String, String> map = credentialsParser("credentials.json");

        String url = "https://api.edamam.com/api/food-database/v2/nutrients?app_id=" + map.get("food-id") + "&app_key=" + map.get("food-key");
        HttpPost poster = new HttpPost(url);

        poster.setEntity(entity);

        InputStream stream = client.execute(poster).getEntity().getContent();

        Scanner sc = new Scanner(stream);

        String out = "";

        while (sc.hasNextLine()) {
            out += sc.nextLine();
        }

        System.out.println(out);
    }

    public static void get() throws Exception {
        Map<String, String> map = credentialsParser("credentials.json");

        CloseableHttpClient client = HttpClients.createDefault();

        String term = "Ferrero Rocher";

        term = term.replace(" ", "%20");

        String url = "https://api.edamam.com/api/food-database/v2/parser?ingr=" + term + "&app_id=" + map.get("food-id") + "&app_key=" + map.get("food-key");

        HttpGet getter = new HttpGet(url);

        InputStream stream = client.execute(getter).getEntity().getContent();

        Scanner sc = new Scanner(stream);

        String out = "";

        while (sc.hasNextLine()) {
            out += sc.nextLine();
        }

        JSONObject json = (JSONObject) new JSONParser().parse(out);

        JSONArray results = (JSONArray) json.get("hints");

        for (Object o : results) {
            Food food = new FoodImpl((JSONObject) o);
            System.out.println(food.getLabel());
            System.out.println("\t" + food.getID());
        }
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
