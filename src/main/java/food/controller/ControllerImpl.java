package food.controller;

import food.model.ModelFacade;
import food.model.models.Food;
import food.model.models.Nutrition;
import food.view.observers.FoodListObserver;
import food.view.observers.MessageObserver;
import food.view.observers.NutritionObserver;

/**
 * Concrete implementation of Controller interface.
 */
public class ControllerImpl implements Controller {

    /**
     * The Facade to interact with.
     */
    private ModelFacade facade;

    /**
     * Creates a new Controller object.
     * @param facade The Facade to interact with.
     */
    public ControllerImpl(ModelFacade facade) {
        this.facade = facade;
    }

    @Override
    public void search(String term, boolean useCache, FoodListObserver observer) {
        if (term == null || term.equals("")) {
            observer.update(new IllegalArgumentException("Search term cannot be empty"));
        } else {
            this.facade.search(term, useCache, observer);
        }
    }

    @Override
    public void getNutrition(Food food, String measure, NutritionObserver observer) {
        if (food == null) {
            observer.update(new IllegalArgumentException("Food cannot be null"));
        } else if (measure == null || measure.equals("")) {
            observer.update(new IllegalArgumentException("Measure cannot be empty"));
        } else {
            this.facade.getNutrition(food.getID(), measure, observer);
        }
    }

    @Override
    public void sendMessage(Food food, Nutrition nutrition, String size, MessageObserver observer) {
        if (food == null) {
            observer.update(new IllegalArgumentException("Food cannot be null"));
        } else if (size == null || size.equals("")) {
            observer.update(new IllegalArgumentException("Size cannot be empty"));
        } else if (nutrition == null) {
            observer.update(new IllegalArgumentException("Nutrition cannot be null"));
        } else if (!food.getMeasures().containsKey(size)) {
            System.out.println(food.getMeasures());
            observer.update(new IllegalArgumentException("Invalid size"));
        } else {
            this.facade.sendMessage(food.generateReport(size, nutrition), observer);
        }
    }
}
