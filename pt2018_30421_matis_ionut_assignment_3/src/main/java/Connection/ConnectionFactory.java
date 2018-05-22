package Connection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class contains the name of the driver (initialized through reflection), the database
 * location (DBURL), and the user and the password for accessing the MySQL Server.
 * The class contains methods for creating a connection, getting an active connection and closing
 * a connection, a Statement or a ResultSet
 *
 * @author Ionut Matis, 30421
 */
public class ConnectionFactory {
    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/ordermanagement";
    private static final String USER = "root";
    private static final String PASS = "";

    /**
     * The connection to the DB will be placed in a Singleton object
     */
    private static ConnectionFactory singleInstance = new ConnectionFactory();

    private ConnectionFactory() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.WARNING, "Fail to load the class for connection.");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(DBURL, USER, PASS);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Failed to create the database connection.");
        }
        return con;
    }

    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Exception at connection close.");
            }
        }
    }

    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Exception at statement close.");
            }
        }
    }

    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Exception at resultSet close.");
            }
        }
    }

}
