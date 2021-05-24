package food.view.screen;

import food.model.models.Food;
import food.view.FoodWindow;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.List;

public class SearchScreen extends AbstractScreen {

    private static final int RESULTS_PER_PAGE = 10;

    private TextField searchBar;

    private Pagination results;

    public SearchScreen(FoodWindow window) {
        super(window);
    }

    @Override
    protected void setupNodes() {
        addText("Food Database", Font.font(30), TextAlignment.LEFT, Color.BLACK, 200, 50);

        this.searchBar = addTextField(40, 80, 440, 10, "Search term");

        addButton("Search", 490, 80, 70, 10, event -> {
//            try {
//                List<Food> foods = this.window.getController().search(this.searchBar.getText());
//                setupResultButtons(foods);
//            } catch (Exception e) {
//                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
//                alert.show();
//            }
        });
    }

    private void setupResultButtons(List<Food> foods) {
        if (this.results != null) {
            this.nodes.remove(this.results);
        }

        if (foods.size() > 0) {
            this.results = new Pagination(foods.size() / RESULTS_PER_PAGE);
            this.results.setLayoutX(40);
            this.results.setLayoutY(120);
            this.results.setPrefWidth(520);
            this.results.setPrefHeight(360);

            // Use setPageFactory to set contents of Pagination
            this.results.setPageFactory(index -> {
                List<Food> sublist = foods.subList(index * RESULTS_PER_PAGE, Math.min(foods.size(), (index + 1) * RESULTS_PER_PAGE));

                Button[] foodButtons = new Button[RESULTS_PER_PAGE];

                for (int i = 0; i < RESULTS_PER_PAGE; i++) {
                    Food food = sublist.get(i);

                    foodButtons[i] = new Button();
                    foodButtons[i].setTextAlignment(TextAlignment.LEFT);
                    foodButtons[i].setAlignment(Pos.BASELINE_LEFT);
                    foodButtons[i].setText(food.getLabel());
                    foodButtons[i].setMaxWidth(520);

                    foodButtons[i].setOnAction(event -> {
                        this.window.setScreen(new FoodScreen(this.window, this, food));
                    });
                }

                return new VBox(5, foodButtons);
            });

            this.nodes.add(this.results);
        }

        this.window.refresh();
    }
}
