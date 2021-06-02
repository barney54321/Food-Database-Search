package food.model.input.cache;

import java.sql.*;

/**
 * Inspired by https://github.com/xerial/sqlite-jdbc#usage
 */
public class DatabaseImpl implements Database {

    /**
     * The connection to the database.
     */
    private Connection connection;

    @Override
    public void setup() throws IllegalStateException {
        String launchPath = System.getProperty("user.dir");
        String databasePath = "jdbc:sqlite:" + launchPath + "/food.db";

        try {
            connection = DriverManager.getConnection(databasePath);

            // Create tables if not present
            String createSearch = "CREATE TABLE IF NOT EXISTS Search (\n" +
                    "    term TEXT PRIMARY KEY,\n" +
                    "    response TEXT\n" +
                    ")";

            String createNutrition = "CREATE TABLE IF NOT EXISTS Nutrition (\n" +
                    "    food TEXT NOT NULL,\n" +
                    "    measure TEXT NOT NULL,\n" +
                    "    response TEXT,\n" +
                    "    PRIMARY KEY(food, measure)\n" +
                    ")";

            connection.createStatement().executeUpdate(createSearch);
            connection.createStatement().executeUpdate(createNutrition);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void executeUpdate(String update) throws SQLException {
        if (connection == null) {
            throw new SQLException("Null database");
        }

        connection.createStatement().executeUpdate(update);
    }

    @Override
    public void executeUpdate(String update, String[] params) throws SQLException {
        if (connection == null) {
            throw new SQLException("Null database");
        }

        PreparedStatement statement = connection.prepareStatement(update);

        for (int i = 0; i < params.length; i++) {
            statement.setObject(i + 1, params[i]);
        }

        statement.executeUpdate();
    }

    @Override
    public ResultSet executeQuery(String query, String[] params) throws SQLException {
        if (connection == null) {
            throw new SQLException("Null database");
        }

        PreparedStatement statement = connection.prepareStatement(query);

        for (int i = 0; i < params.length; i++) {
            statement.setObject(i + 1, params[i]);
        }

        return statement.executeQuery();
    }

    @Override
    public ResultSet executeQuery(String query) throws SQLException {
        if (connection == null) {
            throw new SQLException("Null database");
        }

        return connection.createStatement().executeQuery(query);
    }

    @Override
    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
