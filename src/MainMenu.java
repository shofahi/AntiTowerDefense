/**
 * @author Amanda Dahlin
 * @version 21 November 2016
 */

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

public class MainMenu {

	    JMenu menu;
	    JMenuItem menuItemUndo;
	    JMenuItem menuItemRedo;

	    public MainMenu() {}
	    
	    public JMenuBar createMenu(){
	    	
	    	// Attributes
	    	JMenuBar menuBar;
	    	JMenu menu;
	    	JMenu secondMenu;
	    	JMenuItem start;
	    	JMenuItem pause;
	    	JMenuItem restart;
	    	JMenuItem quit;
	    	JMenuItem about;
	    	JMenuItem help;
	    	JRadioButtonMenuItem rbLevel1;
	    	JRadioButtonMenuItem rbLevel2;
	    	
	    	//Create the Menu & MenuBar
	    	menuBar = new JMenuBar();
	    	menu = new JMenu("TowerPower");

	    	// Group of JMenuItems & ActionListeners
	    	start = new JMenuItem("Start Game");
	    	start.addActionListener(new MenuListener());
	    	menu.add(start);
	    	restart = new JMenuItem("Restart Level");
	    	restart.addActionListener(new MenuListener());
	    	menu.add(restart);
	    	pause = new JMenuItem("Pause");
	    	pause.addActionListener(new MenuListener());
	    	menu.add(pause);

	    	// Separator for style
	    	menu.addSeparator();
	    	
	    	// Radiobuttons for choosing level + ActionListeners
	    	ButtonGroup group = new ButtonGroup();
	    	rbLevel1 = new JRadioButtonMenuItem("Level 1");
	    	rbLevel1.setSelected(true);
	    	rbLevel1.addActionListener(new MenuListener());
	    	group.add(rbLevel1);
	    	menu.add(rbLevel1);
	    	rbLevel2 = new JRadioButtonMenuItem("Level 2");
	    	rbLevel2.addActionListener(new MenuListener());
	    	group.add(rbLevel2);
	    	menu.add(rbLevel2);
	    	
	    	// Separator for style
	    	menu.addSeparator();
	    	
	    	quit = new JMenuItem("Quit");
	    	quit.addActionListener(new MenuListener());
	    	menu.add(quit);
	    	
	    	// Second menu in the MenuBar.
	    	secondMenu = new JMenu("Information");
	    	about = new JMenuItem("About");
	    	help = new JMenuItem("Help");
	    	about.addActionListener(new MenuListener());
	    	help.addActionListener(new MenuListener());
	    	secondMenu.add(about);
	    	secondMenu.add(help);

	    	menuBar.add(menu);
	    	menuBar.add(secondMenu);
	    	
	    	return menuBar;
	    }
}
