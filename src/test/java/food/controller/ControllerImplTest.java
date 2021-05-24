package food.controller;

import food.model.input.FoodDatabase;
import food.model.output.Twilio;
import food.model.models.Food;
import food.model.models.Nutrition;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ControllerImplTest {

    private Controller controller;

    private FoodDatabase foodDatabase;

    private Twilio twilio;

    @Before
    public void setUp() {

        this.foodDatabase = mock(FoodDatabase.class);
        this.twilio = mock(Twilio.class);

        this.controller = new ControllerImpl(foodDatabase, twilio);
    }

    @Test
    public void searchNormal() {
        List<Food> list = new ArrayList<>();

        list.add(mock(Food.class));

        when(foodDatabase.search("Pizza")).thenReturn(list);

        assertEquals(list, controller.search("Pizza"));

        verify(foodDatabase, times(1)).search("Pizza");
    }

    @Test
    public void searchEmpty() {
        assertThrows(IllegalArgumentException.class, () -> controller.search(""));
        verify(foodDatabase, never()).search(anyString());
    }

    @Test
    public void searchNull() {
        assertThrows(IllegalArgumentException.class, () -> controller.search(null));
        verify(foodDatabase, never()).search(anyString());
    }

    @Test
    public void searchEmptyResult() {
        List<Food> list = new ArrayList<>();

        when(foodDatabase.search("Orange")).thenReturn(list);

        assertEquals(list, controller.search("Orange"));

        verify(foodDatabase, times(1)).search(anyString());
    }

    @Test
    public void getNutritionNormal() {
        Nutrition nutrition = mock(Nutrition.class);

        when(foodDatabase.getNutrition("1234", "http://www.edamam.com/ontologies/edamam.owl#Measure_unit")).thenReturn(nutrition);

        assertEquals(nutrition, controller.getNutrition("1234", "http://www.edamam.com/ontologies/edamam.owl#Measure_unit"));

        verify(foodDatabase, times(1)).getNutrition("1234", "http://www.edamam.com/ontologies/edamam.owl#Measure_unit");
    }

    @Test
    public void getNutritionNullResult() {
        when(foodDatabase.getNutrition("2345", "http://www.edamam.com/ontologies/edamam.owl#Measure_unit")).thenReturn(null);

        assertNull(controller.getNutrition("2345", "http://www.edamam.com/ontologies/edamam.owl#Measure_unit"));

        verify(foodDatabase, times(1)).getNutrition("2345", "http://www.edamam.com/ontologies/edamam.owl#Measure_unit");
    }

    @Test
    public void getNutritionEmptyID() {
        assertThrows(IllegalArgumentException.class, () -> controller.getNutrition("", "http://www.edamam.com/ontologies/edamam.owl#Measure_unit"));

        verify(foodDatabase, never()).getNutrition(anyString(), anyString());
    }

    @Test
    public void getNutritionNullID() {
        assertThrows(IllegalArgumentException.class, () -> controller.getNutrition(null, "http://www.edamam.com/ontologies/edamam.owl#Measure_unit"));

        verify(foodDatabase, never()).getNutrition(anyString(), anyString());
    }

    @Test
    public void getNutritionEmptyMeasure() {
        assertThrows(IllegalArgumentException.class, () -> controller.getNutrition("1234", ""));

        verify(foodDatabase, never()).getNutrition(anyString(), anyString());
    }

    @Test
    public void getNutritionNullMeasure() {
        assertThrows(IllegalArgumentException.class, () -> controller.getNutrition("1234", null));

        verify(foodDatabase, never()).getNutrition(anyString(), anyString());
    }

    @Test
    public void sendMessageNormal() {
        Food food = mock(Food.class);
        when(food.generateReport("size1")).thenReturn("Hello world");
        when(twilio.sendMessage("Hello world")).thenReturn(true);

        assertTrue(controller.sendMessage(food, "size1"));
        verify(twilio, times(1)).sendMessage("Hello world");
    }

    @Test
    public void sendMessageNormalFail() {
        Food food = mock(Food.class);
        when(food.generateReport("size1")).thenReturn("Hello world");
        when(twilio.sendMessage("Hello world")).thenReturn(false);

        assertFalse(controller.sendMessage(food, "size1"));
        verify(twilio, times(1)).sendMessage("Hello world");
    }

    @Test
    public void sendMessageNullFood() {
        assertThrows(IllegalArgumentException.class, () -> controller.sendMessage(null, "size1"));
        verify(twilio, never()).sendMessage(anyString());
    }

    @Test
    public void sendMessageNullSize() {
        Food food = mock(Food.class);
        when(food.generateReport("size1")).thenReturn("Hello world");
        when(twilio.sendMessage("Hello world")).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> controller.sendMessage(food, null));
        verify(twilio, never()).sendMessage(anyString());
    }

    @Test
    public void sendMessageEmptySize() {
        Food food = mock(Food.class);
        when(food.generateReport("")).thenThrow(new IllegalStateException());
        when(twilio.sendMessage("Hello world")).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> controller.sendMessage(food, ""));
        verify(twilio, never()).sendMessage(anyString());
    }
}