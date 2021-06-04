package food.view.screen;

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
     * @param window The encompassing window.
     * @param parent The parent Screen object.
     * @param food The food object the screen represents.
     */
    public FoodScreen(FoodWindow window, Screen parent, Food food) {
        super(window);
        this.parent = parent;
        this.food = food;
        setupNodes();
        this.window.refresh();
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
            this.window.setScreen(this.parent);
        });

        addButton("Nutrition", 500, 50, 90, 30, event -> {
            Screen nutritionScreen = new NutritionScreen(this.window, this, this.food);
            this.window.setScreen(nutritionScreen);
        });

        if (this.food != null) {

            if (this.food.getImagePath() != null) {
                Image image = new Image(this.food.getImagePath(), true);
                ImageView view = new ImageView(image);

                view.setX(10);
                view.setY(10);
                view.setFitWidth(190);
                view.setFitHeight(190);

                this.nodes.add(view);
            } else {
                try {
                    Image image = new Image(new FileInputStream("src/main/resources/placeholder.png"));
                    ImageView view = new ImageView(image);

                    view.setX(10);
                    view.setY(10);
                    view.setFitWidth(190);
                    view.setFitHeight(190);

                    this.nodes.add(view);
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

            addText("Servings per size: " + servings.substring(0, Math.min(5, servings.length())), Font.font(20), 10, 370);

            TableView<NutrientMap> nutrients = new TableView<>();
            ObservableList<NutrientMap> list = nutrients.getItems();

            for (Map.Entry<String, Double> entry : this.food.getNutrients().entrySet()) {
                list.add(new NutrientMap(entry.getKey(), entry.getValue()));
            }

            TableColumn<NutrientMap, String> typeCol = new TableColumn<>("Nutrient");
            typeCol.setCellValueFactory(new PropertyValueFactory<>("key"));

            TableColumn<NutrientMap, String> quantityCol = new TableColumn<>("Quantity");
            quantityCol.setCellValueFactory(new PropertyValueFactory<>("value"));

            nutrients.getColumns().add(typeCol);
            nutrients.getColumns().add(quantityCol);
            nutrients.setEditable(false);
            nutrients.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            nutrients.setMaxHeight(150);
            nutrients.setLayoutX(300);
            nutrients.setLayoutY(250);

            this.nodes.add(nutrients);
        }
    }
}
