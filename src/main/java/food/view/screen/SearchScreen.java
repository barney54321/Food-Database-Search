package food.view.screen;

import food.controller.Controller;
import food.model.models.Food;
import food.view.FoodWindow;
import food.view.observers.FoodListObserver;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
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
     * The checkbox for selecting whether cache should be used.
     */
    private CheckBox checkbox;

    /**
     * The checkbox for selecting whether the quick search function should be used.
     */
    private CheckBox quick;

    /**
     * The list of results.
     */
    private Pagination results;

    /**
     * Creates a new SearchScreen object.
     *
     * @param controller The encompassing window's controller.
     */
    public SearchScreen(Controller controller) {
        super(controller);
    }

    @Override
    protected void setupNodes() {
        addText("Food Database", Font.font(30), 200, 40);

        this.searchBar = addTextField(40, 60, 440, 10, "Search term");

        addButton("Search", 490, 60, 70, 10, event -> {
            boolean check = this.checkbox.isSelected();
            boolean quick = this.quick.isSelected();
            this.controller.search(this.searchBar.getText(), check, quick, this);
        });

        this.checkbox = new CheckBox("Use cache");
        this.checkbox.setLayoutX(40);
        this.checkbox.setLayoutY(90);
        this.checkbox.setSelected(true);
        this.nodes.add(this.checkbox);

        this.quick = new CheckBox("I'm feeling lucky");
        this.quick.setLayoutX(240);
        this.quick.setLayoutY(90);
        this.quick.setSelected(false);
        this.nodes.add(this.quick);
    }

    @Override
    public void update(List<Food> foods) {
        if (this.results != null) {
            this.nodes.remove(this.results);
        }

        if (foods.size() > 0) {
            this.results = new Pagination(Math.max(1, foods.size() / RESULTS_PER_PAGE));
            this.results.setLayoutX(40);
            this.results.setLayoutY(120);
            this.results.setPrefWidth(520);
            this.results.setPrefHeight(360);

            // Use setPageFactory to set contents of Pagination
            this.results.setPageFactory(index -> {
                int start = index * RESULTS_PER_PAGE;
                List<Food> sublist = foods.subList(start, Math.min(foods.size(), (index + 1) * RESULTS_PER_PAGE));

                Button[] foodButtons = new Button[Math.min(RESULTS_PER_PAGE, sublist.size())];

                for (int i = 0; i < Math.min(RESULTS_PER_PAGE, sublist.size()); i++) {
                    Food food = sublist.get(i);

                    foodButtons[i] = new Button();
                    foodButtons[i].setTextAlignment(TextAlignment.LEFT);
                    foodButtons[i].setAlignment(Pos.BASELINE_LEFT);
                    foodButtons[i].setText(food.getLabel());
                    foodButtons[i].setMaxWidth(520);

                    foodButtons[i].setOnAction(event -> {
                        this.controller.setScreen(new FoodScreen(this.controller, this, food));
                    });
                }

                return new VBox(5, foodButtons);
            });

            this.nodes.add(this.results);
        }

        this.controller.refresh();

        if (foods.size() == 1 && this.quick.isSelected()) {
            Food food = foods.get(0);
            this.controller.setScreen(new FoodScreen(this.controller, this, food));
        }
    }

    @Override
    public void update(Exception exception) {
        Alert alert = new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK);
        alert.show();
    }
}
