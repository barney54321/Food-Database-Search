package food.model.models;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Concrete implementation of the Food interface.
 */
public class FoodImpl implements Food {

    /**
     * The Food id.
     */
    private final String id;

    /**
     * The label (name) of the food.
     */
    private final String label;

    /**
     * The nutrients present in the food.
     */
    private final Map<String, Double> nutrients;

    /**
     * The brand that produces the food.
     */
    private final String brand;

    /**
     * The category of the food item.
     */
    private final String category;

    /**
     * The label of the category.
     */
    private final String categoryLabel;

    /**
     * The contents of the food.
     */
    private final String foodContentsLabel;

    /**
     * The image path for the food.
     */
    private final String imagePath;

    /**
     * The number of servings per container.
     */
    private final Double servingsPerContainer;

    /**
     * The available measures for the food.
     */
    private final Map<String, String> measures;

    /**
     * Creates a new FoodImpl object based on the provided JSONObject.
     *
     * @param json The JSONObject to base the Food item off of.
     */
    public FoodImpl(JSONObject json) {
        JSONObject food = (JSONObject) json.get("food");

        this.id = (String) food.get("foodId");
        this.label = (String) food.get("label");
        this.brand = (String) food.get("brand");
        this.category = (String) food.get("category");
        this.categoryLabel = (String) food.get("categoryLabel");
        this.foodContentsLabel = (String) food.get("foodContentsLabel");
        this.imagePath = (String) food.get("image");
        this.servingsPerContainer = (Double) food.get("servingsPerContainer");

        this.nutrients = new HashMap<>();

        JSONObject nutrients = (JSONObject) food.get("nutrients");

        this.nutrients.put("ENERC_KCAL", (Double) nutrients.get("ENERC_KCAL"));
        this.nutrients.put("PROCNT", (Double) nutrients.get("PROCNT"));
        this.nutrients.put("FAT", (Double) nutrients.get("FAT"));
        this.nutrients.put("CHOCDF", (Double) nutrients.get("CHOCDF"));
        this.nutrients.put("FIBTG", (Double) nutrients.get("FIBTG"));

        this.measures = new HashMap<>();

        JSONArray measures = (JSONArray) json.get("measures");

        for (Object o : measures) {
            JSONObject measure = (JSONObject) o;
            this.measures.put((String) measure.get("label"), (String) measure.get("uri"));
        }
    }

    @Override
    public String getID() {
        return this.id;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public Map<String, Double> getNutrients() {
        return this.nutrients;
    }

    @Override
    public String getBrand() {
        return this.brand == null ? "N/A" : this.brand;
    }

    @Override
    public String getCategory() {
        return this.category == null ? "N/A" : this.category;
    }

    @Override
    public String getCategoryLabel() {
        return this.categoryLabel == null ? "N/A" : this.categoryLabel;
    }

    @Override
    public String getFoodContentsLabel() {
        return this.foodContentsLabel == null ? "N/A" : this.foodContentsLabel;
    }

    @Override
    public String getImagePath() {
        return this.imagePath;
    }

    @Override
    public Double getServingsPerContainer() {
        return this.servingsPerContainer == null ? 0.0 : this.servingsPerContainer;
    }

    @Override
    public Map<String, String> getMeasures() {
        return this.measures;
    }

    @Override
    public String generateReport(String size, Nutrition nutrition) throws IllegalStateException {

        String res = "";

        if (nutrition.isOverCalorieLimit()) {
            res = "*";
        }

        res += "Food ID: " + this.id + "\n";
        res += "Label: " + this.label + "\n";
        res += "Brand: " + this.getBrand() + "\n";
        res += "Servings per container: " + this.getServingsPerContainer() + "\n";
        res += "Size: " + size + "\n";
        res += "Calories: " + nutrition.getCalories() + "\n";

        String diet = nutrition.getDietLabels().stream().map(x -> x.replace("_", " "))
                                                        .collect(Collectors.toList())
                                                        .toString();

        String health = nutrition.getHealthLabels().stream().map(x -> x.replace("_", " "))
                                                            .collect(Collectors.toList())
                                                            .toString();

        res += "Diet labels: " + diet + "\n";
        res += "Health labels: " + health + "\n\n";
        res += "Nutrients: \n";
        res += "ENERC_KCAL: " + this.nutrients.get("ENERC_KCAL") + "\n";
        res += "PROCNT: " + this.nutrients.get("PROCNT") + "\n";
        res += "FAT: " + this.nutrients.get("FAT") + "\n";
        res += "CHOCDF: " + this.nutrients.get("CHOCDF") + "\n";
        res += "FIBTG: " + this.nutrients.get("FIBTG") + "\n";

        return res;
    }
}
