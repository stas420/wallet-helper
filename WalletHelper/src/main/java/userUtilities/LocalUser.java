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

public class LocalUser {

    private final static Logger logger = LogManager.getLogger(LocalUser.class);

    public LocalUser (String username, String password) {
        pullUserFromDB(username, password);
    }

    private void pullUserFromDB(String username, String password) {
        // if username and password are legitimate...
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

    private UserRecord userInfo;
    private ArrayList<AccountRecord> accountsInfo = new ArrayList<>();
    private ArrayList<GoalRecord> goalsInfo = new ArrayList<>();
    private ArrayList<HistoryRecord> historyInfo = new ArrayList<>();

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