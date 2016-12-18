/**
 * Classname: TestDataBase.java
 * Version info 1.0
 * Copyright notice:    Masoud Shofahi
 *                      Amanda Dahlin
 *                      Gustav Norlander
 *                      Samuel Bylund Felixon
 * Date: 19/12/2017
 * Course: Applikationsutveckling i Java
 */

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;

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

    /**
     * Test adding a highscore to the database table and compare
     * name,level,score with expected values.
     */
    @Test
    public void testAddHighscore(){
        Assert.assertTrue(myDB.getAllHighscores().size() == 0);
        myDB.setHighScore("testName", 2, 10);
        Assert.assertTrue(myDB.getAllHighscores().size() == 1);
        ArrayList<DatabaseModel> score = myDB.getHighScore("testName");
        Assert.assertEquals("testName", score.get(0).getName());
        Assert.assertEquals(2, score.get(0).getLevel());
        Assert.assertEquals(10, score.get(0).getScore());
    }

    /**
     * Test adding multiple entries with same name.
     */
    @Test
    public void testAddMultipleWithSameName(){
        myDB.setHighScore("testName", 1, 1);
        myDB.setHighScore("testName", 2, 2);
        myDB.setHighScore("testName", 3, 3);
        Assert.assertTrue(myDB.getAllHighscores().size() == 3);
        ArrayList<DatabaseModel> score = myDB.getHighScore("testName");
        Assert.assertEquals(3, score.size());
    }

    /**
     * Test the method getThreeHighscores(). Should return top3 highscores.
     */
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

    /**
     * Test the method removeHighscores. Should remove all highscores
     * from database table.
     */
    @Test
    public void testRemoveHighscores(){

        myDB.setHighScore("test1", 1 , 5);
        ArrayList<DatabaseModel> highscores = myDB.getAllHighscores();
        Assert.assertTrue(highscores.size() > 0);
        myDB.removeHighscores();
        highscores = myDB.getAllHighscores();
        Assert.assertFalse(highscores.size() > 0);
    }
}
