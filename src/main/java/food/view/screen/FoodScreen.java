package food.view.screen;

import food.model.Food;
import food.model.Nutrient;
import food.view.FoodWindow;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;

public class FoodScreen extends AbstractScreen {

    private Screen parent;

    private Food food;

    public FoodScreen(FoodWindow window, Screen parent, Food food) {
        super(window);
        this.parent = parent;
        this.food = food;
        setupNodes();
        this.window.refresh();
    }

    // This is here because JavaFX TableView won't use HashMap.Entry
    public class NutrientMap {
        public String key;
        public Double value;

        public NutrientMap(String key, Double value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return this.key;
        }

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
                addText(this.food.getLabel().substring(0, 60) + "...", Font.font(20), TextAlignment.LEFT, Color.BLACK, 10, 240);
            } else if (this.food.getLabel().length() > 30) {
                addText(this.food.getLabel(), Font.font(20), TextAlignment.LEFT, Color.BLACK, 10, 240);
            } else {
                addText(this.food.getLabel(), Font.font(30), TextAlignment.LEFT, Color.BLACK, 10, 240);
            }
            addText("Brand: " + this.food.getBrand(), Font.font(20), TextAlignment.LEFT, Color.BLACK, 10, 270);
            addText("Category: " + this.food.getCategory(), Font.font(20), TextAlignment.LEFT, Color.BLACK, 10, 300);
            addText("Servings per size: " + this.food.getServingsPerContainer(), Font.font(20), TextAlignment.LEFT, Color.BLACK, 10, 330);

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
            nutrients.setLayoutX(10);
            nutrients.setLayoutY(340);

            this.nodes.add(nutrients);
        }
    }
}
