package food.view.observers;

import food.model.models.Nutrition;

/**
 * The interface defining behaviours for observers of Nutrition (e.g. NutritionScreen).
 */
public interface NutritionObserver extends BaseObserver {

    /**
     * Updates the Observer based on the Nutrition object.
     * @param nutrition The Nutrition object to update the observer with.
     */
    void update(Nutrition nutrition);
}
