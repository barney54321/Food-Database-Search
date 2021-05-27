package food.model.input;

import food.model.input.cache.Database;
import food.model.models.Food;
import food.model.models.Nutrition;

import java.util.List;

public class FoodAPIImpl implements FoodAPI {

    public FoodAPIImpl(Database cache, FoodStrategy strategy) {

    }

    @Override
    public List<Food> search(String term) {
        return null;
    }

    @Override
    public Nutrition getNutrition(String foodID, String measure) {
        return null;
    }
}
