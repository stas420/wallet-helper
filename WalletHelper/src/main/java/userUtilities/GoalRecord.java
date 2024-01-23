package userUtilities;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import utilities.stringUtils;

public final class GoalRecord{
    private final static Logger logger = LogManager.getLogger(GoalRecord.class);
    int GoalID;
    int UserID;
    String Title;
    float Val;
    String Currency;
    Date CreateTimeStamp;
    String Deadline;


    public GoalRecord(String[] vals) {
        if (vals.length != 7) {
            logger.error("Wrong array size provided to HistoryRecord constructor.\n" +
                    "Array provided: " + Arrays.toString(vals) + "\n" +
                    "Expected length: 7\n" +
                    "Got: " + vals.length);
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
