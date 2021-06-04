package food.model.models;

import org.json.simple.JSONObject;

/**
 * Concrete implementation of Nutrient interface.
 */
public class NutrientImpl implements Nutrient {

    /**
     * The display label.
     */
    private final String label;

    /**
     * The quantity of specified units.
     */
    private final Double quantity;

    /**
     * The unit of measurement for quantity.
     */
    private final String unit;

    /**
     * Creates a Nutrient object.
     *
     * @param json The JSONObject to base the Nutrient off of.
     */
    public NutrientImpl(JSONObject json) {
        this.label = (String) json.get("label");
        this.quantity = (Double) json.get("quantity");
        this.unit = (String) json.get("unit");
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public Double getQuantity() {
        return this.quantity;
    }

    @Override
    public String getUnit() {
        return this.unit;
    }
}
