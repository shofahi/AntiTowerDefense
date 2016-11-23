/**
 * Handling action events in menu bar
 * 
 * @author Amanda Dahlin
 * @version 21 November 2016
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class MenuListener implements ActionListener  {

	private Window win;
    public MenuListener (Window win){
       this.win = win;
    }

	@Override
	public void actionPerformed(ActionEvent e) {

        if(e.getSource() == win.getPause()){
            System.out.println("Pausar ..");
        }
        else if(e.getSource() == win.getStart()){
            System.out.println("Start Game ..");

        }
        else if(e.getSource() == win.getRestart()){
            System.out.println("Restart level ..");
        }
        else if(e.getSource() == win.getQuit()){
            System.out.println("Quitting ..");

        }
        else if(e.getSource() == win.getAbout()){
            System.out.println("About ..");
            JOptionPane.showMessageDialog(null, "Authors:\n\nAmanda Dahlin\n"
                    + "Gustav Nordlander\nSamuel Bylund Felixson\nMasoud Shofahi\n\n\u00a9 2016");
        }
        else if(e.getSource() == win.getHelp()){
            System.out.println("Helping ..");
            JOptionPane.showMessageDialog(null, "Help:\n\n Play by adding attackers to the\n"
                    + "field. Different attackers have different \nprices. ETC.");
        }
        else if(e.getSource() == win.getChangeLevel()){
            System.out.println("Changing Level ..");

        }

	}

}
