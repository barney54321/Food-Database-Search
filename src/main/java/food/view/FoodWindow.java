package food.view;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/**
 * Manages the Window used by the GUI.
 */
public class FoodWindow {

    /**
     * The width of the window.
     */
    public static final int WIDTH = 1000;

    /**
     * The height of the window.
     */
    public static final int HEIGHT = 500;

    /**
     * The JavaFX Scene for the Window.
     */
    private Scene scene;

    /**
     * The Pane nested in the Window.
     */
    private Pane pane;

    public FoodWindow() {
        this.pane = new Pane();
        this.scene = new Scene(pane, WIDTH, HEIGHT);
    }

    public Scene getScene() {
        return this.scene;
    }
}
