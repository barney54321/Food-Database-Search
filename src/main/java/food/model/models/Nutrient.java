package food.model.models;

public interface Nutrient {

    /**
     * Returns the type of Nutrient.
     * @return The label of the Nutrient.
     */
    String getLabel();

    /**
     * Returns the quantity of the Nutrient.
     * @return The quantity of the Nutrient.
     */
    Double getQuantity();

    /**
     * Returns the unit of measurement for quantity.
     * @return The unit of measurement for quantity.
     */
    String getUnit();
}
