package food.model;

import food.model.input.FoodDatabase;
import food.model.models.Food;
import food.model.models.Nutrition;
import food.model.output.Twilio;
import food.view.observers.FoodListObserver;
import food.view.observers.MessageObserver;
import food.view.observers.NutritionObserver;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ModelFacadeImplTest {

    private ModelFacade facade;

    private FoodDatabase database;
    private Twilio twilio;

    private FoodListObserver list;
    private NutritionObserver nutrition;
    private MessageObserver message;

    @Before
    public void setUp() {
        this.twilio = mock(Twilio.class);
        this.database = mock(FoodDatabase.class);

        this.list = mock(FoodListObserver.class);
        this.nutrition = mock(NutritionObserver.class);
        this.message = mock(MessageObserver.class);

        this.facade = new ModelFacadeImpl(database, twilio);
    }

    @Test
    public void search() {
        Food food1 = mock(Food.class);
        Food food2 = mock(Food.class);
        Food food3 = mock(Food.class);

        when(database.search("Apple")).thenReturn(Arrays.asList(food1, food2, food3));

        facade.search("Apple", list);

        verify(list, times(1)).update(eq(Arrays.asList(food1, food2, food3)));
    }

    @Test
    public void searchEmpty() {
        when(database.search("Apple")).thenReturn(new ArrayList<Food>());

        facade.search("Apple", list);

        verify(list, times(1)).update(eq(new ArrayList<Food>()));
    }

    @Test
    public void getNutrition() {
        Nutrition mock = mock(Nutrition.class);

        when(database.getNutrition("1234", "size1")).thenReturn(mock);

        facade.getNutrition("1234", "size1", nutrition);

        verify(nutrition, times(1)).update(mock);
    }

    @Test
    public void sendMessage() {
        when(twilio.sendMessage("Hello world")).thenReturn(true);

        facade.sendMessage("Hello world", message);

        verify(message, times(1)).update(true);
    }

    @Test
    public void sendMessageFail() {
        when(twilio.sendMessage("Hello world")).thenReturn(false);

        facade.sendMessage("Hello world", message);

        verify(message, times(1)).update(false);
    }
}