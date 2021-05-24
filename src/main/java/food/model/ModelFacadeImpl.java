package food.model;

import food.model.input.FoodDatabase;
import food.model.output.Twilio;
import food.view.observers.FoodListObserver;
import food.view.observers.MessageObserver;
import food.view.observers.NutritionObserver;

public class ModelFacadeImpl implements ModelFacade {

    public ModelFacadeImpl(FoodDatabase database, Twilio twilio) {

    }

    @Override
    public void search(String term, FoodListObserver observer) {

    }

    @Override
    public void getNutrition(String foodID, String measure, NutritionObserver observer) {

    }

    @Override
    public void sendMessage(String message, MessageObserver observer) {

    }
}
