package main.java.userUtilities;

import java.util.Optional;

public class UserRecord{
    public int UserID;
    public String UserName;
    public String Email;
    public String Phone;
    public String Password;
    public int mainAccount;


    public UserRecord(String[] vars) {
        if(vars.length != 6) {
            // TODO log4j
            System.err.println("Wrong length of array stupid");
            return;
        }
        UserID = Integer.parseInt(vars[0]);
        UserName = vars[1];
        Email = vars[2];
        Phone = vars[3];
        Password = vars[4];
        mainAccount = Integer.parseInt(vars[5]);

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
}
