import javax.swing.*;
import java.awt.*;

/**
 * Class creating menu bar including buttons & listener
 * 
 * @version 28 November 2016
 * @authors Amanda Dahlin, Gustav Nordlander, 
 * 			Samuel Bylund Felixson, Masoud Shofahi
 */
public class Window extends JFrame {

	private ButtonListener buttonListener;

	private JMenuItem start;
	private JMenuItem pause;
	private JMenuItem restart;
	private JMenuItem quit;
	private JMenuItem about;
	private JMenuItem help;
	private JRadioButtonMenuItem changeLevel;

	/**
	 * Constructor creating Window, calls method that 
	 * creates menu bar, and some settings
	 * 
	 * @param  title
	 * @param  width
	 * @param  height
	 * @param  buttonListener
	 */
	public Window(String title, int width, int height,
                  ButtonListener buttonListener) {

		super(title);

		JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		ToolTipManager.sharedInstance().setLightWeightPopupEnabled(false);

		this.buttonListener = buttonListener;

		setSize(new Dimension(width, height));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setJMenuBar(createMenu());

		setLocationRelativeTo(null);
		setResizable(false); // Temporary
	}

	/**
	 * Methods that sets the gui visible
	 */
	public void createGUI() {

		setVisible(true);
	}

	/**
	 * Method that creates the actual JMenuBar with the 
	 * different drop down menus and their buttons
	 * 
	 * @return JMenuBar menuBar
	 */
	public JMenuBar createMenu() {
		JMenuBar menuBar;
		JMenu menu;
		JMenu secondMenu;
		// Create the Menu & MenuBar
		menuBar = new JMenuBar();
		menu = new JMenu("TowerPower");

		// Group of JMenuItems & ActionListeners
		start = new JMenuItem("Start Game");
		start.addActionListener(buttonListener);
		menu.add(start);
		restart = new JMenuItem("Restart Level");
		restart.addActionListener(buttonListener);
		menu.add(restart);
		pause = new JMenuItem("Pause");
		pause.addActionListener(buttonListener);
		menu.add(pause);

		menu.addSeparator();

		// Radiobutton for choosing level + ActionListeners
		ButtonGroup group = new ButtonGroup();
		changeLevel = new JRadioButtonMenuItem("Change Level");
		changeLevel.setSelected(false);
		changeLevel.addActionListener(buttonListener);
		group.add(changeLevel);
		menu.add(changeLevel);

		// Separator for style
		menu.addSeparator();

		quit = new JMenuItem("Quit");
		quit.addActionListener(buttonListener);
		menu.add(quit);

		// Second menu in the MenuBar.
		secondMenu = new JMenu("Information");
		about = new JMenuItem("About");
		help = new JMenuItem("Help");
		about.addActionListener(buttonListener);
		help.addActionListener(buttonListener);
		secondMenu.add(about);
		secondMenu.add(help);

		menuBar.add(menu);
		menuBar.add(secondMenu);

		return menuBar;
	} 
	
	/** 
	 * Returns menu item start
	 * @return JMenuItem start
	 */ 
	public JMenuItem getStart() {
		return start;
	}

	/**
	 * Returns menu item pause
	 * @return JMenuItem pause
	 */
	public JMenuItem getPause() {
		return pause;
	}

	/**
	 * Returns menu item restart
	 * @return JMenuItem restart
	 */
	public JMenuItem getRestart() {
		return restart;
	}

	/**
	 * Returns menu item quit
	 * @return JMenuItem quit
	 */
	public JMenuItem getQuit() {
		return quit;
	}

	/**
	 * Returns menu item about
	 * @return JMenuItem about
	 */
	public JMenuItem getAbout() {
		return about;
	}
	
	/**
	 * Returns menu item help
	 * @return JMenuItem help
	 */
	public JMenuItem getHelp() {
		return help;
	}

	/**
	 * Returns radio button in menu
	 * @return JRadioButtonMenuItem changeLevel
	 */
	public JRadioButtonMenuItem getChangeLevel() {
		return changeLevel;
	}

	/**
	 * Toggles text in menu item  
	 */
	public void updateButtonText() {
		if (pause.getText().equals("Pause")) {
			pause.setText("Resume");
		} else {
			pause.setText("Pause");
		}
	}
}
