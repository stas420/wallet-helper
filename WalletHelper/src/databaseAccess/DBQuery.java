package databaseAccess;

import utilities.Enums.DataKey;
import utilities.Enums.TableKey;

// This class is made for SQL query construction and returning it as a String.
// Requires yet some idiot-security and exception throwing (its own exception class).
public abstract class DBQuery {

    protected static String selectQuery(TableKey from, DataKey where, String whereIs, DataKey selectWhat) {
        return ("SELECT " + selectWhat.dataKey + " FROM " + from.tableKey + " WHERE " + where.dataKey + "='" + whereIs + "';");
    }

    protected static String selectQuery(TableKey from, DataKey where, String whereIs) {
        return ("SELECT * FROM " + from.tableKey + " WHERE " + where.dataKey + "='" + whereIs + "';");
    }

    protected static String updateQuery(TableKey table, DataKey col, String val, DataKey where, String whereIs) {
        return ("UPDATE " + table.tableKey + " SET " + col.dataKey + "='" + val + "' WHERE " + where.dataKey + "='" + whereIs + "';");
    }

    protected static String updateQuery(TableKey table, DataKey[] col, String[] val, DataKey where, String whereIs) {

        String out = "";

        if (col.length != val.length || col.length == 1)
            return "ERROR";

        out = "UPDATE " + table.tableKey + " SET ";

        for (int i = 0; i < col.length - 1; i++) {

            out += col[i].dataKey + "='" + val[i] + "', ";
        }

        out += col[col.length-1] + "='" + val[val.length-1] +"' WHERE " + where.dataKey + "='" + whereIs + "';";

        return out;
    }

    protected static String insertQuery(TableKey table, DataKey col, String val) {
        return ("INSERT INTO " + table.tableKey + " (" + col.dataKey + ") VALUES (" + val + ");");
    }

    protected static String insertQuery(TableKey table, DataKey[] col, String[] val) {

        String out = "";

        if (col.length != val.length || col.length == 1)
            return "ERROR";

        out = "INSERT INTO " + table.tableKey + " (";

        for (int i = 0; i < col.length - 1; i++) {
            out += col[i].dataKey + ", ";
        }

        out += col[col.length - 1] + ") VALUES (";

        for (int i = 0; i < val.length - 1; i++) {
            out += val[i] + ", ";
        }

        out += val[val.length - 1] + ");";

        return out;
    }

    protected static String deleteQuery(TableKey table, DataKey where, String whereIs) {
        return ("DELETE FROM " + table.tableKey + " WHERE " + where.dataKey + "='" + whereIs + "';");
    }
}
