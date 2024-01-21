package databaseAccess;

import userUtilities.GoalRecord;
import userUtilities.UserRecord;
import utilities.Enums.DataKey;
import utilities.Enums.TableKey;
import userUtilities.AccountRecord;

import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Optional;

// This class is made for reading data stored in the database, so for SELECT queries.
// Should NOT return ResultSet type, something different is needed to be done.
public abstract class DBGetData {

    /*
    TODO:
        - [x] getUserRows
        - [x] getAccountRows
        - [x]  getGoalRows
        - [ ] getTransactionRows
        - [ ] getHistoryRows
     */

    // It will be useful when logging in a user
    public static Optional<UserRecord[]> getUserRows(String username) {
        if (username.isEmpty())
            return Optional.empty();

        // SELECT * FROM users WHERE UserName = {username}
        String query = DBQuery.select(TableKey.USERS, DataKey.UserName, username);
        ResultSet result;
        ArrayList<UserRecord> records = new ArrayList<>();

        try(Statement statement = DBConnection.getConnector().createStatement()) {
            result = statement.executeQuery(query);

            while(result.next()) {
                String[] results = new String[6];
                for (int colIndex = 0; colIndex < 6; colIndex++) {
                    results[colIndex] = result.getString(colIndex + 1);
                }
                UserRecord record = new UserRecord(results);
                records.add(record);
            }
        }
        catch (Exception e) {
            // TODO log4j
            e.printStackTrace();
            return Optional.empty();
        }
        if (records.isEmpty())
            return Optional.empty();
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

            while(result.next()) {
                String[] results = new String[6];
                for (int colIndex = 0; colIndex < 6; colIndex++) {
                    results[colIndex] = result.getString(colIndex + 1);
                }
                UserRecord record = new UserRecord(results);
                records.add(record);
            }
        }
        catch (Exception e) {
            // TODO log4j
            e.printStackTrace();
            return Optional.empty();
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

            while(result.next()) {

                // Gods forgive me for what I have done - I had to, The Nut must have been destroyed...
                String[] results = new String[6];

                for (int colIndex = 0; colIndex < 6; colIndex++) {
                    results[colIndex] = result.getString(colIndex+1);
                }
                // kurwa... nie przyznaję się do tego gowna
                AccountRecord record = new AccountRecord(results[0], results[1], results[2], results[3], results[4], results[5]); // ?????
                records.add(record);
            }

        } catch (Exception e) {
            // TODO log4j
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.of(records.toArray(new AccountRecord[]{}));
    }
    protected static Optional<GoalRecord[]> getGoalRows(int userId) {
        if (userId < 0)
            return Optional.empty();

        // SELECT * FROM goals WHERE UserID={userId}
        String query = DBQuery.select(TableKey.GOALS, DataKey.UserID, String.valueOf(userId));
        ResultSet result;
        ArrayList<GoalRecord> records = new ArrayList<>();

        try (Statement statement = DBConnection.getConnector().createStatement()) {
            result = statement.executeQuery(query);

            while(result.next()) {
                String[] results = new String[7];
                for (int colIndex = 0; colIndex < 7; colIndex++) {
                    results[colIndex] = result.getString(colIndex + 1);
                }
                GoalRecord record = new GoalRecord(results);
                records.add(record);
            }
        }
        catch (Exception e) {
            // TODO log4j
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.of(records.toArray(new GoalRecord[0]));
    }

    // getUserRecord - returns data from table Users corresponding to user
    protected static Optional<String> getUserRecord(int userId, DataKey col){

        if (userId < 0)
            return Optional.empty();

        String output = "";
        ResultSet result;

        // SELECT ? FROM table WHERE column = ?
        try(Statement statement = DBConnection.getConnector().createStatement()) {
            result = statement.executeQuery(DBQuery.select(TableKey.USERS, DataKey.UserID, String.valueOf(userId), col));
            result.next();
            try {
                output = result.getString(1);
            } catch (Exception e) {
                // TODO log4j
                System.err.println("Couldn't get output");
                e.printStackTrace();
                return Optional.empty();
            }
        } catch(Exception e) {
            // TODO log4j
            System.err.println("Couldn't prepare statement");
            return Optional.empty();
        }

        // At this point we have a result that contains the queried response
        return Optional.of(output);
    }

    // getAccountRecord
    protected static Optional<String> getAccountRecord(int accId, DataKey col){

        if (accId < 0)
            return Optional.empty();

        String output = "";
        ResultSet result;

        // SELECT ? FROM table WHERE column = ?
        try(Statement statement = DBConnection.getConnector().createStatement()) {
            result = statement.executeQuery(DBQuery.select(TableKey.ACCOUNTS, DataKey.AccID, String.valueOf(accId), col));
            result.next();

            try {
                output = result.getString(1);
            } catch (Exception e) {
                // TODO log4j
                System.err.println("Couldn't get output");
                e.printStackTrace();
                return Optional.empty();
            }
        } catch(Exception e) {
            // TODO log4j
            System.err.println("Couldn't prepare statement");
            return Optional.empty();
        }

        // At this point we have a result that contains the queried response
        return Optional.of(output);
    }



    // getGoalRecord
    protected static Optional<String> getGoalRecord(int goalId, DataKey col){

        if (goalId < 0)
            return Optional.empty();

        String output = "";
        ResultSet result;

        // SELECT ? FROM table WHERE column = ?
        try(Statement statement = DBConnection.getConnector().createStatement()) {
            result = statement.executeQuery(DBQuery.select(TableKey.GOALS, DataKey.GoalID, String.valueOf(goalId), col));
            result.next();

            try {
                output = result.getString(1);
            } catch (Exception e) {
                // TODO log4j
                System.err.println("Couldn't get output");
                e.printStackTrace();
                return Optional.empty();
            }
        } catch(Exception e) {
            // TODO log4j
            System.err.println("Couldn't prepare statement");
            return Optional.empty();
        }

        // At this point we have a result that contains the queried response
        return Optional.of(output);
    }

    // getManyGoalRecords
    // getTransactionRecord
    protected static Optional<String> getTransactionRecord(int transId, DataKey col){

        if (transId < 0)
            return Optional.empty();

        String output = "";
        ResultSet result;

        // SELECT ? FROM table WHERE column = ?
        try(Statement statement = DBConnection.getConnector().createStatement()) {
            result = statement.executeQuery(DBQuery.select(TableKey.HISTORY, DataKey.TransID, String.valueOf(transId), col));
            result.next();

            try {
                output = result.getString(1);
            } catch (Exception e) {
                // TODO log4j
                System.err.println("Couldn't get output");
                e.printStackTrace();
                return Optional.empty();
            }
        } catch(Exception e) {
            // TODO log4j
            System.err.println("Couldn't prepare statement");
            return Optional.empty();
        }

        // At this point we have a result that contains the queried response
        return Optional.of(output);
    }

    // getManyTransactionRecords

    /*
    public static void main(String[] args) throws SQLException {
        DataKey[] tab1 = { DataKey.UserID, DataKey.Title, DataKey.Val, DataKey.Currency, DataKey.CreateTimeStamp };
        String[] vals1 = { "2", "ugabfdvga", "213.37", "gbp", "1705610700"};
        String[] vals2 = {"2", "hafwuaiwf", "3617.89", "pln", "1705610800"};
        DBManageData.insertRow(TableKey.ACCOUNTS, tab1, vals1);
        DBManageData.insertRow(TableKey.ACCOUNTS, tab1, vals2);


        AccountRecord[] accountRecords = getAccountRows(2).get();
        for (AccountRecord account : accountRecords) {
            System.out.println(account.toString());
        }
    } */
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