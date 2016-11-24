import javax.swing.*;
import java.awt.*;

public class Window extends JFrame{

    private ButtonListener buttonListener;


    static JMenuItem start;
    static JMenuItem pause;
    static JMenuItem restart;
    static JMenuItem quit;
    static JMenuItem about;
    static JMenuItem help;
    static JRadioButtonMenuItem changeLevel;

    public Window(String title, int width,int height){

        super(title);

        buttonListener = new ButtonListener();

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
        start.addActionListener(buttonListener);
        menu.add(start);
        restart = new JMenuItem("Restart Level");
        restart.addActionListener(buttonListener);
        menu.add(restart);
        pause = new JMenuItem("Pause");
        pause.addActionListener(buttonListener);
        menu.add(pause);

        // Separator for style
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
        about = new JMenuItem();
        help = new JMenuItem("Help");
        about.addActionListener(buttonListener);
        help.addActionListener(buttonListener);
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
        return changeLevel;
    }
}
