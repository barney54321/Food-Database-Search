package food.controller;

import food.model.ModelFacade;
import food.model.models.Food;
import food.model.models.Nutrition;
import food.view.FoodWindow;
import food.view.observers.BaseObserver;
import food.view.observers.FoodListObserver;
import food.view.observers.MessageObserver;
import food.view.observers.NutritionObserver;
import food.view.screen.FoodScreen;
import food.view.screen.Screen;

/**
 * Concrete implementation of Controller interface.
 */
public class ControllerImpl implements Controller {

    /**
     * Characters not allowed to be present in search terms
     */
    private final static String[] ILLEGAL_CHARS = {"\0", "'", "\"", "\r", "\b", "\\", "%", "_", ";"};

    /**
     * The Facade to interact with.
     */
    private final ModelFacade facade;

    /**
     * The view object as per MVC
     */
    private FoodWindow view;

    /**
     * Creates a new Controller object.
     *
     * @param facade The Facade to interact with.
     */
    public ControllerImpl(ModelFacade facade) {
        this.facade = facade;
    }

    @Override
    public void search(String term, boolean useCache, boolean quick, BaseObserver observer) {

        if (term == null || term.equals("")) {
            observer.update(new IllegalArgumentException("Search term cannot be empty"));
            return;
        }

        for (String illegal : ILLEGAL_CHARS) {
            if (term.contains(illegal)) {
                observer.update(new IllegalArgumentException("Illegal characters present in search term"));
                return;
            }
        }

        this.facade.queueSearch(term, useCache, quick);
    }

    @Override
    public void getNutrition(Food food, String measure, boolean useCache, BaseObserver observer) {
        if (food == null) {
            observer.update(new IllegalArgumentException("Food cannot be null"));
        } else if (measure == null || measure.equals("")) {
            observer.update(new IllegalArgumentException("Measure cannot be empty"));
        } else {
            this.facade.queueGetNutrition(food.getID(), measure, useCache);
        }
    }

    @Override
    public void sendMessage(Food food, Nutrition nutrition, String size, BaseObserver observer) {
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
            this.facade.queueSendMessage(food.generateReport(size, nutrition));
        }
    }

    @Override
    public void setView(FoodWindow view) {
        this.view = view;
    }

    @Override
    public void setScreen(Screen screen) {
        this.view.setScreen(screen);
    }

    @Override
    public void refresh() {
        this.view.refresh();
    }

    @Override
    public void registerFoodListObserver(FoodListObserver observer) {
        this.facade.attach(observer);
    }

    @Override
    public void registerNutritionObserver(NutritionObserver observer) {
        this.facade.attach(observer);
    }

    @Override
    public void registerMessageObserver(MessageObserver observer) {
        this.facade.attach(observer);
    }

    @Override
    public void removeNutritionObserver(NutritionObserver observer) {
        this.facade.detach(observer);
    }

    @Override
    public void removeMessageObserver(MessageObserver observer) {
        this.facade.detach(observer);
    }

    @Override
    public void setMaxCalories(String field, BaseObserver observer) {

    }
}
