package databaseAccess;

import utilities.Enums.TableKey;
import utilities.Enums.DataKey;
import static utilities.Enums.dataToType;

import java.sql.SQLException;
import java.sql.PreparedStatement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;

// Used for queries changing structure of the database (INSERT, UPDATE, DELETE),
// so they do not return actually anything.
public class DBManageData {
    
    static final Logger logger = LogManager.getLogger(DBManageData.class);
   
    public static void insertRow(TableKey table, DataKey[] columns, String[] values) throws SQLException {

        while(!DBConnection.isConnected()) {
            DBConnection.setConnection();
        }

        if (columns.length != values.length) {
            System.out.println("insertRow - incorrect arrays' sizes.");
            return;
        }

        String query = DBQuery.insert(table, columns);
        System.out.println(columns);
        PreparedStatement ps = DBConnection.getConnector().prepareStatement(query);

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

        try {
            DBConnection.closeConnection();
        }
        catch (SQLException e) {
            logger.warn("insertRow - closeConnection - couldn't close connection");
        }
    }

    public static void updateSingleData (TableKey table, DataKey ID, String IDVal, DataKey column, String newVal) throws SQLException {

        while(!DBConnection.isConnected()) {
            DBConnection.setConnection();
        }

        PreparedStatement ps = DBConnection.getConnector().prepareStatement(DBQuery.update(table, column, ID));

        switch(dataToType(column)) {
            case INT: {
                ps.setInt(1, Integer.parseInt(newVal));
                break;
            }
            case FLOAT: {
                ps.setInt(1, Integer.parseInt(newVal) * 100);
                break;
            }
            default: {
                ps.setString(1, newVal);
            }
        }

        ps.setInt(2, Integer.parseInt(IDVal));

        ps.execute();

        try {
            DBConnection.closeConnection();
        }
        catch (SQLException e) {
            logger.warn("updateSingleData - closeConnection - couldn't close connection");
        }

    }

    public static void updateRow (TableKey table, DataKey ID, String IDVal, DataKey[] columns, String[] newVals) throws SQLException {
        while(!DBConnection.isConnected()) {
            DBConnection.setConnection();
        }

        String query = DBQuery.update(table, columns, ID);
        PreparedStatement ps = DBConnection.getConnector().prepareStatement(query);

        for (int i = 0; i < newVals.length; i++) {
            switch (dataToType(columns[i])) {
                case INT: {
                    ps.setInt(i+1, Integer.parseInt(newVals[i]));
                    break;
                }
                case FLOAT: {
                    ps.setInt(i+1, (int) (Float.parseFloat(newVals[i]) * 100));
                    break;
                }
                default: {
                    ps.setString(i+1, newVals[i]);
                }
            }
        }

        ps.setInt(newVals.length + 1, Integer.parseInt(IDVal));

        ps.execute();

        try {
            DBConnection.closeConnection();
        }
        catch (SQLException e) {
            logger.warn("updateRow - closeConnection - couldn't close connection");
        }

    }

    // SPECIAL CASE: delete USER -> this action will be public by app locally (when any data, then cannot delete)
    public static void deleteRow(TableKey table, DataKey ID, String IDVal) throws SQLException {

        while(!DBConnection.isConnected()) {
            DBConnection.setConnection();
        }

        PreparedStatement ps = DBConnection.getConnector().prepareStatement(DBQuery.delete(table, ID));
        ps.setInt(1, Integer.parseInt(IDVal));
        ps.execute();

        try {
            DBConnection.closeConnection();
        }
        catch (SQLException e) {
            logger.warn("deleteRow - closeConnection - couldn't close connection");
        }

    }
    

}
