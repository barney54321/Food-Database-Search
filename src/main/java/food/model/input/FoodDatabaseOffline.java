package food.model.input;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Offline extension of FoodDatabase template.
 * Note that search only works with "Hawaiian Pizza".
 */
public class FoodDatabaseOffline implements FoodStrategy {
    @Override
    public String searchFood(String term) {
        try {
            FileReader reader = new FileReader("src/main/resources/hawaiian_pizza.json");

            Scanner sc = new Scanner(reader);

            String out = "";

            while (sc.hasNextLine()) {
                out += sc.nextLine();
            }

            return out;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public String searchNutrition(String foodID, String measure) {
        try {
            FileReader reader = new FileReader("src/main/resources/hawaiian_nutrition.json");

            Scanner sc = new Scanner(reader);

            String out = "";

            while (sc.hasNextLine()) {
                out += sc.nextLine();
            }

            return out;

        } catch (IOException e) {
            return null;
        }
    }
}
