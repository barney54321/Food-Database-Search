package food.model;

import org.json.simple.JSONObject;

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

    public IngredientImpl(JSONObject json) {
        this.quantity = ((Long) json.get("quantity")).doubleValue();
        this.measure = (String) json.get("measure");
        this.weight = ((Long) json.get("weight")).doubleValue();
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
