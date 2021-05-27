package food.model.input.cache;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Inspired by https://github.com/xerial/sqlite-jdbc#usage
 */
public class DatabaseImpl implements Database {

    /**
     * The connection to the database.
     */
    private Connection connection;

    /**
     * Object used for executing statements.
     */
    private Statement statement;

    @Override
    public void setup() throws IllegalStateException {
        String launchPath = System.getProperty("user.dir");
        String databasePath = "jdbc:sqlite:" + launchPath + "/food.db";

        try {
            connection = DriverManager.getConnection(databasePath);
            statement = connection.createStatement();

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

            statement.executeUpdate(createSearch);
            statement.executeUpdate(createNutrition);

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
