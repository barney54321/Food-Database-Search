package food.controller;

import food.backend.input.FoodDatabase;
import food.model.Food;
import food.model.Nutrition;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FacadeImplTest {

    private Facade facade;

    private FoodDatabase foodDatabase;

    @Before
    public void setUp() {

        this.foodDatabase = mock(FoodDatabase.class);

        this.facade = new FacadeImpl(foodDatabase);
    }

    @Test
    public void searchNormal() {
        List<Food> list = new ArrayList<>();

        list.add(mock(Food.class));

        when(foodDatabase.search("Pizza")).thenReturn(list);

        assertEquals(list, facade.search("Pizza"));

        verify(foodDatabase, times(1)).search("Pizza");
    }

    @Test
    public void searchEmpty() {
        assertThrows(IllegalArgumentException.class, () -> facade.search(""));
        verify(foodDatabase, never()).search(anyString());
    }

    @Test
    public void searchNull() {
        assertThrows(IllegalArgumentException.class, () -> facade.search(null));
        verify(foodDatabase, never()).search(anyString());
    }

    @Test
    public void searchEmptyResult() {
        List<Food> list = new ArrayList<>();

        when(foodDatabase.search("Orange")).thenReturn(list);

        assertEquals(list, facade.search("Orange"));

        verify(foodDatabase, times(1)).search(anyString());
    }

    @Test
    public void getNutritionNormal() {
        Nutrition nutrition = mock(Nutrition.class);

        when(foodDatabase.getNutrition("1234", "http://www.edamam.com/ontologies/edamam.owl#Measure_unit")).thenReturn(nutrition);

        assertEquals(nutrition, facade.getNutrition("1234", "http://www.edamam.com/ontologies/edamam.owl#Measure_unit"));

        verify(foodDatabase, times(1)).getNutrition("1234", "http://www.edamam.com/ontologies/edamam.owl#Measure_unit");
    }

    @Test
    public void getNutritionNullResult() {
        when(foodDatabase.getNutrition("2345", "http://www.edamam.com/ontologies/edamam.owl#Measure_unit")).thenReturn(null);

        assertNull(facade.getNutrition("2345", "http://www.edamam.com/ontologies/edamam.owl#Measure_unit"));

        verify(foodDatabase, times(1)).getNutrition("2345", "http://www.edamam.com/ontologies/edamam.owl#Measure_unit");
    }

    @Test
    public void getNutritionEmptyID() {
        assertThrows(IllegalArgumentException.class, () -> facade.getNutrition("", "http://www.edamam.com/ontologies/edamam.owl#Measure_unit"));

        verify(foodDatabase, never()).getNutrition(anyString(), anyString());
    }

    @Test
    public void getNutritionNullID() {
        assertThrows(IllegalArgumentException.class, () -> facade.getNutrition(null, "http://www.edamam.com/ontologies/edamam.owl#Measure_unit"));

        verify(foodDatabase, never()).getNutrition(anyString(), anyString());
    }

    @Test
    public void getNutritionEmptyMeasure() {
        assertThrows(IllegalArgumentException.class, () -> facade.getNutrition("1234", ""));

        verify(foodDatabase, never()).getNutrition(anyString(), anyString());
    }

    @Test
    public void getNutritionNullMeasure() {
        assertThrows(IllegalArgumentException.class, () -> facade.getNutrition("1234", null));

        verify(foodDatabase, never()).getNutrition(anyString(), anyString());
    }
}