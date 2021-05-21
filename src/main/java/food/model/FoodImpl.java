package food.model;

import org.json.simple.JSONObject;

import java.util.Map;

/**
 * Concrete implementation of the Food interface.
 */
public class FoodImpl implements Food {

    public FoodImpl(JSONObject json) {

    }

    @Override
    public String getID() {
        return null;
    }

    @Override
    public String getLabel() {
        return null;
    }

    @Override
    public Map<String, Double> getNutrients() {
        return null;
    }

    @Override
    public String getBrand() {
        return null;
    }

    @Override
    public String getCategory() {
        return null;
    }

    @Override
    public String getCategoryLabel() {
        return null;
    }

    @Override
    public String getFoodContentsLabel() {
        return null;
    }

    @Override
    public String getImagePath() {
        return null;
    }

    @Override
    public Double getServingsPerContainer() {
        return null;
    }

    @Override
    public Map<String, Double> getMeasures() {
        return null;
    }
}
