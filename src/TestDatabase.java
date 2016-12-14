import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Samuel on 2016-12-12.
 */
public class TestDatabase {
    Database myDB = null;

    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        myDB = new Database();
    }

    @After
    public void tearDown(){
        myDB.removeHighscores();
        myDB.shutdown();
    }

    @Test
    public void testAddHighscore(){
        Assert.assertTrue(myDB.getAllHighscores().size() == 0);
        myDB.setHighScore("testName", 2, 10);
        Assert.assertTrue(myDB.getAllHighscores().size() > 0);
        Assert.assertEquals(10, myDB.getHighScore("testName").getScore());
        Assert.assertEquals("testName", myDB.getHighScore("testName").getName());
    }

    @Test
    public void testRemoveHighscores(){

        myDB.setHighScore("test1", 1 , 5);
        ArrayList<DatabaseModel> highscores = myDB.getAllHighscores();
        Assert.assertTrue(highscores.size() > 0);
        myDB.removeHighscores();
        highscores = myDB.getAllHighscores();
        Assert.assertFalse(highscores.size() > 0);
    }

    @Test
    public void testGetDatabaseModel(){
        myDB.setHighScore("kalle", 3, 3);
        myDB.setHighScore("kalle", 3, 53);
        myDB.setHighScore("kalle", 3, 22);
        myDB.setHighScore("kalle", 3, 100);
        myDB.setHighScore("kalle", 3, 500);
        myDB.setHighScore("anka", 4, 355);
        myDB.setHighScore("anka", 2, 355);
        myDB.setHighScore("tjalle", 2, 1337);
        myDB.printHighScore(myDB.getAllHighscores());
        System.out.println("BELOW IS TOP THREE!!!");
        myDB.printHighScore(myDB.getThreeHighscores());
    }
}
