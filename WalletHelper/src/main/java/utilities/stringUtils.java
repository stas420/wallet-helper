package utilities;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
                && !cred.contains(":") && !cred.contains("/") && !cred.contains("*");
    } //!cred.contains(" ") &&

    public static String[] getColumnArray(Enums.TableKey key) {
        return switch(key) {
            case ACCOUNTS: {
                yield new String[] {"ID", "Title", "Funds", "Currency", "Created"};
            }
            case GOALS: {
                yield new String[] { "ID", "Title", "Funds", "Goal", "Currency", "Created", "Deadline"};
            }
            case HISTORY: {
                yield new String[] { "ID", "Account", "Value before", "Change", "Currency", "Title", "Created"};
            }
            default: {
                yield new String[] {"error"};
            }
        };
    }

    public static String dateFormat(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(date);
    }

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
}