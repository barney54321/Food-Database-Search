package food.view.observers;

import food.model.models.Food;

import java.util.List;

public interface FoodListObserver {

    void update(List<Food> food);
}
