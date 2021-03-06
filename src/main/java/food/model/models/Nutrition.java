package food.model.models;

import java.util.List;
import java.util.Map;

/**
 * Interface representing a Nutrition object.
 */
public interface Nutrition {

    /**
     * Returns the number of calories in the item.
     *
     * @return The number of calories.
     */
    Integer getCalories();

    /**
     * Returns the total weight of the item.
     *
     * @return The total weight.
     */
    Double getTotalWeight();

    /**
     * Returns the diet labels applicable to the item.
     *
     * @return The associated diet labels.
     */
    List<String> getDietLabels();

    /**
     * Returns the health labels applicable to the item.
     *
     * @return The associated health labels.
     */
    List<String> getHealthLabels();

    /**
     * Returns the cautions applicable to the item.
     *
     * @return The associated cautions.
     */
    List<String> getCautions();

    /**
     * Returns the Nutrients present in the item.
     *
     * @return The map of nutrients in the item.
     */
    Map<String, Nutrient> getTotalNutrients();

    /**
     * Returns the number of nutrients consumed everyday in item.
     *
     * @return The map of daily nutrients in the item.
     */
    Map<String, Nutrient> getTotalDaily();

    /**
     * The list of ingredients present in the food item.
     */
    List<Ingredient> getIngredients();

    /**
     * Sets whether the Nutrition object is above the calorie limit.
     */
    void setOverCalorieLimit();

    /**
     * Whether the calorie count is above the limit.
     *
     * @return True if the calorie count is above the limit.
     */
    boolean isOverCalorieLimit();
}
