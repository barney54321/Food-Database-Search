package food.controller;

import food.backend.input.FoodDatabase;
import food.model.Food;
import food.model.Nutrition;

import java.util.List;

/**
 * Concrete implementation of Facade interface.
 */
public class FacadeImpl implements Facade {

    private FoodDatabase foodDatabase;

    public FacadeImpl(FoodDatabase foodDatabase) {
        this.foodDatabase = foodDatabase;
    }

    @Override
    public List<Food> search(String term) throws IllegalArgumentException {
        if (term == null || term.equals("")) {
            throw new IllegalArgumentException("Search term cannot be empty");
        }

        return this.foodDatabase.search(term);
    }

    @Override
    public Nutrition getNutrition(String foodID, String measure) throws IllegalArgumentException {
        if (foodID == null || foodID.equals("")) {
            throw new IllegalArgumentException("ID cannot be empty");
        } else if (measure == null || measure.equals("")) {
            throw new IllegalArgumentException("Measure cannot be empty");
        }

        return this.foodDatabase.getNutrition(foodID, measure);
    }
}
