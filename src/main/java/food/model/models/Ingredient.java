package food.model.models;

public interface Ingredient {

    /**
     * Returns the quantity of the ingredient that is present.
     * @return The quantity of the ingredient.
     */
    Double getQuantity();

    /**
     * The measure used for the quantity.
     * @return The ingredient quantity.
     */
    String getMeasure();

    /**
     * The weight of the ingredient.
     * @return The ingredient's weight.
     */
    Double getWeight();

    /**
     * The label of the food this ingredient is present in.
     * @return The name of the food the ingredient is in.
     */
    String getFood();

    /**
     * The id of the food the ingredient is in.
     * @return The foodID of the encompassing food.
     */
    String getFoodID();
}
