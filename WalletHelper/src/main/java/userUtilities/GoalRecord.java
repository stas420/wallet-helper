package userUtilities;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import utilities.stringUtils;

public final class GoalRecord{
    private final static Logger logger = LogManager.getLogger(GoalRecord.class);
    public int GoalID;
    public int UserID;
    public String Title;
    public float Val;
    public float Goal;
    public String Currency;
    public Date CreateTimeStamp;
    public String Deadline;


    public GoalRecord(String[] vals) {
        if (vals.length != 8) {
            logger.error("Wrong array size provided to HistoryRecord constructor.\n" +
                    "Array provided: " + Arrays.toString(vals) + "\n" +
                    "Expected length: 8\n" +
                    "Got: " + vals.length);
            System.err.println("Wrong length of array stoopid");
            return;
        }
        GoalID = Integer.parseInt(vals[0]);
        UserID = Integer.parseInt(vals[1]);
        Title = vals[2];
        Val = Float.parseFloat(vals[3]) / 100;
        Goal = Float.parseFloat(vals[4]) / 100;
        Currency = vals[5];
        CreateTimeStamp = stringUtils.parseEpochToDate(Long.parseLong(vals[6]));
        Deadline = vals[7];
    }

}
