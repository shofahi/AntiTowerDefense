/**
 * Classname: Database.java
 * Version info 1.0
 * Copyright notice:        Samuel Bylund Felixon
 * Date: 17/12/2017
 * Course: Applikationsutveckling i Java
 */
import java.sql.*;
import java.util.ArrayList;

public class Database {
    private static final String DRIVER = "org.apache.derby.jdbc.ClientDriver";
    private static final String JDBC_URL = "jdbc:derby:antiTDDatabase";
    private static final String JDBC_CREATE = ";create=true";
    private static final String JDBC_SHUTDOWN = ";shutdown=true";
    private static final String TABLE_NAME = "antiTDHighscores";

    private Connection connection = null;
    private Statement statement = null;
    private PreparedStatement pStatement = null;
    private ResultSet resultSet = null;

    private final static String SQL_GETALLSCORES =
            "SELECT * FROM " + TABLE_NAME + " ORDER BY level DESC,score DESC";

    private final static String SQL_GETSCOREBYNAME =
            "SELECT * FROM " + TABLE_NAME +  " WHERE name = ? "
                    + "ORDER BY level DESC, score DESC";

    private final static String SQL_ADDSCORE =
            "INSERT INTO " + TABLE_NAME + " (name, level, score) VALUES (?, "
                    + "?, ?)";

    private final static String SQL_REMOVEALL =
            "DELETE FROM " + TABLE_NAME + " WHERE 1=1";

    /**
     * Default constructor.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public Database() throws SQLException, ClassNotFoundException {
        setUpDBConnection();
        createDBTable();
    }

    /**
     * Method to set up the connection to the database. If no database
     * is found, the database is created.
     */
    private synchronized void setUpDBConnection() {
        try{
            Class.forName(DRIVER).newInstance();
            connection = DriverManager.getConnection(JDBC_URL+JDBC_CREATE);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException | IllegalAccessException |
                InstantiationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to create a table in the database. If a table
     * already exists, the method just continues as if nothing is wrong
     */
    private synchronized void createDBTable(){
        try{
            statement = connection.createStatement();
            statement.execute("CREATE TABLE "+ TABLE_NAME
                    + " (id INTEGER NOT NULL PRIMARY KEY "
                    + "GENERATED ALWAYS AS IDENTITY "
                    + "(START WITH 1, INCREMENT BY 1) , "
                    + "name VARCHAR(50) NOT NULL, "
                    + "level INTEGER NOT NULL, "
                    + "score INTEGER NOT NULL)");
            statement.close();
        } catch (SQLException e) {
            if(DatabaseHelper.tableAlreadyExists(e)){
                return; //It's no problem that the table alreay exist. pass
            }
        }
    }

    /**
     * Method to add a highscore entry to the table.
     * @param name The name
     * @param level Level that was completed
     * @param score Score when completing level
     */
    public synchronized void setHighScore(String name, int level, int score){
        try{
            pStatement = connection.prepareStatement(SQL_ADDSCORE);
            pStatement.setString(1,name);
            pStatement.setInt(2, level);
            pStatement.setInt(3, score);
            pStatement.execute();
            pStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to get all highscores from a single username from the
     * database.
     * @param name Name to match
     * @return Arraylist containing the highscores
     */
    public synchronized ArrayList<DatabaseModel> getHighScore(String name){
        ArrayList<DatabaseModel> playerHighscores
                = new ArrayList<DatabaseModel>();
        try {
            pStatement = connection.prepareStatement(SQL_GETSCOREBYNAME);
            pStatement.setString(1,name);
            resultSet = pStatement.executeQuery();
            try{
                while(resultSet.next()){
                    DatabaseModel tmpModel = new DatabaseModel();
                    tmpModel.setId(resultSet.getInt("id"));
                    tmpModel.setName(resultSet.getString("name"));
                    tmpModel.setLevel(resultSet.getInt("level"));
                    tmpModel.setScore(resultSet.getInt("score"));
                    playerHighscores.add(tmpModel);
                }
            }catch(SQLException e){
                    e.printStackTrace();
            }
            resultSet.close();
            pStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playerHighscores;
    }

    /**
     * Method to get all highscores stored in the whole database table
     * @return Arraylist containing the highscores
     */
    public synchronized ArrayList<DatabaseModel> getAllHighscores(){
        ArrayList<DatabaseModel> highscoreList = new ArrayList<DatabaseModel>();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_GETALLSCORES);
            while(resultSet.next()){
                DatabaseModel tmpModel = new DatabaseModel();
                tmpModel.setId(resultSet.getInt("id"));
                tmpModel.setName(resultSet.getString("name"));
                tmpModel.setLevel(resultSet.getInt("level"));
                tmpModel.setScore(resultSet.getInt("score"));
                highscoreList.add(tmpModel);
            }
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return highscoreList;
    }

    /**
     * Get top three highscores. Sadly derby doesnt allow
     * "LIMIT" as mySQL keyword. Therefor getting top3 is not
     * made by the database itself.
     * @return
     */
    public synchronized ArrayList<DatabaseModel> getThreeHighscores(){
        ArrayList<DatabaseModel> highscoreList = new ArrayList<DatabaseModel>();
        int i = 0;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_GETALLSCORES);
            while(resultSet.next() && i < 3){
                DatabaseModel tmpModel = new DatabaseModel();
                tmpModel.setId(resultSet.getInt("id"));
                tmpModel.setName(resultSet.getString("name"));
                tmpModel.setLevel(resultSet.getInt("level"));
                tmpModel.setScore(resultSet.getInt("score"));
                highscoreList.add(tmpModel);
                i++;
            }
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return highscoreList;
    }

    /**
     * Shut down connection to database
     */
    public synchronized void shutdown() {
        try {
            if(pStatement != null){
                pStatement.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                DriverManager.getConnection(JDBC_URL + JDBC_SHUTDOWN);
                connection.close();
            }
        } catch (SQLException sqlExcept) {
            System.out.println(sqlExcept.getMessage());
        }
    }

    /**
     * Remove highscore entries from database
     */
    public synchronized void removeHighscores(){
        try {
            pStatement = connection.prepareStatement(SQL_REMOVEALL);
            pStatement.execute();
            pStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
