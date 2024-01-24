package databaseAccess;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
// This class is made for database connection management: it opens and closes the connection
// with database and holds its current status (whether it is set or not).
public abstract class DBConnection {

    private static final String DATABASE_URL = "jdbc:sqlite:./database.db";
    private static final Logger logger = LogManager.getLogger(DBConnection.class);

    public static void setConnection(/*String user, String pass*/){
        try {
            // Establish a connection to the sqlite db
            Connection connection = DriverManager.getConnection(DATABASE_URL);
            isSet = true;
            mainConnector = connection;
        }
        catch(SQLException e) {
            logger.fatal("Couldn't establish a connection with the database.\n" +
                    "SQLException message: " + e.getMessage() + "\n" +
                    "SQL state: " + e.getSQLState() + "\n" +
                    "Stack trace: " + e.getStackTrace() + "\n");
        }
    }

    protected static void closeConnection() throws SQLException {

        mainConnector.close();
        isSet = false;
    }

    protected static boolean isConnected() {
        return isSet;
    }

    protected static Connection getConnector() {
        while(!DBConnection.isConnected())
            DBConnection.setConnection();

        return mainConnector;
    }

    private static Connection mainConnector;
    private static String username = "app";
    private static String password = "1app2Password3";
    private static boolean isSet = false;

    /*
    public static void main(String[] args) {
        setConnection();

    }*/
}

