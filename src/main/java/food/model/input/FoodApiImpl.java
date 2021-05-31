package food.model.input;

import food.model.input.cache.Database;
import food.model.models.Food;
import food.model.models.FoodImpl;
import food.model.models.Nutrition;
import food.model.models.NutritionImpl;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation of FoodApi.
 * Outsources API requests to Database and then FoodStrategy.
 */
public class FoodApiImpl implements FoodApi {

    /**
     * The database cache.
     */
    private Database cache;

    /**
     * The FoodStrategy object that defines behaviour (offline/online)
     */
    private FoodStrategy strategy;

    /**
     * Creates a new FoodApi object.
     *
     * @param cache The database cache to use.
     * @param strategy The strategy object to use.
     */
    public FoodApiImpl(Database cache, FoodStrategy strategy) {
        this.cache = cache;
        this.strategy = strategy;

        this.cache.setup();
    }

    @Override
    public List<Food> search(String term, boolean useCache) {

        String response = null;

        if (useCache) {
            try {
                String[] params = {term.toLowerCase()};
                ResultSet set = this.cache.executeQuery("select response from Search where term like ?", params);
                response = set.getString("response");
            } catch (SQLException e) {
                // Nothing
            }
        }

        if (response == null) {
            // Database retrieval failed
            response = this.strategy.searchFood(term);

            try {
                if (response != null) {
                    response = response.replace("'", "");
                    String update = "replace into Search (term, response) values(?, ?)";
                    String[] terms = {term, response};
                    this.cache.executeUpdate(update, terms);
                }
            } catch (SQLException e) {
                // Nothing
            }
        }

        if (response == null) {
            return null;
        }

        try {
            JSONObject json = (JSONObject) new JSONParser().parse(response);
            JSONArray array = (JSONArray) json.get("hints");
            List<Food> food = new ArrayList<>();

            for (Object object : array) {
                food.add(new FoodImpl((JSONObject) object));
            }

            return food;

        } catch (ParseException e) {
            return null;
        }
    }

    @Override
    public Nutrition getNutrition(String foodID, String measure, boolean useCache) {
        String response = null;

        if (useCache) {
            // Try searching database
            String query = "select response from Nutrition where food like ? and measure like ?";
            String[] terms = {foodID, measure};

            try {
                ResultSet set = this.cache.executeQuery(query, terms);
                response = set.getString("response");
            } catch (SQLException e) {
                // Nothing
            } catch (NullPointerException e) {
                // Nothing
            }
        }

        if (response == null) {
            // Database retrieval failed
            response = this.strategy.searchNutrition(foodID, measure);

            try {
                if (response != null) {
                    String update = "replace into Nutrition values(?, ?, ?)";
                    String[] terms = {foodID, measure, response};
                    this.cache.executeUpdate(update, terms);
                }
            } catch (SQLException e) {
                // Nothing
            }
        }

        if (response == null) {
            return null;
        }

        try {
            JSONObject json = (JSONObject) new JSONParser().parse(response);
            return new NutritionImpl(json);
        } catch (ParseException e) {
            return null;
        }
    }
}
