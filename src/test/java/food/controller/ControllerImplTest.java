package food.controller;

import food.model.ModelFacade;
import food.model.input.FoodDatabase;
import food.model.output.Twilio;
import food.model.models.Food;
import food.model.models.Nutrition;
import food.view.observers.FoodListObserver;
import food.view.observers.MessageObserver;
import food.view.observers.NutritionObserver;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
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
    public void searchNormal() {
        controller.search("Apple", foodListObserver);

        verify(facade, times(1)).search("Apple", foodListObserver);
    }

    @Test
    public void searchEmpty() {
        controller.search("", foodListObserver);

        verify(facade, times(0)).search("", foodListObserver);
        verify(foodListObserver, times(1)).update(any(Exception.class));
    }

    @Test
    public void searchNull() {
        controller.search(null, foodListObserver);

        verify(facade, times(0)).search(null, foodListObserver);
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
        when(mock.generateReport("size1")).thenReturn("Hello world");

        controller.sendMessage(mock, "size1", messageObserver);

        verify(facade, times(1)).sendMessage("Hello world", messageObserver);
    }

    @Test
    public void sendMessageNullFood() {
        controller.sendMessage(null, "size1", messageObserver);

        verify(messageObserver, times(1)).update(any(Exception.class));
    }

    @Test
    public void sendMessageEmptySize() {
        Food mock = mock(Food.class);
        when(mock.generateReport("")).thenReturn("Hello world");
        controller.sendMessage(mock, "", messageObserver);

        verify(messageObserver, times(1)).update(any(Exception.class));
    }

    @Test
    public void sendMessageNullSize() {
        Food mock = mock(Food.class);
        when(mock.generateReport(null)).thenReturn("Hello world");
        controller.sendMessage(mock, null, messageObserver);

        verify(messageObserver, times(1)).update(any(Exception.class));
    }
}