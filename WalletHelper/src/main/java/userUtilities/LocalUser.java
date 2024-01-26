package userUtilities;

import java.util.*;
import java.sql.SQLException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import databaseAccess.DBGetData;
import databaseAccess.DBManageData;
import utilities.Enums;
import utilities.Enums.DataKey;
import utilities.Enums.TableKey;

import static utilities.stringUtils.getEpochTimeStamp;
import static utilities.stringUtils.isCredentialValid;

// access definers should be reviewed and probably changed, you silly-nutty faggot ;p
public class LocalUser {

    // Tested 24/01/24 00:13
    public static Optional<LocalUser> logIn (String username, String password) {

        LocalUser localUser = new LocalUser();
        pullUserResult result = localUser.pullUserFromDB(username, password);

        if (result != pullUserResult.OK) {
            logger.warn("logIn - invalid credentials, empty user");
            return Optional.empty();
        }

        localUser.pullAllAccountsFromDB();
        localUser.pullAllGoalsFromDB();
        localUser.pullAllHistoryFromDB();

        logger.info("New user logged in.\n" +
                "User ID: " + localUser.userInfo.UserID + "\n" +
                "Username: " + localUser.userInfo.UserName + "\n" +
                "Accounts List size: " + localUser.accountsInfo.size() + "\n" +
                "Goals List size: " + localUser.goalsInfo.size() + "\n" +
                "History List size: " + localUser.historyInfo.size());

        return Optional.of(localUser);
    }

    // Tested via logIn() 24/01/24 00:13
    private enum pullUserResult {
        INVALID_USERNAME,
        INVALID_CRED,
        NO_SUCH_USER,
        OK
    }
    public pullUserResult pullUserFromDB(String username, String password) {

        if (!isCredentialValid(username)) {
            logger.error("in pullUserFromDB - invalid username: " + username);
            return pullUserResult.INVALID_USERNAME;
        }

        Optional<UserRecord[]> userRecords = DBGetData.getUserRows(username);

        if (userRecords.isEmpty()) {
            logger.warn("DBGetData.getUserRows(" + username + ") returned an empty optional.\n" +
                    "Passed username: " + username);
            // do something scary ;3
            // (UwU 👉👈)
            return pullUserResult.NO_SUCH_USER;
        }

        // Check if password is correct  V IMPORTANT! (this pleases the Java gods)
        Optional<UserRecord> goodUser /* pat pat */ = Arrays.stream(userRecords.get())  // Unpack users from Optional and turn to stream
                .filter(user -> user.Password.equals(password)) // Filter if password is correct
                .findFirst();

        // Check if any matched
        if (goodUser.isEmpty()) {
            logger.warn("None of the users from userRecords have matching passwords.\n" +
                    "Searched password: " + password);
            return pullUserResult.INVALID_CRED;
        }

        this.userInfo = goodUser.get();
        return pullUserResult.OK;
    }

    // part of registerNewUser
    // Tested 23/01/24 23:53
    public void pushUserToDB() {

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

    // Tested 23/01/24 23:53
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

        LocalUser user = logIn(username, password).get();

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

        logger.info("Registered new user.\n" +
                "User ID: " + user.userInfo.UserID + "\n" +
                "Username: " + user.userInfo.UserName + "\n" +
                "Password: " + user.userInfo.Password + "\n" +
                "Email: " + user.userInfo.Email + "\n" +
                "Phone: " + user.userInfo.Phone + "\n" +
                "Main Account ID: " + user.userInfo.mainAccount);
        return Optional.of(user);
    }

    // Tested via deleteUser() 24/01/24 00:18
    public void logOutLocally() {
        this.goalsInfo.clear();
        this.goalsInfo = null;

        this.historyInfo.clear();
        this.historyInfo = null;

        this.accountsInfo.clear();
        this.accountsInfo = null;

        this.userInfo = null;
    }

    // Tested 24/01/24 00:18
    public static void deleteUser(LocalUser localUser) {
        deleteUserFromDB(localUser);
        localUser.logOutLocally();
        // more happy :))
    }

    // Tested via deleteUser() 24/01/24 00:18
    private static void deleteUserFromDB(LocalUser localUser) {
        // 1. delete everything else they have: accs, goals, histories | Delete from DB
        final TableKey[] tablesToDeleteFrom = {TableKey.GOALS, TableKey.HISTORY, TableKey.ACCOUNTS, TableKey.USERS};

        for (TableKey tableKey : tablesToDeleteFrom) {
            try {
                DBManageData.deleteRow(tableKey, DataKey.UserID, String.valueOf(localUser.userInfo.UserID));
            } catch (SQLException e) {
                logger.error("Couldn't delete user data from table " + tableKey + ".\n" +
                        "SQLException message: " + e.getMessage() + "\n" +
                        "SQL state: " + e.getSQLState() + "\n" +
                        "Stack trace: " + e.getStackTrace());
                return;
            }
        }
        // 2. happy :)
    }

    // Tested via logIn() 24/01/24 00:13
    public void pullAllAccountsFromDB() {

        Optional<AccountRecord[]> userAccounts = DBGetData.getAccountRows(this.userInfo.UserID);

        if (userAccounts.isEmpty()) {
            logger.error("LocalUser::pullAllAccountsFromDB - empty userAccounts[] for UID: " + this.userInfo.UserID);
            return;
        }

        this.accountsInfo.clear();
        this.accountsInfo.addAll(List.of(userAccounts.get()));
    }

    // Tested 24/01/24 01:15
    public void pushNewAccountToDB(String title, String val, String currency) {

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
            return;
        }

        this.pullAllAccountsFromDB();
    }

    public void updateAccountInDB(String accID, String title, String val, String currency, String timeStamp) {

        DataKey[] columns = {DataKey.Title, DataKey.Val, DataKey.Currency, DataKey.CreateTimeStamp};
        String[] values = {title, val, currency, timeStamp};

        try {
            DBManageData.updateRow(TableKey.ACCOUNTS, DataKey.AccID, accID, columns, values);
        }
        catch (SQLException e) {
            logger.error("updateAccountInDB - updateRow error, couldn't insert into DB: "
                        + accID + ", " + title + ", " + val + ", " + currency + ", " + timeStamp);
            return;
        }
        
        this.pullAllAccountsFromDB();
    }

    // Tested 24/01/24 01:16
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
            this.pullAllAccountsFromDB();
        }
    }

    // Tested via logIn() 24/01/24 00:13
    public void pullAllGoalsFromDB() {

        Optional<GoalRecord[]> userGoals = DBGetData.getGoalRows(this.userInfo.UserID);

        if (userGoals.isEmpty()) {
            logger.error("LocalUser::pullAllGoalsFromDB - empty userGoals[] for UID: " + this.userInfo.UserID);
            return;
        }

        this.goalsInfo.clear();
        this.goalsInfo.addAll(List.of(userGoals.get()));
    }

    // Tested 24/01/24 01:16
    public void pushNewGoalToDB(String title, String value, String goal, String currency, String timeStamp,
                                String deadline) {
        if (!isCredentialValid(title) || !isCredentialValid(currency) || !isCredentialValid(goal)
                || !isCredentialValid(timeStamp) || !isCredentialValid(deadline)) {
            logger.error("pushNewAccountToDB - credential error - title: " + title + " | currency:  " + currency);
            return;
        }

        Date date = new Date();
        DataKey[] columns = {DataKey.UserID, DataKey.Title, DataKey.Val, DataKey.Goal, DataKey.Currency,
                DataKey.CreateTimeStamp, DataKey.Deadline};
        String[] values = {String.valueOf(this.userInfo.UserID), title, value, goal, currency, timeStamp, deadline};

        try {
            DBManageData.insertRow(Enums.TableKey.GOALS, columns, values);
        }
        catch (SQLException e) {
            logger.error("Couldn't push this user's -> " + this.userInfo.UserName + " new account to DB.\n" +
                    "Info meant to be inserted: "+ title + ", " + value + ", " + goal + ", " + currency + ", " + deadline + "\n" +
                    "SQLException message: " + e.getMessage() + "\n" +
                    "SQL state: " + e.getSQLState() + "\n" +
                    "Stack trace: " + e.getStackTrace());

            return;
        }

        this.pullAllGoalsFromDB();
    }

    // Tested 24/01/24 01:16
    public void deleteGoalFromDB(int... goalID) {
        for (int currentGoalID : goalID) {
            try {
                DBManageData.deleteRow(TableKey.GOALS, DataKey.GoalID, String.valueOf(currentGoalID));
            }
            catch (SQLException e) {
                logger.error("deleteAccountFromDB - error while removing goal of ID:" + currentGoalID
                        + "\n SQLException message: " + e.getMessage()
                        + "\n SQL state: " + e.getSQLState()
                        + "\n stack trace: " + e.getStackTrace());
                continue;
            }
            this.pullAllGoalsFromDB();
        }
    }

    // Tested via logIn() 24/01/24 00:13
    public void pullAllHistoryFromDB() {

        // to be written
        Optional<HistoryRecord[]> userHistory = DBGetData.getHistoryRows(this.userInfo.UserID);

        if (userHistory.isEmpty()) {
            logger.error("LocalUser::pullAllHistoryFromDB - empty userHistory[] for UID: " + this.userInfo.UserID);
            return;
        }

        this.historyInfo.clear();
        this.historyInfo.addAll(List.of(userHistory.get()));
    }

    public void updateHistoryInDB(String transID, String accID, String valueBefore, String change, String currency,
                                  String title, String timeStamp) {

        DataKey[] columns = {DataKey.AccID, DataKey.ValBefore, DataKey.Change, DataKey.Currency, DataKey.Title,
                            DataKey.TimeStamp};

        String[] values = {accID, valueBefore, change, currency, title, timeStamp};

        try {
            DBManageData.updateRow(TableKey.HISTORY, DataKey.TransID, transID, columns, values);
        }
        catch (SQLException e) {
            logger.error("updateHistoryInDB - updateRow error, couldn't insert into DB: "
                    + transID + ", " + accID + ", " + valueBefore + ", " + change + ", " + currency + ", " + title + ", "
                    + timeStamp);
            return;
        }

        this.pullAllAccountsFromDB();
    }
    public void updateGoalInDB(String goalID, String userID, String title, String value, String goal,
                                   String currency, String createTimeStamp, String deadline) {

        DataKey[] columns = {DataKey.GoalID, DataKey.UserID, DataKey.Title, DataKey.Val, DataKey.Goal,
                DataKey.Currency, DataKey.CreateTimeStamp, DataKey.Deadline};

        String[] values = {goalID, userID, title, value, goal, currency, createTimeStamp, deadline};

        try {
            DBManageData.updateRow(TableKey.GOALS, DataKey.GoalID, goalID, columns, values);
        }
        catch (SQLException e) {
            logger.error("updateGoalInDB - updateRow error, couldn't insert into DB: "
                    + goalID + ", " + userID + ", " + title + ", " + value + ", " + goal + ", " + currency + ", "
                    + createTimeStamp + ", " + deadline);
            return;
        }

        this.pullAllGoalsFromDB();
    }

    // Tested 24/01/24 01:16
    public void pushNewHistoryToDB(String accID, String change, String currency, String title) {

         Optional<String> optValBefore = DBGetData.getAccountRecord(Integer.parseInt(accID), DataKey.Val);
         String valBefore = "";

         if(optValBefore.isPresent()) {
            valBefore = optValBefore.get();
         }
         else {
             logger.error("pushNewHistoryToDB - couldn't reach valBefore\n" +
                          "credentials: accID: " + accID + " userID: " + this.userInfo.UserID);
             return;
         }

        DataKey[] columns = {DataKey.UserID, DataKey.AccID, DataKey.ValBefore, DataKey.Change, DataKey.Currency,
                DataKey.Title, DataKey.TimeStamp};

        Date date = new Date();

        String[] values = { String.valueOf(this.userInfo.UserID), accID, valBefore, change, currency,
                            title, String.valueOf(getEpochTimeStamp(date))};

        try {
            DBManageData.insertRow(TableKey.HISTORY, columns, values);
        }
        catch (SQLException e) {
            logger.error("pushNewHistoryToDB - insert row error \n"
                        + "SQL message: " + e.getMessage() +
                        "\n SQL state: " + e.getSQLState() +
                        "\n stacktrace: " + e.getStackTrace());
            return;
        }
        
        this.pullAllHistoryFromDB();
    }

    // Tested 24/01/24 01:16
    public void deleteHistoryFromDB(int... transID) {

        for (int i : transID) {
            try {
                DBManageData.deleteRow(TableKey.HISTORY, DataKey.TransID, String.valueOf(i));
            }
            catch (SQLException e) {
                logger.error("deleteAccountFromDB - error while removing history of ID: " + String.valueOf(i)
                        + "\n SQLException message: " + e.getMessage()
                        + "\n SQL state: " + e.getSQLState()
                        + "\n stack trace: " + e.getStackTrace());
                continue;
            }
            this.pullAllGoalsFromDB();
        }
    }

    // ===== fields =====

    private final static Logger logger = LogManager.getLogger(LocalUser.class);

    public UserRecord userInfo;
    public ArrayList<AccountRecord> accountsInfo = new ArrayList<>();
    public ArrayList<GoalRecord> goalsInfo = new ArrayList<>();
    public ArrayList<HistoryRecord> historyInfo = new ArrayList<>();

    // ===== main =====

    /*
    public static void main(String[] args) {
        LocalUser user = logIn("aaaaa", "aaaaaa");
        user.pushNewAccountToDB("cebuliony", "696969.69", "cbln");
        user.pushNewGoalToDB("na dziadka do orzechów", "1000", "42069", "cbln",
                String.valueOf(utilities.stringUtils.getEpochTimeStamp(new Date())), "na wczoraj");
        user.pushNewHistoryToDB("1", "-100.60", "cbln", "onlyfans niepełnoletnich gimnastyczek z tiktoka");
        user.deleteAccountFromDB(6);
        user.deleteGoalFromDB(6);
        user.deleteHistoryFromDB(5);
    }*/
}