/**
 * Classname: TestNormalAttacker.java
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

public class TestNormalAttacker {

    private NormalAttacker nA;

    @Before
    public void setUp(){
        LinkedList<Block> dirSign = new LinkedList<>();
        this.nA = new NormalAttacker(new Position(40,40), dirSign);
    }

    @After
    public void tearDown(){
        this.nA = null;
    }

    /**
     * Test that you can set attacker hp
     */
    @Test
    public void testSetHealth(){
        nA.setHealth(555);

        Assert.assertEquals(555, nA.getHealth());
    }

    /**
     * Test method inflictDamage
     */
    @Test
    public void testInflictDamage(){
        nA.setHealth(100);

        nA.inflictDamage(10);

        Assert.assertEquals(90, nA.getHealth());
    }

    /**
     * Test that getTurn will change the direction attacker should move.
     * (SOUTH)
     */
    @Test
    public void testGetTurnSouthDirectionSign() {
        Block turnSouth = new LevelBlocks(60, 40, 20, 20, BlockType.TURNSOUTH);
        nA.getDirSign().add(turnSouth);

        nA.setTurnValue("EAST");

        nA.update();
        nA.getTurn();
        Assert.assertEquals("SOUTH", nA.getTurnValue());
    }

    /**
     * Test that getTurn will change the direction attacker should move.
     * (WEST)
     */
    @Test
    public void testGetTurnWestDirectionSign(){
        Block turnWest = new LevelBlocks(40, 59, 20, 20, BlockType.TURNWEST);
        nA.getDirSign().add(turnWest);

        nA.setTurnValue("South");

        nA.getTurn();
        Assert.assertEquals("WEST", nA.getTurnValue());
    }

    /**
     * Test that getTurn will change the direction attacker should move.
     * (NORTH)
     */
    @Test
    public void testGetTurnNorthDirectionSign(){
        Block turnNorth = new LevelBlocks(59, 40, 20, 20, BlockType.TURNNORTH);
        nA.getDirSign().add(turnNorth);

        nA.setTurnValue("WEST");

        nA.getTurn();
        Assert.assertEquals("NORTH", nA.getTurnValue());
    }

    /**
     * Test that getTurn will change the direction attacker should move.
     * (EAST)
     */
    @Test
    public void testGetTurnEastDirectionSign(){
        Block turnEast = new LevelBlocks(40, 59, 20, 20, BlockType.TURNEAST);
        nA.getDirSign().add(turnEast);

        nA.setTurnValue("NORTH");
        nA.getTurn();
        Assert.assertEquals("EAST", nA.getTurnValue());
    }

    /**
     * test that Update will move the attacker one step in current direction
     */
    @Test
    public void testUpdate(){
        Position attackerPos = new Position(20, 30);
        Position expectedPos = new Position(19, 30);
        nA.setPos(attackerPos);
        nA.setTurnValue("WEST");
        nA.update();
        Assert.assertEquals(nA.getPos(), expectedPos);
    }

    /**
     * Test that should fail. Attacker moves to another direction than
     * compared to.
     */
    @Test
    public void testUpdateShouldFail(){
        Position attackerPos = new Position(20, 30);
        Position anotherPos = new Position(21, 31);
        nA.setPos(attackerPos);
        nA.setTurnValue("EAST");
        nA.update();
        Assert.assertNotEquals(nA.getPos(), anotherPos);
    }
}
