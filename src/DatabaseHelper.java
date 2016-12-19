/**
 * Classname: DatabaseHelper.java
 * Version info 1.0
 * Copyright notice:        Samuel Bylund Felixon
 * Date: 17/12/2017
 * Course: Applikationsutveckling i Java
 */

import java.sql.SQLException;

public class DatabaseHelper {

    /**
     * Empty constructor since it's a helper class
     */
    public DatabaseHelper() {
        //empty constructor -- helper class
    }

    /**
     * Method to determin if the SQLException thrown was
     * due to a table being created when there already was a table.
     * @param e The caught exception
     * @return Boolean
     */
    public static boolean tableAlreadyExists(SQLException e) {
        return e.getSQLState().equals("X0Y32");
    }
}
