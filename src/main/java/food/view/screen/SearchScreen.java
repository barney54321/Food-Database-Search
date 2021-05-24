package food.view.screen;

import food.model.models.Food;
import food.view.FoodWindow;
import food.view.observers.FoodListObserver;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.List;

/**
 * The Screen where users can search for Food items.
 */
public class SearchScreen extends AbstractScreen implements FoodListObserver {

    /**
     * The number of results shown per page.
     */
    private static final int RESULTS_PER_PAGE = 10;

    /**
     * The search bar at the top of the screen.
     */
    private TextField searchBar;

    /**
     * The list of results.
     */
    private Pagination results;

    /**
     * Creates a new SearchScreen object.
     * @param window The encompassing window.
     */
    public SearchScreen(FoodWindow window) {
        super(window);
    }

    @Override
    protected void setupNodes() {
        addText("Food Database", Font.font(30), TextAlignment.LEFT, Color.BLACK, 200, 50);

        this.searchBar = addTextField(40, 80, 440, 10, "Search term");

        addButton("Search", 490, 80, 70, 10, event -> {
            this.window.getController().search(this.searchBar.getText(), this);
        });
    }

    @Override
    public void update(List<Food> foods) {
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

    @Override
    public void update(Exception exception) {
        Alert alert = new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK);
        alert.show();
    }
}
