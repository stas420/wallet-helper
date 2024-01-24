package utilities;

import java.util.Scanner;
import java.util.Date;

// This class is made for simpler access to 'more-low-level' string operations, especially
// considering date formatting.
public abstract class stringUtils {

    public static String getString() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    // uses dot notation (21.37 not 21,37)
    public static float getFloatValue() {
        Scanner sc = new Scanner(System.in);
        return sc.nextFloat();
    }

    public static int getIntValue() {
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

    public static long getEpochTimeStamp(Date date) {
        return date.getTime() / 1000;
    }

    public static Date parseEpochToDate(long epoch) {
        return new Date(epoch * 1000);
    }

    // checks if credential to be queried to database is valid, i.e. does not have any unwanted characters
    // more should be added soon
    public static boolean isCredentialValid(String cred) {
        return !cred.contains("(") && !cred.contains(")") && !cred.contains("\\") && !cred.contains(";") && !cred.contains(",")
                && !cred.contains(":") &&  !cred.contains("/") && !cred.contains("*");
    } //!cred.contains(" ") &&
}
/*
    public static String currentTimeStamp() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime ld = LocalDateTime.now();

        return dtf.format(ld);
    }

    public static String timeStamp(int day, int month, int year, int hours, int minutes, int seconds) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime ld = LocalDateTime.of(year, month, day, hours, minutes, seconds);

        return dtf.format(ld);
    }
    */