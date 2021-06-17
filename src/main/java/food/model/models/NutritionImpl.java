package food.model.models;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Concrete implementation of Nutrition interface.
 */
public class NutritionImpl implements Nutrition {

    /**
     * The total energy in kcal.
     */
    private final Integer calories;

    /**
     * The total weight of the item.
     */
    private final Double totalWeight;

    /**
     * The diet labels for the food item.
     */
    private final List<String> dietLabels;

    /**
     * The health labels for the food item.
     */
    private final List<String> healthLabels;

    /**
     * The cuations for the food item.
     */
    private final List<String> cautions;

    /**
     * The Total nutrients present in the food item.
     */
    private final Map<String, Nutrient> totalNutrients;

    /**
     * The nutrients as a percentage of daily intake.
     */
    private final Map<String, Nutrient> totalDaily;

    /**
     * The list of ingredients present in the food item.
     */
    private List<Ingredient> ingredients;

    /**
     * Whether the calorie count is over the limit.
     */
    private boolean over;

    /**
     * Creates a Nutrient object.
     *
     * @param json The JSONObject to base the Nutrition off of.
     */
    public NutritionImpl(JSONObject json) {
        this.calories = ((Long) json.get("calories")).intValue();
        this.totalWeight = (Double) json.get("totalWeight");

        this.dietLabels = new ArrayList<>();

        JSONArray diet = (JSONArray) json.get("dietLabels");

        for (Object o : diet) {
            dietLabels.add((String) o);
        }

        this.healthLabels = new ArrayList<>();

        JSONArray health = (JSONArray) json.get("healthLabels");

        for (Object o : health) {
            healthLabels.add((String) o);
        }

        this.cautions = new ArrayList<>();

        JSONArray caution = (JSONArray) json.get("cautions");

        for (Object o : caution) {
            cautions.add((String) o);
        }

        this.totalNutrients = new HashMap<>();

        JSONObject totalNuts = (JSONObject) json.get("totalNutrients");

        for (Object o : totalNuts.keySet()) {
            String nut = (String) o;
            totalNutrients.put(nut, new NutrientImpl((JSONObject) totalNuts.get(nut)));
        }

        this.totalDaily = new HashMap<>();

        JSONObject daily = (JSONObject) json.get("totalDaily");

        for (Object o : daily.keySet()) {
            String nut = (String) o;
            totalDaily.put(nut, new NutrientImpl((JSONObject) daily.get(nut)));
        }

        this.ingredients = new ArrayList<>();

        JSONArray ingredients = (JSONArray) json.get("ingredients");

        if (ingredients.size() > 0) {
            JSONObject ingredient = (JSONObject) ingredients.get(0);

            JSONArray parsed = (JSONArray) ingredient.get("parsed");

            for (Object o : parsed) {
                this.ingredients.add(new IngredientImpl((JSONObject) o));
            }
        }
    }

    @Override
    public Integer getCalories() {
        return this.calories;
    }

    @Override
    public Double getTotalWeight() {
        return this.totalWeight;
    }

    @Override
    public List<String> getDietLabels() {
        return this.dietLabels;
    }

    @Override
    public List<String> getHealthLabels() {
        return this.healthLabels;
    }

    @Override
    public List<String> getCautions() {
        return this.cautions;
    }

    @Override
    public Map<String, Nutrient> getTotalNutrients() {
        return this.totalNutrients;
    }

    @Override
    public Map<String, Nutrient> getTotalDaily() {
        return this.totalDaily;
    }

    @Override
    public List<Ingredient> getIngredients() {
        return this.ingredients;
    }

    @Override
    public void setOverCalorieLimit() {

    }

    @Override
    public boolean isOverCalorieLimit() {
        return false;
    }
}
