package food.model;

import food.model.input.FoodDatabase;
import food.model.models.Food;
import food.model.models.Nutrition;
import food.model.output.Twilio;
import food.view.observers.FoodListObserver;
import food.view.observers.MessageObserver;
import food.view.observers.NutritionObserver;

import java.util.List;

/**
 * Concrete implementation of the ModelFacade class.
 */
public class ModelFacadeImpl implements ModelFacade {

    /**
     * The FoodDatabase API handler.
     */
    private FoodDatabase database;

    /**
     * The Twilio API handler.
     */
    private Twilio twilio;

    /**
     * Creates a new ModelFacadeImpl object.
     * @param database The FoodDatabase the Facade will use.
     * @param twilio The Twilio the facade will use.
     */
    public ModelFacadeImpl(FoodDatabase database, Twilio twilio) {
        this.database = database;
        this.twilio = twilio;
    }

    @Override
    public void search(String term, FoodListObserver observer) {
        List<Food> list = database.search(term);

        observer.update(list);
    }

    @Override
    public void getNutrition(String foodID, String measure, NutritionObserver observer) {
        Nutrition nutrition = database.getNutrition(foodID, measure);

        observer.update(nutrition);
    }

    @Override
    public void sendMessage(String message, MessageObserver observer) {
        boolean result = twilio.sendMessage(message);

        observer.update(result);
    }
}