package databaseAccess;

import java.sql.*;

public class databaseConnection {

    protected static void setConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            // log4j instructions
            return;
        }

        String databaseUrl = "jdbc:mysql//localhost:3306/wallethelper_example";
        String username = "example";
        String password = "apppassword";

        mainConnector = DriverManager.getConnection(databaseUrl, username, password);
    }

    protected static void closeConnection() {
        try {
            mainConnector.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            // log4j instructions
            return;
        }
    }

    private static Connection mainConnector;
}
