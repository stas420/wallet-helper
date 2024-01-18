package databaseAccess;

import utilities.Enums.TableKey;
import utilities.Enums.DataKey;
import static utilities.Enums.dataToType;

import java.sql.SQLException;
import java.sql.PreparedStatement;

import java.math.BigDecimal;
import java.math.RoundingMode;

// Used for queries changing structure of the database (INSERT, UPDATE, DELETE),
// so they do not return actually anything.
public class DBManageData {

    protected static void insertRow(TableKey table, DataKey[] columns, String[] values) throws SQLException {

        while(!DBConnection.isConnected()) {
            DBConnection.setConnection();
        }

        if (columns.length != values.length) {
            System.out.println("insertRow - incorrect arrays' sizes.");
            return;
        }

        PreparedStatement ps = DBConnection.getConnector().prepareStatement(DBQuery.insert(table, columns));

        for (int i = 0; i < values.length; i++) {

            switch (dataToType(columns[i])) {
                case INT: {
                    ps.setInt(i + 1, Integer.parseInt(values[i]));
                    break;
                }

                // We store (2 decimal places) floats in the database as ints
                // Like: 21.37 -> 2137
                case FLOAT: {
                    float valueFloat = Float.parseFloat(values[i]);
                    int valueInt = Math.round(valueFloat * 100);
                    ps.setInt(i + 1, valueInt);
                    break;
                }

                default: {
                    ps.setString(i+1, values[i]);
                    break;
                }
            }
        }

        ps.execute();
    }

    protected static void updateSingleData (TableKey table, DataKey ID, String IDVal, DataKey column, String newVal) throws SQLException {

        while(!DBConnection.isConnected()) {
            DBConnection.setConnection();
        }

        PreparedStatement ps = DBConnection.getConnector().prepareStatement(DBQuery.update(table, column, ID));

        switch(dataToType(column)) {
            case INT: {
                ps.setInt(1, Integer.parseInt(newVal));
                break;
            }
            default:
                ps.setString(1, newVal);
        }

        ps.setInt(2, Integer.parseInt(IDVal));

        ps.execute();
    }

    // SPECIAL CASE: delete USER -> this action will be protected by app locally (when any data, then cannot delete)
    protected static void deleteRow(TableKey table, DataKey ID, String IDVal) throws SQLException {

        while(!DBConnection.isConnected()) {
            DBConnection.setConnection();
        }

        PreparedStatement ps = DBConnection.getConnector().prepareStatement(DBQuery.delete(table, ID));
        ps.setInt(1, Integer.parseInt(IDVal));
        ps.execute();
    }

}
