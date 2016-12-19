/**
 * Classname: TestStore.java
 * Version info 1.0
 * Copyright notice:    Amanda Dahlin
 * Date: 17/12/2017
 * Course: Applikationsutveckling i Java
 */
import static org.junit.Assert.*;
import java.awt.Color;
import javax.swing.JPanel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Class testing Store
 * 
 * @author Amanda
 * @version 6 December 2016
 */
public class TestStore {

	private Store store;
	private ButtonListener btnListener;

	@Before
	public void setUp() throws Exception {
		//this.wh = new WorldHandler(20);
		this.btnListener = new ButtonListener();
		this.store = new Store(btnListener);
	}

	@After
	public void tearDown() throws Exception {
		btnListener = null;
		store = null;
	}
	
	@Test
    public void testBuildStore() {
		JPanel testPanel = store.buildStore();
		assertEquals(testPanel.getBackground(), Color.BLACK);
		assertEquals(testPanel.getComponentCount(), 5);
    }
    
	@Test
    public void testHeaderPanel(){
		JPanel testPanel = store.headerPanel();
		assertEquals(testPanel.getBackground(), Color.BLACK);
    }
    
	@Test
    public void testSpecialAttackerPanel(){
		JPanel testPanel = store.specialAttackerPanel();
		assertEquals(testPanel.getComponentCount(), 2);
		assertEquals(testPanel.getBackground(), Color.BLACK);
    }
    
	@Test
    public void testNormalAttackerPanel(){
		JPanel testPanel = store.normalAttackerPanel();
		assertEquals(testPanel.getComponentCount(), 2);
		assertEquals(testPanel.getBackground(), Color.BLACK);
    }
	
	@Test
    public void testSetAndGetWallet(){
    	store.setWallet(10);
    	assertTrue(store.getWallet() == 10);
    }
    
	@Test
    public void testSetAndGetNormalAttackerPrice(){
    	store.setNormalAttackerPrice(50);
    	assertTrue(store.getNormalAttackerPrice() == 50);
    }
    
	@Test
    public void testSetAndGetSpecialAttackerPrice(){
    	store.setSpecialAttackerPrice(100);
    	assertTrue(store.getSpecialAttackerPrice() == 100);
    }
	
	@Test
    public void testSetAndGetMuscleAttackerPrice(){
    	store.setMuscleAttackerPrice(150);
    	assertTrue(store.getMuscleAttackerPrice() == 150);
    }

	@Test
	public void testMuscleAttackerPanel() {
		JPanel testPanel = store.muscleAttackerPanel();
		assertEquals(2, testPanel.getComponentCount());
		assertEquals(testPanel.getBackground(), Color.BLACK);
	}

	@Test
	public void testHighScorePanel() {
		JPanel testPanel = store.highScorePanel();
		assertEquals(testPanel.getComponentCount(), 4);
		assertEquals(testPanel.getBackground(), Color.BLACK);
	}

	@Test
	public void testSetAndGetFirstPlace() {
		store.setFirstPlace("Lotta",1, 243);
		assertEquals(store.getFirstPlace(), 243);
	}

	@Test
	public void testSetAndGetSecondPlace() {
		store.setSecondPlace("Johannes",1, 202);
		assertEquals(store.getSecondPlace(), 202);
	}

	@Test
	public void testSetAndGetThirdPlace() {
		store.setThirdPlace("Petronella",1, 79);
		assertEquals(store.getThirdPlace(), 79);
	}
}
