package food.model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.io.Reader;
import java.util.Map;

import static org.junit.Assert.*;

public class FoodImplTest {

    private FoodImpl food;

    @Before
    public void setUp() throws Exception {
        Reader reader = new FileReader("src/test/resources/ferrero.json");

        JSONParser parser = new JSONParser();

        JSONObject json = (JSONObject) parser.parse(reader);

        this.food = new FoodImpl(null);
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

        Map<String, Double> measures = food.getMeasures();

        assertEquals(40.0, measures.get("Serving"), 0.01);
        assertEquals(100.0, measures.get("Package"), 0.01);
        assertEquals(1.0, measures.get("Gram"), 0.01);
        assertEquals(28.349523125, measures.get("Ounce"), 0.01);
        assertEquals(453.59237, measures.get("Pound"), 0.01);
        assertEquals(1000.0, measures.get("Kilogram"), 0.01);
    }
}