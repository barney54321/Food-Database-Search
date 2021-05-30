package food.view.observers;

/**
 * Base Observer type that is notified of any Exceptions thrown.
 */
public interface BaseObserver {

    /**
     * Updates the observer based on the provided exception.
     *
     * @param exception The exception thrown.
     */
    void update(Exception exception);
}
