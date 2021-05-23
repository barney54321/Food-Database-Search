package food.view.screen;

import food.model.Food;
import food.view.FoodWindow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

    @Override
    protected void setupNodes() {
        addButton("Return", 500, 10, 90, 30, event -> {
            this.window.setScreen(this.parent);
        });

        addButton("Nutrition", 500, 50, 90, 30, event -> {

        });

        addButton("Generate\nReport", 500, 90, 90, 50, event -> {

        });

        if (this.food != null) {

            if (this.food.getImagePath() != null) {
                Image image = new Image(this.food.getImagePath(), true);
                ImageView view = new ImageView(image);

                view.setX(10);
                view.setY(10);
                view.setFitWidth(200);
                view.setFitHeight(200);

                this.nodes.add(view);
            } else {
                try {
                    Image image = new Image(new FileInputStream("src/main/resources/placeholder.png"));
                    ImageView view = new ImageView(image);

                    view.setX(10);
                    view.setY(10);
                    view.setFitWidth(200);
                    view.setFitHeight(200);

                    this.nodes.add(view);
                } catch (FileNotFoundException e) {
                    // Just give up at this point
                    e.printStackTrace();
                }
            }

            addText(this.food.getLabel(), Font.font(30), TextAlignment.LEFT, Color.BLACK, 10, 250);
            addText("Brand: " + this.food.getBrand(), Font.font(20), TextAlignment.LEFT, Color.BLACK, 10, 280);
            addText("Category: " + this.food.getCategory(), Font.font(20), TextAlignment.LEFT, Color.BLACK, 10, 310);
            addText("Servings per size: " + this.food.getServingsPerContainer(), Font.font(20), TextAlignment.LEFT, Color.BLACK, 10, 340);

            Map<String, Double> nutrients = this.food.getNutrients();
            addText("ENERC KCAL: " + nutrients.get("ENERC_KCAL"), Font.font(15), TextAlignment.LEFT, Color.BLACK, 10, 370);
            addText("PROCNT: " + nutrients.get("PROCNT"), Font.font(15), TextAlignment.LEFT, Color.BLACK, 10, 390);
            addText("FAT: " + nutrients.get("FAT"), Font.font(15), TextAlignment.LEFT, Color.BLACK, 10, 410);
            addText("CHOCDF: " + nutrients.get("CHOCDF"), Font.font(15), TextAlignment.LEFT, Color.BLACK, 10, 430);
            addText("FIBTG: " + nutrients.get("FIBTG"), Font.font(15), TextAlignment.LEFT, Color.BLACK, 10, 450);
        }
    }
}
