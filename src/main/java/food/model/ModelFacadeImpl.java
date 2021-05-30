package food.model;

import food.model.input.FoodApi;
import food.model.models.Food;
import food.model.models.Nutrition;
import food.model.output.Twilio;
import food.view.observers.FoodListObserver;
import food.view.observers.MessageObserver;
import food.view.observers.NutritionObserver;
import javafx.application.Platform;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Concrete implementation of the ModelFacade class.
 */
public class ModelFacadeImpl implements ModelFacade {

    /**
     * The FoodDatabase API handler.
     */
    private FoodApi database;

    /**
     * The Twilio API handler.
     */
    private Twilio twilio;

    /**
     * Whether the run() method should continue running.
     */
    private boolean run;

    /**
     * The list of tasks to be executed within run().
     */
    private List<Runnable> tasks;

    /**
     * Creates a new ModelFacadeImpl object.
     *
     * @param database The FoodDatabase the Facade will use.
     * @param twilio The Twilio the facade will use.
     */
    public ModelFacadeImpl(FoodApi database, Twilio twilio) {
        this.database = database;
        this.twilio = twilio;
        this.run = true;
        this.tasks = new CopyOnWriteArrayList<>();
    }

    @Override
    public void search(String term, boolean useCache, FoodListObserver observer) {
        List<Food> list = database.search(term, useCache);

        Platform.runLater(() -> observer.update(list));
    }

    @Override
    public void getNutrition(String foodID, String measure, boolean useCache, NutritionObserver observer) {
        Nutrition nutrition = database.getNutrition(foodID, measure, useCache);

        if (nutrition == null) {
            Platform.runLater(() -> observer.update(new NoSuchElementException("No matching nutrition object")));
        } else {
            Platform.runLater(() -> observer.update(nutrition));
        }
    }

    @Override
    public void sendMessage(String message, MessageObserver observer) {
        boolean result = twilio.sendMessage(message);

        Platform.runLater(() -> observer.update(result));
    }

    @Override
    public void run() {
        while (run) {

            if (this.tasks.size() > 0) {
                Runnable task = tasks.remove(0);
                task.run();
            }

            // This is just here so that the loop doesn't cache run
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                // No op
            }
        }
    }

    @Override
    public void stop() {
        this.run = false;
    }

    @Override
    public void queueSearch(String term, boolean useCache, FoodListObserver observer) {
        this.tasks.add(() -> this.search(term, useCache, observer));
    }

    @Override
    public void queueGetNutrition(String foodID, String measure, boolean useCache, NutritionObserver observer) {
        this.tasks.add(() -> this.getNutrition(foodID, measure, useCache, observer));
    }

    @Override
    public void queueSendMessage(String message, MessageObserver observer) {
        this.tasks.add(() -> this.sendMessage(message, observer));
    }
}
