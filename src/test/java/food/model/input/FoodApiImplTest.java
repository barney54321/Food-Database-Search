package food.model.input;

import food.model.input.cache.Database;
import food.model.models.Food;
import food.model.models.Nutrition;
import food.model.models.NutritionImpl;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;
import org.mockito.AdditionalMatchers;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FoodApiImplTest {

    private Database cache;

    private FoodStrategy strategy;

    private FoodApi api;

    private String hawaiianSuccess;
    private String nutritionSuccess;
    private Nutrition sampleNutrition;

    @Before
    public void setUp() throws Exception {
        cache = mock(Database.class);
        strategy = mock(FoodStrategy.class);

        api = new FoodApiImpl(cache, strategy);

        hawaiianSuccess = "";

        File f = new File("src/test/resources/hawaiian.json");
        Scanner sc = new Scanner(f);

        while (sc.hasNextLine()) {
            hawaiianSuccess += sc.nextLine();
        }

        nutritionSuccess = "";

        f = new File("src/test/resources/hawaiian_nutrition.json");
        sc = new Scanner(f);

        while (sc.hasNextLine()) {
            nutritionSuccess += sc.nextLine();
        }

        JSONObject json = (JSONObject) new JSONParser().parse(nutritionSuccess);
        sampleNutrition = new NutritionImpl(json);
    }

    private void pizzaCheck(Food pizza) {
        assertEquals("food_at830s9amds32fb8w6ufmaopzk8n", pizza.getID());
        assertEquals("Pizza", pizza.getLabel());
        assertEquals(268.0, pizza.getNutrients().get("ENERC_KCAL"), 0.01);
        assertEquals(10.36, pizza.getNutrients().get("PROCNT"), 0.01);
        assertEquals(12.28, pizza.getNutrients().get("FAT"), 0.01);
        assertEquals(29.02, pizza.getNutrients().get("CHOCDF"), 0.01);
        assertEquals(2.2, pizza.getNutrients().get("FIBTG"), 0.01);
        assertEquals("Generic foods", pizza.getCategory());
        assertEquals("food", pizza.getCategoryLabel());
        assertEquals("https://www.edamam.com/food-img/c01/c0150280d71059c23c025f501f502dc0.jpg", pizza.getImagePath());
        assertEquals(8.975308641975309, pizza.getServingsPerContainer(), 0.001);
        assertEquals(8, pizza.getMeasures().size());
    }

    @Test
    public void searchCacheSuccessTrue() throws SQLException {
        String query = "select response from Search where term like ?";
        String[] params = new String[] {"hawaiian pizza"};

        ResultSet set = mock(ResultSet.class);
        when(set.getString("response")).thenReturn(hawaiianSuccess);

        when(cache.executeQuery(query, params)).thenReturn(set);
        when(strategy.searchFood("hawaiian pizza")).thenReturn(null);

        List<Food> res = api.search("hawaiian pizza", true);

        assertEquals(20, res.size());

        Food pizza = res.get(0);

        pizzaCheck(pizza);

        verify(strategy, never()).searchFood("hawaiian pizza");
    }

    @Test
    public void searchCacheSuccessFalse() throws SQLException {
        String query = "select response from Search where term like ?";
        String[] params = new String[] {"hawaiian pizza"};

        ResultSet set = mock(ResultSet.class);
        when(set.getString("response")).thenReturn(hawaiianSuccess);

        when(cache.executeQuery(query, params)).thenReturn(set);
        when(strategy.searchFood("hawaiian pizza")).thenReturn(hawaiianSuccess);

        List<Food> res = api.search("hawaiian pizza", false);

        assertEquals(20, res.size());

        Food pizza = res.get(0);

        pizzaCheck(pizza);

        verify(strategy, times(1)).searchFood("hawaiian pizza");
    }

    @Test
    public void searchAPISuccessTrue() throws SQLException {
        String query = "select response from Search where term like ?";
        String[] params = new String[] {"hawaiian pizza"};

        ResultSet set = mock(ResultSet.class);
        when(set.getString("response")).thenReturn(null);

        when(cache.executeQuery(eq(query), AdditionalMatchers.aryEq(params))).thenReturn(set);
        when(strategy.searchFood("hawaiian pizza")).thenReturn(hawaiianSuccess);

        List<Food> res = api.search("hawaiian pizza", true);

        assertEquals(20, res.size());

        Food pizza = res.get(0);

        pizzaCheck(pizza);

        String update = "replace into Search (term, response) values(?, ?)";
        String[] terms = {"hawaiian pizza", hawaiianSuccess.replace("'", "")};

        verify(cache, times(1)).executeUpdate(eq(update), AdditionalMatchers.aryEq(terms));
        verify(cache, times(1)).executeQuery(eq(query), AdditionalMatchers.aryEq(params));
    }

    @Test
    public void searchAPISuccessFalse() throws SQLException {
        String query = "select response from Search where term like ?";
        String[] params = new String[] {"hawaiian pizza"};

        ResultSet set = mock(ResultSet.class);
        when(set.getString("response")).thenReturn(null);

        when(cache.executeQuery(eq(query), AdditionalMatchers.aryEq(params))).thenReturn(set);
        when(strategy.searchFood("hawaiian pizza")).thenReturn(hawaiianSuccess);

        List<Food> res = api.search("hawaiian pizza", false);

        assertEquals(20, res.size());

        Food pizza = res.get(0);

        pizzaCheck(pizza);

        String update = "replace into Search (term, response) values(?, ?)";
        String[] terms = {"hawaiian pizza", hawaiianSuccess.replace("'", "")};

        verify(cache, times(1)).executeUpdate(update, terms);
        verify(cache, never()).executeQuery(eq(query), AdditionalMatchers.aryEq(params));
    }

    @Test
    public void getNutritionDatabaseSuccessTrue() throws SQLException {
        String query = "select response from Nutrition where food like ? and measure like ?";
        String[] params = {"food_id_1", "measure"};

        ResultSet set = mock(ResultSet.class);
        when(set.getString("response")).thenReturn(nutritionSuccess);

        when(cache.executeQuery(eq(query), AdditionalMatchers.aryEq(params))).thenReturn(set);
        when(strategy.searchNutrition(anyString(), anyString())).thenReturn(null);

        Nutrition nut = api.getNutrition("food_id_1", "measure", true);

        assertEquals(sampleNutrition.getCalories(), nut.getCalories());
        assertEquals(sampleNutrition.getTotalWeight(), nut.getTotalWeight(), 0.01);
        assertEquals(sampleNutrition.getDietLabels(), nut.getDietLabels());
        assertEquals(sampleNutrition.getHealthLabels(), nut.getHealthLabels());
        assertEquals(sampleNutrition.getCautions(), nut.getCautions());

        verify(cache, times(1)).executeQuery(eq(query), AdditionalMatchers.aryEq(params));
        verify(strategy, never()).searchNutrition(anyString(), anyString());
    }

    @Test
    public void getNutritionAPISuccessTrue() throws SQLException {
        String query = "select response from Nutrition where food like ? and measure like ?";
        String[] params = {"food_id_1", "measure"};

        ResultSet set = mock(ResultSet.class);
        when(set.getString("response")).thenReturn(null);

        when(cache.executeQuery(eq(query), AdditionalMatchers.aryEq(params))).thenReturn(set);
        when(strategy.searchNutrition(anyString(), anyString())).thenReturn(nutritionSuccess);

        Nutrition nut = api.getNutrition("food_id_1", "measure", true);

        assertEquals(sampleNutrition.getCalories(), nut.getCalories());
        assertEquals(sampleNutrition.getTotalWeight(), nut.getTotalWeight(), 0.01);
        assertEquals(sampleNutrition.getDietLabels(), nut.getDietLabels());
        assertEquals(sampleNutrition.getHealthLabels(), nut.getHealthLabels());
        assertEquals(sampleNutrition.getCautions(), nut.getCautions());

        String update = "replace into Nutrition values(?, ?, ?)";
        String[] terms = {"food_id_1", "measure", nutritionSuccess};
        verify(cache, times(1)).executeUpdate(update, terms);

        verify(cache, times(1)).executeQuery(eq(query), AdditionalMatchers.aryEq(params));
        verify(strategy, times(1)).searchNutrition(anyString(), anyString());
    }

    @Test
    public void getNutritionAPISuccessFalse() throws SQLException {
        String query = "select response from Nutrition where food like ? and measure like ?";
        String[] params = {"food_id_1", "measure"};

        ResultSet set = mock(ResultSet.class);
        when(set.getString("response")).thenReturn(null);

        when(cache.executeQuery(eq(query), AdditionalMatchers.aryEq(params))).thenReturn(set);
        when(strategy.searchNutrition(anyString(), anyString())).thenReturn(nutritionSuccess);

        Nutrition nut = api.getNutrition("food_id_1", "measure", false);

        assertEquals(sampleNutrition.getCalories(), nut.getCalories());
        assertEquals(sampleNutrition.getTotalWeight(), nut.getTotalWeight(), 0.01);
        assertEquals(sampleNutrition.getDietLabels(), nut.getDietLabels());
        assertEquals(sampleNutrition.getHealthLabels(), nut.getHealthLabels());
        assertEquals(sampleNutrition.getCautions(), nut.getCautions());

        String update = "replace into Nutrition values(?, ?, ?)";
        String[] terms = {"food_id_1", "measure", nutritionSuccess};
        verify(cache, times(1)).executeUpdate(eq(update), AdditionalMatchers.aryEq(terms));

        verify(cache, never()).executeQuery(eq(query), AdditionalMatchers.aryEq(params));
        verify(strategy, times(1)).searchNutrition(anyString(), anyString());
    }
}