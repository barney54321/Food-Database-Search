package food.view.screen;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class SearchScreen extends AbstractScreen {

    private TextField searchBar;
    private Button button;

    @Override
    protected void setupNodes() {
        addText("Food Database", Font.font(30), TextAlignment.LEFT, Color.BLACK, 400, 50);

        this.searchBar = addTextField(250, 80, 440, 10, "Search term");

        this.button = addButton("Search", 700, 80, 100, 10, event -> {
            System.out.println(this.searchBar.getText());
        });
    }
}
