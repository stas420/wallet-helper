package databaseAccess;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;

//import java.sql.Statement;
//import java.sql.ResultSet;

// This class is made for database connection management: it opens and closes the connection
// with database and holds its current status (whether it is set or not), which is
// essential for any querying.
// It should stay abstract for now.
public abstract class DBConnection {

    protected static void setConnection(/*String username, String password*/) throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("setConnection() - no Driver: " + e.getMessage());
            // log4j instructions
            return;
        }

        String databaseUrl = "jdbc:mysql://localhost:3306/wallethelper_example";
        String username = "app";
        String password = "1app2Password3";

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
    private static boolean isSet = false;

    /*
    // Exemplary reaching database
    public static void main(String[] args) {
        try {
            setConnection();
            Statement st = getConnector().createStatement();
            ResultSet res = st.executeQuery("SELECT * FROM Users WHERE UserID='2'");
            res.next();
            System.out.println("result: " + res.getString("Email"));
            closeConnection();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("Ended correctly!");
    }*/
}
