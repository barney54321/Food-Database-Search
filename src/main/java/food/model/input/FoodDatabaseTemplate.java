package food.model.input;

import food.model.Food;
import food.model.Nutrition;

import java.util.List;

/**
 * The Template class for FoodDatabase objects (both offline and online).
 * Template is used because the initial behaviour (database searching) is common.
 */
public abstract class FoodDatabaseTemplate implements FoodDatabase {

    @Override
    public List<Food> search(String term) {
        // Run database call

       return this.searchFood(term);
    }

    /**
     * Searches the API for matching food items.
     * @param term The term to search on.
     * @return The list of matching food items.
     */
    protected abstract List<Food> searchFood(String term);

    @Override
    public Nutrition getNutrition(String foodID, String measure) {
        // Run database call

        return this.searchNutrition(foodID, measure);
    }

    /**
     * Searches the API for the matching Nutrition object.
     * @param foodID The food object for the nutrition object.
     * @param measure The size for the Nutrition object.
     * @return The associated nutrition object.
     */
    protected abstract Nutrition searchNutrition(String foodID, String measure);
}
