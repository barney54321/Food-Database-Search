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

    private Nutrition nut;

    private String text;

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

        this.facade.attach(list);
        this.facade.attach(nutrition);
        this.facade.attach(message);

        this.facade.setMaxCalories(1000);

        this.nut = mock(Nutrition.class);
        when(this.nut.getCalories()).thenReturn(100);

        this.text = "";
        this.text += "Food ID: food_bn4bryqayjl958auu03k3bxf6ja8\n";
        this.text += "Label: Ferrero Rocher Ferrero Eggs, Cocoa\n";
        this.text += "Brand: Ferrero Rocher\n";
        this.text += "Servings per container: 2.5\n";
        this.text += "Size: size1\n";
        this.text += "Calories: 100\n";
        this.text += "Diet labels: [GLUTEN FREE]\n";
        this.text += "Health labels: [HALAL, VEGETARIAN]\n\n";
        this.text += "Nutrients: \n";
        this.text += "ENERC_KCAL: 573.0\n";
        this.text += "PROCNT: 5.0\n";
        this.text += "FAT: 37.5\n";
        this.text += "CHOCDF: 55.0\n";
        this.text += "FIBTG: 5.0\n";
    }

    private void waitForPlatformRunLater() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            // No op
        }
    }

    @Test
    public void searchTrueFalse() {
        Food food1 = mock(Food.class);
        Food food2 = mock(Food.class);
        Food food3 = mock(Food.class);

        when(database.search("Apple", true)).thenReturn(Arrays.asList(food1, food2, food3));

        facade.search("Apple", true, false);

        waitForPlatformRunLater();

        verify(list, times(1)).update(eq(Arrays.asList(food1, food2, food3)));
    }

    @Test
    public void searchTrueTrue() {
        Food food1 = mock(Food.class);
        Food food2 = mock(Food.class);
        Food food3 = mock(Food.class);

        when(food1.getLabel()).thenReturn("Orange");
        when(food2.getLabel()).thenReturn("Apple");
        when(food3.getLabel()).thenReturn("Mandarin");

        when(database.search("Apple", true)).thenReturn(Arrays.asList(food1, food2, food3));

        facade.search("Apple", true, true);

        waitForPlatformRunLater();

        verify(list, times(1)).update(eq(Arrays.asList(food2)));
    }

    @Test
    public void searchFalseFalse() {
        Food food1 = mock(Food.class);
        Food food2 = mock(Food.class);
        Food food3 = mock(Food.class);

        when(database.search("Apple", false)).thenReturn(Arrays.asList(food1, food2, food3));

        facade.search("Apple", false, false);

        waitForPlatformRunLater();

        verify(list, times(1)).update(eq(Arrays.asList(food1, food2, food3)));
    }

    @Test
    public void searchFalseTrue() {
        Food food1 = mock(Food.class);
        Food food2 = mock(Food.class);
        Food food3 = mock(Food.class);

        when(food1.getLabel()).thenReturn("Orange");
        when(food2.getLabel()).thenReturn("Apple");
        when(food3.getLabel()).thenReturn("Mandarin");

        when(database.search("Apple", false)).thenReturn(Arrays.asList(food1, food2, food3));

        facade.search("Apple", false, true);

        waitForPlatformRunLater();

        verify(list, times(1)).update(eq(Arrays.asList(food2)));
    }

    @Test
    public void searchFalseError() {
        when(database.search("Apple", true)).thenReturn(null);

        facade.search("Apple", true, false);

        waitForPlatformRunLater();

        verify(list, times(1)).update(any(Exception.class));
    }

    @Test
    public void searchEmptyTrue() {
        when(database.search("Apple", true)).thenReturn(new ArrayList<Food>());

        facade.search("Apple", true, false);

        waitForPlatformRunLater();

        verify(list, times(1)).update(eq(new ArrayList<Food>()));
    }

    @Test
    public void searchEmptyFalse() {
        when(database.search("Apple", false)).thenReturn(new ArrayList<Food>());

        facade.search("Apple", true, false);

        waitForPlatformRunLater();

        verify(list, times(1)).update(eq(new ArrayList<Food>()));
    }

    @Test
    public void getNutritionTrue() {
        when(database.getNutrition("1234", "size1", true)).thenReturn(nut);

        facade.getNutrition("1234", "size1", true);

        waitForPlatformRunLater();

        verify(nutrition, times(1)).update(nut);
    }

    @Test
    public void getNutritionFalse() {
        when(database.getNutrition("1234", "size1", false)).thenReturn(nut);

        facade.getNutrition("1234", "size1", false);

        waitForPlatformRunLater();

        verify(nutrition, times(1)).update(nut);
    }

    @Test
    public void getNutritionFailTrue() {
        when(database.getNutrition("1234", "size1", true)).thenReturn(null);

        facade.getNutrition("1234", "size1", true);

        waitForPlatformRunLater();

        verify(nutrition, times(1)).update(any(Exception.class));
    }

    @Test
    public void getNutritionFailFalse() {
        when(database.getNutrition("1234", "size1", false)).thenReturn(null);

        facade.getNutrition("1234", "size1", false);

        waitForPlatformRunLater();

        verify(nutrition, times(1)).update(any(Exception.class));
    }

    @Test
    public void getNutritionBelowBound() {
        when(database.getNutrition("1234", "size1", true)).thenReturn(nut);

        facade.getNutrition("1234", "size1", true);

        waitForPlatformRunLater();

        verify(nutrition, times(1)).update(nut);
        verify(nut, never()).setOverCalorieLimit();
    }

    @Test
    public void getNutritionEqualToBound() {
        this.facade.setMaxCalories(100);

        when(database.getNutrition("1234", "size1", true)).thenReturn(nut);

        facade.getNutrition("1234", "size1", true);

        waitForPlatformRunLater();

        verify(nutrition, times(1)).update(nut);
        verify(nut, never()).setOverCalorieLimit();
    }

    @Test
    public void getNutritionAboveBound() {
        this.facade.setMaxCalories(99);

        when(database.getNutrition("1234", "size1", true)).thenReturn(nut);

        facade.getNutrition("1234", "size1", true);

        waitForPlatformRunLater();

        verify(nutrition, times(1)).update(nut);
        verify(nutrition, times(1)).update(any(Exception.class));
        verify(nut, times(1)).setOverCalorieLimit();
    }

    @Test
    public void getNutritionNullNutrition() {
        when(nut.getCalories()).thenReturn(null);

        when(database.getNutrition("1234", "size1", true)).thenReturn(nut);

        facade.getNutrition("1234", "size1", true);

        waitForPlatformRunLater();

        verify(nutrition, times(1)).update(nut);
        verify(nutrition, times(1)).update(any(Exception.class));
    }

    @Test
    public void sendMessage() {
        when(twilio.sendMessage(text)).thenReturn(true);

        facade.sendMessage(text);

        waitForPlatformRunLater();

        verify(message, times(1)).update(true);
    }

    @Test
    public void sendMessageFail() {
        when(twilio.sendMessage(text)).thenReturn(false);

        facade.sendMessage(text);

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
    public void queueSearchSimpleFalse() {
        Food food1 = mock(Food.class);
        Food food2 = mock(Food.class);
        Food food3 = mock(Food.class);

        when(food1.getLabel()).thenReturn("Orange");
        when(food2.getLabel()).thenReturn("Apple");
        when(food3.getLabel()).thenReturn("Mandarin");

        when(database.search("Apple", true)).thenReturn(Arrays.asList(food1, food2, food3));

        Thread thread = new Thread(facade::run);
        thread.start();

        try {
            facade.queueSearch("Apple", true, false);
            Thread.sleep(300);
            facade.stop();
            thread.join();
        } catch (InterruptedException e) {
            // Not sure whether to fail this?
        }

        verify(list, times( 1)).update(eq(Arrays.asList(food1, food2, food3)));
    }

    @Test(timeout = 2000)
    public void queueSearchSimpleTrue() {
        Food food1 = mock(Food.class);
        Food food2 = mock(Food.class);
        Food food3 = mock(Food.class);

        when(food1.getLabel()).thenReturn("Orange");
        when(food2.getLabel()).thenReturn("Apple");
        when(food3.getLabel()).thenReturn("Mandarin");

        when(database.search("Apple", true)).thenReturn(Arrays.asList(food1, food2, food3));

        Thread thread = new Thread(facade::run);
        thread.start();

        try {
            facade.queueSearch("Apple", true, true);
            Thread.sleep(300);
            facade.stop();
            thread.join();
        } catch (InterruptedException e) {
            // Not sure whether to fail this?
        }

        verify(list, times( 1)).update(eq(Arrays.asList(food2)));
    }

    @Test(timeout = 2000)
    public void queueNutritionSimple() {
        Nutrition mock = mock(Nutrition.class);

        when(database.getNutrition("1234", "size1", true)).thenReturn(mock);

        Thread thread = new Thread(facade::run);
        thread.start();

        try {
            facade.queueGetNutrition("1234", "size1", true);
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
            facade.queueSendMessage("Hello world");
            Thread.sleep(100);
            facade.stop();
            thread.join();
        } catch (InterruptedException e) {
            // Not sure whether to fail this?
        }

        verify(message, times(1)).update(true);
    }

    @Test(timeout = 4000)
    public void queueCompound() {
        Food food1 = mock(Food.class);
        Food food2 = mock(Food.class);
        Food food3 = mock(Food.class);

        when(food1.getLabel()).thenReturn("Orange");
        when(food2.getLabel()).thenReturn("Apple");
        when(food3.getLabel()).thenReturn("Mandarin");

        when(database.search("Apple", true)).thenReturn(Arrays.asList(food1, food2, food3));

        Nutrition mock = mock(Nutrition.class);

        when(database.getNutrition("1234", "size1", true)).thenReturn(mock);

        when(twilio.sendMessage("Hello world")).thenReturn(true);

        Thread thread = new Thread(facade::run);
        thread.start();

        try {
            facade.queueSearch("Apple", true, false);
            facade.queueSendMessage("Hello world");
            facade.queueGetNutrition("1234", "size1", true);
            Thread.sleep(1000);
            facade.stop();
            thread.join();
        } catch (InterruptedException e) {
            // Not sure whether to fail this?
        }

        verify(nutrition, times(1)).update(mock);
        verify(list, times(1)).update(eq(Arrays.asList(food1, food2, food3)));
        verify(message, times(1)).update(true);
    }

    @Test
    public void detachFoodObserver() {
        facade.detach(list);

        Food food1 = mock(Food.class);
        Food food2 = mock(Food.class);
        Food food3 = mock(Food.class);

        when(database.search("Apple", true)).thenReturn(Arrays.asList(food1, food2, food3));

        facade.search("Apple", true, false);

        waitForPlatformRunLater();

        verify(list, never()).update(eq(Arrays.asList(food1, food2, food3)));
    }

    @Test
    public void detachNutritionObserver() {
        facade.detach(nutrition);

        Nutrition mock = mock(Nutrition.class);

        when(database.getNutrition("1234", "size1", true)).thenReturn(mock);

        facade.getNutrition("1234", "size1", true);

        waitForPlatformRunLater();

        verify(nutrition, never()).update(mock);
    }

    @Test
    public void detachMessageObserver() {
        facade.detach(message);

        when(twilio.sendMessage("Hello world")).thenReturn(true);

        facade.sendMessage("Hello world");

        waitForPlatformRunLater();

        verify(message, never()).update(true);
    }
}