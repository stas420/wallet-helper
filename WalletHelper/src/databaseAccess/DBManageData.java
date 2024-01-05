package databaseAccess;

import utilities.Enums.TableKey;
import utilities.Enums.DataKey;
import static utilities.Enums.dataToType;

import java.sql.SQLException;
import java.sql.PreparedStatement;

// Used for queries changing structure of the database (INSERT, UPDATE, DELETE),
// so they do not return actually anything.
public class DBManageData {

    /* // Needs rewriting and rethinking if it will ever be used
    protected static void insertSingleData (TableKey table, DataKey column, String val) throws SQLException {

        DBConnection.setConnection();
        PreparedStatement ps = DBConnection.getConnector().prepareStatement(DBQuery.insert(table, new DataKey[] {column}));

        switch (dataToType(column)) {
            case INT: {
                ps.setInt(1, Integer.parseInt(val));
                break;
            }

            case FLOAT: {
                ps.setFloat(1, Float.parseFloat(val));
                break;
            }

            default: {
                ps.setString(1, val);
                break;
            }
        }

        ps.execute();
        DBConnection.closeConnection();
    }*/

    protected static void insertRow(TableKey table, DataKey[] columns, String[] values) throws SQLException {

        if (columns.length != values.length) {
            System.out.println("insertRow - incorrect arrays' sizes.");
            return;
        }

        DBConnection.setConnection();
        PreparedStatement ps = DBConnection.getConnector().prepareStatement(DBQuery.insert(table, columns));

        for (int i = 0; i < values.length; i++) {

            switch (dataToType(columns[i])) {
                case INT: {
                    ps.setInt(i+1, Integer.parseInt(values[i]));
                    break;
                }

                case FLOAT: {
                    ps.setFloat(i+1, Float.parseFloat(values[i]));
                    break;
                }

                default: {
                    ps.setString(i+1, values[i]);
                    break;
                }
            }
        }

        ps.execute();
        DBConnection.closeConnection();
    }

    protected static void updateSingleData (TableKey table, DataKey ID, String IDVal, DataKey column, String newVal) throws SQLException {

        DBConnection.setConnection();
        PreparedStatement ps = DBConnection.getConnector().prepareStatement(DBQuery.update(table, column, ID));

        switch(dataToType(column)) {
            case INT: {
                ps.setInt(1, Integer.parseInt(newVal));
                break;
            }
            case FLOAT: {
                ps.setFloat(1, Float.parseFloat(newVal));
                break;
            }
            default:
                ps.setString(1, newVal);
        }

        ps.setInt(2, Integer.parseInt(IDVal));

        ps.execute();
        DBConnection.closeConnection();
    }

    // SPECIAL CASE: delete USER -> this action will be protected by app locally (when any data, then cannot delete)
    protected static void deleteRow(TableKey table, DataKey ID, String IDVal) throws SQLException {

        DBConnection.setConnection();
        PreparedStatement ps = DBConnection.getConnector().prepareStatement(DBQuery.delete(table, ID));
        ps.setInt(1, Integer.parseInt(IDVal));
        ps.execute();

        DBConnection.closeConnection();
    }

}
