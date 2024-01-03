package utilities;

public abstract class Enums {

    public enum QueryKey {
        SELECT("SELECT"), INSERT("INSERT"), UPDATE("UPDATE"), DELETE("DELETE");

        public final String queryKey;

        private QueryKey(String text) {
            this.queryKey = text;
        }
    }

    public enum DataKey {
        UserID("UserID"), AccID("AccID"), GoalID("GoalID"), TransID("TransID"),
        Val("Val"), ValBefore("ValBefore"), Change("Change"), Goal("Goal"), Curr("Curr"),
        UserName("UserName"), Email("Email"), Phone("Phone"), Password("Password"),
        Title("Title"), TimeStamp("TimeStamp"), Deadline("Deadline");

        public final String dataKey;

        private DataKey(String text) {
            this.dataKey = text;
        }
    }

    public enum TableKey {
        USERS("Users"), ACCOUNTS("Accounts"), GOALS("Goals"), HISTORY("History");

        public final String tableKey;

        private TableKey(String text) {
            this.tableKey = text;
        }
    }

}
