package food.view.screen;

import food.controller.Controller;
import food.model.models.Food;
import food.view.FoodWindow;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * The Template for all concrete Screen classes.
 * Each Screen class overrides the setupNodes() method, which specifies what appears on the screen and where.
 */
public abstract class AbstractScreen implements Screen {

    /**
     * The list of JavaFX nodes on the Screen.
     */
    protected List<Node> nodes;

    /**
     * The enveloping window's controller.
     */
    protected final Controller controller;

    /**
     * Creates a new Abstract Screen object.
     *
     * @param controller The encompassing window's controller.
     */
    public AbstractScreen(Controller controller) {
        this.controller = controller;
        this.nodes = new ArrayList<>();
        setupNodes();
    }

    /**
     * Sets up the JavaFX nodes on the screen.
     */
    protected abstract void setupNodes();

    /**
     * Creates a Text object and adds it to the Screen.
     *
     * @param str The text of the object.
     * @param font The font used for the Text.
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @return The created Text object.
     */
    protected Text addText(String str, Font font, double x, double y) {
        Text text = new Text();
        text.setText(str);
        text.setTextAlignment(TextAlignment.LEFT);
        text.setFont(font);
        text.setLayoutX(x);
        text.setLayoutY(y);
        text.setFill(Color.BLACK);
        this.nodes.add(text);
        return text;
    }

    /**
     * Creates a TextField object the user can type in and adds it to the Screen.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @param width The width of the field.
     * @param height The height of the field.
     * @param prompt The prompt within the field.
     * @return The created TextField.
     */
    protected TextField addTextField(double x, double y, double width, double height, String prompt) {
        TextField field = new TextField();
        field.setLayoutX(x);
        field.setLayoutY(y);
        field.setPrefWidth(width);
        field.setPrefHeight(height);
        field.setPromptText(prompt);
        this.nodes.add(field);
        return field;
    }

    /**
     * Creates a Button and adds it to the Screen.
     *
     * @param text The button prompt.
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @param width The width of the button.
     * @param height The height of the button.
     * @param method The event handler for the button.
     * @return The created Button.
     */
    protected Button addButton(String text, double x, double y,
                               double width, double height, EventHandler<ActionEvent> method) {
        Button button = new Button();
        button.setText(text);
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setPrefWidth(width);
        button.setPrefHeight(height);
        button.setOnAction(method);
        this.nodes.add(button);
        return button;
    }

    /**
     * Creates a new Checkbox and adds it to the Screen.
     *
     * @param text The checkbox prompt.
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @param selected Whether the checkbox should be pre-selected
     * @return The created checkbox.
     */
    protected CheckBox addCheckbox(String text, double x, double y, boolean selected) {
        CheckBox checkbox = new CheckBox(text);
        checkbox.setLayoutX(x);
        checkbox.setLayoutY(y);
        checkbox.setSelected(selected);
        this.nodes.add(checkbox);
        return checkbox;
    }

    /**
     * Creates a new Pagination and adds it to the screen.
     *
     * @param pages The number of pages.
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @param width The width of the pagination.
     * @param height The height of the pagination.
     * @param factory The callback to create the pages.
     * @return The created pagination
     */
    protected Pagination addPagination(int pages, double x, double y,
                                       double width, double height, Callback<Integer, Node> factory) {

        Pagination pagination = new Pagination(pages);
        pagination.setLayoutX(x);
        pagination.setLayoutY(y);
        pagination.setPrefWidth(width);
        pagination.setPrefHeight(height);
        pagination.setPageFactory(factory);
        this.nodes.add(pagination);
        return pagination;
    }

    /**
     * Creates a new ImageView and adds it to the screen.
     *
     * @param path The image path.
     * @param backgroundLoad Whether to load the image in the background.
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @param width The width of the image.
     * @param height The height of the image.
     * @return The created image.
     */
    protected ImageView addImage(String path, boolean backgroundLoad, int x, int y, int width, int height) {
        Image image = new Image(path, backgroundLoad);
        ImageView view = new ImageView(image);

        view.setX(x);
        view.setY(y);
        view.setFitWidth(width);
        view.setFitHeight(height);

        this.nodes.add(view);

        return view;
    }

    /**
     * Creates a new imageview and adds it to the screen.
     *
     * @param stream The image's input stream.
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @param width The width of the image.
     * @param height The height of the image.
     * @return The created image.
     */
    protected ImageView addImage(InputStream stream, int x, int y, int width, int height) {
        Image image = new Image(stream);
        ImageView view = new ImageView(image);

        view.setX(x);
        view.setY(y);
        view.setFitWidth(width);
        view.setFitHeight(height);

        this.nodes.add(view);

        return view;
    }

    /**
     * Creates a new TableView and adds it to the screen.
     *
     * @param values The rows of the table.
     * @param editable Whether the table should be editable.
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @param height The height of the table.
     * @param <T> The type of the contents.
     * @return The created table.
     */
    protected <T> TableView<T> addTableView(List<T> values, boolean editable, double x, double y, double height) {
        TableView<T> table = new TableView<>();
        ObservableList<T> list = table.getItems();

        for (T value : values) {
            list.add(value);
        }

        table.setEditable(editable);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setMaxHeight(height);
        table.setLayoutX(x);
        table.setLayoutY(y);

        this.nodes.add(table);

        return table;
    }

    /**
     * Creates a new column in a given table view.
     *
     * @param table The table to modify.
     * @param columnName The name of the column.
     * @param columnAttribute The attribute name (based on T).
     * @param <T> The type of the row in the table.
     * @param <S> The data type for the column value.
     * @return The created column.
     */
    protected <T, S> TableColumn<T,S> addColumnToTable(TableView<T> table, String columnName, String columnAttribute) {
        TableColumn<T, S> typeCol = new TableColumn<>(columnName);
        typeCol.setCellValueFactory(new PropertyValueFactory<>(columnAttribute));
        table.getColumns().add(typeCol);
        return typeCol;
    }

    /**
     * Creates a new combo box and adds it to the screen.
     *
     * @param items The options to include in the dropdown.
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @param width The width of the dropdown.
     * @param <T> The type of the dropdown options.
     * @return The created dropdown.
     */
    protected <T> ComboBox<T> addComboBox(Collection<T> items, double x, double y, double width) {
        ComboBox<T> options = new ComboBox<>();
        options.setLayoutX(x);
        options.setLayoutY(y);
        options.setMaxWidth(width);
        options.setPrefWidth(width);
        options.getItems().addAll(items);
        options.getSelectionModel().selectFirst();
        this.nodes.add(options);
        return options;
    }

    /**
     * Creates a text that can be added to a Pagination.
     *
     * @param str The contents of the text.
     * @param font The font for the text.
     * @return The created text.
     */
    protected Text createTextForPagination(String str, Font font) {
        Text text = new Text();
        text.setText(str);
        text.setFont(font);
        return text;
    }

    /**
     * Creates a text area that can be added to a Pagination.
     *
     * @param str The contents of the text area.
     * @param width The width of the area.
     * @param rows The number of rows for the area.
     * @param editable Whether it should be editable.
     * @return The created text area.
     */
    protected TextArea createTextAreaForPagination(String str, int width, int rows, boolean editable) {
        TextArea text = new TextArea();
        text.setText(str);
        text.maxWidth(width);
        text.setPrefRowCount(rows);
        text.setEditable(editable);
        return text;
    }

    /**
     * Creates a table that can be added to a Pagination.
     *
     * @param values The rows of the table.
     * @param editable Whether the table should be editable.
     * @param height The height of the table.
     * @param <T> The type of the rows.
     * @return The created table.
     */
    protected <T> TableView<T> createTableViewForPagination(Collection<T> values, boolean editable, double height) {

        TableView<T> table = new TableView<>();
        ObservableList<T> list = table.getItems();

        for (T value : values) {
            list.add(value);
        }

        table.setEditable(editable);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setMaxHeight(height);

        return table;
    }

    /**
     * Creates a button that can be used in a Pagination.
     *
     * @param str The button prompt.
     * @param align The alignment for the button.
     * @param width The width of the button.
     * @param event The event handler.
     * @return The created button.
     */
    protected Button createButtonForPagination(String str, Pos align, int width, EventHandler<ActionEvent> event) {
        Button button = new Button();
        button.setAlignment(align);
        button.setText(str);
        button.setMaxWidth(width);
        button.setOnAction(event);
        return button;
    }

    @Override
    public List<Node> getNodes() {
        return this.nodes;
    }
}
