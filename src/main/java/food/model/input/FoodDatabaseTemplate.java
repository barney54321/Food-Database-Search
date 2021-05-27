package food.model.input;

import food.model.models.Food;
import food.model.models.FoodImpl;
import food.model.models.Nutrition;
import food.model.models.NutritionImpl;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

/**
 * The Template class for FoodDatabase objects (both offline and online).
 * Template is used because the initial behaviour (database searching) is common.
 */
public abstract class FoodDatabaseTemplate implements FoodDatabase {

    @Override
    public List<Food> search(String term) {
        // Run database call

        try {
            String out = this.searchFood(term);

            JSONObject json = (JSONObject) new JSONParser().parse(out);

            JSONArray results = (JSONArray) json.get("hints");

            List<Food> res = new ArrayList<>();

            for (Object o : results) {
                res.add(new FoodImpl((JSONObject) o, (foodID, size) -> this.getNutrition(foodID, size)));
            }

            return res;
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Searches the API for matching food objects and returns the resulting JSON string.
     * @param term The term to search on.
     * @return The API's response JSON string.
     */
    protected abstract String searchFood(String term);

    @Override
    public Nutrition getNutrition(String foodID, String measure) {
        // Run database call

        String out = this.searchNutrition(foodID, measure);

        try {
            Nutrition nutrition = new NutritionImpl((JSONObject) new JSONParser().parse(out));

            return nutrition;
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Searches the API for the matching Nutrition object and returns the response JSON string.
     * @param foodID The food object for the nutrition object.
     * @param measure The size for the Nutrition object.
     * @return The API's response JSON string.
     */
    protected abstract String searchNutrition(String foodID, String measure);
}
