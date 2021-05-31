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
     * Updates the database with the provided parameterised string and array of objects.
     * @param update The parameterised update string.
     * @param params The objects to use within the update.
     * @throws SQLException If there are any exceptions raised while updating the database.
     */
    void executeUpdate(String update, String[] params) throws SQLException;

    /**
     * Runs the provided parameterised query through the database.
     * @param query The parameterised query string.
     * @param params The objects to use within the query.
     * @return The resulting set.
     * @throws SQLException If there are any exceptions raised while querying the database.
     */
    ResultSet executeQuery(String query, String[] params) throws SQLException;

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
