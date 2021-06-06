package food.model;

import food.model.input.FoodApi;
import food.model.models.Food;
import food.model.models.Nutrition;
import food.model.output.Twilio;
import food.view.observers.FoodListObserver;
import food.view.observers.MessageObserver;
import food.view.observers.NutritionObserver;
import javafx.application.Platform;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Concrete implementation of the ModelFacade class.
 */
public class ModelFacadeImpl implements ModelFacade {

    /**
     * The FoodDatabase API handler.
     */
    private final FoodApi database;

    /**
     * The Twilio API handler.
     */
    private final Twilio twilio;

    /**
     * Whether the run() method should continue running.
     * AtomicBoolean is a thread-safe wrapper for Boolean.
     */
    private final AtomicBoolean run;

    /**
     * The list of tasks to be executed within run().
     */
    private final List<Runnable> tasks;

    /**
     * Creates a new ModelFacadeImpl object.
     *
     * @param database The FoodDatabase the Facade will use.
     * @param twilio The Twilio the facade will use.
     */
    public ModelFacadeImpl(FoodApi database, Twilio twilio) {
        this.database = database;
        this.twilio = twilio;
        this.run = new AtomicBoolean(true);
        this.tasks = new CopyOnWriteArrayList<>();
    }

    @Override
    public void search(String term, boolean useCache, boolean quick) {
        List<Food> list = database.search(term, useCache);

        if (list == null) {
//            Platform.runLater(() -> observer.update(new NoSuchElementException("Unable to search cache or database")));
        } else if (quick) {
            // Check to see if quick search is possible
            boolean match = false;

            for (Food food : list) {
                if (food.getLabel().equalsIgnoreCase(term)) {
                    match = true;
//                    Platform.runLater(() -> observer.update(Collections.singletonList(food)));
                    break;
                }
            }

            // Quick search isn't possible
            if (!match) {
//                Platform.runLater(() -> observer.update(list));
            }
        } else {
//            Platform.runLater(() -> observer.update(list));
        }
    }

    @Override
    public void getNutrition(String foodID, String measure, boolean useCache) {
        Nutrition nutrition = database.getNutrition(foodID, measure, useCache);

        if (nutrition == null) {
//            Platform.runLater(() -> observer.update(new NoSuchElementException("No matching nutrition object")));
        } else {
//            Platform.runLater(() -> observer.update(nutrition));
        }
    }

    @Override
    public void sendMessage(String message) {
        boolean result = twilio.sendMessage(message);

//        Platform.runLater(() -> observer.update(result));
    }

    @Override
    public void run() {
        while (run.get()) {
            if (this.tasks.size() > 0) {
                Runnable task = tasks.remove(0);
                task.run();
            }
        }
    }

    @Override
    public void stop() {
        this.run.set(false);
    }

    @Override
    public void queueSearch(String term, boolean useCache, boolean quick) {
//        this.tasks.add(() -> this.search(term, useCache, quick, observer));
    }

    @Override
    public void queueGetNutrition(String foodID, String measure, boolean useCache) {
//        this.tasks.add(() -> this.getNutrition(foodID, measure, useCache, observer));
    }

    @Override
    public void queueSendMessage(String message) {
//        this.tasks.add(() -> this.sendMessage(message, observer));
    }

    @Override
    public void attach(FoodListObserver observer) {

    }

    @Override
    public void detach(FoodListObserver observer) {

    }

    @Override
    public void attach(NutritionObserver observer) {

    }

    @Override
    public void detach(NutritionObserver observer) {

    }

    @Override
    public void attach(MessageObserver observer) {

    }

    @Override
    public void detach(MessageObserver observer) {

    }
}
