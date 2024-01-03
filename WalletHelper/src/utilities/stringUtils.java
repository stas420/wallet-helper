package utilities;

import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.*;

public class stringUtils {

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

    /*
    // main for testing
    public static void main(String[] args) {
        float txt = getFloatValue();
        System.out.println(txt);
    }
    */
}
