/**
 * Created by Samuel on 2016-11-24.
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

    @Test
    public void testSetHealth(){
        nA.setHealth(555);

        Assert.assertEquals(555, nA.getHealth());
    }

    @Test
    public void testInflictDamage(){
        nA.setHealth(100);

        nA.inflictDamage(10);

        Assert.assertEquals(90, nA.getHealth());
    }

    @Test
    public void testGetTurnSouthDirectionSign() {
        Block turnSouth = new LevelBlocks(60, 40, 20, 20, BlockType.TURNSOUTH);
        nA.getDirSign().add(turnSouth);

        nA.setTurnValue("EAST");

        nA.update();
        nA.getTurn();
        Assert.assertEquals("SOUTH", nA.getTurnValue());
    }

    @Test
    public void testGetTurnWestDirectionSign(){
        Block turnWest = new LevelBlocks(40, 59, 20, 20, BlockType.TURNWEST);
        nA.getDirSign().add(turnWest);

        nA.setTurnValue("South");

        nA.getTurn();
        Assert.assertEquals("WEST", nA.getTurnValue());
    }

    @Test
    public void testGetTurnNorthDirectionSign(){
        Block turnNorth = new LevelBlocks(59, 40, 20, 20, BlockType.TURNNORTH);
        nA.getDirSign().add(turnNorth);

        nA.setTurnValue("WEST");

        nA.getTurn();
        Assert.assertEquals("NORTH", nA.getTurnValue());
    }

    @Test
    public void testGetTurnEastDirectionSign(){
        Block turnEast = new LevelBlocks(40, 59, 20, 20, BlockType.TURNEAST);
        nA.getDirSign().add(turnEast);

        nA.setTurnValue("NORTH");
        nA.getTurn();
        Assert.assertEquals("EAST", nA.getTurnValue());
    }

    @Test
    public void testUpdate(){
        Position attackerPos = new Position(20, 30);
        Position expectedPos = new Position(19, 30);
        nA.setPos(attackerPos);
        nA.setTurnValue("WEST");
        nA.update();
        Assert.assertEquals(nA.getPos(), expectedPos);
    }

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
