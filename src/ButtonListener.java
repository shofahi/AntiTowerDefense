import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;


public class ButtonListener implements ActionListener{

    static LinkedList<ActionEvent> listOfActions;

    public ButtonListener(){
        listOfActions = new LinkedList<>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        listOfActions.add(e);

        /*//STORE BUTTONS
        if(e.getSource() == Store.btnBuyNormal){


            System.out.println("Buying Normal Attacker & Subtracting Money");
        }
        else if(e.getSource() == Store.btnBuySpecial) {
            System.out.println("Buying Special Attacker & Subtracting Money");
        }
        //WINDOW BUTTONS
        else if(e.getSource() == Window.pause){
            System.out.println("Pausar ..");
        }
        else if(e.getSource() == Window.start){
            System.out.println("Start Game ..");

        }
        else if(e.getSource() == Window.restart){
            System.out.println("Restart level ..");
        }
        else if(e.getSource() == Window.quit){
            System.out.println("Quitting ..");

        }
        else if(e.getSource() == Window.about){
            System.out.println("About ..");
            JOptionPane.showMessageDialog(null, "Authors:\n\nAmanda Dahlin\n"
                    + "Gustav Nordlander\nSamuel Bylund Felixson\nMasoud Shofahi\n\n\u00a9 2016");
        }
        else if(e.getSource() == Window.about){
            System.out.println("Helping ..");
            JOptionPane.showMessageDialog(null, "Help:\n\n Play by adding attackers to the\n"
                    + "field. Different attackers have different \nprices. ETC.");
        }
        else if(e.getSource() == Window.changeLevel){
            System.out.println("Changing Level ..");

        }*/


    }

}
