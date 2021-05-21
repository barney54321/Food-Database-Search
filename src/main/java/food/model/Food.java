package food.model;

import java.util.Map;

public interface Food {

    /**
     * Returns the Food-Database foodID of the item.
     * @return The foodID of the item.
     */
    String getID();

    /**
     * Returns the Food-Database label of the item.
     * @return The label of the item.
     */
    String getLabel();

    /**
     * Returns the basic nutrition information of the item.
     * Structured as Nutrition:Amount.
     * E.g. FAT:37.5.
     * @return A mapping of nutrition name to double value.
     */
    Map<String, Double> getNutrients();

    /**
     * Returns the brand that produces the item.
     * @return The brand that produces the item.
     */
    String getBrand();

    /**
     * Returns the category of the item (e.g. "Packaged Foods").
     * @return The category of the item.
     */
    String getCategory();

    /**
     * Returns the category label of the item (e.g. "food").
     * @return The category label of the item.
     */
    String getCategoryLabel();

    /**
     * Returns a String of all the ingredients in the item without quantity.
     * @return All of the ingredients in the item.
     */
    String getFoodContentsLabel();

    /**
     * Returns the image path (url) for the item.
     * @return The image path for the item.
     */
    String getImagePath();

    /**
     * Returns the number of servings per package for the item.
     * @return The number of servings for the item.
     */
    Double getServingsPerContainer();

    /**
     * Returns the different sizes stored in the nutrition database for the item.
     * E.g. Package:38.6
     * @return A mapping of the sizes to weight stored in the nutrition database.
     */
    Map<String, Double> getMeasures();
}