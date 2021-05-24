package food.view.observers;

import food.model.models.Food;

import java.util.List;

public interface FoodListObserver extends BaseObserver {

    void update(List<Food> food);
}
