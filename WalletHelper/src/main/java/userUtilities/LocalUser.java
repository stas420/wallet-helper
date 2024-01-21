package main.java.userUtilities;

import java.util.ArrayList;
import main.java.databaseAccess.DBGetData;

public class LocalUser {



    public LocalUser (String username, String password) {

        // if username and password are legitimate...



    }

    private boolean logIn(String usr, String pass) {

        UserRecord user = DBGetData.getUserRows()

        return false;
    }

    private ArrayList<UserRecord> userInfo;
    private ArrayList<AccountRecord> accountsInfo;
    private ArrayList<GoalRecord> goalsInfo;
    private ArrayList<HistoryRecord> historyInfo;
}
