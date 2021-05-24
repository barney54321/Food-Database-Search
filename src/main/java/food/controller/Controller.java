package food.controller;

import food.model.Food;
import food.model.Nutrition;

import java.util.List;

/**
 * The base Controller interface.
 * Defines behaviours used for accessing Database and APIs.
 */
public interface Controller {

    /**
     * Searches the input API for food items based on the given search term.
     * @param term The search term to search on.
     * @return The list of matching Food objects.
     * @throws IllegalArgumentException If the search term is empty or null.
     */
    List<Food> search(String term) throws IllegalArgumentException;

    /**
     * Returns the Nutrition object associated with the given foodID, based on result from input API.
     * @param foodID The foodID to search on.
     * @param measure The url for the measurement unit to search on.
     * @return The corresponding foodID.
     * @throws IllegalArgumentException If the id or measure is empty or null.
     */
    Nutrition getNutrition(String foodID, String measure) throws IllegalArgumentException;

    /**
     * Sends the Food objects report by SMS, using Twilio.
     * @param food The Food object to generate a report for.
     * @param size The size to use for the food.
     * @return Whether the message is successfully sent.
     * @throws IllegalArgumentException If the food object or size is missing or invalid.
     */
    boolean sendMessage(Food food, String size) throws IllegalArgumentException;
}
