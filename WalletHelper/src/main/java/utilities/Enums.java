package utilities;

public abstract class Enums {

    // Enums provide some 'argument insertion' security and I like to use them

    public enum QueryKey {
        SELECT("SELECT"), INSERT("INSERT"), UPDATE("UPDATE"), DELETE("DELETE");

        public final String queryKey;

        private QueryKey(String text) {
            this.queryKey = text;
        }
    }

    public enum DataKey {
        UserID("UserID"), AccID("AccID"), GoalID("GoalID"), TransID("TransID"),
        Val("Val"), ValBefore("ValBefore"), Change("Change"), Goal("Goal"), Currency("Currency"),
        UserName("UserName"), Email("Email"), Phone("Phone"), Password("Password"),
        Title("Title"), CreateTimeStamp("CreateTimeStamp"), TimeStamp("TimeStamp"), Deadline("Deadline"),
        mainAccount("mainAccount");

        public final String dataKey;

        private DataKey(String text) {
            this.dataKey = text;
        }
    }

    public enum TableKey {
        USERS("users"), ACCOUNTS("accounts"), GOALS("goals"), HISTORY("history");

        public final String tableKey;

        private TableKey(String text) {
            this.tableKey = text;
        }
    }

    public enum TypeKey {
        INT, STRING, FLOAT
    }

    public static TypeKey dataToType(DataKey data) {

        // A weird, Java 'enhanced switch expression' IntelliJ forced me to make ;_;
        return switch (data) {
            case UserID, AccID, GoalID, TransID, Phone, mainAccount, CreateTimeStamp, TimeStamp: {
                yield TypeKey.INT;
            }

            case Val, ValBefore, Change, Goal: {
                yield TypeKey.FLOAT;
            }

            default: {
                yield TypeKey.STRING;
            }
        };
    }

    /*
    // Is this even useful?
    public static boolean ifForeignKey (DataKey column) {
        return switch (column) {
            case UserID, AccID:
                yield true;
            default:
                yield false;
        };
    }
    // And this?
    public static TableKey foreignKeySource (DataKey column) {

        if (column == DataKey.UserID)
            return TableKey.USERS;

        return TableKey.ACCOUNTS;
    }*/
}
