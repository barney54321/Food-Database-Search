package food.model;

import food.model.models.Nutrient;
import food.model.models.Nutrition;
import food.model.models.NutritionImpl;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class NutritionImplTest {

    private Nutrition nutrition;

    @Before
    public void setUp() throws Exception {
        Reader reader = new FileReader("src/test/resources/ferrero_nutrition.json");

        JSONParser parser = new JSONParser();

        JSONObject json = (JSONObject) parser.parse(reader);

        this.nutrition = new NutritionImpl(json);
    }

    @Test
    public void getURI() {
        assertEquals("http://www.edamam.com/ontologies/edamam.owl#c7b90d7e-cfd8-4eab-9717-a8a2fff9acd1", nutrition.getURI());
    }

    @Test
    public void getCalories() {
        assertEquals((Integer) 10, nutrition.getCalories());
    }

    @Test
    public void getTotalWeight() {
        assertEquals(5.6, nutrition.getTotalWeight(), 0.01);
    }

    @Test
    public void getDietLabels() {
        List<String> labels = nutrition.getDietLabels();

        assertEquals(2, labels.size());
        assertTrue(labels.contains("LOW_FAT"));
        assertTrue(labels.contains("LOW_SODIUM"));
    }

    @Test
    public void getHealthLabels() {
        List<String> labels = nutrition.getHealthLabels();

        assertEquals(2, labels.size());
        assertTrue(labels.contains("LOW_SUGAR"));
        assertTrue(labels.contains("VEGETARIAN"));
    }

    @Test
    public void getCautions() {
        List<String> labels = nutrition.getCautions();

        assertEquals(1, labels.size());
        assertTrue(labels.contains("SULFITES"));
    }

    @Test
    public void getTotalNutrients() {
        Map<String, Nutrient> nutrients = nutrition.getTotalNutrients();

        assertEquals(2, nutrients.size());

        assertNotNull(nutrients.get("ENERC_KCAL"));
        assertNotNull(nutrients.get("FAT"));

        Nutrient fat = nutrients.get("FAT");

        assertEquals("Fat", fat.getLabel());
        assertEquals(0.246, fat.getQuantity(), 0.01);
        assertEquals("g", fat.getUnit());
    }

    @Test
    public void getTotalDaily() {
        Map<String, Nutrient> nutrients = nutrition.getTotalDaily();

        assertEquals(2, nutrients.size());

        assertNotNull(nutrients.get("ENERC_KCAL"));
        assertNotNull(nutrients.get("FAT"));

        Nutrient fat = nutrients.get("FAT");

        assertEquals("Fat", fat.getLabel());
        assertEquals(0.37846153846153846, fat.getQuantity(), 0.00001);
        assertEquals("%", fat.getUnit());
    }
}