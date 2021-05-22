package food.view.screen;

import javafx.scene.Node;

import java.util.List;

/**
 * Interface representing the States FoodWindow can be in.
 */
public interface Screen {

    /**
     * Returns the nodes present in the Screen.
     * @return The list of nodes in the Screen.
     */
    List<Node> getNodes();
}
