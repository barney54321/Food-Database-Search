package food.model.input;

/**
 * Interface outlining the behaviours of a FoodDatabase Strategy object.
 */
public interface FoodStrategy {

    /**
     * Searches the database based on the given search term.
     *
     * @param term The search term to search on.
     * @return The resulting JSON string.
     */
    String searchFood(String term);

    /**
     * Returns the Nutrition object associated with the given foodID.
     *
     * @param foodID The foodID to search on.
     * @param measure The url for the measurement unit to search on.
     * @return The resulting JSON string
     */
    String searchNutrition(String foodID, String measure);
}
