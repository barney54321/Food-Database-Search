package food.backend.input;

import food.model.Food;
import food.model.FoodImpl;
import food.model.Nutrition;
import food.model.NutritionImpl;
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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Concrete implementation of the FoodDatabase Strategy.
 */
public class FoodDatabaseOnline implements FoodDatabase {

    /**
     * The HtppClient used for connections.
     */
    private CloseableHttpClient client;

    /**
     * The App ID credential.
     */
    private String appID;

    /**
     * The App token credential.
     */
    private String appKey;

    public FoodDatabaseOnline(String appID, String appKey) {
        this.client = HttpClients.createDefault();
        this.appID = appID;
        this.appKey = appKey;
    }

    @Override
    public List<Food> search(String term) {
        try {
            term = term.replace(" ", "%20");

            String url = "https://api.edamam.com/api/food-database/v2/parser?ingr=" + term + "&app_id=" + appID + "&app_key=" + appKey;

            HttpGet getter = new HttpGet(url);

            InputStream stream = client.execute(getter).getEntity().getContent();

            String out = readInputStream(stream);

            JSONObject json = (JSONObject) new JSONParser().parse(out);

            JSONArray results = (JSONArray) json.get("hints");

            List<Food> res = new ArrayList<>();

            for (Object o : results) {
                res.add(new FoodImpl((JSONObject) o, (foodID, size) -> this.getNutrition(foodID, size)));
            }

            return res;

        } catch (IOException e) {
            return null;
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Converts an InputStream into a String.
     * @param stream The InputStream to read in.
     * @return The String representation of the InputStream.
     */
    private String readInputStream(InputStream stream) {
        Scanner sc = new Scanner(stream);

        String out = "";

        while (sc.hasNextLine()) {
            out += sc.nextLine();
        }

        return out;
    }

    @Override
    public Nutrition getNutrition(String foodID, String measure) {
        try {

            CloseableHttpClient client = HttpClients.createDefault();

            String body = "{\"ingredients\": [{\"quantity\": 1, \"measureURI\": \"" + measure + "\", \"foodId\": \"" + foodID + "\"}]}";

            StringEntity entity = new StringEntity(body, ContentType.APPLICATION_JSON);

            String url = "https://api.edamam.com/api/food-database/v2/nutrients?app_id=" + appID + "&app_key=" + appKey;
            HttpPost poster = new HttpPost(url);

            poster.setEntity(entity);

            InputStream stream = client.execute(poster).getEntity().getContent();

            String out = readInputStream(stream);

            Nutrition nutrition = new NutritionImpl((JSONObject) new JSONParser().parse(out));

            client.close();

            return nutrition;
        } catch (IOException e) {
            return null;
        } catch (ParseException e) {
            return null;
        }
    }
}
