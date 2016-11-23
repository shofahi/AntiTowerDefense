import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StoreListener  implements ActionListener  {

	private Store store;
	
	public StoreListener(Store store){
		this.store = store;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == store.getBtnBuyNormal()){
			System.out.println("Buying Normal Attacker & Subtracting Money");
		} else if(e.getSource() == store.getBtnBuySpecial()) {
			System.out.println("Buying Special Attacker & Subtracting Money");
		}
	}

}
