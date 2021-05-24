package food.view.screen;

import food.view.FoodWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
    protected FoodWindow window;

    public AbstractScreen(FoodWindow window) {
        this.window = window;
        this.nodes = new ArrayList<>();
        setupNodes();
    }

    protected abstract void setupNodes();

    protected Text addText(String str, Font font, TextAlignment alignment, Color color, double x, double y) {
        Text text = new Text();
        text.setText(str);
        text.setTextAlignment(alignment);
        text.setFont(font);
        text.setLayoutX(x);
        text.setLayoutY(y);
        text.setFill(color);
        this.nodes.add(text);
        return text;
    }

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

    protected Label addLabel(String str, Font font, double x, double y) {
        Label label = new Label();
        label.setText(str);
        label.setFont(font);
        label.setLayoutX(x);
        label.setLayoutY(y);
        this.nodes.add(label);
        return label;
    }

    protected Button addButton(String text, double x, double y, double width, double height, EventHandler<ActionEvent> method) {
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

    protected TextArea addReadOnlyTextArea(String text, double x, double y, double width, double height, int rows) {
        TextArea area = new TextArea();
        area.setText(text);
        area.setLayoutX(x);
        area.setLayoutY(y);
        area.maxWidth(width);
        area.maxHeight(height);
        area.setPrefRowCount(rows);
        area.setEditable(false);
        this.nodes.add(area);
        return area;
    }

    @Override
    public List<Node> getNodes() {
        return this.nodes;
    }
}
