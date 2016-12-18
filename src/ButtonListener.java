import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

/**
 *2
 */


public class ButtonListener implements ActionListener {

    private LinkedList<ActionEvent> listOfActions;

    /**
     * Constructor which creates LinkedList filled with
     * ActionEvents
     */
    public ButtonListener(){
        listOfActions = new LinkedList<ActionEvent>();
    }

    /**
     * Adds action event to list of actions
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        listOfActions.add(e);
    }

    /**
     * Returns the list of ActionsEvents
     * @return LinkedList<ActionEvent> listOfActions
     */
    public LinkedList<ActionEvent> getListOfActions() {
        return listOfActions;
    }
}
