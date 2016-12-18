/**
 * Classname: TestNormalDefender.java
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

import java.util.LinkedList;

public class TestNormalDefender {

    private NormalDefender nD;

    @Before
    public void setUp(){
        LinkedList<Attacker> attackersList = new LinkedList<>();
        nD = new NormalDefender(new Position(500,500), attackersList);
    }

    @After
    public void tearDown(){
        nD = null;
    }

    /**
     * Check that the tower has expected damage
     */
    @Test
    public void checkDamage(){
        int expectedDmg = 1;

        Assert.assertEquals(expectedDmg, nD.getDamage());
    }

    /**
     * Test that the tower has expected range
     */
    @Test
    public void checkRange(){
        int expectedRange = 300;

        Assert.assertEquals(expectedRange, nD.getRange());
    }

    /**
     * Test that the tower will have correct damage after changing it
     */
    @Test
    public void changeDamage(){
        nD.setDamage(123);

        Assert.assertEquals(123, nD.getDamage());
    }

    /**
     * Test that the tower will have correct range after changing it
     */
    @Test
    public void changeRange(){
        nD.setRange(321);

        Assert.assertEquals(321, nD.getRange());
    }

    /**
     * Test that if an attacker steps into tower range it gets added to
     * the towers' "enemyList"
     */
    @Test
    public void testUpdateAttackerInRange(){
        NormalAttacker nA = new NormalAttacker(new Position(500,100),null);
        nD.getAttackerList().add(nA);
        nD.update();
        Assert.assertEquals(0, nD.getEnemyList().size());
        nA.setPos(new Position(500,nD.getRange()*2));
        nD.update();
        Assert.assertEquals(1, nD.getEnemyList().size());
    }

    /**
     * Test that if an attacker steps out of tower range it gets removed
     * from the towers' "enemyList"
     */
    @Test
    public void testUpdateAttackerOutOfRange(){
        NormalAttacker nA = new NormalAttacker(new Position(500,400),null);
        nD.getAttackerList().add(nA);
        nD.update();
        Assert.assertEquals(1, nD.getEnemyList().size());
        nA.setPos(new Position(500, 500-nD.getRange()*2));
        nD.update();
        Assert.assertEquals(0, nD.getEnemyList().size());
    }

    /**
     * Test that the tower will eventuall kill an attacker if attacking it.
     */
    @Test
    public void testUpdateKillAttacker(){
        NormalAttacker nA = new NormalAttacker(new Position(500,400),null);
        nD.getAttackerList().add(nA);
        nD.update();
        Assert.assertEquals(1, nD.getEnemyList().size());
        while(nD.getEnemyList().size() > 0){
            nD.update();
        }
        Assert.assertEquals(0, nD.getEnemyList().size());
    }

}
