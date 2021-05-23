package food.view.screen;

import food.model.Food;
import food.view.FoodWindow;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class NutritionScreen extends AbstractScreen {

    private Screen parent;

    private Food food;

    private ComboBox<String> options;

    public NutritionScreen(FoodWindow window, Screen parent, Food food) {
        super(window);
        this.parent = parent;
        this.food = food;
        setupNodes();
        this.window.refresh();
    }

    @Override
    protected void setupNodes() {
        addButton("Return", 500, 10, 90, 30, event -> {
            this.window.setScreen(this.parent);
        });

        if (this.food != null) {
            if (this.food.getLabel().length() > 50) {
                addText(this.food.getLabel().substring(0, 50) + "...", Font.font(20), TextAlignment.LEFT, Color.BLACK, 10, 30);
            } else if (this.food.getLabel().length() > 25) {
                addText(this.food.getLabel(), Font.font(20), TextAlignment.LEFT, Color.BLACK, 10, 30);
            } else {
                addText(this.food.getLabel(), Font.font(30), TextAlignment.LEFT, Color.BLACK, 10, 40);
            }

            this.options = new ComboBox<>();
            this.options.setLayoutX(10);
            this.options.setLayoutY(50);
            this.options.setMaxWidth(300);
            this.options.setPrefWidth(300);

            this.nodes.add(options);
        }
    }
}
