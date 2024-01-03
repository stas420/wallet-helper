package databaseAccess;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

public class DBQuery {

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

    protected void query() throws SQLException {
        this.res = this.statement.executeQuery(this.query);
    }

    protected void changeQuery(String newQuery) {
        this.query = newQuery;
    }

    protected String readQuery(String read) throws SQLException {
        return res.getString(read);
    }

    private Statement statement;
    private String query;
    private ResultSet res;
}
