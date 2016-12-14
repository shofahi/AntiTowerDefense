import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Samuel on 2016-12-12.
 */
public class Database {
    private static final String DRIVER = "org.apache.derby.jdbc.ClientDriver";
    private static final String JDBC_URL = "jdbc:derby:antiTDDatabase";
    private static final String JDBC_CREATE = ";create=true";
    private static final String JDBC_SHUTDOWN = ";shutdown=true";
    private static final String tableName = "antiTDHighscores";

    private Connection connection = null;
    private Statement statement = null;
    private PreparedStatement pStatement = null;
    private ResultSet resultSet = null;

    private final static String SQL_GETALLSCORES =
            "SELECT * FROM "+tableName+" ORDER BY level DESC,score DESC";

    private final static String SQL_GETSCOREBYNAME =
            "SELECT * FROM "+tableName+ " WHERE name = ?";

    private final static String SQL_ADDSCORE =
            "INSERT INTO "+tableName+" (name, level, score) VALUES (?, ?, ?)";

    private final static String SQL_REMOVEALL =
            "DELETE FROM "+tableName+" WHERE 1=1";

    public Database() throws SQLException, ClassNotFoundException {
        setUpDBConnection();
        createDBTable();
    }

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

    private synchronized void createDBTable(){
        try{
            //Class.forName(DRIVER).newInstance();
            //connection = DriverManager.getConnection(JDBC_URL);
            statement = connection.createStatement();
            statement.execute("CREATE TABLE "+tableName+" " +
                        "(id INTEGER NOT NULL PRIMARY KEY " +
                        "GENERATED ALWAYS AS IDENTITY " +
                        "(START WITH 1, INCREMENT BY 1) , " +
                        "name VARCHAR(50) NOT NULL, " +
                        "level INTEGER NOT NULL, " +
                        "score INTEGER NOT NULL)");
            statement.close();
        } catch (SQLException e) {
            if(DatabaseHelper.tableAlreadyExists(e)){
                return; //It's no problem that the table alreay exist. pass
            }
        }
    }

    public synchronized void setHighScore(String name, int level, int score){
        try{
            //connection = DriverManager.getConnection(JDBC_URL);
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

    public synchronized DatabaseModel getHighScore(String name){
        DatabaseModel playerInfo = null;
        try {
            //connection = DriverManager.getConnection(JDBC_URL);
            pStatement = connection.prepareStatement(SQL_GETSCOREBYNAME);
            pStatement.setString(1,name);
            resultSet = pStatement.executeQuery();
            try{
                resultSet.next();
                playerInfo = new DatabaseModel(resultSet.getInt(1),
                                                resultSet.getString(2),
                                                resultSet.getInt(3),
                                                resultSet.getInt(4));
            }catch(SQLException e){
                    e.printStackTrace();
            }
            resultSet.close();
            pStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playerInfo;
    }

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

    public synchronized void shutdown()
    {
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

        }
    }

    public synchronized void removeHighscores(){
        try {
            pStatement = connection.prepareStatement(SQL_REMOVEALL);
            pStatement.execute();
            pStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public synchronized void printHighScore(ArrayList<DatabaseModel> hsList){
        System.out.println("ID:\t\tNAME:\t\tLEVEL:\t\tSCORE:");
        for(DatabaseModel tmpModel : hsList){
            System.out.println(tmpModel.getId()+"\t\t"+
                                tmpModel.getName()+"\t\t"+
                                tmpModel.getLevel()+"\t\t"+
                                tmpModel.getScore());
        }
    }
}
