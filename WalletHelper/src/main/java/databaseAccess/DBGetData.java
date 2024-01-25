package databaseAccess;

import userUtilities.GoalRecord;
import userUtilities.HistoryRecord;
import userUtilities.UserRecord;
import utilities.Enums.DataKey;
import utilities.Enums.TableKey;
import userUtilities.AccountRecord;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Optional;

// This class is made for reading data stored in the database, so for SELECT queries.
// Should NOT return ResultSet type, something different is needed to be done.
public abstract class DBGetData {

    private static final Logger logger = LogManager.getLogger(DBGetData.class);



    // It will be useful when logging in a user
    public static Optional<UserRecord[]> getUserRows(String username) {
        if (username.isEmpty())
            return Optional.empty();

        // SELECT * FROM users WHERE UserName = {username}
        String query = DBQuery.select(TableKey.USERS, DataKey.UserName, username);
        ResultSet result;
        ArrayList<UserRecord> records = new ArrayList<>();

        try (Statement statement = DBConnection.getConnector().createStatement()) {
            result = statement.executeQuery(query);

            while (result.next()) {
                String[] results = new String[6];
                for (int colIndex = 0; colIndex < 6; colIndex++) {
                    results[colIndex] = result.getString(colIndex + 1);
                }
                UserRecord record = new UserRecord(results);
                records.add(record);
            }
        } catch (SQLException e) {
            logger.error("Couldn't execute query `" + query + "`. Returning an empty optional.\n" +
                    "SQL state: " + e.getSQLState() + "\n" +
                    "SQLException message: " + e.getMessage() + "\n" +
                    "Stack trace: " + e.getStackTrace());
            return Optional.empty();
        }
        if (records.isEmpty())
            return Optional.empty();

        try {
            DBConnection.closeConnection();
        }
        catch (SQLException e) {
            logger.warn("getUserRows - closeConnection - couldn't close connection");
        }

        return Optional.of(records.toArray(new UserRecord[0]));
    }

    public static Optional<UserRecord[]> getUserRows(int userId) {
        if (userId < 0)
            return Optional.empty();

        // SELECT * FROM users WHERE UserID={userId}
        String query = DBQuery.select(TableKey.USERS, DataKey.UserID, String.valueOf(userId));
        ResultSet result;
        ArrayList<UserRecord> records = new ArrayList<>();

        try (Statement statement = DBConnection.getConnector().createStatement()) {
            result = statement.executeQuery(query);

            while (result.next()) {
                String[] results = new String[6];
                for (int colIndex = 0; colIndex < 6; colIndex++) {
                    results[colIndex] = result.getString(colIndex + 1);
                }
                UserRecord record = new UserRecord(results);
                records.add(record);
            }
        } catch (SQLException e) {
            logger.error("Couldn't execute query `" + query + "`. Returning an empty optional.\n" +
                    "SQL state: " + e.getSQLState() + "\n" +
                    "SQLException message: " + e.getMessage() + "\n" +
                    "Stack trace: " + e.getStackTrace());
            return Optional.empty();
        }

        try {
            DBConnection.closeConnection();
        }
        catch (SQLException e) {
            logger.warn("getUserRows - closeConnection - couldn't close connection");
        }

        return Optional.of(records.toArray(new UserRecord[0]));
    }

    public static Optional<AccountRecord[]> getAccountRows(int userID) {
        if (userID < 0)
            return Optional.empty();

        // SELECT * FROM accounts WHERE UserID={userID}
        String query = DBQuery.select(TableKey.ACCOUNTS, DataKey.UserID, String.valueOf(userID));
        ResultSet result;
        ArrayList<AccountRecord> records = new ArrayList<>();

        try (Statement statement = DBConnection.getConnector().createStatement()) {
            result = statement.executeQuery(query);

            while (result.next()) {

                // Gods forgive me for what I have done - I had to, The Nut must have been destroyed...
                String[] results = new String[6];

                for (int colIndex = 0; colIndex < 6; colIndex++) {
                    results[colIndex] = result.getString(colIndex + 1);
                }
                // kurwa... nie przyznaję się do tego gowna
                AccountRecord record = new AccountRecord(results[0], results[1], results[2], results[3], results[4], results[5]); // ?????
                records.add(record);
            }

        } catch (SQLException e) {
            logger.error("Couldn't execute query `" + query + "`. Returning an empty optional.\n" +
                    "SQL state: " + e.getSQLState() + "\n" +
                    "SQLException message: " + e.getMessage() + "\n" +
                    "Stack trace: " + e.getStackTrace());
            return Optional.empty();
        }

        try {
            DBConnection.closeConnection();
        }
        catch (SQLException e) {
            logger.warn("getAccountRows - closeConnection - couldn't close connection");
        }

        return Optional.of(records.toArray(new AccountRecord[]{}));
    }

    public static Optional<GoalRecord[]> getGoalRows(int userId) {
        if (userId < 0)
            return Optional.empty();

        // SELECT * FROM goals WHERE UserID={userId}
        String query = DBQuery.select(TableKey.GOALS, DataKey.UserID, String.valueOf(userId));
        ResultSet result;
        ArrayList<GoalRecord> records = new ArrayList<>();

        try (Statement statement = DBConnection.getConnector().createStatement()) {
            result = statement.executeQuery(query);

            while (result.next()) {
                String[] results = new String[8];
                for (int colIndex = 0; colIndex < 8; colIndex++) {
                    results[colIndex] = result.getString(colIndex + 1);
                }
                GoalRecord record = new GoalRecord(results);
                records.add(record);
            }
        } catch (SQLException e) {
            logger.error("Couldn't execute query `" + query + "`. Returning an empty optional.\n" +
                    "SQL state: " + e.getSQLState() + "\n" +
                    "SQLException message: " + e.getMessage() + "\n" +
                    "Stack trace: " + e.getStackTrace());
            return Optional.empty();
        }

        try {
            DBConnection.closeConnection();
        }
        catch (SQLException e) {
            logger.warn("getGoalRows - closeConnection - couldn't close connection");
        }

        return Optional.of(records.toArray(new GoalRecord[0]));
    }

    // getUserRecord - returns data from table Users corresponding to user
    public static Optional<String> getUserRecord(int userId, DataKey col) {

        if (userId < 0)
            return Optional.empty();

        String output = "";
        ResultSet result;

        // SELECT ? FROM table WHERE column = ?
        String query = DBQuery.select(TableKey.USERS, DataKey.UserID, String.valueOf(userId), col);
        try (Statement statement = DBConnection.getConnector().createStatement()) {
            result = statement.executeQuery(query);
            result.next();
            try {
                output = result.getString(1);
            } catch (SQLException e) {
                logger.error("Couldn't get value from the 1st column. Returning an empty optional.\n" +
                        "SQL state: " + e.getSQLState() + "\n" +
                        "SQLException message: " + e.getMessage() + "\n" +
                        "Stack trace: " + e.getStackTrace());
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.error("Couldn't execute query `" + query + "`. Returning an empty optional.\n" +
                    "SQL state: " + e.getSQLState() + "\n" +
                    "SQLException message: " + e.getMessage() + "\n" +
                    "Stack trace: " + e.getStackTrace());
            return Optional.empty();
        }

        try {
            DBConnection.closeConnection();
        }
        catch (SQLException e) {
            logger.warn("getUserRecord - closeConnection - couldn't close connection");
        }

        // At this point we have a result that contains the queried response
        return Optional.of(output);
    }

    // getAccountRecord
    public static Optional<String> getAccountRecord(int accId, DataKey col) {

        if (accId < 0)
            return Optional.empty();

        String output = "";
        ResultSet result;

        // SELECT ? FROM table WHERE column = ?
        String query = DBQuery.select(TableKey.ACCOUNTS, DataKey.AccID, String.valueOf(accId), col);
        try (Statement statement = DBConnection.getConnector().createStatement()) {
            result = statement.executeQuery(query);
            result.next();

            try {
                output = result.getString(1);
            } catch (SQLException e) {
                logger.error("Couldn't get value from the 1st column. Returning an empty optional.\n" +
                        "SQL state: " + e.getSQLState() + "\n" +
                        "SQLException message: " + e.getMessage() + "\n" +
                        "Stack trace: " + e.getStackTrace());
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.error("Couldn't execute query `" + query + "`. Returning an empty optional.\n" +
                    "SQL state: " + e.getSQLState() + "\n" +
                    "SQLException message: " + e.getMessage() + "\n" +
                    "Stack trace: " + e.getStackTrace());
            return Optional.empty();
        }

        try {
            DBConnection.closeConnection();
        }
        catch (SQLException e) {
            logger.warn("getAccountRecord - closeConnection - couldn't close connection");
        }

        // At this point we have a result that contains the queried response
        return Optional.of(output);
    }

    // getGoalRecord
    public static Optional<String> getGoalRecord(int goalId, DataKey col) {
        if (goalId < 0)
            return Optional.empty();

        String output = "";
        ResultSet result;

        // SELECT ? FROM table WHERE column = ?
        String query = DBQuery.select(TableKey.GOALS, DataKey.GoalID, String.valueOf(goalId), col);
        try (Statement statement = DBConnection.getConnector().createStatement()) {
            result = statement.executeQuery(query);
            result.next();

            try {
                output = result.getString(1);
            } catch (SQLException e) {
                logger.error("Couldn't get value from the 1st column. Returning an empty optional.\n" +
                        "SQL state: " + e.getSQLState() + "\n" +
                        "SQLException message: " + e.getMessage() + "\n" +
                        "Stack trace: " + e.getStackTrace());
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.error("Couldn't execute query `" + query + "`. Returning an empty optional.\n" +
                    "SQL state: " + e.getSQLState() + "\n" +
                    "SQLException message: " + e.getMessage() + "\n" +
                    "Stack trace: " + e.getStackTrace());
            return Optional.empty();
        }

        try {
            DBConnection.closeConnection();
        }
        catch (SQLException e) {
            logger.warn("getGoalRecord - closeConnection - couldn't close connection");
        }

        // At this point we have a result that contains the queried response
        return Optional.of(output);
    }

    // TODO Didn't check if it works - didn't care~
    public static Optional<HistoryRecord[]> getHistoryRows(int userID) {
        if (userID < 0)
            return Optional.empty();

        // SELECT * FROM history WHERE UserID={userID}
        String query = DBQuery.select(TableKey.HISTORY, DataKey.UserID, String.valueOf(userID));
        ResultSet result;
        ArrayList<HistoryRecord> records = new ArrayList<>();

        try (Statement statement = DBConnection.getConnector().createStatement()) {
            result = statement.executeQuery(query);

            while (result.next()) {

                // Carrying on my previous sins, this project is fucking hell
                // Yep, we *know* that it's eight
                String[] results = new String[8];

                for (int colIndex = 0; colIndex < 8; colIndex++) {
                    results[colIndex] = result.getString(colIndex + 1);
                }
                // kurwa... nie przyznaję się do tego gowna
                HistoryRecord record = new HistoryRecord(results); // ?????
                records.add(record);
            }

        } catch (SQLException e) {
            logger.error("Couldn't execute query `" + query + "`. Returning an empty optional.\n" +
                    "SQL state: " + e.getSQLState() + "\n" +
                    "SQLException message: " + e.getMessage() + "\n" +
                    "Stack trace: " + e.getStackTrace());
            return Optional.empty();
        }

        try {
            DBConnection.closeConnection();
        }
        catch (SQLException e) {
            logger.warn("getHistoryRows - closeConnection - couldn't close connection");
        }

        return Optional.of(records.toArray(new HistoryRecord[]{}));
    }
}
/*
SELECT UserID FROM users WHERE UserName='jakiesimie';

CREATE TABLE users (
    UserID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    UserName TEXT NOT NULL,
    Email TEXT,
    Phone TEXT,
    Password TEXT NOT NULL,
    mainAccount INT NOT NULL
);

CREATE TABLE accounts (
    AccID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    UserID INTEGER NOT NULL,
    Title TEXT NOT NULL,
    Val REAL NOT NULL,
    Currency TEXT NOT NULL,
    CreateTimeStamp DATE NOT NULL
);

CREATE TABLE goals (
    GoalID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    UserID INTEGER NOT NULL,
    Title TEXT NOT NULL,
    Val REAL NOT NULL,
    Currency TEXT NOT NULL,
    CreateTimeStamp DATETIME NOT NULL,
    Deadline TEXT NOT NULL
);

CREATE TABLE history (
    TransID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    UserID INTEGER NOT NULL,
    AccID INTEGER NOT NULL,
    ValBefore REAL NOT NULL,
    Change REAL NOT NULL,
    Currency TEXT NOT NULL,
    Title TEXT NOT NULL,
    TimeStamp DATETIME NOT NULL
);
 */