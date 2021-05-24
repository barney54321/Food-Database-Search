package food.view.observers;

import food.model.models.Nutrition;

public interface NutritionObserver extends BaseObserver {

    void update(Nutrition nutrition);
}
