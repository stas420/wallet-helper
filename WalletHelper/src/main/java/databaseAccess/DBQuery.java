package main.java.databaseAccess;

//import static utilities.Enums.ifForeignKey;
//import static utilities.Enums.foreignKeySource;
import main.java.utilities.Enums.DataKey;
import main.java.utilities.Enums.TableKey;

// This class is made for SQL query construction and returning it as a String, which shall be
// later parametrized with PreparedStatement in DBGetData and DBManageData.
// Foreign-key operations require providing it explicitly, i.e. not by SELECT or SET combinations.
// Requires yet some:
//      - idiot-security and exception throwing (its own exception class)
//      - rewriting to handle PreparedStatement (i.e. select ? from ? where ...)
public abstract class DBQuery {

    // SELECT ? FROM table WHERE column=?
    protected static String select(TableKey table, DataKey where) {
        return ("SELECT ? FROM " + table.tableKey + " WHERE " + where.dataKey + "=?");
    }
    // SELECT what/* FROM table WHERE where='whereIs';
    protected static String select(TableKey table, DataKey where, String whereIs) {
        return ("SELECT * FROM " + table.tableKey + " WHERE " + where.dataKey + "='" + whereIs + "'");
    }

    protected static String select(TableKey table, DataKey where, String whereIs, DataKey what) {
        return ("SELECT " + what.dataKey + " FROM " + table.tableKey + " WHERE " + where.dataKey + "='" + whereIs + "'");
    }

    // INSERT INTO table (col1, col2, ...) VALUES (v1, v2, ...);
    protected static String insert(TableKey table, DataKey... where) {
        if (where.length == 0)
            return "ERROR";

        String output = "INSERT INTO " + table.tableKey + " (";

        for (int i = 0; i < (where.length - 1); i++) {
                output += where[i].dataKey +  ", ";
        }

        output += where[where.length - 1] + ") VALUES (";
        int j = 0;

        for (int i = 0; i < (where.length - 1); i++) {
            output += "?, ";
        }

        output += "?)";

        return output;
    }

    // UPDATE table SET c1=v1, c2=v2, ... WHERE ...;
    protected static String update(TableKey table, DataKey what, DataKey where) {
        return ("UPDATE " + table.tableKey + " SET " + what.dataKey + "= ? WHERE " + where.dataKey + "= ?");
    }

    protected static String update(TableKey table, DataKey[] what, DataKey where) {
        if(what.length == 1)
            return "ERROR";

        String output = "UPDATE " + table.tableKey + " SET ";

        for (int i = 0; i < (what.length - 1); i++) {
            output += what[i].dataKey + "= ?, ";
        }

        output += what[what.length - 1] + "= ? WHERE " + where.dataKey + "= ?";

        return output;
    }

    // DELETE FROM table WHERE where=whereIs;
    protected static String delete(TableKey table, DataKey where) {
        return ("DELETE FROM " + table.tableKey + " WHERE " + where.dataKey + "=?");
    }
}