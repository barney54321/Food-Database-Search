package food.model;

import org.json.simple.JSONObject;

import java.util.List;
import java.util.Map;

public class NutritionImpl implements Nutrition {

    public NutritionImpl(JSONObject json) {

    }

    @Override
    public String getURI() {
        return null;
    }

    @Override
    public Integer getCalories() {
        return null;
    }

    @Override
    public Double getTotalWeight() {
        return null;
    }

    @Override
    public List<String> getDietLabels() {
        return null;
    }

    @Override
    public List<String> getHealthLabels() {
        return null;
    }

    @Override
    public List<String> getCautions() {
        return null;
    }

    @Override
    public Map<String, Nutrient> getTotalNutrients() {
        return null;
    }

    @Override
    public Map<String, Nutrient> getTotalDaily() {
        return null;
    }

    @Override
    public List<Ingredient> getIngredients() {
        return null;
    }
}
