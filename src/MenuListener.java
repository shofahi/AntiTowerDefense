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

	@Override
	public void actionPerformed(ActionEvent e) {
		String clicked = e.getActionCommand();
		
		switch (clicked) {
			case "Pause":
				System.out.println("Pausar ..");
				break;
			case "Start Game":
				System.out.println("Start Game ..");
				break;
			case "Restart Level":
				System.out.println("Restart level ..");
				break;
			case "Quit":
				System.out.println("Quitting ..");
				break;
			case "About":
				System.out.println("About ..");
				JOptionPane.showMessageDialog(null, "Authors:\n\nAmanda Dahlin\n"
						+ "Gustav Nordlander\nSamuel Bylund Felixson\nMasoud Shofahi\n\n\u00a9 2016");
				break;
			case "Help":
				System.out.println("Helping ..");
				JOptionPane.showMessageDialog(null, "Help:\n\n Play by adding attackers to the\n"
						+ "field. Different attackers have different \nprices. ETC.");
				break;
			case "Level 1":
				System.out.println("Starting level 1 ..");
				break;	
			case "Level 2":
				System.out.println("Starting level 2 ..");
				break;
			default:
				System.out.println("Do nothing?");
				break;
		}
	}

}
