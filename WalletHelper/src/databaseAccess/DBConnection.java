package databaseAccess;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;

// This class is made for database connection management: it opens and closes the connection
// with database and holds its current status (whether it is set or not), which is
// essential for any querying.
public class DBConnection {

    protected static void setConnection(String username, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("setConnection() - no Driver: " + e.getMessage());
            // log4j instructions
            return;
        }

        String databaseUrl = "jdbc:mysql//localhost:3306/wallethelper_example";

        try {
            mainConnector = DriverManager.getConnection(databaseUrl, username, password);
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("setConnection() - connection error: " + e.getMessage());
            // log4j instructions
            return;
        }

        isSet = true;
    }

    protected static void closeConnection() {
        try {
            mainConnector.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("closeConnection(): " + e.getMessage());
            // log4j instructions
            return;
        }

        isSet = false;
    }

    protected static boolean isConnected() {
        return isSet;
    }

    private static Connection mainConnector;
    private static boolean isSet;
}
