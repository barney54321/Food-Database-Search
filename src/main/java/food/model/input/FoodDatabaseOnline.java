package food.model.input;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Online extension of FoodDatabase template.
 */
public class FoodDatabaseOnline implements FoodStrategy {

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

    /**
     * Creates a new FoodDatabase object.
     *
     * @param appID The App ID.
     * @param appKey The App Key.
     */
    public FoodDatabaseOnline(String appID, String appKey) {
        this.client = HttpClients.createDefault();
        this.appID = appID;
        this.appKey = appKey;
    }

    @Override
    public String searchFood(String term) {
        try {
            term = term.replace(" ", "%20");

            String base = "https://api.edamam.com/api/food-database/v2/";
            String url = base + "parser?ingr=" + term + "&app_id=" + appID + "&app_key=" + appKey;

            HttpGet getter = new HttpGet(url);

            InputStream stream = client.execute(getter).getEntity().getContent();

            return readInputStream(stream);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Converts an InputStream into a String.
     *
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
    public String searchNutrition(String foodID, String measure) {
        try {
            CloseableHttpClient client = HttpClients.createDefault();

            String body = "{\"ingredients\": [{\"quantity\": 1, \"measureURI\": \"";
            body += measure + "\", \"foodId\": \"" + foodID + "\"}]}";

            StringEntity entity = new StringEntity(body, ContentType.APPLICATION_JSON);

            String url = "https://api.edamam.com/api/food-database/v2/nutrients?app_id=" + appID + "&app_key=" + appKey;
            HttpPost poster = new HttpPost(url);

            poster.setEntity(entity);

            InputStream stream = client.execute(poster).getEntity().getContent();

            String out = readInputStream(stream);

            client.close();

            return out;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
