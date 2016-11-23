import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StoreListener  implements ActionListener  {

	private Store store;
	private WorldHandler worldHandler;

	public StoreListener(Store store, WorldHandler worldHandler){
		this.worldHandler = worldHandler;
		this.store = store;
	}

	public void newAttacker(AttackerType type){
		worldHandler.createNewAttacker(type);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == store.getBtnBuyNormal()){
			System.out.println("Buying Normal Attacker & Subtracting Money");
		} else if(e.getSource() == store.getBtnBuySpecial()) {
			System.out.println("Buying Special Attacker & Subtracting Money");
			newAttacker(AttackerType.SPECIALATTACKER);
		}
	}

}
