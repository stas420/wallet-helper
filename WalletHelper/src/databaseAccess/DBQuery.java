package databaseAccess;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

// This class is for query management: it is made for creating queries, checking their
// validity and then sending them to the database and returning database's output.
// It is dependant on opened database connection, which shall be provided by DBConnection.
public class DBQuery {

    // ==== Constructors ====
    protected DBQuery(Connection conn, String query) {
        this.query = query;

        try {
            statement = conn.createStatement();
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("DBQuery constr. - statement error: " + e.getMessage());
        }
    }

    protected DBQuery(Connection conn) {
        this.query = "select 1 from users";

        try {
            statement = conn.createStatement();
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("DBQuery constr. - statement error: " + e.getMessage());
        }
    }

    // ==== Methods ====
    protected void query() throws SQLException {
        this.res = this.statement.executeQuery(this.query);
    }

    protected void changeQuery(String newQuery) {
        this.query = newQuery;
    }

    protected String readQuery(String read) throws SQLException {
        return res.getString(read);
    }

    // ==== Fields ====
    private Statement statement;
    private String query;
    private ResultSet res;
}
