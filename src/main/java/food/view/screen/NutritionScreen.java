package food.view.screen;

import food.controller.Controller;
import food.model.models.Food;
import food.model.models.Ingredient;
import food.model.models.Nutrient;
import food.model.models.Nutrition;
import food.view.FoodWindow;
import food.view.observers.MessageObserver;
import food.view.observers.NutritionObserver;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Collection;
import java.util.List;

/**
 * The detailed Nutrition Screen.
 */
public class NutritionScreen extends AbstractScreen implements NutritionObserver, MessageObserver {

    /**
     * The parent Screen object.
     */
    private final Screen parent;

    /**
     * The Food the Nutrition is based on.
     */
    private final Food food;

    /**
     * The Nutrition being displayed.
     */
    private Nutrition nutrition;

    /**
     * The options for sizes.
     */
    private ComboBox<String> options;

    /**
     * The pagination used to show Nutrition.
     */
    private Pagination pagination;

    /**
     * The checkbox used to select whether the cache should be used.
     */
    private CheckBox checkbox;

    /**
     * Creates a new NutritionScreen object.
     *
     * @param controller The encompassing window's controller.
     * @param parent The parent Screen object.
     * @param food The Food object the Screen is based on.
     */
    public NutritionScreen(Controller controller, Screen parent, Food food) {
        super(controller);
        this.parent = parent;
        this.food = food;

        this.controller.registerNutritionObserver(this);
        this.controller.registerMessageObserver(this);

        setupDropdown();
        this.controller.refresh();
    }

    @Override
    protected void setupNodes() {
        addButton("Return", 500, 10, 90, 30, event -> {
            this.controller.removeNutritionObserver(this);
            this.controller.removeMessageObserver(this);
            this.controller.setScreen(this.parent);
        });

        addButton("Generate\nReport", 500, 50, 90, 50, event -> {
            this.controller.sendMessage(this.food, this.nutrition, options.getValue(), this);
        });
    }

    /**
     * Creates the nodes once the Food object is loaded in.
     */
    private void setupDropdown() {
        if (this.food.getLabel().length() > 50) {
            String reducedLabel = this.food.getLabel().substring(0, 50) + "...";
            addText(reducedLabel, Font.font(20), 10, 30);
        } else if (this.food.getLabel().length() > 25) {
            addText(this.food.getLabel(), Font.font(20), 10, 30);
        } else {
            addText(this.food.getLabel(), Font.font(30), 10, 40);
        }

        this.options = addComboBox(this.food.getMeasures().keySet(), 10, 50, 300);

        addButton("Search", 320, 50, 100, 10, event -> {
            String measure = this.food.getMeasures().get(this.options.getValue());
            this.controller.getNutrition(this.food, measure, this.checkbox.isSelected(), this);
        });

        this.checkbox = addCheckbox("Use cache if possible", 10, 80, true);
    }

    /**
     * Sets up the Nutrition pagination.
     */
    private void setupNutrition() {
        if (this.pagination != null) {
            this.nodes.remove(pagination);
        }

        this.pagination = addPagination(4, 10, 100, 580, 390, index -> {
            if (index == 0) {
                return setPageOne();
            } else if (index == 1) {
                return setPageTwo();
            } else if (index == 2) {
                return setPageThree();
            } else if (index == 3) {
                return setPageFour();
            } else {
                return new VBox();
            }
        });
    }

    /**
     * Creates Page 1 of Pagination.
     *
     * @return The encompassing VBox.
     */
    private VBox setPageOne() {
        Text calories = createTextForPagination("Calories: " + nutrition.getCalories() + "kcal", Font.font(15));
        Text weight = createTextForPagination("Total weight: " + nutrition.getTotalWeight() + "g", Font.font(15));
        Text dietLabel = createTextForPagination("Diet labels:", Font.font(15));

        StringBuilder dietStr = new StringBuilder();

        for (String label : nutrition.getDietLabels()) {
            label = label.replace("_", " ");
            dietStr.append(label).append("\n");
        }

        TextArea diet = createTextAreaForPagination(dietStr.toString(), 160, 5, false);

        Text healthLabel = createTextForPagination("Health labels:", Font.font(15));

        StringBuilder healthStr = new StringBuilder();

        for (String label : nutrition.getHealthLabels()) {
            label = label.replace("_", " ");
            healthStr.append(label).append("\n");
        }

        TextArea health = createTextAreaForPagination(healthStr.toString(), 160, 6, false);

        return new VBox(5, calories, weight, dietLabel, diet, healthLabel, health);
    }

    /**
     * Creates Page 2 of Pagination.
     *
     * @return The encompassing VBox.
     */
    private VBox setPageTwo() {
        Text cautionLabels = createTextForPagination("Cautions:", Font.font(15));

        StringBuilder cautionStr = new StringBuilder();

        for (String label : nutrition.getCautions()) {
            label = label.replace("_", " ");
            cautionStr.append(label).append("\n");
        }

        TextArea cautions = createTextAreaForPagination(cautionStr.toString(), 160, 6, false);

        Text totalNutrientsLabel = createTextForPagination("Total nutrients:", Font.font(15));

        TableView<Nutrient> totalNutrients = createTableViewForPagination(nutrition.getTotalNutrients().values(),
                false, 240);

        addColumnToTable(totalNutrients, "Nutrient", "label");
        addColumnToTable(totalNutrients, "Quantity", "quantity");
        addColumnToTable(totalNutrients, "Unit", "unit");

        return new VBox(5, cautionLabels, cautions, totalNutrientsLabel, totalNutrients);
    }

    /**
     * Creates Page 3 of Pagination.
     *
     * @return The encompassing VBox.
     */
    private VBox setPageThree() {

        Text dailyNutrientsLabel = createTextForPagination("Daily nutrients:", Font.font(15));

        TableView<Nutrient> dailyNutrients = createTableViewForPagination(nutrition.getTotalDaily().values(),
                false, 320);

        addColumnToTable(dailyNutrients, "Nutrient", "label");
        addColumnToTable(dailyNutrients, "Quantity (%)", "quantity");

        return new VBox(5, dailyNutrientsLabel, dailyNutrients);
    }

    /**
     * Creates Page 4 of Pagination.
     *
     * @return The encompassing VBox.
     */
    private VBox setPageFour() {

        Text ingredientsLabel = createTextForPagination("Ingredients:", Font.font(15));

        TableView<Ingredient> ingredients = createTableViewForPagination(nutrition.getIngredients(),
                false, 320);

        addColumnToTable(ingredients, "Ingredient", "food");
        addColumnToTable(ingredients, "Quantity", "quantity");
        addColumnToTable(ingredients, "Weight", "weight");

        return new VBox(5, ingredientsLabel, ingredients);
    }

    @Override
    public void update(boolean messageSuccess) {
        if (messageSuccess) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Message sent", ButtonType.OK);
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Message failure", ButtonType.OK);
            alert.show();
        }
    }

    @Override
    public void update(Nutrition nutrition) {
        this.nutrition = nutrition;
        this.setupNutrition();
        this.controller.refresh();
    }

    @Override
    public void update(Exception exception) {
        Alert alert = new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK);
        alert.show();
    }
}
