package databaseAccess;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;

// This class is made for database connection management: it opens and closes the connection
// with database and holds its current status (whether it is set or not).
public abstract class DBConnection {

    protected static void setConnection(/*String user, String pass*/) throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("setConnection - no Driver: " + e.getMessage());
            // log4j instructions
            return;
        }

        /*
        username = user;
        password = pass;
         */

        mainConnector = DriverManager.getConnection(databaseUrl, username, password);

        isSet = true;
    }

    protected static void closeConnection() throws SQLException {

        mainConnector.close();
        isSet = false;
    }

    protected static boolean isConnected() {
        return isSet;
    }

    protected static Connection getConnector() {
        return mainConnector;
    }

    private static Connection mainConnector;
    private static final String databaseUrl = "jdbc:mysql://localhost:3306/wallethelper_example";
    private static String username = "app";
    private static String password = "1app2Password3";
    private static boolean isSet = false;
}
