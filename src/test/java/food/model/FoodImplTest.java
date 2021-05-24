package food.model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.Map;
import java.util.function.BiFunction;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class FoodImplTest {

    private FoodImpl food;

    private Nutrition nutrition;
    private BiFunction<String, String, Nutrition> function;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() throws Exception {
        Reader reader = new FileReader("src/test/resources/ferrero.json");

        JSONParser parser = new JSONParser();

        JSONObject json = (JSONObject) parser.parse(reader);

        this.function = (BiFunction<String, String, Nutrition>) mock(BiFunction.class);
        this.nutrition = mock(Nutrition.class);

        when(function.apply(anyString(), anyString())).thenReturn(this.nutrition);

        this.food = new FoodImpl(json, function);
    }

    @Test
    public void getID() {
        assertEquals("food_bn4bryqayjl958auu03k3bxf6ja8", food.getID());
    }

    @Test
    public void getLabel() {
        assertEquals("Ferrero Rocher Ferrero Eggs, Cocoa", food.getLabel());
    }

    @Test
    public void getNutrients() {

        Map<String, Double> nutrients = food.getNutrients();

        assertEquals(573.0, nutrients.get("ENERC_KCAL"), 0.01);
        assertEquals(5.0, nutrients.get("PROCNT"), 0.01);
        assertEquals(37.5, nutrients.get("FAT"), 0.01);
        assertEquals(55.0, nutrients.get("CHOCDF"), 0.01);
        assertEquals(5.0, nutrients.get("FIBTG"), 0.01);
    }

    @Test
    public void getBrand() {
        assertEquals("Ferrero Rocher", food.getBrand());
    }

    @Test
    public void getCategory() {
        assertEquals("Packaged foods", food.getCategory());
    }

    @Test
    public void getCategoryLabel() {
        assertEquals("food", food.getCategoryLabel());
    }

    @Test
    public void getFoodContentsLabel() {
        assertEquals("SEMISWEET CHOCOLATE (SUGAR; COCOA MASS; COCOA BUTTER; LECITHIN AS EMULSIFIER (SOY); VANILLIN: AN ARTIFICIAL FLAVOR); MILK CHOCOLATE (SUGAR; MILK POWDER; COCOA BUTTER; COCOA MASS; LECITHIN AS EMULSIFIER (SOY); VANILLIN: AN ARTIFICIAL FLAVOR); SUGAR; PALM OIL; COCOA; WHEAT FLOUR; HAZELNUTS; LECITHIN AS EMULSIFIER (SOY); SKIM MILK POWDER; SODIUM BICARBONATE AND AMMONIUM CARBONATE AS LEAVENING AGENTS; VANILLIN: AN ARTIFICIAL FLAVOR; SALT.", food.getFoodContentsLabel());
    }

    @Test
    public void getImagePath() {
        assertEquals("https://www.edamam.com/food-img/4e9/4e9845d490d9df5161b963d310d0ce3e.png", food.getImagePath());
    }

    @Test
    public void getServingsPerContainer() {
        assertEquals(2.5, food.getServingsPerContainer(), 0.01);
    }

    @Test
    public void getMeasures() {

        Map<String, String> measures = food.getMeasures();

        assertEquals("http://www.edamam.com/ontologies/edamam.owl#Measure_serving", measures.get("Serving"));
        assertEquals("http://www.edamam.com/ontologies/edamam.owl#Measure_package", measures.get("Package"));
        assertEquals("http://www.edamam.com/ontologies/edamam.owl#Measure_gram", measures.get("Gram"));
        assertEquals("http://www.edamam.com/ontologies/edamam.owl#Measure_ounce", measures.get("Ounce"));
        assertEquals("http://www.edamam.com/ontologies/edamam.owl#Measure_pound", measures.get("Pound"));
        assertEquals("http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram", measures.get("Kilogram"));
    }

    @Test
    public void getNutritionOnce() {
        assertEquals(nutrition, food.getNutrition("size1"));
        verify(function, times(1)).apply("food_bn4bryqayjl958auu03k3bxf6ja8", "size1");
    }

    @Test
    public void getNutritionMultipleTimes() {
        assertEquals(nutrition, food.getNutrition("size1"));
        assertEquals(nutrition, food.getNutrition("size1"));
        assertEquals(nutrition, food.getNutrition("size1"));
        verify(function, times(1)).apply("food_bn4bryqayjl958auu03k3bxf6ja8", "size1");
    }

    @Test
    public void generateReportSimple() {
        String expected = "";

        expected += "Food ID: food_bn4bryqayjl958auu03k3bxf6ja8\n";
        expected += "Label: Ferrero Rocher Ferrero Eggs, Cocoa\n";
        expected += "Brand: Ferrero Rocher\n";
        expected += "Servings per container: 0.01\n";
        expected += "Size: size1\n";
        expected += "Calories: 100\n";
        expected += "Diet labes: [Gluten Free]\n";
        expected += "Health labels: [Halal, Vegetarian]\n\n";
        expected += "Nutrients: \n";
        expected += "ENERC_KCAL: 573.0\n";
        expected += "PROCNT: 5.0\n";
        expected += "FAT: 37.5\n";
        expected += "CHOCDF: 55.0\n";
        expected += "FIBTG: 5.0\n";

        when(nutrition.getCalories()).thenReturn(100);
        when(nutrition.getDietLabels()).thenReturn(Arrays.asList("GLUTEN_FREE"));
        when(nutrition.getHealthLabels()).thenReturn(Arrays.asList("HALAL, VEGETARIAN"));

        food.getNutrition("size1");
        assertEquals(expected, food.generateReport());
    }

    @Test
    public void generateReportEarly() {
        assertThrows(IllegalStateException.class, () -> food.generateReport());
    }
}