/**
 * Class testing TestButtonListener
 * 
 * @authors Amanda Dahlin, Gustav Nordlander, 
 * 			Samuel Bylund Felixson, Masoud Shofahi
 * @version 6 December 2016
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
