package food.view.screen;

import food.view.FoodWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.List;

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
     * The enveloping window object.
     */
    protected final FoodWindow window;

    /**
     * Creates a new Abstract Screen object.
     *
     * @param window The encompassing window.
     */
    public AbstractScreen(FoodWindow window) {
        this.window = window;
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

    @Override
    public List<Node> getNodes() {
        return this.nodes;
    }
}
