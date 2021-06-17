package food.model;

import food.model.input.FoodApi;
import food.model.models.Food;
import food.model.models.Nutrition;
import food.model.output.Twilio;
import food.view.observers.FoodListObserver;
import food.view.observers.MessageObserver;
import food.view.observers.NutritionObserver;
import javafx.application.Platform;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     * The list of food list observers.
     */
    private List<FoodListObserver> foodObservers;

    /**
     * The list of nutrition observers.
     */
    private List<NutritionObserver> nutritionObservers;

    /**
     * The list of message observers.
     */
    private List<MessageObserver> messageObservers;

    /**
     * The maximum number of calories that can be displayed without an error message.
     */
    private int maxCalories;

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

        this.foodObservers = new CopyOnWriteArrayList<>();
        this.nutritionObservers = new CopyOnWriteArrayList<>();
        this.messageObservers = new CopyOnWriteArrayList<>();

        this.maxCalories = 0;
    }

    @Override
    public void search(String term, boolean useCache, boolean quick) {
        List<Food> list = database.search(term, useCache);

        if (list == null) {
            updateFoodObservers(new NoSuchElementException("Unable to search cache or database"));
        } else if (quick) {
            // Check to see if quick search is possible
            boolean match = false;

            for (Food food : list) {
                if (food.getLabel().equalsIgnoreCase(term)) {
                    match = true;
                    updateFoodObservers(Collections.singletonList(food));
                    break;
                }
            }

            // Quick search isn't possible
            if (!match && list.size() > 0) {
                updateFoodObservers(Collections.singletonList(list.get(0)));
            } else if (!match) {
                updateFoodObservers(list);
            }
        } else {
            updateFoodObservers(list);
        }
    }

    private void updateFoodObservers(Exception exception) {
        for (FoodListObserver observer : foodObservers) {
            Platform.runLater(() -> observer.update(exception));
        }
    }

    private void updateFoodObservers(List<Food> list) {
        for (FoodListObserver observer : foodObservers) {
            Platform.runLater(() -> observer.update(list));
        }
    }

    @Override
    public void getNutrition(String foodID, String measure, boolean useCache) {
        Nutrition nutrition = database.getNutrition(foodID, measure, useCache);

        if (nutrition == null) {
            updateNutritionObservers(new NoSuchElementException("No matching nutrition object"));
        } else {
            updateNutritionObservers(nutrition);

            if (nutrition.getCalories() == null || nutrition.getCalories() > this.maxCalories) {
                nutrition.setOverCalorieLimit();
                updateNutritionObservers(new Exception("This item is above the max calorie amount."));
            }
        }
    }

    private void updateNutritionObservers(Exception exception) {
        for (NutritionObserver observer : nutritionObservers) {
            Platform.runLater(() -> observer.update(exception));
        }
    }

    private void updateNutritionObservers(Nutrition nutrition) {
        for (NutritionObserver observer : nutritionObservers) {
            Platform.runLater(() -> observer.update(nutrition));
        }
    }

    @Override
    public void sendMessage(String message) {
        boolean result = twilio.sendMessage(message);
        updateMessageObservers(result);
    }

    private void updateMessageObservers(boolean result) {
        for (MessageObserver observer : messageObservers) {
            Platform.runLater(() -> observer.update(result));
        }
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
        this.tasks.add(() -> this.search(term, useCache, quick));
    }

    @Override
    public void queueGetNutrition(String foodID, String measure, boolean useCache) {
        this.tasks.add(() -> this.getNutrition(foodID, measure, useCache));
    }

    @Override
    public void queueSendMessage(String message) {
        this.tasks.add(() -> this.sendMessage(message));
    }

    @Override
    public void attach(FoodListObserver observer) {
        this.foodObservers.add(observer);
    }

    @Override
    public void detach(FoodListObserver observer) {
        this.foodObservers.remove(observer);
    }

    @Override
    public void attach(NutritionObserver observer) {
        this.nutritionObservers.add(observer);
    }

    @Override
    public void detach(NutritionObserver observer) {
        this.nutritionObservers.remove(observer);
    }

    @Override
    public void attach(MessageObserver observer) {
        this.messageObservers.add(observer);
    }

    @Override
    public void detach(MessageObserver observer) {
        this.messageObservers.remove(observer);
    }

    @Override
    public void setMaxCalories(int maxCalories) {
        this.maxCalories = maxCalories;
    }
}
