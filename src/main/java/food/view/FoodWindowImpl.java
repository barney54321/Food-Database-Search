package food.view;

import food.controller.Controller;
import food.view.screen.Screen;
import food.view.screen.SearchScreen;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/**
 * Manages the Window used by the GUI.
 */
public class FoodWindowImpl implements FoodWindow {

    /**
     * The width of the window.
     */
    public static final int WIDTH = 600;

    /**
     * The height of the window.
     */
    public static final int HEIGHT = 500;

    /**
     * The JavaFX Scene for the Window.
     */
    private final Scene scene;

    /**
     * The Pane nested in the Window.
     */
    private final Pane pane;

    /**
     * The Screen object for the window (uses State pattern).
     */
    private Screen screen;

    /**
     * The Controller used to interact with the Model.
     */
    private final Controller controller;

    /**
     * Creates a FoodWindow object.
     *
     * @param controller The controller all logic is handled by.
     */
    public FoodWindowImpl(Controller controller) {
        this.controller = controller;

        this.pane = new Pane();
        this.scene = new Scene(pane, WIDTH, HEIGHT);

        this.setScreen(new SearchScreen(controller));
    }

    @Override
    public Scene getScene() {
        return this.scene;
    }

    @Override
    public Controller getController() {
        return this.controller;
    }

    @Override
    public void setScreen(Screen screen) {
        if (this.screen != null) {
            this.pane.getChildren().removeAll(this.screen.getNodes());
        }

        this.screen = screen;
        this.pane.getChildren().addAll(this.screen.getNodes());
    }

    @Override
    public void refresh() {
        this.pane.getChildren().clear();
        this.pane.getChildren().addAll(this.screen.getNodes());
    }
}
