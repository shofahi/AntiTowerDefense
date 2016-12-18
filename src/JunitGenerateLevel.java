/**
 * Classname: JunitGenerateLevel.java
 * Version info 1.0
 * Copyright notice: Masoud Shofahi, Amanda Dahlin, Gustav Norlander, Samuel Bylund
 * Date: 19/12/2017
 * Course: Applikationsutveckling i Java
 */

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.testng.AssertJUnit.assertFalse;

/**
 * Created by MasoudMac on 18/12/16.
 */

public class JunitGenerateLevel {

    private GenerateLevel generateLevel;
    @Before
    public void setUp(){
        generateLevel = new GenerateLevel(20);
        generateLevel.init();
    }

    /**
     * Method will test landOn method
     * Create a Block (path) and the LinkedList should not be empty
     * @throws Exception
     */
    @Test
    public void test1LandOn()throws Exception{
        Position pos = new Position(0,0);
        generateLevel.landOn(pos,BlockType.PATH.toString());
        assertFalse(generateLevel.getBlocks().isEmpty());
    }

    /**
     * Method will test isCreatable
     * Create a defender when there is no zone available
     * @throws Exception if the isCreatable returns true
     */
    @Test
    public void testIsCreatable()throws Exception{

        NormalDefender nd = new NormalDefender(new Position(0,0),generateLevel.getAttackersList());
        assertFalse(generateLevel.isCreatable(nd));
    }

    /**
     * Method will test isCreatable
     * Create a defender and zone
     * @throws Exception if the isCreatable returns false
     */
    @Test
    public void test2IsCreatable()throws Exception{

        Position pos = new Position(0,0);
        generateLevel.landOn(pos,BlockType.PATH.toString());
        NormalDefender nd = new NormalDefender(generateLevel.getZoneList().get(0).getPos(),generateLevel.getAttackersList());
        assertTrue(generateLevel.isCreatable(nd));
    }

    /**
     * Method will test isCreatable
     * Create 2 defender and 1 zone. Place both on the same zone.
     * @throws Exception if the isCreatable returns true the second time
     */
    @Test
    public void test3IsCreatable()throws Exception{

        //Zone
        Position pos = new Position(0,0);
        generateLevel.landOn(pos,BlockType.PATH.toString());

        NormalDefender nd = new NormalDefender(generateLevel.getZoneList().get(0).getPos(),generateLevel.getAttackersList());
        if(generateLevel.isCreatable(nd)){
            generateLevel.getDefendersList().push(nd);
        }

        NormalDefender nd2 = new NormalDefender(generateLevel.getZoneList().get(0).getPos(),generateLevel.getAttackersList());
        assertFalse(generateLevel.isCreatable(nd2));
    }

    /**
     * Method load a xml level that does not contain any start and goal position
     *@throws Exception if the checkStartAndGoalPosition method returns true
     */
    @Test
    public void testCheckStartAndGoalPosition()throws Exception{
        GenerateLevel generateLevel = new GenerateLevel(20,"XmlFiles/missingStartGoalPos.xml");
        generateLevel.loadLevel(1);
        assertFalse(generateLevel.checkStartAndGoalPosition());
    }

    /**
     * This method will try to load a level in a xml file that does not exist
     */
    @Test
    public void testAccessLevelDoesNotExist(){
        GenerateLevel generateLevel = new GenerateLevel(20,"XmlFiles/missingStartGoalPos.xml");
        generateLevel.loadLevel(2);
    }
}
