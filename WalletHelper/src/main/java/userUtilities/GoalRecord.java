package main.java.userUtilities;

import java.util.Date;
import java.util.Optional;

import main.java.utilities.stringUtils;

public final class GoalRecord{
    int GoalID;
    int UserID;
    String Title;
    float Val;
    String Currency;
    Date CreateTimeStamp;
    String Deadline;


    public GoalRecord(String[] vals) {
        if (vals.length != 7) {
            //TODO log4j
            System.err.println("Wrong length of array stoopid");
            return;
        }
        GoalID = Integer.parseInt(vals[0]);
        UserID = Integer.parseInt(vals[1]);
        Title = vals[2];
        Val = Float.parseFloat(vals[3]) / 100;
        Currency = vals[4];
        CreateTimeStamp = stringUtils.parseEpochToDate(Integer.parseInt(vals[5]));
        Deadline = vals[6];
    }
}
