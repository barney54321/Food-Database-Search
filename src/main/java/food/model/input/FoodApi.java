package food.model.input;

import food.model.models.Food;
import food.model.models.Nutrition;

import java.util.List;

/**
 * Interface representing the input API (Food-Database).
 * In previous versions of program, Facade interacted directly with the FoodDatabase implementations. This
 * has been replaced with this facade which interacts with both the cache and the API strategies.
 */
public interface FoodApi {

    /**
     * Searches the database based on the given search term.
     *
     * @param term The search term to search on.
     * @param useCache Whether cached data should be used.
     * @return The list of matching Food objects.
     */
    List<Food> search(String term, boolean useCache);

    /**
     * Returns the Nutrition object associated with the given foodID.
     *
     * @param foodID The foodID to search on.
     * @param measure The url for the measurement unit to search on.
     * @param useCache Whether cached data should be used.
     * @return The corresponding foodID.
     */
    Nutrition getNutrition(String foodID, String measure, boolean useCache);
}
