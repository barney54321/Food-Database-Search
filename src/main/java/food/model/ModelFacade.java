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
     * @param term The term to search on.
     * @param useCache Whether to use cached data.
     * @param observer The observer to update.
     */
    void search(String term, boolean useCache, FoodListObserver observer);

    /**
     * Retrieves the nutritional information for the given food item and updates the observer.
     * @param foodID The associated food item.
     * @param measure The size of the nutritional information.
     * @param useCache Whether to use cached data.
     * @param observer The observer to update.
     */
    void getNutrition(String foodID, String measure, boolean useCache, NutritionObserver observer);

    /**
     * Sends the provided message and updates the observer on the outcome.
     * @param message The message to send.
     * @param observer The observer to update.
     */
    void sendMessage(String message, MessageObserver observer);
}
