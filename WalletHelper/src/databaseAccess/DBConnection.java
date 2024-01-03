package databaseAccess;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;

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

    protected static Connection getConnection () {
        return mainConnector;
    }

    private static Connection mainConnector;
    private static boolean isSet;
}
