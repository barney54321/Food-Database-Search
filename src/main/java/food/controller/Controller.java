package food.controller;

import food.model.models.Food;
import food.model.models.Nutrition;
import food.view.FoodWindow;
import food.view.observers.BaseObserver;
import food.view.observers.FoodListObserver;
import food.view.observers.MessageObserver;
import food.view.observers.NutritionObserver;
import food.view.screen.Screen;

/**
 * The base Controller interface.
 *
 * Defines behaviours used for accessing Database and APIs.
 */
public interface Controller {

    /**
     * Delegates searching for item to Model and passes along the observer.
     * Updates observer with exception if term is empty or null.
     *
     * @param term The term to search on.
     * @param useCache Whether to use cached data.
     * @param quick Whether to use quick search.
     * @param observer The observer to update in case of error.
     */
    void search(String term, boolean useCache, boolean quick, BaseObserver observer);

    /**
     * Delegates retrieving nutrition to the Model and passes along the observer.
     * Updates observer with exception if food or measure is empty or null.
     *
     * @param food The food item to retrieve nutritional information for.
     * @param measure The size to use.
     * @param useCache Whether to use cached data.
     * @param observer The observer to update in case of error.
     */
    void getNutrition(Food food, String measure, boolean useCache, BaseObserver observer);

    /**
     * Delegates sending messages to the Model and passes along the observer.
     * Updates observer with exception if food or size is empty or null.
     *
     * @param food The food item to generate the report for.
     * @param nutrition The nutrition item to use in the report.
     * @param size The size for the nutritional information.
     * @param observer The observer to update in case of error.
     */
    void sendMessage(Food food, Nutrition nutrition, String size, BaseObserver observer);

    /**
     * Injects the view object for MVC.
     *
     * @param view The View object.
     */
    void setView(FoodWindow view);

    /**
     * Updates the FoodWindow screen state.
     *
     * @param screen The new state.
     */
    void setScreen(Screen screen);

    /**
     * Refreshes the view.
     */
    void refresh();

    /**
     * Informs model of a new FoodListObserver object.
     *
     * @param observer The observer to update with.
     */
    void registerFoodListObserver(FoodListObserver observer);

    /**
     * Informs model of a new NutritionObserver object.
     *
     * @param observer The observer to update with.
     */
    void registerNutritionObserver(NutritionObserver observer);

    /**
     * Informs model of a new MessageObserver object.
     *
     * @param observer The observer to update with.
     */
    void registerMessageObserver(MessageObserver observer);

    /**
     * Informs model that a NutritionObserver object is being removed.
     *
     * @param observer The observer to update with.
     */
    void removeNutritionObserver(NutritionObserver observer);

    /**
     * Informs model that a MessageObserver object is being removed.
     *
     * @param observer The observer to update with.
     */
    void removeMessageObserver(MessageObserver observer);
}
