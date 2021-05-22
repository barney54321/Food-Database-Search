package food.view;

import food.view.screen.Screen;
import food.view.screen.SearchScreen;
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

    /**
     * The Screen object for the window (uses State pattern).
     */
    private Screen screen;

    public FoodWindow() {
        this.pane = new Pane();
        this.scene = new Scene(pane, WIDTH, HEIGHT);

        this.setScreen(new SearchScreen());
    }

    public Scene getScene() {
        return this.scene;
    }

    public void setScreen(Screen screen) {
        if (this.screen != null) {
            this.pane.getChildren().removeAll(this.screen.getNodes());
        }

        this.screen = screen;
        this.pane.getChildren().addAll(this.screen.getNodes());
    }
}
