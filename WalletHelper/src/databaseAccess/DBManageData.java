package databaseAccess;

import utilities.Enums.TableKey;
import utilities.Enums.DataKey;
import static utilities.Enums.dataToType;

import java.sql.SQLException;
import java.sql.PreparedStatement;

public class DBManageData {

    // Update data:
    //      single user data, single goal data, single acc data

    // Insert data:
    //      single user, single trans, single goal, single acc

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
    }

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

    // Delete data:
    //      acc record, goal record, transaction record
    //      SPECIAL CASE: delete USER
    protected static void deleteRow(TableKey table, DataKey )

}
