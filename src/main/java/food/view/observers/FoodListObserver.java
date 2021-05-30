package food.view.observers;

import food.model.models.Food;

import java.util.List;

/**
 * The interface defining behaviours for observers of Food Lists (e.g. SearchScreen).
 */
public interface FoodListObserver extends BaseObserver {

    /**
     * Updates the observer based on the provided list of Food objects.
     *
     * @param food The list of Food objects to update on.
     */
    void update(List<Food> food);
}
