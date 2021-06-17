package food.controller;

import food.model.ModelFacade;
import food.model.models.Food;
import food.model.models.Nutrition;
import food.view.FoodWindow;
import food.view.observers.BaseObserver;
import food.view.observers.FoodListObserver;
import food.view.observers.MessageObserver;
import food.view.observers.NutritionObserver;
import food.view.screen.Screen;
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
    private BaseObserver baseObserver;

    private FoodWindow view;
    private Screen screen;

    @Before
    public void setUp() {
        this.facade = mock(ModelFacade.class);
        this.foodListObserver = mock(FoodListObserver.class);
        this.nutritionObserver = mock(NutritionObserver.class);
        this.messageObserver = mock(MessageObserver.class);
        this.baseObserver = mock(BaseObserver.class);

        this.controller = new ControllerImpl(facade);

        this.view = mock(FoodWindow.class);
        this.controller.setView(view);

        this.screen = mock(Screen.class);
    }

    @Test
    public void searchNormalTrueTrue() {
        controller.search("Apple", true, true, foodListObserver);

        verify(facade, times(1)).queueSearch("Apple", true, true);
    }

    @Test
    public void searchNormalTrueFalse() {
        controller.search("Apple", true, false, foodListObserver);

        verify(facade, times(1)).queueSearch("Apple", true, false);
    }

    @Test
    public void searchNormalFalseTrue() {
        controller.search("Apple", false, true, foodListObserver);

        verify(facade, times(1)).queueSearch("Apple", false, true);
    }

    @Test
    public void searchNormalFalseFalse() {
        controller.search("Apple", false, false, foodListObserver);

        verify(facade, times(1)).queueSearch("Apple", false, false);
    }

    @Test
    public void searchEmpty() {
        controller.search("", true, true, foodListObserver);

        verify(facade, times(0)).queueSearch("", true, true);
        verify(foodListObserver, times(1)).update(any(Exception.class));
    }

    @Test
    public void searchNull() {
        controller.search(null, true, true, foodListObserver);

        verify(facade, times(0)).queueSearch(null, true, true);
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

        verify(facade, times(0)).queueSearch(null, true, true);
        verify(foodListObserver, times(9)).update(any(Exception.class));
    }

    @Test
    public void getNutritionNormalTrue() {
        Food mock = mock(Food.class);

        when(mock.getID()).thenReturn("1234");

        controller.getNutrition(mock, "size1", true, nutritionObserver);

        verify(facade, times(1)).queueGetNutrition("1234", "size1", true);
    }

    @Test
    public void getNutritionNormalFalse() {
        Food mock = mock(Food.class);

        when(mock.getID()).thenReturn("1234");

        controller.getNutrition(mock, "size1", false, nutritionObserver);

        verify(facade, times(1)).queueGetNutrition("1234", "size1", false);
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

        verify(facade, times(1)).queueSendMessage("Hello world");
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

    @Test
    public void registerFoodListObserver() {
        controller.registerFoodListObserver(foodListObserver);
        verify(facade, times(1)).attach(foodListObserver);
    }

    @Test
    public void registerNutritionObserver() {
        controller.registerNutritionObserver(nutritionObserver);
        verify(facade, times(1)).attach(nutritionObserver);
    }

    @Test
    public void registerMessageObserver() {
        controller.registerMessageObserver(messageObserver);
        verify(facade, times(1)).attach(messageObserver);
    }

    @Test
    public void removeNutritionObserver() {
        controller.registerNutritionObserver(nutritionObserver);
        controller.removeNutritionObserver(nutritionObserver);
        verify(facade, times(1)).attach(nutritionObserver);
    }

    @Test
    public void removeMessageObserver() {
        controller.registerMessageObserver(messageObserver);
        controller.removeMessageObserver(messageObserver);
        verify(facade, times(1)).attach(messageObserver);
    }

    @Test
    public void setMaxCaloriesSuccess() {
        controller.setMaxCalories("123", baseObserver, screen);
        verify(baseObserver, never()).update(any(Exception.class));
        verify(facade, times(1)).setMaxCalories(123);
        verify(view, times(1)).setScreen(screen);
    }

    @Test
    public void setMaxCaloriesLowerBound() {
        controller.setMaxCalories("1", baseObserver, screen);
        verify(baseObserver, never()).update(any(Exception.class));
        verify(facade, times(1)).setMaxCalories(1);
        verify(view, times(1)).setScreen(screen);
    }

    @Test
    public void setMaxCaloriesUpperBound() {
        controller.setMaxCalories("1000", baseObserver, screen);
        verify(baseObserver, never()).update(any(Exception.class));
        verify(facade, times(1)).setMaxCalories(1000);
        verify(view, times(1)).setScreen(screen);
    }

    @Test
    public void setMaxCaloriesEmpty() {
        controller.setMaxCalories("", baseObserver, screen);
        verify(baseObserver, times(1)).update(any(Exception.class));
        verify(facade, never()).setMaxCalories(anyInt());
        verify(view, never()).setScreen(screen);
    }

    @Test
    public void setMaxCaloriesNull() {
        controller.setMaxCalories(null, baseObserver, screen);
        verify(baseObserver, times(1)).update(any(Exception.class));
        verify(facade, never()).setMaxCalories(anyInt());
        verify(view, never()).setScreen(screen);
    }

    @Test
    public void setMaxCaloriesZero() {
        controller.setMaxCalories("0", baseObserver, screen);
        verify(baseObserver, times(1)).update(any(Exception.class));
        verify(facade, never()).setMaxCalories(anyInt());
        verify(view, never()).setScreen(screen);
    }

    @Test
    public void setMaxCaloriesTooHigh() {
        controller.setMaxCalories("1001", baseObserver, screen);
        verify(baseObserver, times(1)).update(any(Exception.class));
        verify(facade, never()).setMaxCalories(anyInt());
        verify(view, never()).setScreen(screen);
    }

    @Test
    public void setMaxCaloriesNegative() {
        controller.setMaxCalories("-10", baseObserver, screen);
        verify(baseObserver, times(1)).update(any(Exception.class));
        verify(facade, never()).setMaxCalories(anyInt());
        verify(view, never()).setScreen(screen);
    }

    @Test
    public void setMaxCaloriesNonNumerical1() {
        controller.setMaxCalories("1a5", baseObserver, screen);
        verify(baseObserver, times(1)).update(any(Exception.class));
        verify(facade, never()).setMaxCalories(anyInt());
        verify(view, never()).setScreen(screen);
    }

    @Test
    public void setMaxCaloriesNonNumerical2() {
        controller.setMaxCalories("abc", baseObserver, screen);
        verify(baseObserver, times(1)).update(any(Exception.class));
        verify(facade, never()).setMaxCalories(anyInt());
        verify(view, never()).setScreen(screen);
    }

    @Test
    public void setMaxCaloriesNonNumerical3() {
        controller.setMaxCalories("1 2", baseObserver, screen);
        verify(baseObserver, times(1)).update(any(Exception.class));
        verify(facade, never()).setMaxCalories(anyInt());
        verify(view, never()).setScreen(screen);
    }

    @Test
    public void setMaxCaloriesNonNumerical4() {
        controller.setMaxCalories("1+2", baseObserver, screen);
        verify(baseObserver, times(1)).update(any(Exception.class));
        verify(facade, never()).setMaxCalories(anyInt());
        verify(view, never()).setScreen(screen);
    }

    @Test
    public void setMaxCaloriesNonNumerical5() {
        controller.setMaxCalories("1:2", baseObserver, screen);
        verify(baseObserver, times(1)).update(any(Exception.class));
        verify(facade, never()).setMaxCalories(anyInt());
        verify(view, never()).setScreen(screen);
    }

    @Test
    public void setMaxCaloriesDecimal() {
        controller.setMaxCalories("12.5", baseObserver, screen);
        verify(baseObserver, times(1)).update(any(Exception.class));
        verify(facade, never()).setMaxCalories(anyInt());
        verify(view, never()).setScreen(screen);
    }

    @Test
    public void setMaxCaloriesTooLarge() {
        controller.setMaxCalories("2147483648", baseObserver, screen);
        verify(baseObserver, times(1)).update(any(Exception.class));
        verify(facade, never()).setMaxCalories(anyInt());
        verify(view, never()).setScreen(screen);
    }

    @Test
    public void setMaxCaloriesTooSmall() {
        controller.setMaxCalories("-2147483649", baseObserver, screen);
        verify(baseObserver, times(1)).update(any(Exception.class));
        verify(facade, never()).setMaxCalories(anyInt());
        verify(view, never()).setScreen(screen);
    }
}