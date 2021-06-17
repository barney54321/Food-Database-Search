package food.view.screen;

import food.controller.Controller;
import food.view.observers.BaseObserver;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

/**
 * The Screen in which users enter the maximum calorie amount.
 * Implements BaseObserver as error alerts may need to be shown in case of bad input.
 * Note that input should not be malformatted because of how the input is read in, but stress testing
 * exhibited an error with the JavaFX listener that was able to break the system, hence the need for a
 * backup check.
 */
public class MaxCalorieScreen extends AbstractScreen implements BaseObserver {

    /**
     * The TextField the user enters
     */
    private TextField calorieField;

    /**
     * Creates a new Max Calorie Screen object.
     *
     * @param controller The encompassing window's controller.
     */
    public MaxCalorieScreen(Controller controller) {
        super(controller);
    }

    @Override
    protected void setupNodes() {
        addText("Food Database", Font.font(30), 200, 40);

        addText("Maximum number of calories", Font.font(15), 200, 190);

        this.calorieField = addTextField(195, 200, 100, 20, "Maximum calories");
        this.calorieField.setAlignment(Pos.BASELINE_CENTER);

        // Only allow numbers to be entered
        // Note that addListener accepts ChangeListener, which is a functional interface
        // that is called whenever the textfield value is changed
        this.calorieField.textProperty().addListener(((observable, oldValue, newValue) -> {
            // If field is empty, do nothing
            if (newValue.length() == 0) {
                return;
            }

            // Check if field can be turned into a number within range (1:1000)
            try {
                int calories = Integer.parseInt(newValue);

                if (calories < 1 || calories > 1000) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                // If invalid input, don't change the field
                this.calorieField.setText(oldValue);
            }
        }));

        addButton("Enter", 305, 200, 100, 10, event -> {

        });
    }

    @Override
    public void update(Exception exception) {
        Alert alert = new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK);
        alert.show();
    }
}
