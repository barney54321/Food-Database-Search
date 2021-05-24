package food.view.observers;

/**
 * The interface defining behaviours for observers of Messages (e.g. NutritionScreen).
 */
public interface MessageObserver extends BaseObserver {

    /**
     * Updates the observer based on the outcome of the message.
     * @param messageSuccess Whether the message was successfully sent.
     */
    void update(boolean messageSuccess);
}
