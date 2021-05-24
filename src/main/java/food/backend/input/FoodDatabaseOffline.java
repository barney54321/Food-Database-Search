package food.backend.input;

import food.model.Food;
import food.model.FoodImpl;
import food.model.Nutrition;
import food.model.NutritionImpl;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Offline implementation of FoodDatabase interface.
 * Note that search only works with "Hawaiian Pizza".
 */
public class FoodDatabaseOffline implements FoodDatabase {
    @Override
    public List<Food> search(String term) {

        List<Food> res = new ArrayList<>();

        if (!term.equalsIgnoreCase("hawaiian pizza")) {
            return res;
        }

        try {

            FileReader reader = new FileReader("src/main/resources/hawaiian_pizza.json");

            Scanner sc = new Scanner(reader);

            String out = "";

            while (sc.hasNextLine()) {
                out += sc.nextLine();
            }

            JSONObject json = (JSONObject) new JSONParser().parse(out);

            JSONArray results = (JSONArray) json.get("hints");

            for (Object o : results) {
                res.add(new FoodImpl((JSONObject) o, (foodID, size) -> this.getNutrition(foodID, "http://www.edamam.com/ontologies/edamam.owl#Measure_unit")));
            }

            return res;

        } catch (IOException e) {
            return null;
        } catch (ParseException e) {
            return null;
        }
    }

    @Override
    public Nutrition getNutrition(String foodID, String measure) {
        try {
            FileReader reader = new FileReader("src/main/resources/hawaiian_nutrition.json");

            Scanner sc = new Scanner(reader);

            String out = "";

            while (sc.hasNextLine()) {
                out += sc.nextLine();
            }

            JSONObject json = (JSONObject) new JSONParser().parse(out);

            return new NutritionImpl(json);

        } catch (IOException e) {
            return null;
        } catch (ParseException e) {
            return null;
        }
    }
}
