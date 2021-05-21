package food.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Concrete implementation of the Food interface.
 */
public class FoodImpl implements Food {

    private String id;
    private String label;
    private Map<String, Double> nutrients;
    private String brand;
    private String category;
    private String categoryLabel;
    private String foodContentsLabel;
    private String imagePath;
    private Double servingsPerContainer;
    private Map<String, Double> measures;

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

        this.nutrients = new HashMap<String, Double>();

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
            this.measures.put((String) measure.get("label"), (Double) measure.get("weight"));
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
        return this.brand;
    }

    @Override
    public String getCategory() {
        return this.category;
    }

    @Override
    public String getCategoryLabel() {
        return this.categoryLabel;
    }

    @Override
    public String getFoodContentsLabel() {
        return this.foodContentsLabel;
    }

    @Override
    public String getImagePath() {
        return this.imagePath;
    }

    @Override
    public Double getServingsPerContainer() {
        return this.servingsPerContainer;
    }

    @Override
    public Map<String, Double> getMeasures() {
        return this.measures;
    }
}
