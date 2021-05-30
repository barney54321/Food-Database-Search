package food.model.input.cache;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Defines behaviour for the SQLite based cache.
 */
public interface Database {

    /**
     * Attempts to connect to Food database and create Search and Nutrition tables.
     *
     * @throws IllegalStateException If the connection cannot be formed.
     */
    void setup() throws IllegalStateException;

    /**
     * Updates the database with the provided string.
     *
     * @param update The update to run.
     * @throws SQLException If there are any exceptions raised.
     */
    void executeUpdate(String update) throws SQLException;

    /**
     * Runs the provided query through the database.
     *
     * @param query The query to call.
     * @return The resulting set.
     * @throws SQLException If there is an exception raised while attempting to query.
     */
    ResultSet executeQuery(String query) throws SQLException;

    /**
     * Closes the Database connection.
     */
    void close();
}
