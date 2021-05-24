package food.model.models;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NutritionImpl implements Nutrition {

    /**
     * THe Ontology identifier.
     */
    private String url;

    /**
     * The total energy in kcal.
     */
    private Integer calories;

    /**
     * The total weight of the item.
     */
    private Double totalWeight;

    /**
     * The diet labels for the food item.
     */
    private List<String> dietLabels;

    /**
     * The health labels for the food item.
     */
    private List<String> healthLabels;

    /**
     * The cuations for the food item.
     */
    private List<String> cautions;

    /**
     * The Total nutrients present in the food item.
     */
    private Map<String, Nutrient> totalNutrients;

    /**
     * The nutrients as a percentage of daily intake.
     */
    private Map<String, Nutrient> totalDaily;

    /**
     * The list of ingredients present in the food item.
     */
    private List<Ingredient> ingredients;

    public NutritionImpl(JSONObject json) {
        this.url = (String) json.get("uri");
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
    public String getURI() {
        return this.url;
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
}
