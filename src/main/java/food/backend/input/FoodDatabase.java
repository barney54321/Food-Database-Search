package food.backend.input;

import food.model.Food;
import food.model.Nutrition;

import java.util.List;

/**
 * Interface outlining the behaviours of a FoodDatabase Strategy object.
 */
public interface FoodDatabase {

    /**
     * Searches the database based on the given search term.
     * @param term The search term to search on.
     * @return The list of matching Food objects.
     */
    List<Food> search(String term);

    /**
     * Returns the Nutrition object associated with the given foodID.
     * @param foodID The foodID to search on.
     * @param measure The url for the measurement unit to search on.
     * @return The corresponding foodID.
     */
    Nutrition getNutrition(String foodID, String measure);
}
