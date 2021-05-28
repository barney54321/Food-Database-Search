package food.controller;

import food.model.ModelFacade;
import food.model.models.Food;
import food.model.models.Nutrition;
import food.view.observers.FoodListObserver;
import food.view.observers.MessageObserver;
import food.view.observers.NutritionObserver;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.mockito.Mockito.*;

public class ControllerImplTest {

    private Controller controller;

    private ModelFacade facade;

    private FoodListObserver foodListObserver;
    private NutritionObserver nutritionObserver;
    private MessageObserver messageObserver;

    @Before
    public void setUp() {
        this.facade = mock(ModelFacade.class);
        this.foodListObserver = mock(FoodListObserver.class);
        this.nutritionObserver = mock(NutritionObserver.class);
        this.messageObserver = mock(MessageObserver.class);

        this.controller = new ControllerImpl(facade);
    }

    @Test
    public void searchNormalTrue() {
        controller.search("Apple", true, foodListObserver);

        verify(facade, times(1)).search("Apple", true, foodListObserver);
    }

    @Test
    public void searchNormalFalse() {
        controller.search("Apple", false, foodListObserver);

        verify(facade, times(1)).search("Apple", false, foodListObserver);
    }

    @Test
    public void searchEmpty() {
        controller.search("", true, foodListObserver);

        verify(facade, times(0)).search("", true, foodListObserver);
        verify(foodListObserver, times(1)).update(any(Exception.class));
    }

    @Test
    public void searchNull() {
        controller.search(null, true, foodListObserver);

        verify(facade, times(0)).search(null, true, foodListObserver);
        verify(foodListObserver, times(1)).update(any(Exception.class));
    }

    @Test
    public void getNutritionNormal() {
        Food mock = mock(Food.class);

        when(mock.getID()).thenReturn("1234");

        controller.getNutrition(mock, "size1", nutritionObserver);

        verify(facade, times(1)).getNutrition("1234", "size1", nutritionObserver);
    }

    @Test
    public void getNutritionNullFood() {
        controller.getNutrition(null, "size1", nutritionObserver);

        verify(nutritionObserver, times(1)).update(any(Exception.class));
    }

    @Test
    public void getNutritionEmptyMeasure() {
        Food mock = mock(Food.class);
        controller.getNutrition(mock, "", nutritionObserver);

        verify(nutritionObserver, times(1)).update(any(Exception.class));
    }

    @Test
    public void getNutritionNullMeasure() {
        Food mock = mock(Food.class);
        controller.getNutrition(mock, null, nutritionObserver);

        verify(nutritionObserver, times(1)).update(any(Exception.class));
    }

    @Test
    public void sendMessageNormal() {
        Food mock = mock(Food.class);

        HashMap<String, String> measures = new HashMap<>();
        measures.put("size1", "asasdasd");

        when(mock.getMeasures()).thenReturn(measures);

        Nutrition nutrition = mock(Nutrition.class);
        when(mock.generateReport("size1", nutrition)).thenReturn("Hello world");

        controller.sendMessage(mock, nutrition, "size1", messageObserver);

        verify(facade, times(1)).sendMessage("Hello world", messageObserver);
    }

    @Test
    public void sendMessageNullFood() {
        Nutrition nutrition = mock(Nutrition.class);
        controller.sendMessage(null, nutrition, "size1", messageObserver);

        verify(messageObserver, times(1)).update(any(Exception.class));
    }

    @Test
    public void sendMessageEmptySize() {
        Food mock = mock(Food.class);
        Nutrition nutrition = mock(Nutrition.class);
        when(mock.generateReport("", nutrition)).thenReturn("Hello world");
        controller.sendMessage(mock, nutrition, "", messageObserver);

        verify(messageObserver, times(1)).update(any(Exception.class));
    }

    @Test
    public void sendMessageNullSize() {
        Food mock = mock(Food.class);
        Nutrition nutrition = mock(Nutrition.class);
        when(mock.generateReport(null, nutrition)).thenReturn("Hello world");
        controller.sendMessage(mock, nutrition, null, messageObserver);

        verify(messageObserver, times(1)).update(any(Exception.class));
    }

    @Test
    public void sendMessageNullNutrition() {
        Food mock = mock(Food.class);

        HashMap<String, String> measures = new HashMap<>();
        measures.put("size1", "asasdasd");

        when(mock.getMeasures()).thenReturn(measures);

        when(mock.generateReport("size1", null)).thenReturn("Hello world");
        controller.sendMessage(mock, null, "size1", messageObserver);

        verify(messageObserver, times(1)).update(any(Exception.class));
    }
}