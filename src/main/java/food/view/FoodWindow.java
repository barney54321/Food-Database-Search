package food.view;

import food.controller.Controller;
import food.view.screen.Screen;
import javafx.scene.Scene;

/**
 * Interface representing a Window.
 */
public interface FoodWindow {

    /**
     * Returns the Window's scene.
     *
     * @return The current scene.
     */
    Scene getScene();

    /**
     * Updates the Screen.
     *
     * @param screen The new Screen.
     */
    void setScreen(Screen screen);

    /**
     * Refreshes the Window when nodes are added/removed.
     */
    void refresh();
}
