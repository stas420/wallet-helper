package userUtilities;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class UserRecord{
    public int UserID;
    public String UserName;
    public String Email;
    public String Phone;
    public String Password;
    public int mainAccount;
    private static final Logger logger = LogManager.getLogger(UserRecord.class);

    public UserRecord(String[] vals) {
        if(vals.length != 6) {
            logger.error("Wrong array size provided to UserRecord constructor.\n" +
                    "Array provided: " + Arrays.toString(vals) + "\n" +
                    "Expected length: 6\n" +
                    "Got: " + vals.length);
            System.err.println("Wrong length of array stupid");
            return;
        }
        UserID = Integer.parseInt(vals[0]);
        UserName = vals[1];
        Email = vals[2];
        Phone = vals[3];
        Password = vals[4];
        mainAccount = Integer.parseInt(vals[5]);

    }


    public UserRecord(String uid, String username, String email, String phone, String password, String mainAcc) {
        UserID = Integer.parseInt(uid);
        UserName = username;
        Email = email;
        Phone = phone;
        Password = password;
        mainAccount = Integer.parseInt(mainAcc);
    }
    public UserRecord(int uid, String username, String email, String phone, String password, int mainAcc) {
        UserID = uid;
        UserName = username;
        Email = email;
        Phone = phone;
        Password = password;
        mainAccount = mainAcc;
    }

    /*
    public static void main(String[] args) {
        new UserRecord(new String[]{"test", "chuj ci w dupe"});
    } */
}
