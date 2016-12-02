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

	public BufferedImage attacker;
	public LoadImage loader;
	private JButton btnBuyNormal;
	private JButton btnBuySpecial; 
	private JButton btnBuyMuscle; 
	private int normalAttackerPrice = 10;
	private int specialAttackerPrice = 50;
	private int muscleAttackerPrice = 75;
    private int wallet = 150;
	private JLabel lblMoney;
	private ButtonListener buttonListener;
    private JPanel headerPanel;
	private final int STORE_WIDTH = 180;
	private final int STORE_HEIGHT = 600;

	public Store(ButtonListener buttonListener){
        this.buttonListener = buttonListener;
		this.loader = new LoadImage();
	}
	
    public JPanel buildStore() {
    	JPanel storePanel = new JPanel();
    	
    	storePanel.setBackground(Color.BLACK);
    	storePanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.WHITE));
    	
    	storePanel.setPreferredSize(new Dimension(STORE_WIDTH,STORE_HEIGHT));
    	
    	storePanel.add(headerPanel());
    	storePanel.add(normalAttackerPanel());
    	storePanel.add(specialAttackerPanel());
    	storePanel.add(muscleAttackerPanel());
        return storePanel;
    }


    public JPanel headerPanel(){

	    headerPanel = new JPanel();
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

    	lblMoney = new JLabel("$" + getWallet());
    	lblMoney.setText(String.valueOf(getWallet()));

    	lblMoney.setFont(new Font("Sans-Serif", Font.PLAIN, 28));
    	lblMoney.setForeground(Color.white);
    	headerPanel.add(lblMoney,BorderLayout.EAST);

    	return headerPanel;
    }


    public JPanel specialAttackerPanel(){
    	JPanel specialAttackerPanel = new JPanel();
    	specialAttackerPanel.setBackground(Color.BLACK);
    	specialAttackerPanel.setPreferredSize(new Dimension(175,60));
    	JLabel specialAttacker = new JLabel("Special Attacker, $" + getSpecialAttackerPrice());
    	specialAttacker.setForeground(Color.white);
    	specialAttackerPanel.add(specialAttacker,BorderLayout.WEST);
    	btnBuySpecial = new JButton("Buy Special Attacker");
        btnBuySpecial.addActionListener(buttonListener);
        specialAttackerPanel.add(btnBuySpecial,BorderLayout.EAST);

    	
    	return specialAttackerPanel;
    }
    
    public JPanel normalAttackerPanel(){
    	JPanel normalAttackerPanel = new JPanel();
    	normalAttackerPanel.setPreferredSize(new Dimension(175,60));
    	normalAttackerPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));
    	normalAttackerPanel.setBackground(Color.BLACK);
    	JLabel normalAttacker = new JLabel("Normal Attacker, $" + getNormalAttackerPrice());
    	normalAttacker.setForeground(Color.white);
    	normalAttackerPanel.add(normalAttacker, BorderLayout.WEST);
        
        btnBuyNormal = new JButton("Buy Normal Attacker");
        btnBuyNormal.addActionListener(buttonListener);
        normalAttackerPanel.add(btnBuyNormal,BorderLayout.EAST);

    	return normalAttackerPanel;
    }
    
    public JPanel muscleAttackerPanel(){
    	JPanel muscleAttackerPanel = new JPanel();
    	muscleAttackerPanel.setPreferredSize(new Dimension(175,100));
    	muscleAttackerPanel.setBackground(Color.BLACK);
    	JLabel normalAttacker = new JLabel("Buy Muscle Attacker, $" + getMuscleAttackerPrice());
    	normalAttacker.setForeground(Color.white);
    	muscleAttackerPanel.add(normalAttacker, BorderLayout.WEST);
        
        btnBuyMuscle = new JButton("Buy Muscle Attacker");
        btnBuyMuscle.addActionListener(buttonListener);
        muscleAttackerPanel.add(btnBuyMuscle,BorderLayout.EAST);

    	return muscleAttackerPanel;
    }

    public void canAfford(){
        if(wallet < getNormalAttackerPrice()) {
            btnBuyNormal.setEnabled(false);
        } else {
            btnBuyNormal.setEnabled(true);
        }

        if(wallet < getMuscleAttackerPrice()) {
            btnBuyMuscle.setEnabled(false);
        } else {
            btnBuyMuscle.setEnabled(true);
        }

        if(wallet < getSpecialAttackerPrice()) {
            btnBuySpecial.setEnabled(false);
        } else {
            btnBuySpecial.setEnabled(true);
        }
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
    public void setMuscleAttackerPrice(int newPrice){
    	this.muscleAttackerPrice = newPrice;
    }
    
    public int getMuscleAttackerPrice(){
    	return muscleAttackerPrice;
    }

    public void setWallet(int money) {
    	this.wallet = money;
    }
    public int getWallet(){
    	return this.wallet;
    }

    public JLabel getLblMoney() {
        return lblMoney;
    }

	public int getSTORE_WIDTH() {
		return STORE_WIDTH;
	}

	public int getSTORE_HEIGHT() {
		return STORE_HEIGHT;
	}
}
