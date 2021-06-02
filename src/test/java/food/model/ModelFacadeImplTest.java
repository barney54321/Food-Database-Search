package food.model;

import food.model.input.FoodApi;
import food.model.models.Food;
import food.model.models.Nutrition;
import food.model.output.Twilio;
import food.view.observers.FoodListObserver;
import food.view.observers.MessageObserver;
import food.view.observers.NutritionObserver;
import javafx.application.Application;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.*;

public class ModelFacadeImplTest extends Application {

    private ModelFacade facade;

    private FoodApi database;
    private Twilio twilio;

    private FoodListObserver list;
    private NutritionObserver nutrition;
    private MessageObserver message;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // No op
    }

    @BeforeClass
    public static void setUpJavaFX() {
        new Thread(() -> Application.launch(new String[0])).start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {

        }
    }

    @Before
    public void setUp() {
        this.twilio = mock(Twilio.class);
        this.database = mock(FoodApi.class);

        this.list = mock(FoodListObserver.class);
        this.nutrition = mock(NutritionObserver.class);
        this.message = mock(MessageObserver.class);

        this.facade = new ModelFacadeImpl(database, twilio);
    }

    private void waitForPlatformRunLater() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            // No op
        }
    }

    @Test
    public void searchTrue() {
        Food food1 = mock(Food.class);
        Food food2 = mock(Food.class);
        Food food3 = mock(Food.class);

        when(database.search("Apple", true)).thenReturn(Arrays.asList(food1, food2, food3));

        facade.search("Apple", true, list);

        waitForPlatformRunLater();

        verify(list, times(1)).update(eq(Arrays.asList(food1, food2, food3)));
    }

    @Test
    public void searchFalse() {
        Food food1 = mock(Food.class);
        Food food2 = mock(Food.class);
        Food food3 = mock(Food.class);

        when(database.search("Apple", false)).thenReturn(Arrays.asList(food1, food2, food3));

        facade.search("Apple", false, list);

        waitForPlatformRunLater();

        verify(list, times(1)).update(eq(Arrays.asList(food1, food2, food3)));
    }

    @Test
    public void searchFalseError() {
        when(database.search("Apple", true)).thenReturn(null);

        facade.search("Apple", true, list);

        waitForPlatformRunLater();

        verify(list, times(1)).update(any(Exception.class));
    }

    @Test
    public void searchEmptyTrue() {
        when(database.search("Apple", true)).thenReturn(new ArrayList<Food>());

        facade.search("Apple", true, list);

        waitForPlatformRunLater();

        verify(list, times(1)).update(eq(new ArrayList<Food>()));
    }

    @Test
    public void searchEmptyFalse() {
        when(database.search("Apple", false)).thenReturn(new ArrayList<Food>());

        facade.search("Apple", true, list);

        waitForPlatformRunLater();

        verify(list, times(1)).update(eq(new ArrayList<Food>()));
    }

    @Test
    public void getNutritionTrue() {
        Nutrition mock = mock(Nutrition.class);

        when(database.getNutrition("1234", "size1", true)).thenReturn(mock);

        facade.getNutrition("1234", "size1", true, nutrition);

        waitForPlatformRunLater();

        verify(nutrition, times(1)).update(mock);
    }

    @Test
    public void getNutritionFalse() {
        Nutrition mock = mock(Nutrition.class);

        when(database.getNutrition("1234", "size1", false)).thenReturn(mock);

        facade.getNutrition("1234", "size1", false, nutrition);

        waitForPlatformRunLater();

        verify(nutrition, times(1)).update(mock);
    }

    @Test
    public void getNutritionFailTrue() {
        when(database.getNutrition("1234", "size1", true)).thenReturn(null);

        facade.getNutrition("1234", "size1", true, nutrition);

        waitForPlatformRunLater();

        verify(nutrition, times(1)).update(any(Exception.class));
    }

    @Test
    public void getNutritionFailFalse() {
        when(database.getNutrition("1234", "size1", false)).thenReturn(null);

        facade.getNutrition("1234", "size1", false, nutrition);

        waitForPlatformRunLater();

        verify(nutrition, times(1)).update(any(Exception.class));
    }

    @Test
    public void sendMessage() {
        when(twilio.sendMessage("Hello world")).thenReturn(true);

        facade.sendMessage("Hello world", message);

        waitForPlatformRunLater();

        verify(message, times(1)).update(true);
    }

    @Test
    public void sendMessageFail() {
        when(twilio.sendMessage("Hello world")).thenReturn(false);

        facade.sendMessage("Hello world", message);

        waitForPlatformRunLater();

        verify(message, times(1)).update(false);
    }

    @Test(timeout = 2000)
    public void runSimple() {
        // Just ensure that no exceptions are raised and the test doesn't infinite loop
        Thread thread = new Thread(facade::run);
        thread.start();

        try {
            Thread.sleep(100);
            facade.stop();
            thread.join();
        } catch (InterruptedException e) {
            // Not sure whether to fail this?
        }
    }

    @Test(timeout = 2000)
    public void queueSearchSimple() {
        Food food1 = mock(Food.class);
        Food food2 = mock(Food.class);
        Food food3 = mock(Food.class);

        when(database.search("Apple", true)).thenReturn(Arrays.asList(food1, food2, food3));

        Thread thread = new Thread(facade::run);
        thread.start();

        try {
            facade.queueSearch("Apple", true, list);
            Thread.sleep(300);
            facade.stop();
            thread.join();
        } catch (InterruptedException e) {
            // Not sure whether to fail this?
        }

        verify(list, times( 1)).update(eq(Arrays.asList(food1, food2, food3)));
    }

    @Test(timeout = 2000)
    public void queueNutritionSimple() {
        Nutrition mock = mock(Nutrition.class);

        when(database.getNutrition("1234", "size1", true)).thenReturn(mock);

        Thread thread = new Thread(facade::run);
        thread.start();

        try {
            facade.queueGetNutrition("1234", "size1", true, nutrition);
            Thread.sleep(100);
            facade.stop();
            thread.join();
        } catch (InterruptedException e) {
            // Not sure whether to fail this?
        }

        verify(nutrition, times(1)).update(mock);
    }

    @Test(timeout = 2000)
    public void queueSendMessageSimple() {
        when(twilio.sendMessage("Hello world")).thenReturn(true);

        Thread thread = new Thread(facade::run);
        thread.start();

        try {
            facade.queueSendMessage("Hello world", message);
            Thread.sleep(100);
            facade.stop();
            thread.join();
        } catch (InterruptedException e) {
            // Not sure whether to fail this?
        }

        verify(message, times(1)).update(true);
    }

    @Test(timeout = 2000)
    public void queueCompound() {
        Food food1 = mock(Food.class);
        Food food2 = mock(Food.class);
        Food food3 = mock(Food.class);

        when(database.search("Apple", true)).thenReturn(Arrays.asList(food1, food2, food3));

        Nutrition mock = mock(Nutrition.class);

        when(database.getNutrition("1234", "size1", true)).thenReturn(mock);

        when(twilio.sendMessage("Hello world")).thenReturn(true);

        Thread thread = new Thread(facade::run);
        thread.start();

        try {
            facade.queueSearch("Apple", true, list);
            facade.queueSendMessage("Hello world", message);
            facade.queueGetNutrition("1234", "size1", true, nutrition);
            Thread.sleep(200);
            facade.stop();
            thread.join();
        } catch (InterruptedException e) {
            // Not sure whether to fail this?
        }

        verify(nutrition, times(1)).update(mock);
        verify(list, times(1)).update(eq(Arrays.asList(food1, food2, food3)));
        verify(message, times(1)).update(true);
    }
}