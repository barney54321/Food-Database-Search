package food.view.screen;

import food.controller.Controller;
import food.model.models.Food;
import food.view.FoodWindow;
import food.view.observers.FoodListObserver;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;

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

        this.controller.registerFoodListObserver(this);
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

        this.checkbox = addCheckbox("Use cache", 40, 90, true);

        this.quick = addCheckbox("I'm feeling lucky", 240, 90, false);
    }

    @Override
    public void update(List<Food> foods) {
        if (this.results != null) {
            this.nodes.remove(this.results);
        }

        if (foods.size() > 0) {

            Callback<Integer, Node> callback = index -> {
                int start = index * RESULTS_PER_PAGE;
                List<Food> sublist = foods.subList(start, Math.min(foods.size(), (index + 1) * RESULTS_PER_PAGE));

                Button[] foodButtons = new Button[Math.min(RESULTS_PER_PAGE, sublist.size())];

                for (int i = 0; i < Math.min(RESULTS_PER_PAGE, sublist.size()); i++) {
                    Food food = sublist.get(i);
                    foodButtons[i] = createButtonForPagination(food);
                }

                return new VBox(5, foodButtons);
            };

            int count = Math.max(1, foods.size() / RESULTS_PER_PAGE);
            this.results = addPagination(count,40, 120, 520, 360, callback);
        }

        this.controller.refresh();

        if (foods.size() == 1 && this.quick.isSelected()) {
            Food food = foods.get(0);
            this.controller.setScreen(new FoodScreen(this.controller, this, food));
        }
    }

    private Button createButtonForPagination(Food food) {
        Button button = createButtonForPagination(food.getLabel(), Pos.BASELINE_LEFT, 520, event -> {
            this.controller.setScreen(new FoodScreen(this.controller, this, food));
        });

        return button;
    }

    @Override
    public void update(Exception exception) {
        Alert alert = new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK);
        alert.show();
    }
}
