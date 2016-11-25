import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;


public class ButtonListener implements ActionListener{

    private LinkedList<ActionEvent> listOfActions;

    public ButtonListener(){
        listOfActions = new LinkedList<>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        listOfActions.add(e);
    }

    public LinkedList<ActionEvent> getListOfActions() {
        return listOfActions;
    }
}
