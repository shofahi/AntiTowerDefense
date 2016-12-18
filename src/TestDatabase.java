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
        ArrayList<DatabaseModel> score = myDB.getHighScore("testName");
        Assert.assertEquals("testName", score.get(0).getName());
        Assert.assertEquals(2, score.get(0).getLevel());
        Assert.assertEquals(10, score.get(0).getScore());
    }

    @Test
    public void testAddMultipleWithSameName(){
        myDB.setHighScore("testName", 1, 1);
        myDB.setHighScore("testName", 2, 2);
        myDB.setHighScore("testName", 3, 3);
        Assert.assertTrue(myDB.getAllHighscores().size() == 3);
        ArrayList<DatabaseModel> score = myDB.getHighScore("testName");
        Assert.assertEquals(3, score.size());
    }

    @Test
    public void testGetTopThree(){
        myDB.setHighScore("testName1", 10, 10);
        myDB.setHighScore("testName2", 10, 9);
        myDB.setHighScore("testName3", 10, 1);
        myDB.setHighScore("testName4", 10, 8);
        myDB.setHighScore("testName5", 9, 2);
        myDB.setHighScore("testName6", 8, 3);
        ArrayList<DatabaseModel> topThree = myDB.getThreeHighscores();
        Assert.assertEquals(3, topThree.size());

        Assert.assertEquals("testName1", topThree.get(0).getName());
        Assert.assertEquals("testName2", topThree.get(1).getName());
        Assert.assertEquals("testName4", topThree.get(2).getName());
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
    public void testGetTopThreeHighscores(){
        myDB.setHighScore("test1", 1, 5);
        myDB.setHighScore("test2", 1, 5);
        myDB.setHighScore("test3", 1, 5);
        ArrayList<DatabaseModel> highscores = myDB.getThreeHighscores();
        Assert.assertTrue(highscores.size() == 3);
    }

}
