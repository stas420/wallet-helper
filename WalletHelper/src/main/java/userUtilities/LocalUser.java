package userUtilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.sql.SQLException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import databaseAccess.DBGetData;
import databaseAccess.DBManageData;
import utilities.Enums;
import utilities.Enums.DataKey;

import static utilities.stringUtils.getEpochTimeStamp;
import static utilities.stringUtils.isCredentialValid;

// access definers should be reviewed and probably changed, you silly-nutty faggot ;p
public class LocalUser {

    public LocalUser (String username, String password) {
        pullUserFromDB(username, password);

        if (!this.isLoggedIn) {
            // do something really, REALLY scary
        }

        pullAllAccountsFromDB();
        pullAllGoalsFromDB();
        pullAllHistoryFromDB();
    }

    private void pullUserFromDB(String username, String password) {

        if (this.isLoggedIn) {
            logger.error("in pullUserFromDB - someone is already logged in; log out firstly");
            return;
        }

        if (!isCredentialValid(username)) {
            logger.error("in pullUserFromDB - invalid username: " + username);
            return;
        }

        Optional<UserRecord[]> userRecords = DBGetData.getUserRows(username);

        if (userRecords.isEmpty()) {
            logger.error("DBGetData.getUserRows(" + username + ") returned an empty optional.\n" +
                    "Passed username: " + username);
            // do something scary ;3
            // (UwU 👉👈)
            return;
        }

        // Check if password is correct  V IMPORTANT! (this pleases the Java gods)
        Optional<UserRecord> goodUser /* pat pat */ = Arrays.stream(userRecords.get())  // Unpack users from Optional and turn to stream
                .filter(user -> user.Password.equals(password)) // Filter if password is correct
                .findFirst();

        // Check if any matched
        if (goodUser.isEmpty()) {
            logger.warn("None of the users from userRecords have matching passwords.\n" +
                    "Searched password: " + password);
            return;
        }
        this.userInfo = goodUser.get();
        this.isLoggedIn = true;
    }

    private void pushUserToDB() {

        DataKey[] columns = {DataKey.UserName, DataKey.Email, DataKey.Phone, DataKey.Password, DataKey.mainAccount};
        String[] values = {userInfo.UserName, userInfo.Email, userInfo.Phone, userInfo.Password, String.valueOf(userInfo.mainAccount)};

        try {
            DBManageData.updateRow(Enums.TableKey.USERS, DataKey.UserID, String.valueOf(userInfo.UserID), columns, values);
        }
        catch (SQLException e) {
            logger.error("Couldn't push user " + userInfo.UserName + "to DB.\n" +
                    "SQLException message: " + e.getMessage() + "\n" +
                    "SQL state: " + e.getSQLState() + "\n" +
                    "Stack trace: " + e.getStackTrace());
        }
    }

    public static Optional<LocalUser> registerNewUser(String username, String password, String email, String phone,
                                     String currency, String accountTitle) {

        Optional<UserRecord[]> userRecords = DBGetData.getUserRows(username);

        if (userRecords.isPresent()) {
            System.out.println(userRecords.get().length);
            logger.warn("User with this username already exists.\n" +
                    "Username in question: " + username);
            return Optional.empty();
        }

        try {
            String[] values = {username, email, phone, password, String.valueOf(1)};
            //                                                   ^^^^^^^^^^^^^^^^^
            //                                                   Only temporary value, remember to change later
            DataKey[] columns = {DataKey.UserName, DataKey.Email, DataKey.Phone, DataKey.Password, DataKey.mainAccount};

            DBManageData.insertRow(Enums.TableKey.USERS, columns, values);
        } catch (SQLException e) {
            logger.error("Couldn't push user " + username + "to DB.\n" +
                    "SQLException message: " + e.getMessage() + "\n" +
                    "SQL state: " + e.getSQLState() + "\n" +
                    "Stack trace: " + e.getStackTrace());
            return Optional.empty();
        }

        LocalUser user = new LocalUser(username, password);

        try {
            Date date = new Date();
            String[] values = { String.valueOf(user.userInfo.UserID), accountTitle, String.valueOf(0), currency,
                    String.valueOf(utilities.stringUtils.getEpochTimeStamp(date))};

            DataKey[] columns = { DataKey.UserID, DataKey.Title, DataKey.Val, DataKey.Currency,
                    DataKey.CreateTimeStamp };

            DBManageData.insertRow(Enums.TableKey.ACCOUNTS, columns, values);
            AccountRecord accountRecord = DBGetData.getAccountRows(user.userInfo.UserID).get()[0];

            user.accountsInfo.add(accountRecord);

            user.userInfo.mainAccount = user.accountsInfo.get(0).AccID;

            // UPDATE users SET mainAccount = {user.accountsInfo.get(0)} where userId = {user.userInfo.UserID}
            DBManageData.updateSingleData(Enums.TableKey.USERS, DataKey.UserID, String.valueOf(user.userInfo.UserID),
                                            DataKey.mainAccount, String.valueOf(user.accountsInfo.get(0).AccID));
        } catch (SQLException e) {
            logger.error("Couldn't update user's " + user.userInfo.UserName + "main account in DB.\n" +
                    "SQLException message: " + e.getMessage() + "\n" +
                    "SQL state: " + e.getSQLState() + "\n" +
                    "Stack trace: " + e.getStackTrace());
            return Optional.empty();
        }

        return Optional.of(user);
    }

    // TODO how the fuck
    // public void updateUserInDB()

    public static void deleteUserFromDB() {
        // 1. delete everything else they have: accs, goals, histories
        // 2. delete them also
        // 3. happy :)
    }

    private void pullAllAccountsFromDB() {

        Optional<AccountRecord[]> userAccounts = DBGetData.getAccountRows(this.userInfo.UserID);

        if (userAccounts.isEmpty()) {
            logger.error("LocalUser::pullAllAccountsFromDB - empty userAccounts[] for UID: " + this.userInfo.UserID);
            return;
        }

        // implement fucking assigning userAccount to this.accountInfo, fuck you Optionals
        // ...
    }

    // TODO Needs test!
    private void pushNewAccountToDB(String title, String val, String currency) {

        // idk if it's enough
        if (!isCredentialValid(title) || !isCredentialValid(currency)) {
            logger.error("pushNewAccountToDB - credential error - title: " + title + " | currency:  " + currency);
            return;
        }

        Date date = new Date();
        DataKey[] columns = {DataKey.UserID, DataKey.Title, DataKey.Val, DataKey.Currency, DataKey.CreateTimeStamp};
        String[] values = {String.valueOf(this.userInfo.UserID), title, val, currency, String.valueOf(getEpochTimeStamp(date))};

        try {
            DBManageData.insertRow(Enums.TableKey.ACCOUNTS, columns, values);
        }
        catch (SQLException e) {
            logger.error("Couldn't push this user's -> " + userInfo.UserName + " new account to DB.\n" +
                    "Info meant to be inserted: "+ title + ", " + val + ", " + currency + "\n" +
                    "SQLException message: " + e.getMessage() + "\n" +
                    "SQL state: " + e.getSQLState() + "\n" +
                    "Stack trace: " + e.getStackTrace());
        }
    }

    // TODO: how the fuck
    //private void updateAccountInDB()

    public void deleteAccountFromDB(int... accID) {

        for (int i : accID) {

            try {
                    DBManageData.deleteRow(Enums.TableKey.ACCOUNTS, DataKey.AccID, String.valueOf(i));
            }
            catch (SQLException e) {
                logger.error("deleteAccountFromDB - error while removing acc of ID:" + i
                            + "\n SQLException message: " + e.getMessage()
                            + "\n SQL state: " + e.getSQLState()
                            + "\n stack trace: " + e.getStackTrace());
                continue;
            }
            // remove from local data somehow...
        }
    }

    public void pullAllGoalsFromDB() {

        Optional<GoalRecord[]> userGoals = DBGetData.getGoalRows(this.userInfo.UserID);

        if (userGoals.isEmpty()) {
            logger.error("LocalUser::pullAllGoalsFromDB - empty userGoals[] for UID: " + this.userInfo.UserID);
            return;
        }

        // implement fucking assigning userGoals to this.accountInfo, fuck you Optionals
        // ...
    }

    public void pushNewGoalToDB(String title, String value, String Goal, String Currency, String timeStamp, String deadline) {

    }

    public void deleteGoalFromDB(int... goalID) {

    }

    public void pullAllHistoryFromDB() {

        // to be written
        Optional<HistoryRecord[]> userHistory = DBGetData.getHistoryRows(this.userInfo.UserID);

        if (userHistory.isEmpty()) {
            logger.error("LocalUser::pullAllHistoryFromDB - empty userHistory[] for UID: " + this.userInfo.UserID);
            return;
        }

        // implement fucking assigning userHistory to this.accountInfo, fuck you Optionals
        // ...
    }

    public void pushNewHistoryToDB() {

    }

    public void deleteHistoryFromDB(int... transID) {

    }

    private final static Logger logger = LogManager.getLogger(LocalUser.class);

    private boolean isLoggedIn = false;
    private UserRecord userInfo;
    private ArrayList<AccountRecord> accountsInfo = new ArrayList<>();
    private ArrayList<GoalRecord> goalsInfo = new ArrayList<>();
    private ArrayList<HistoryRecord> historyInfo = new ArrayList<>();


    // ===== main =====

    public static void main(String[] args) {

        Optional<LocalUser> user = registerNewUser("abecaduo", "adfbcdsb", "dupaa@roxa.pl", "6942013", "huj", "iiiii");
        if (user.isEmpty()) {
            System.out.println("zesrales sie");
            return;
        }
        user.get().userInfo.UserName = "huj ci w dupe";
        user.get().pushUserToDB();

    }
}