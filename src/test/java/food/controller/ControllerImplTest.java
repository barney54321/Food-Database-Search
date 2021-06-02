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
    public void searchNormalTrueTrue() {
        controller.search("Apple", true, true, foodListObserver);

        verify(facade, times(1)).queueSearch("Apple", true, true, foodListObserver);
    }

    @Test
    public void searchNormalTrueFalse() {
        controller.search("Apple", true, false, foodListObserver);

        verify(facade, times(1)).queueSearch("Apple", true, false, foodListObserver);
    }

    @Test
    public void searchNormalFalseTrue() {
        controller.search("Apple", false, true, foodListObserver);

        verify(facade, times(1)).queueSearch("Apple", false, true, foodListObserver);
    }

    @Test
    public void searchNormalFalseFalse() {
        controller.search("Apple", false, false, foodListObserver);

        verify(facade, times(1)).queueSearch("Apple", false, false, foodListObserver);
    }

    @Test
    public void searchEmpty() {
        controller.search("", true, true, foodListObserver);

        verify(facade, times(0)).queueSearch("", true, true, foodListObserver);
        verify(foodListObserver, times(1)).update(any(Exception.class));
    }

    @Test
    public void searchNull() {
        controller.search(null, true, true, foodListObserver);

        verify(facade, times(0)).queueSearch(null, true, true, foodListObserver);
        verify(foodListObserver, times(1)).update(any(Exception.class));
    }

    @Test
    public void searchInjection() {
        controller.search("hello\0 world", true, true, foodListObserver);
        controller.search("hello' world", true, true, foodListObserver);
        controller.search("hello\" world", true, true, foodListObserver);
        controller.search("hello\r world", true, true, foodListObserver);
        controller.search("hello\b world", true, true, foodListObserver);
        controller.search("hello\\ world", true, true, foodListObserver);
        controller.search("hello% world", true, true, foodListObserver);
        controller.search("hello_ world", true, true, foodListObserver);
        controller.search("hello; world", true, true, foodListObserver);

        verify(facade, times(0)).queueSearch(null, true, true, foodListObserver);
        verify(foodListObserver, times(9)).update(any(Exception.class));
    }

    @Test
    public void getNutritionNormalTrue() {
        Food mock = mock(Food.class);

        when(mock.getID()).thenReturn("1234");

        controller.getNutrition(mock, "size1", true, nutritionObserver);

        verify(facade, times(1)).queueGetNutrition("1234", "size1", true, nutritionObserver);
    }

    @Test
    public void getNutritionNormalFalse() {
        Food mock = mock(Food.class);

        when(mock.getID()).thenReturn("1234");

        controller.getNutrition(mock, "size1", false, nutritionObserver);

        verify(facade, times(1)).queueGetNutrition("1234", "size1", false, nutritionObserver);
    }

    @Test
    public void getNutritionNullFood() {
        controller.getNutrition(null, "size1", true, nutritionObserver);

        verify(nutritionObserver, times(1)).update(any(Exception.class));
    }

    @Test
    public void getNutritionEmptyMeasure() {
        Food mock = mock(Food.class);
        controller.getNutrition(mock, "", true, nutritionObserver);

        verify(nutritionObserver, times(1)).update(any(Exception.class));
    }

    @Test
    public void getNutritionNullMeasure() {
        Food mock = mock(Food.class);
        controller.getNutrition(mock, null, true, nutritionObserver);

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

        verify(facade, times(1)).queueSendMessage("Hello world", messageObserver);
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