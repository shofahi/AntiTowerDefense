import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * @author Amanda
 * @version 
 */

public class Store {

	public BufferedImage attacker;// = new BufferedImage(null, null, false, null);
	public LoadImage loader;
	private JButton btnBuyNormal;
	private JButton btnBuySpecial; 
	private JButton btnBuyMuscle; 
	private int wallet = 25;
	private int normalAttackerPrice;
	private int specialAttackerPrice;
	private WorldHandler worldHandler;
    private StoreListener storeListener;

	public Store(WorldHandler worldHandler){
		this.worldHandler = worldHandler;
        storeListener = new StoreListener(this,worldHandler);
		this.loader = new LoadImage();
	}
	
    public JPanel buildStore() {
    	
    	JPanel storePanel = new JPanel();
    	
    	storePanel.setBackground(Color.BLACK);
    	storePanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.WHITE));
    	
    	storePanel.setPreferredSize(new Dimension(180,600));
    	
    	storePanel.add(headerPanel());
    	storePanel.add(specialAttackerPanel());
    	storePanel.add(normalAttackerPanel());
    	storePanel.add(muscleAttackerPanel());
        return storePanel;
    }
    
    public JPanel headerPanel(){
    	JPanel headerPanel = new JPanel();
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
    	headerPanel.add(lblMoney,BorderLayout.EAST);
    	return headerPanel;
    }
    
    public JPanel specialAttackerPanel(){
    	JPanel specialAttackerPanel = new JPanel();
    	specialAttackerPanel.setBackground(Color.BLACK);
    	specialAttackerPanel.setPreferredSize(new Dimension(175,60));
    	specialAttackerPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));
    	JLabel specialAttacker = new JLabel("Special Attacker, $50");
    	specialAttacker.setForeground(Color.white);
    	specialAttackerPanel.add(specialAttacker,BorderLayout.WEST);
    	btnBuySpecial = new JButton("Buy Special Attacker");
        btnBuySpecial.addActionListener(storeListener);
        specialAttackerPanel.add(btnBuySpecial,BorderLayout.EAST);
    	
    	return specialAttackerPanel;
    }
    
    public JPanel normalAttackerPanel(){
    	JPanel normalAttackerPanel = new JPanel();
    	normalAttackerPanel.setPreferredSize(new Dimension(175,60));
    	normalAttackerPanel.setBackground(Color.BLACK);
    	JLabel normalAttacker = new JLabel("Normal Attacker, $10");
    	normalAttacker.setForeground(Color.white);
    	normalAttackerPanel.add(normalAttacker, BorderLayout.WEST);
        
        btnBuyNormal = new JButton("Buy Normal Attacker");
        btnBuyNormal.addActionListener(storeListener);
        normalAttackerPanel.add(btnBuyNormal,BorderLayout.EAST);
    	return normalAttackerPanel;
    }
    
    public JPanel muscleAttackerPanel(){
    	JPanel muscleAttackerPanel = new JPanel();
    	muscleAttackerPanel.setPreferredSize(new Dimension(175,100));
    	muscleAttackerPanel.setBackground(Color.BLACK);
    	JLabel normalAttacker = new JLabel("Normal Attacker, $75");
    	normalAttacker.setForeground(Color.white);
    	muscleAttackerPanel.add(normalAttacker, BorderLayout.WEST);
        
        btnBuyMuscle = new JButton("Buy Muscle Attacker");
        btnBuyMuscle.addActionListener(storeListener);
        muscleAttackerPanel.add(btnBuyMuscle,BorderLayout.EAST);
    	return muscleAttackerPanel;
    }
    
    public JButton getBtnBuySpecial() {
        return btnBuySpecial;
    }
    public JButton getBtnBuyNormal() {
        return btnBuyNormal;
    }
    public JButton getBtnBuyMuscle() {
        return btnBuyMuscle;
    }
    
    public void setWallet(int money){
    	this.wallet = money;
    }
    public int getWallet(){
    	return this.wallet;
    }
    
    public void setNormalAttackerPrice(int newPrice){
    	this.normalAttackerPrice = newPrice;
    }
    
    public int getNormalAttackerPrice(){
    	return normalAttackerPrice;
    }
    
    public void setSpecialAttackerPrice(int newPrice){
    	this.specialAttackerPrice = newPrice;
    }
    
    public int getSpecialAttackerPrice(){
    	return specialAttackerPrice;
    }
}
