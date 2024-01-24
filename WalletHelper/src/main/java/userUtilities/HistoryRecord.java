package userUtilities;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import utilities.stringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HistoryRecord {
    public int transId;
    public int userId;
    public int accId;
    public float valBefore;
    public float change;
    public String currency;
    public String title;
    public Date timeStamp;

    private static final Logger logger = LogManager.getLogger(HistoryRecord.class);

    public HistoryRecord(String[] vals) {
        if (vals.length != 8) {
            System.err.println("Array is the wrong size you silly goose");
            logger.error("Wrong array size provided to HistoryRecord constructor.\n" +
                    "Array provided: " + Arrays.toString(vals) + "\n" +
                    "Expected length: 8\n" +
                    "Got: " + vals.length);
            return;
        }
        transId = Integer.parseInt(vals[0]);
        userId = Integer.parseInt(vals[1]);
        accId = Integer.parseInt(vals[2]);
        valBefore = Float.parseFloat(vals[3]) / 100;
        change = Float.parseFloat(vals[4]) / 100;
        currency = vals[5];
        title = vals[6];
        timeStamp = stringUtils.parseEpochToDate(Long.parseLong(vals[7]));
    }

    public HistoryRecord(String transID, String userID, String accID, String ValBefore, String Change, String Currency,
                         String Title, String TimeStamp) {
        transId = Integer.parseInt(transID);
        userId = Integer.parseInt(userID);
        accId = Integer.parseInt(accID);
        valBefore = Float.parseFloat(ValBefore) / 100;
        change = Float.parseFloat(Change) / 100;
        currency = Currency;
        title = Title;
        timeStamp = stringUtils.parseEpochToDate(Integer.parseInt(TimeStamp));
    }
}
