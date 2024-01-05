package databaseAccess;

import utilities.Enums.DataKey;
import utilities.Enums.TableKey;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

// This class is made for accessing any data from the database: it is a tunnel between user's
// input and queries made and sent to the database by app.

// Should be rewritten using PreparedStatement, for now it is left this way for simplicity - tbc.
public abstract class DBGetData {

    protected static ResultSet getUserSingleData(String UserID, DataKey column) throws SQLException {

        DBConnection.setConnection();
        Statement s = DBConnection.getConnector().createStatement();
        ResultSet r = s.executeQuery(DBQuery.select(TableKey.USERS, DataKey.UserID, UserID, column));

        return r;
    }

    protected static ResultSet getUserRecord(String UserID) throws SQLException {

        DBConnection.setConnection();
        Statement s = DBConnection.getConnector().createStatement();
        ResultSet r = s.executeQuery(DBQuery.select(TableKey.USERS, DataKey.UserID, UserID));

        return r;
    }

    protected static ResultSet getAccountSingleData(String AccID, DataKey column) throws SQLException {

        DBConnection.setConnection();
        Statement s = DBConnection.getConnector().createStatement();
        ResultSet r = s.executeQuery(DBQuery.select(TableKey.ACCOUNTS, DataKey.AccID, AccID, column));

        return r;
    }

    protected static ResultSet getAccountRecord(String AccID) throws SQLException {

        DBConnection.setConnection();
        Statement s = DBConnection.getConnector().createStatement();
        ResultSet r = s.executeQuery(DBQuery.select(TableKey.ACCOUNTS, DataKey.AccID, AccID));

        return r;
    }

    protected static ResultSet getManyAccountRecords(String UserID) throws SQLException {

        DBConnection.setConnection();
        Statement s = DBConnection.getConnector().createStatement();
        ResultSet r = s.executeQuery(DBQuery.select(TableKey.ACCOUNTS, DataKey.UserID, UserID));

        return r;
    }

    protected static ResultSet getGoalSingleData(String GoalID, DataKey column) throws SQLException {

        DBConnection.setConnection();
        Statement s = DBConnection.getConnector().createStatement();
        ResultSet r = s.executeQuery(DBQuery.select(TableKey.GOALS, DataKey.GoalID, GoalID, column));

        return r;
    }

    protected static ResultSet getGoalRecord(String GoalID) throws SQLException {

        DBConnection.setConnection();
        Statement s = DBConnection.getConnector().createStatement();
        ResultSet r = s.executeQuery(DBQuery.select(TableKey.GOALS, DataKey.GoalID, GoalID));

        return r;
    }

    protected static ResultSet getManyGoalRecords(String UserID) throws SQLException {

        DBConnection.setConnection();
        Statement s = DBConnection.getConnector().createStatement();
        ResultSet r = s.executeQuery(DBQuery.select(TableKey.GOALS, DataKey.UserID, UserID));

        return r;
    }

    protected static ResultSet getTransactionSingleData(String TransID, DataKey column) throws SQLException {

        DBConnection.setConnection();
        Statement s = DBConnection.getConnector().createStatement();
        ResultSet r = s.executeQuery(DBQuery.select(TableKey.HISTORY, DataKey.TransID, TransID, column));

        return r;
    }

    protected static ResultSet getTransactionRecord(String TransID) throws SQLException {

        DBConnection.setConnection();
        Statement s = DBConnection.getConnector().createStatement();
        ResultSet r = s.executeQuery(DBQuery.select(TableKey.HISTORY, DataKey.TransID, TransID));

        return r;
    }

    protected static ResultSet getManyTransactionRecords(DataKey key, String keyID) throws SQLException {

        DBConnection.setConnection();
        Statement s = DBConnection.getConnector().createStatement();
        ResultSet r = s.executeQuery(DBQuery.select(TableKey.HISTORY, key, keyID));

        return r;
    }
}
