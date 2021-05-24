package food.view.screen;

import food.model.models.Food;
import food.model.models.Nutrient;
import food.model.models.Nutrition;
import food.view.FoodWindow;
import food.view.observers.MessageObserver;
import food.view.observers.NutritionObserver;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class NutritionScreen extends AbstractScreen implements NutritionObserver, MessageObserver {

    private Screen parent;

    private Food food;

    private Nutrition nutrition;

    private ComboBox<String> options;

    private Pagination pagination;

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

        addButton("Generate\nReport", 500, 50, 90, 50, event -> {
            System.out.println(this.food.getMeasures().get(options.getValue()));
            this.window.getController().sendMessage(this.food, options.getValue(), this);
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
            this.options.getItems().addAll(this.food.getMeasures().keySet());
            this.options.getSelectionModel().selectFirst();

            this.nodes.add(options);

            addButton("Search", 320, 50, 100, 10, event -> {
                String measure = this.food.getMeasures().get(this.options.getValue());
                this.window.getController().getNutrition(this.food, measure, this);
            });
        }
    }

    private void setupNutrition() {
        if (this.pagination != null) {
            this.nodes.remove(pagination);
        }

        this.pagination = new Pagination(3);

        pagination.setLayoutX(10);
        pagination.setLayoutY(90);
        pagination.setMaxWidth(580);
        pagination.setPrefWidth(580);
        pagination.setMaxHeight(400);
        pagination.setPrefHeight(400);

        pagination.setPageFactory(index -> {
            if (index == 0) {
                return setPageOne();
            } else if (index == 1) {
                return setPageTwo();
            } else if (index == 2) {
                return setPageThree();
            } else {
                return new VBox();
            }
        });

        this.nodes.add(pagination);
    }

    private VBox setPageOne() {
        Text calories = new Text();
        calories.setText("Calories: " + nutrition.getCalories() + "kcal");
        calories.setFont(Font.font(15));

        Text weight = new Text();
        weight.setText("Total weight: " + nutrition.getTotalWeight() + "kg");
        weight.setFont(Font.font(15));

        Text dietLabel = new Text();
        dietLabel.setText("Diet labels:");
        dietLabel.setFont(Font.font(15));

        String dietStr = "";

        for (String label : nutrition.getDietLabels()) {
            label = label.replace("_", " ");
            dietStr += label + "\n";
        }

        TextArea diet = new TextArea();
        diet.setText(dietStr);
        diet.maxWidth(160);
        diet.setPrefRowCount(6);
        diet.setEditable(false);

        Text healthLabel = new Text();
        healthLabel.setText("Health labels:");
        healthLabel.setFont(Font.font(15));

        String healthStr = "";

        for (String label : nutrition.getHealthLabels()) {
            label = label.replace("_", " ");
            healthStr += label + "\n";
        }

        TextArea health = new TextArea();
        health.setText(healthStr);
        health.maxWidth(160);
        health.setPrefRowCount(6);
        health.setEditable(false);

        return new VBox(5, calories, weight, dietLabel, diet, healthLabel, health);
    }

    private VBox setPageTwo() {

        Text cautionLabels = new Text();
        cautionLabels.setText("Cautions:");
        cautionLabels.setFont(Font.font(15));

        String cautionStr = "";

        for (String label : nutrition.getCautions()) {
            label = label.replace("_", " ");
            cautionStr += label + "\n";
        }

        TextArea cautions = new TextArea();
        cautions.setText(cautionStr);
        cautions.maxWidth(160);
        cautions.setPrefRowCount(6);
        cautions.setEditable(false);

        Text totalNutrientsLabel = new Text();
        totalNutrientsLabel.setText("Total nutrients:");
        totalNutrientsLabel.setFont(Font.font(15));

        TableView<Nutrient> totalNutrients = new TableView<>();
        ObservableList<Nutrient> list = totalNutrients.getItems();
        list.addAll(nutrition.getTotalNutrients().values());

        TableColumn<Nutrient, String> typeCol = new TableColumn<>("Nutrient");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("label"));

        TableColumn<Nutrient, String> quantityCol = new TableColumn<>("Quantity");
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<Nutrient, String> unitCol = new TableColumn<>("Unit");
        unitCol.setCellValueFactory(new PropertyValueFactory<>("unit"));

        totalNutrients.getColumns().add(typeCol);
        totalNutrients.getColumns().add(quantityCol);
        totalNutrients.getColumns().add(unitCol);
        totalNutrients.setEditable(false);
        totalNutrients.setMaxHeight(250);
        totalNutrients.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return new VBox(5, cautionLabels, cautions, totalNutrientsLabel, totalNutrients);
    }

    private VBox setPageThree() {

        Text dailyNutrientsLabel = new Text();
        dailyNutrientsLabel.setText("Daily nutrients:");
        dailyNutrientsLabel.setFont(Font.font(15));

        TableView<Nutrient> dailyNutrients = new TableView<>();
        ObservableList<Nutrient> list = dailyNutrients.getItems();
        list.addAll(nutrition.getTotalDaily().values());

        TableColumn<Nutrient, String> typeCol = new TableColumn<>("Nutrient");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("label"));

        TableColumn<Nutrient, String> quantityCol = new TableColumn<>("Quantity (%)");
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        dailyNutrients.getColumns().add(typeCol);
        dailyNutrients.getColumns().add(quantityCol);
        dailyNutrients.setEditable(false);
        dailyNutrients.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return new VBox(5, dailyNutrientsLabel, dailyNutrients);
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
        this.window.refresh();
    }

    @Override
    public void update(Exception exception) {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Select a size", ButtonType.OK);
        alert.show();
    }
}
