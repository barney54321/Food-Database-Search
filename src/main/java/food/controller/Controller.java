package food.controller;

import food.model.models.Food;
import food.model.models.Nutrition;
import food.view.observers.FoodListObserver;
import food.view.observers.MessageObserver;
import food.view.observers.NutritionObserver;

/**
 * The base Controller interface.
 * Defines behaviours used for accessing Database and APIs.
 */
public interface Controller {

    /**
     * Delegates searching for item to Model and passes along the observer.
     * Updates observer with exception if term is empty or null.
     * @param term The term to search on.
     * @param useCache Whether to use cached data.
     * @param observer The observer the model will update.
     */
    void search(String term, boolean useCache, FoodListObserver observer);

    /**
     * Delegates retrieving nutrition to the Model and passes along the observer.
     * Updates observer with exception if food or measure is empty or null.
     * @param food The food item to retrieve nutritional information for.
     * @param measure The size to use.
     * @param useCache Whether to use cached data.
     * @param observer The observer the model will update.
     */
    void getNutrition(Food food, String measure, boolean useCache, NutritionObserver observer);

    /**
     * Delegates sending messages to the Model and passes along the observer.
     * Updates observer with exception if food or size is empty or null.
     * @param food The food item to generate the report for.
     * @param nutrition The nutrition item to use in the report.
     * @param size The size for the nutritional information.
     * @param observer The observer the model will update.
     */
    void sendMessage(Food food, Nutrition nutrition, String size, MessageObserver observer);
}
