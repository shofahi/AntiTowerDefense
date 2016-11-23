import javax.swing.*;
import java.awt.*;

public class Window extends JFrame{

    private MenuListener menuListener;


    private JMenuItem start;
    private JMenuItem pause;
    private JMenuItem restart;
    private JMenuItem quit;
    private JMenuItem about;
    private JMenuItem help;
    private JRadioButtonMenuItem getChangeLevel;

    public Window(String title, int width,int height){

        super(title);

        menuListener = new MenuListener(this);

        setSize(new Dimension(width,height));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(createMenu());

        setLocationRelativeTo(null);
        setResizable(false); //Temporary
    }

    public void createGUI(){
        setVisible(true);
    }

    public JMenuBar createMenu(){

        JMenuBar menuBar;
        JMenu menu;
        JMenu secondMenu;
        //Create the Menu & MenuBar
        menuBar = new JMenuBar();
        menu = new JMenu("TowerPower");

        // Group of JMenuItems & ActionListeners
        start = new JMenuItem("Start Game");
        start.addActionListener(menuListener);
        menu.add(start);
        restart = new JMenuItem("Restart Level");
        restart.addActionListener(menuListener);
        menu.add(restart);
        pause = new JMenuItem("Pause");
        pause.addActionListener(menuListener);
        menu.add(pause);

        // Separator for style
        menu.addSeparator();

        // Radiobutton for choosing level + ActionListeners
        ButtonGroup group = new ButtonGroup();
        getChangeLevel = new JRadioButtonMenuItem("Change Level");
        getChangeLevel.setSelected(false);
        getChangeLevel.addActionListener(menuListener);
        group.add(getChangeLevel);
        menu.add(getChangeLevel);


        // Separator for style
        menu.addSeparator();

        quit = new JMenuItem("Quit");
        quit.addActionListener(menuListener);
        menu.add(quit);

        // Second menu in the MenuBar.
        secondMenu = new JMenu("Information");
        about = new JMenuItem();
        help = new JMenuItem("Help");
        about.addActionListener(menuListener);
        help.addActionListener(menuListener);
        secondMenu.add(about);
        secondMenu.add(help);

        menuBar.add(menu);
        menuBar.add(secondMenu);

        return menuBar;
    }


    public JMenuItem getStart() {
        return start;
    }

    public JMenuItem getPause() {
        return pause;
    }

    public JMenuItem getRestart() {
        return restart;
    }

    public JMenuItem getQuit() {
        return quit;
    }

    public JMenuItem getAbout() {
        return about;
    }

    public JMenuItem getHelp() {
        return help;
    }

    public JRadioButtonMenuItem getChangeLevel() {
        return getChangeLevel;
    }
}
