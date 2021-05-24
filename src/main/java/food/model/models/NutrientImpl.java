package food.model.models;

import org.json.simple.JSONObject;

public class NutrientImpl implements Nutrient {

    /**
     * The display label.
     */
    private String label;

    /**
     * The quantity of specified units.
     */
    private Double quantity;

    /**
     * The unit of measurement for quantity.
     */
    private String unit;

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
