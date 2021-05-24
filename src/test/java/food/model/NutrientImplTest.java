package food.model;

import food.model.models.Nutrient;
import food.model.models.NutrientImpl;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.io.Reader;

import static org.junit.Assert.*;

public class NutrientImplTest {

    private Nutrient nutrient;

    @Before
    public void setUp() throws Exception {
        Reader reader = new FileReader("src/test/resources/fat.json");

        JSONParser parser = new JSONParser();

        JSONObject json = (JSONObject) parser.parse(reader);

        this.nutrient = new NutrientImpl(json);
    }

    @Test
    public void getLabel() {
        assertEquals("Fat", nutrient.getLabel());
    }

    @Test
    public void getQuantity() {
        assertEquals(0.246, nutrient.getQuantity(), 0.01);
    }

    @Test
    public void getUnit() {
        assertEquals("g", nutrient.getUnit());
    }
}