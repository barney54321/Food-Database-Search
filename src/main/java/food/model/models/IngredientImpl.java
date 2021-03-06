package food.model.models;

import org.json.simple.JSONObject;

/**
 * Concrete implementation of Ingredient interface.
 */
public class IngredientImpl implements Ingredient {

    /**
     * Quantity of specified measure.
     */
    private Double quantity;

    /**
     * Measurement unit used for quantity.
     */
    private String measure;

    /**
     * Total weight in grams.
     */
    private Double weight;

    /**
     * The source food item for the ingredient.
     */
    private String food;

    /**
     * The ID of the source food item.
     */
    private String foodID;

    /**
     * Creates a new Ingredient object based on JSONObject.
     * @param json The JSONObject the ingredient is based on.
     */
    public IngredientImpl(JSONObject json) {

        try {
            this.quantity = ((Long) json.get("quantity")).doubleValue();
        } catch (ClassCastException e) {
            this.quantity = (Double) json.get("quantity");
        }

        this.measure = (String) json.get("measure");

        try {
            this.weight = ((Long) json.get("weight")).doubleValue();
        } catch (ClassCastException e) {
            this.weight = (Double) json.get("weight");
        }

        this.food = (String) json.get("food");
        this.foodID = (String) json.get("foodId");
    }

    @Override
    public Double getQuantity() {
        return this.quantity;
    }

    @Override
    public String getMeasure() {
        return this.measure;
    }

    @Override
    public Double getWeight() {
        return this.weight;
    }

    @Override
    public String getFood() {
        return this.food;
    }

    @Override
    public String getFoodID() {
        return this.foodID;
    }
}