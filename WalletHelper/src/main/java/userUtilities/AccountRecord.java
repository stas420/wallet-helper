package userUtilities;

import java.util.Date;
import java.util.Optional;
import java.util.ArrayList;

import databaseAccess.DBGetData;
import utilities.stringUtils;

public class AccountRecord{

    public AccountRecord(String[] vals) {
        AccID = Integer.parseInt(vals[0]);
        UserID = Integer.parseInt(vals[1]);
        Title = vals[2];
        Val = Float.parseFloat(vals[3]) / 100;
        Currency = vals[4];
        CreateTimeStamp = stringUtils.parseEpochToDate(Long.parseLong(vals[5]));
    }
    public AccountRecord() {
        this.AccID = this.UserID = 0;
        this.Title = this.Currency = "";
        this.CreateTimeStamp = new Date();
        this.Val = 0f;
    }

    public String toString() {
        return "Account ID: " + AccID + "\n" +
                "User ID: " + UserID + "\n" +
                "Title: " + Title + "\n" +
                "Value: " + Val + "\n" +
                "Currency: " + Currency + "\n" +
                "CreateTimeStamp: " + CreateTimeStamp.toString() + "\n";
    }

    public AccountRecord(String accID, String userID, String title, String valueInt, String currency, String createTimeStamp) {
        AccID = Integer.parseInt(accID);
        UserID = Integer.parseInt(userID);
        Title = title;
        Val = Float.parseFloat(valueInt) / 100;
        Currency = currency;
        CreateTimeStamp = stringUtils.parseEpochToDate(Long.parseLong(createTimeStamp));
    }

    // private + setters() getters()? może później
    public int AccID;
    public int UserID;
    public String Title;
    public float Val;
    public String Currency;
    public Date CreateTimeStamp;
}
