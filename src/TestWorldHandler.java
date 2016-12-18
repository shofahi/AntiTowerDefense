
/**
 * Class testing WorldHandler
 * 
 * @version 6 December 2016
 * @authors Amanda Dahlin, Gustav Nordlander, 
 * 			Samuel Bylund Felixson, Masoud Shofahi
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
	private Position pos;
	private LinkedList<Block> direction;

	@Before
	public void setUp() throws Exception {
		this.generateLvl = new GenerateLevel(20);
		this.worldHandler = new WorldHandler(generateLvl);
	}

	@After
	public void tearDown() throws Exception {
		generateLvl = null;
		worldHandler = null;
	}

	@Test
	public void testRender() {
	
	}

	@Test
	public void testUpdate() {
		
	}

	@Test
	public void testCreateNewAttacker() {
		worldHandler.createNewAttacker(AttackerType.MUSCLEATTACKER);
		//assertEquals(1, generateLvl.getAttackersList().size());
		//System.out.println(generateLvl.getAttackersList().get(0).getClass());
		//assertEquals(generateLvl.getAttackersList().get(0).getClass())
	}
}
