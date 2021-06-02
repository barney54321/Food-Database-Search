package food.model;

import food.view.observers.FoodListObserver;
import food.view.observers.MessageObserver;
import food.view.observers.NutritionObserver;

/**
 * Interface representing the Facade that covers all Model aspects.
 */
public interface ModelFacade {

    /**
     * Searches for matching food items and updates the observer.
     *
     * @param term The term to search on.
     * @param useCache Whether to use cached data.
     * @param quick Whether to run a quick search.
     * @param observer The observer to update.
     */
    void search(String term, boolean useCache, boolean quick, FoodListObserver observer);

    /**
     * Retrieves the nutritional information for the given food item and updates the observer.
     *
     * @param foodID The associated food item.
     * @param measure The size of the nutritional information.
     * @param useCache Whether to use cached data.
     * @param observer The observer to update.
     */
    void getNutrition(String foodID, String measure, boolean useCache, NutritionObserver observer);

    /**
     * Sends the provided message and updates the observer on the outcome.
     *
     * @param message The message to send.
     * @param observer The observer to update.
     */
    void sendMessage(String message, MessageObserver observer);

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
     * @param observer The observer the task will update.
     */
    void queueSearch(String term, boolean useCache, boolean quick, FoodListObserver observer);

    /**
     * Adds getNutrition() to the list of tasks to be executed.
     *
     * @param foodID The food item the task will use.
     * @param measure The size the task will use.
     * @param useCache Whether the task will use cached data.
     * @param observer The observer the task will update.
     */
    void queueGetNutrition(String foodID, String measure, boolean useCache, NutritionObserver observer);

    /**
     * Adds sendMessage() to the list of tasks to be executed.
     *
     * @param message The message the task will send.
     * @param observer The observer the task will update.
     */
    void queueSendMessage(String message, MessageObserver observer);

}
