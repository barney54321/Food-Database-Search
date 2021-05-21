package food.model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.io.Reader;

import static org.junit.Assert.*;

public class IngredientImplTest {

    private IngredientImpl ingredient;

    @Before
    public void setUp() throws Exception {
        Reader reader = new FileReader("src/test/resources/tomato.json");

        JSONParser parser = new JSONParser();

        JSONObject json = (JSONObject) parser.parse(reader);

        this.ingredient = new IngredientImpl(json);
    }

    @Test
    public void getURI() {
        assertEquals("http://www.edamam.com/ontologies/edamam.owl#Measure_unit", ingredient.getURI());
    }

    @Test
    public void getQuantity() {
        assertEquals(1, ingredient.getQuantity(), 0.01);
    }

    @Test
    public void getMeasure() {
        assertEquals("whole", ingredient.getMeasure());
    }

    @Test
    public void getWeight() {
        assertEquals(123, ingredient.getWeight(), 0.01);
    }

    @Test
    public void getFood() {
        assertEquals("Tomatoes, red, ripe, raw, year round average", ingredient.getFood());
    }

    @Test
    public void getFoodID() {
        assertEquals("Food_11529", ingredient.getFoodID());
    }
}