package food.controller;

import food.backend.input.FoodDatabase;
import food.backend.output.Twilio;
import food.model.Food;
import food.model.Nutrition;

import java.util.List;

/**
 * Concrete implementation of Controller interface.
 */
public class ControllerImpl implements Controller {

    private FoodDatabase foodDatabase;
    private Twilio twilio;

    public ControllerImpl(FoodDatabase foodDatabase, Twilio twilio) {
        this.foodDatabase = foodDatabase;
        this.twilio = twilio;
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

    @Override
    public boolean sendMessage(Food food, String size) throws IllegalArgumentException {
        if (food == null || size == null || size.equals("")) {
            throw new IllegalArgumentException("Arguments cannot be empty");
        }

        try {
            return this.twilio.sendMessage(food.generateReport(size));
        } catch (IllegalStateException e) {
            throw new IllegalArgumentException("Size doesn't exist");
        }
    }
}
