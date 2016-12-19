/**
 * Classname: TestButtonListener.java
 * Version info 1.0
 * Copyright notice:    Amanda Dahlin
 * Date: 17/12/2017
 * Course: Applikationsutveckling i Java
 */

import static org.junit.Assert.*;
import javax.swing.JButton;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestButtonListener {

	private ButtonListener btnListener;

	@Before
	public void setUp() throws Exception {
		btnListener = new ButtonListener();
	}

	@After
	public void tearDown() throws Exception {
		btnListener = null;
	}
	
	@Test
    public void testActionPerformed() {
		JButton btn = new JButton();
		btn.addActionListener(btnListener);
		btn.doClick();
		assertTrue(btnListener.getListOfActions().size() == 1);
		
    }
}
