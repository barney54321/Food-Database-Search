package food.view.screen;

import food.controller.Controller;
import food.model.models.Food;
import food.view.FoodWindow;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The basic Food information Screen.
 */
public class FoodScreen extends AbstractScreen {

    /**
     * The Screen which the user came to this Screen from.
     * Used for navigating without hitting API again.
     */
    private final Screen parent;

    /**
     * The Food object this Screen represents.
     */
    private final Food food;

    /**
     * Creates a new FoodScreen object based on the provided Food object.
     *
     * @param controller The encompassing window's controller.
     * @param parent The parent Screen object.
     * @param food The food object the screen represents.
     */
    public FoodScreen(Controller controller, Screen parent, Food food) {
        super(controller);
        this.parent = parent;
        this.food = food;

        this.setupFoodNodes();
        this.controller.refresh();
    }

    /**
     * Represents a Key-Value pairing for Nutrient information.
     * This class is here simply because JavaFX TableView can't access Map.Entry with Reflections.
     */
    public class NutrientMap {

        /**
         * The key (nutrient name).
         */
        public final String key;

        /**
         * The value (nutrient amount).
         */
        public final Double value;

        /**
         * Creates a new NutrientMap object.
         *
         * @param key The key the map is based on.
         * @param value The associated value.
         */
        public NutrientMap(String key, Double value) {
            this.key = key;
            this.value = value;
        }

        /**
         * Returns the stored key.
         *
         * @return The key.
         */
        public String getKey() {
            return this.key;
        }

        /**
         * Returns the stored value.
         *
         * @return The value.
         */
        public Double getValue() {
            return this.value;
        }

    }

    @Override
    protected void setupNodes() {
        addButton("Return", 500, 10, 90, 30, event -> {
            this.controller.setScreen(this.parent);
        });

        addButton("Nutrition", 500, 50, 90, 30, event -> {
            Screen nutritionScreen = new NutritionScreen(this.controller, this, this.food);
            this.controller.setScreen(nutritionScreen);
        });
    }

    /**
     * Creates the nodes once the Food object is loaded in.
     */
    private void setupFoodNodes() {
        if (this.food != null) {

            if (this.food.getImagePath() != null) {
                addImage(this.food.getImagePath(), true, 10, 10, 190, 190);
            } else {
                try {
                    InputStream stream = new FileInputStream("src/main/resources/placeholder.png");
                    addImage(stream, 10, 10, 190, 190);
                } catch (FileNotFoundException e) {
                    // Just give up at this point
                    e.printStackTrace();
                }
            }

            if (this.food.getLabel().length() > 60) {
                String text = this.food.getLabel().substring(0, 60) + "...";
                addText(text, Font.font(20), 10, 240);
            } else if (this.food.getLabel().length() > 30) {
                addText(this.food.getLabel(), Font.font(20), 10, 240);
            } else {
                addText(this.food.getLabel(), Font.font(30), 10, 240);
            }

            addText("Brand: " + this.food.getBrand(), Font.font(20), 10, 280);

            String category = this.food.getCategory();

            addText("Category: " + category, Font.font(20), 10, 310);

            String categoryLabel = this.food.getCategoryLabel();

            addText("Category label: " + categoryLabel, Font.font(20), 10, 340);

            String servings = this.food.getServingsPerContainer() + "";
            String servingText = "Servings per size: " + servings.substring(0, Math.min(5, servings.length()));

            addText(servingText, Font.font(20), 10, 370);

            List<NutrientMap> list = new ArrayList<>();
            for (Map.Entry<String, Double> entry : this.food.getNutrients().entrySet()) {
                list.add(new NutrientMap(entry.getKey(), entry.getValue()));
            }

            TableView<NutrientMap> table = addTableView(list, false, 300, 250, 150);
            addColumnToTable(table, "Nutrient", "key");
            addColumnToTable(table, "Quantity", "value");
        }
    }
}
