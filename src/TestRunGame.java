import org.junit.Before;
import org.junit.Test;
import org.testng.Assert;

import static junit.framework.TestCase.assertTrue;
import static org.testng.AssertJUnit.assertFalse;

/**
 * Classname: TestRunGame.java
 * Version info 1.0
 * Copyright notice:    Masoud Shofahi
 *                      Amanda Dahlin
 *                      Gustav Norlander
 *                      Samuel Bylund Felixon
 * Date: 19/12/2017
 * Course: Applikationsutveckling i Java
 */


public class TestRunGame {

    private RunGame runGame;
    @Before
    public void setUp(){
        runGame = new RunGame("TestRunGame","XmlFiles/levels.xml");

    }

    /**
     * This method will check if the game ends when user has less
     * than 10 gold left
     * @throws Exception if isGameOver method returns false
     */
    @Test
    public void testIsGameOver()throws Exception {
        runGame.init();
        runGame.getStore().setWallet(9);
        assertTrue(runGame.isGameOver());
    }

    /**
     * This method will check if the game ends when user has 10 or more gold left
     * @throws Exception if isGameOver method returns true
     */
    @Test
    public void test2IsGameOver()throws Exception {
        runGame.init();
        runGame.getStore().setWallet(10);
        assertFalse(runGame.isGameOver());
    }

    /**
     * Create as many attacker required to finish the level.
     * Place them on goal position. And update game logic.
     * @throws Exception if didFinishLevel return false
     */
    @Test
    public void testDidFinishLevel()throws Exception{
        System.out.println(runGame.getWorldHandler().getNrOfAttackersToGoal());

        runGame.init();

        Position goalPosition = new Position(runGame.getGenerateLvl().
                getGoalPosition().x,
                runGame.getGenerateLvl().getGoalPosition().y);

        for (int i = 0; i < runGame.getGenerateLvl().getAttackersToFinish()
                ; i++){
            runGame.getGenerateLvl().getAttackersList().
                    add(new NormalAttacker(goalPosition,
                            runGame.getGenerateLvl().getBlocks()));
            runGame.getWorldHandler().update();
        }

        assertTrue(runGame.didFinishLevel());
    }

    /**
     * Create one less attacker than required to finish the level.
     * Place de on goal position. And update game logic.
     * @throws Exception if didFinishLevel return true
     */
    @Test
    public void test2DidFinishLevel()throws Exception{
        runGame.init();
        Position goalPosition = new Position(runGame.getGenerateLvl().
                getGoalPosition().x,
                runGame.getGenerateLvl().getGoalPosition().y);

        for (int i = 0; i < runGame.getGenerateLvl().getAttackersToFinish()-1
                ;i++){
            runGame.getGenerateLvl().getAttackersList().
                    add(new NormalAttacker(goalPosition,
                            runGame.getGenerateLvl().getBlocks()));
            runGame.getWorldHandler().update();
        }

        assertFalse(runGame.didFinishLevel());
    }

    /**
     * Place one attacker to goal.
     * Restarts and checks if number of attackers reached goal is 0.
     * Also set wallet to 0 gold. After Restart the wallet should not be 0.
     * Note: the actual restart method is not called here because it will
     * require to create a GUI, init does the same thing.
     * @throws Exception if the values are equal
     */
    @Test
    public void testRestartLevel() throws Exception{
        runGame.init();
        runGame.getStore().setWallet(0);

        Position goalPosition = new Position(runGame.getGenerateLvl().
                getGoalPosition().x,
                runGame.getGenerateLvl().getGoalPosition().y);

        for (int i = 0; i < runGame.getGenerateLvl().getAttackersToFinish()-1
                ;i++){
            runGame.getGenerateLvl().getAttackersList().
                    add(new NormalAttacker(goalPosition,
                            runGame.getGenerateLvl().getBlocks()));
            runGame.getWorldHandler().update();
        }

        int actualGold = runGame.getStore().getWallet();
        int actualAttackerReachedGoal = runGame.getWorldHandler().
                getNrOfAttackersToGoal();

        runGame.init();

        Assert.assertNotEquals(actualGold,runGame.getStore().getWallet());

        Assert.assertNotEquals(actualAttackerReachedGoal,
                runGame.getWorldHandler().getNrOfAttackersToGoal());
    }
}
