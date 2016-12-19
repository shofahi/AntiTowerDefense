/**
 * Classname: TestWorldHandler.java
 * Version info 1.0
 * Copyright notice:    Masoud Shofahi
 *                      Amanda Dahlin
 *                      Gustav Norlander
 *                      Samuel Bylund Felixon
 * Date: 19/12/2017
 * Course: Applikationsutveckling i Java
 */

import static org.junit.Assert.*;

import java.awt.Graphics;
import java.util.LinkedList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestWorldHandler {

	private WorldHandler worldHandler;
	private GenerateLevel generateLvl;

	@Before
	public void setUp() throws Exception {
		this.generateLvl = new GenerateLevel(20,"XmlFiles/levels.xml");
		generateLvl.init();
		this.worldHandler = new WorldHandler(generateLvl);
	}

	@After
	public void tearDown() throws Exception {
		generateLvl = null;
		worldHandler = null;
	}


	/**
	 * This method will try to create a MuscleAttacker
	 * @throws Exception if the newly created Attacker is not a MuscleAttacker
	 */
	@Test
	public void testCreateNewAttacker() throws  Exception{

	    generateLvl.loadLevel(1);
        worldHandler.createNewAttacker(AttackerType.MUSCLEATTACKER);

        assertTrue(generateLvl.getAttackersList().getLast()
				instanceof MuscleAttacker);
	}

    /**
     * This method will try to create a NormalAttacker
     * @throws Exception if the newly created Attacker is a MuscleAttacker
     */
	@Test
    public void test2CreateNewAttacker()throws Exception{
        generateLvl.loadLevel(1);
        worldHandler.createNewAttacker(AttackerType.NORMALATTACKER);

        assertFalse(generateLvl.getAttackersList().getLast()
                instanceof MuscleAttacker);
    }

    /**
     * This method will try to create a SpecialAttack
     * @throws Exception if the newly created Attacker has no special ID
     */
    @Test
    public void test3CreateNewAttacker()throws Exception{
        generateLvl.loadLevel(1);
        worldHandler.createNewAttacker(AttackerType.SPECIALATTACKER);

        assertNotNull(worldHandler.getSpecialID());
    }



}
