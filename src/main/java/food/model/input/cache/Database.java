package food.model.input.cache;

/**
 * Defines behaviour for the SQLite based cache.
 */
public interface Database {

    /**
     * Attempts to connect to Food database and create Search and Nutrition tables.
     * @throws IllegalStateException If the connection cannot be formed.
     */
    void setup() throws IllegalStateException;

    /**
     * Closes the Database connection.
     */
    void close();
}
