/**
 * 
 */

import static org.junit.Assert.*;
import javax.swing.JButton;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestStore {

	private Store store;
	private WorldHandler wh;

	@Before
	public void setUp() throws Exception {
		this.wh = new WorldHandler(20);
		this.store = new Store();
	}

	@After
	public void tearDown() throws Exception {
		wh = null;
		store = null;
	}
	
	@Test
    public void testBuildStore() {
    	
    	/*JPanel storePanel = new JPanel();
    	
    	storePanel.setBackground(Color.BLACK);
    	storePanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.WHITE));
    	
    	storePanel.setPreferredSize(new Dimension(180,600));
    	
    	storePanel.add(headerPanel());
    	storePanel.add(specialAttackerPanel());
    	storePanel.add(normalAttackerPanel());*/
		
    }
    
	@Test
    public void testHeaderPanel(){
    	/*JPanel headerPanel = new JPanel();
    	headerPanel.setPreferredSize(new Dimension(155,125));
    	headerPanel.setBackground(Color.BLACK);
    	JLabel store = new JLabel("STORE");
    	store.setForeground(Color.WHITE);
    	store.setFont(new Font("Sans-Serif", Font.BOLD, 48));
    	headerPanel.add(store, BorderLayout.WEST);
    	
    	JLabel lblTitle = new JLabel("Money: ");
    	lblTitle.setForeground(Color.white);
    	lblTitle.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
    	headerPanel.add(lblTitle,BorderLayout.WEST);
    	JLabel lblMoney = new JLabel("$" + wallet);
    	lblMoney.setFont(new Font("Sans-Serif", Font.PLAIN, 28));
    	lblMoney.setForeground(Color.white);
    	headerPanel.add(lblMoney,BorderLayout.EAST);*/
    	//return headerPanel;
    }
    
	@Test
    public void testSpecialAttackerPanel(){
    	/*JPanel specialAttackerPanel = new JPanel();
    	specialAttackerPanel.setBackground(Color.BLACK);
    	specialAttackerPanel.setPreferredSize(new Dimension(175,60));
    	specialAttackerPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));
    	JLabel specialAttacker = new JLabel("Special Attacker, $50");
    	specialAttacker.setForeground(Color.white);
    	specialAttackerPanel.add(specialAttacker,BorderLayout.WEST);
    	btnBuySpecial = new JButton("Buy Special Attacker");
        btnBuySpecial.addActionListener(storeListener);
        specialAttackerPanel.add(btnBuySpecial,BorderLayout.EAST);
    	*/
    }
    
	@Test
    public void testNormalAttackerPanel(){
    	/*JPanel normalAttackerPanel = new JPanel();
    	normalAttackerPanel.setPreferredSize(new Dimension(175,100));
    	normalAttackerPanel.setBackground(Color.BLACK);
    	JLabel normalAttacker = new JLabel("Normal Attacker, $10");
    	normalAttacker.setForeground(Color.white);
    	normalAttackerPanel.add(normalAttacker, BorderLayout.WEST);
        
        btnBuyNormal = new JButton("Buy Normal Attacker");
        btnBuyNormal.addActionListener(storeListener);
        normalAttackerPanel.add(btnBuyNormal,BorderLayout.EAST);*/
    }
    
	@Test
    public void testGetBtnBuySpecial() {
        //return btnBuySpecial;
    }
	
	@Test
    public void testGetBtnBuyNormal() {
		JButton btn = new JButton();
		Object o = store.getBtnBuyNormal();
		//SubClass subClass = new SubClass();
        assertTrue(o instanceof JButton);
        //assertEquals(store.getBtnBuyNormal(), btn);
        //if(store.getBtnBuyNormal() instanceof JButton ){}
    }
    
	@Test
    public void testSetAndGetWallet(){
    	store.setWallet(10);
    	assertTrue(store.getWallet() == 10);
    }
    
    public void testSetAndGetNormalAttackerPrice(){
    	store.setNormalAttackerPrice(50);
    	assertTrue(store.getNormalAttackerPrice() == 50);
    }
    
    public void testSetAndGetSpecialAttackerPrice(){
    	store.setSpecialAttackerPrice(100);
    	assertTrue(store.getSpecialAttackerPrice() == 100);
    }
}
