import java.sql.SQLException;

/**
 * Created by Samuel on 2016-12-13.
 */
public class DatabaseHelper {

    public DatabaseHelper() {
        //empty constructor -- helper class
    }

    public static boolean tableAlreadyExists(SQLException e) {
        return e.getSQLState().equals("X0Y32");
    }
}
