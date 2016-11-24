
/**
 * Adapter reacting to changes made and updating GUI 
 * @author Amanda
 * @version 24 nov 2016
 */
public class ChangeAdapter implements ChangeListener{
	private RunGame gui;
	
	public ChangeAdapter(RunGame gui){
		super();
		this.gui = gui;
	}
	
	/**
	 * Send updated wallet value to user interface
	 * @param Object source - wallet value
	 */
	@Override
	public void dataChanged(Object source){
		gui.updateStore((int) source);
	}
}

