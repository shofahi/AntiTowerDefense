/**
 * Classname: TestWindow.java
 * Version info 1.0
 * Copyright notice:    Amanda Dahlin
 * Date: 17/12/2017
 * Course: Applikationsutveckling i Java
 */
import static org.junit.Assert.*;

import javax.swing.JMenuBar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestWindow {

	private Window window;
	private ButtonListener btnListener;

	@Before
	public void setUp() throws Exception {
		btnListener = new ButtonListener(); 
		this.window = new Window("TestWindow", 800, 600, btnListener);
	}

	@After
	public void tearDown() throws Exception {
		btnListener = null;
		this.window = null;
	}
	
	@Test
	public void testConstructor(){
		assertNotNull(btnListener);
		assertEquals(1 ,window.getComponentCount());
		assertFalse(window.isResizable());
	}

	@Test
	public void testCreateGUI(){
		window.createGUI();
		assertTrue(window.isVisible());
	}

	@Test
	public void testUpdateButtonText(){
		window.updateButtonText();
		assertTrue(window.getPause().getText().equals("Pause") || window.getPause().getText().equals("Resume"));
	}

	@Test
	public void testCreateMenu(){
		JMenuBar test = window.createMenu();
		assertEquals(2 ,test.getComponentCount());
	}
	
}
	
