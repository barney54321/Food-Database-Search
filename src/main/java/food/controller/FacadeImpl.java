package food.controller;

import food.backend.input.FoodDatabase;
import food.model.Food;
import food.model.Nutrition;

import java.util.List;

/**
 * Concrete implementation of Facade interface.
 */
public class FacadeImpl implements Facade {

    public FacadeImpl(FoodDatabase foodDatabase) {

    }

    @Override
    public List<Food> search(String term) throws IllegalArgumentException {
        return null;
    }

    @Override
    public Nutrition getNutrition(String foodID, String measure) throws IllegalArgumentException {
        return null;
    }
}
