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

public class FoodAPIImpl implements FoodAPI {

    private Database cache;
    private FoodStrategy strategy;

    public FoodAPIImpl(Database cache, FoodStrategy strategy) {
        this.cache = cache;
        this.strategy = strategy;
    }

    @Override
    public List<Food> search(String term, boolean useCache) {

        String response = null;

        // Try searching database
        term = term.toLowerCase();
        String query = "select response from Search where term like '%" + term + "%'";

        try {
            ResultSet set = this.cache.executeQuery(query);
            response = set.getString("response");
        } catch (SQLException e) {
            // Nothing
            System.out.println(e);
        }

        if (response == null) {
            // Database retrieval failed
            response = this.strategy.searchFood(term);

            try {
                if (response != null) {
                    String update = "insert into Search values('" + term + "', '" + response + "')";
                    this.cache.executeUpdate(update);
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
    public Nutrition getNutrition(String foodID, String measure) {
        String response = null;

        // Try searching database
        String query = "select response from Nutrition where food like '%" + foodID + "%' and measure like '%" + measure + "%'";

        try {
            ResultSet set = this.cache.executeQuery(query);
            response = set.getString("response");
        } catch (SQLException e) {
            // Nothing
        } catch (NullPointerException e) {
            // Nothing
        }

        if (response == null) {
            // Database retrieval failed
            response = this.strategy.searchNutrition(foodID, measure);

            try {
                if (response != null) {
                    String update = "insert into Nutrition values('" + foodID + "', '" + measure + "', '" + response + "')";
                    this.cache.executeUpdate(update);
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
