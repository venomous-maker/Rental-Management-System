package HelperClasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHandler {

    private static final String DRIVER_NAME = ApplicationConstants.DRIVER_NAME;
    private static final String CONNECTION_STRING = ApplicationConstants.CONNECTION_STRING;
    private static final String USER_NAME = ApplicationConstants.USER_NAME;
    private static final String PASSWORD = ApplicationConstants.PASSWORD;

    // Initialize connection as null
    private static Connection con = null;

    static {
        try {
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            // Handle class not found exception
        }
    }

    public static synchronized Connection getConnection() throws SQLException {
        // Check if the connection is closed or null
        if (con == null || con.isClosed()) {
            // Create a new connection
            con = DriverManager.getConnection(CONNECTION_STRING, USER_NAME, PASSWORD);
        }
        return con;
    }

    // Close connection
    public static synchronized void closeConnection() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
    }
}
