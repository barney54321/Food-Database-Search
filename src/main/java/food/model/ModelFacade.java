package food.model;

import food.view.observers.FoodListObserver;
import food.view.observers.MessageObserver;
import food.view.observers.NutritionObserver;

/**
 * Interface representing the Facade that covers all Model aspects.
 */
public interface ModelFacade {

    /**
     * Searches for matching food items and updates the observers.
     *
     * @param term The term to search on.
     * @param useCache Whether to use cached data.
     * @param quick Whether to run a quick search.
     */
    void search(String term, boolean useCache, boolean quick);

    /**
     * Retrieves the nutritional information for the given food item and updates the observers.
     * If the number of calories in the item exceeds the max calorie value, the observers are also
     * updated with an error message.
     *
     * @param foodID The associated food item.
     * @param measure The size of the nutritional information.
     * @param useCache Whether to use cached data.
     */
    void getNutrition(String foodID, String measure, boolean useCache);

    /**
     * Sends the provided message and updates the observers on the outcome.
     *
     * @param message The message to send.
     */
    void sendMessage(String message);

    /**
     * The method run inside Worker thread.
     */
    void run();

    /**
     * Ends the worker thread method.
     */
    void stop();

    /**
     * Adds search() to the list of tasks to be executed.
     *
     * @param term The term the task will use.
     * @param useCache Whether the task will use cached data.
     * @param quick Whether the task will use a quick search.
     */
    void queueSearch(String term, boolean useCache, boolean quick);

    /**
     * Adds getNutrition() to the list of tasks to be executed.
     *
     * @param foodID The food item the task will use.
     * @param measure The size the task will use.
     * @param useCache Whether the task will use cached data.
     */
    void queueGetNutrition(String foodID, String measure, boolean useCache);

    /**
     * Adds sendMessage() to the list of tasks to be executed.
     *
     * @param message The message the task will send.
     */
    void queueSendMessage(String message);

    /**
     * Attaches a new FoodListObserver object.
     *
     * @param observer The observer wishing to subscribe.
     */
    void attach(FoodListObserver observer);

    /**
     * Detaches the given observer.
     *
     * @param observer The observer to detach.
     */
    void detach(FoodListObserver observer);

    /**
     * Attaches a new NutritionObserver object.
     *
     * @param observer The observer wishing to subscribe.
     */
    void attach(NutritionObserver observer);

    /**
     * Detaches the given observer.
     *
     * @param observer The observer to detach.
     */
    void detach(NutritionObserver observer);

    /**
     * Attaches a new MessageObserver object.
     *
     * @param observer The observer wishing to subscribe.
     */
    void attach(MessageObserver observer);

    /**
     * Detaches the given observer.
     *
     * @param observer The observer to detach.
     */
    void detach(MessageObserver observer);

    /**
     * Sets the maximum amount of calories without error.
     *
     * @param maxCalories The new upper limit for calories.
     */
    void setMaxCalories(int maxCalories);
}
