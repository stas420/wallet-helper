package databaseAccess;

import utilities.Enums.QueryKey;
import utilities.Enums.DataKey;

// This class is for query management: it is made for creating queries, checking their
// validity and then sending them to the database and returning database's output.
// It is dependant on opened database connection, which shall be provided by DBConnection.
public class DBQuery {

    protected DBQuery() {

    }

    protected String queryString() {
        return this.queryString;
    }

    private String queryString;
}
