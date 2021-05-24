package food.controller;

import food.model.ModelFacade;
import food.model.input.FoodDatabase;
import food.model.output.Twilio;
import food.model.models.Food;
import food.model.models.Nutrition;
import food.view.observers.FoodListObserver;
import food.view.observers.MessageObserver;
import food.view.observers.NutritionObserver;

import java.util.List;

/**
 * Concrete implementation of Controller interface.
 */
public class ControllerImpl implements Controller {

    public ControllerImpl(ModelFacade facade) {

    }

    @Override
    public void search(String term, FoodListObserver observer) {

    }

    @Override
    public void getNutrition(Food food, String measure, NutritionObserver observer) {

    }

    @Override
    public void sendMessage(Food food, String size, MessageObserver observer) {

    }
}
