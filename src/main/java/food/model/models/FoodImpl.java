package food.model.models;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * Concrete implementation of the Food interface.
 */
public class FoodImpl implements Food {

    /**
     * The Food id.
     */
    private String id;

    /**
     * The label (name) of the food.
     */
    private String label;

    /**
     * The nutrients present in the food.
     */
    private Map<String, Double> nutrients;

    /**
     * The brand that produces the food.
     */
    private String brand;

    /**
     * The category of the food item.
     */
    private String category;

    /**
     * The label of the category.
     */
    private String categoryLabel;

    /**
     * The contents of the food.
     */
    private String foodContentsLabel;

    /**
     * The image path for the food.
     */
    private String imagePath;

    /**
     * The number of servings per container.
     */
    private Double servingsPerContainer;

    /**
     * The available measures for the food.
     */
    private Map<String, String> measures;

    /**
     * The Food item's associated Nutrition objects.
     * String key represents the size for the nutrition object.
     */
    private Map<String, Nutrition> nutritionMap;

    /**
     * The method for loading in the associated Nutrition object.
     */
    private BiFunction<String, String, Nutrition> loader;

    /**
     * Creates a new FoodImpl object based on the provided JSONObject.
     * @param json The JSONObject to base the Food item off of.
     * @param loader How to retrieve the lazy loaded attributes.
     */
    public FoodImpl(JSONObject json, BiFunction<String, String, Nutrition> loader) {
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

        this.loader = loader;

        this.nutritionMap = new HashMap<>();
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
    public Nutrition getNutrition(String size) {
        return this.nutritionMap.computeIfAbsent(size, (str) -> loader.apply(this.id, str));
    }

    @Override
    public String generateReport(String size) throws IllegalStateException {
        if (!this.nutritionMap.containsKey(size)) {
            throw new IllegalStateException("Nutrition not present for given size");
        }

        String res = "";

        res += "Food ID: " + this.id + "\n";
        res += "Label: " + this.label + "\n";
        res += "Brand: " + this.getBrand() + "\n";
        res += "Servings per container: " + this.getServingsPerContainer() + "\n";
        res += "Size: " + size + "\n";
        res += "Calories: " + this.getNutrition(size).getCalories() + "\n";
        res += "Diet labels: " + this.getNutrition(size).getDietLabels().stream().map(x -> x.replace("_", " ")).collect(Collectors.toList()) + "\n";
        res += "Health labels: " + this.getNutrition(size).getHealthLabels().stream().map(x -> x.replace("_", " ")).collect(Collectors.toList()) + "\n\n";
        res += "Nutrients: \n";
        res += "ENERC_KCAL: " + this.nutrients.get("ENERC_KCAL") + "\n";
        res += "PROCNT: " + this.nutrients.get("PROCNT") + "\n";
        res += "FAT: " + this.nutrients.get("FAT") + "\n";
        res += "CHOCDF: " + this.nutrients.get("CHOCDF") + "\n";
        res += "FIBTG: " + this.nutrients.get("FIBTG") + "\n";

        return res;
    }
}