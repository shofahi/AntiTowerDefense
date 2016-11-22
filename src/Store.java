import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
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

	private int money;
	public BufferedImage attacker;// = new BufferedImage(null, null, false, null);
	public LoadImage loader;
	
	public Store(){
		this.loader = new LoadImage();
	}
	
    public JPanel buildStore() {
    	GridBagConstraints left = new GridBagConstraints();
        left.anchor = GridBagConstraints.EAST;
        GridBagConstraints right = new GridBagConstraints();
        right.weightx = 2.0;
    	
    	JPanel storePanel = new JPanel();
    	
    	storePanel.setBackground(Color.BLACK);
    	storePanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.WHITE));
    	
    	storePanel.setPreferredSize(new Dimension(180,600));
    	
    	JLabel store = new JLabel("STORE");
    	store.setForeground(Color.WHITE);
    	store.setFont(new Font("Sans-Serif", Font.BOLD, 48));
    	storePanel.add(store, left);
    	
    	JLabel lblTitle = new JLabel("Money: ");
    	lblTitle.setForeground(Color.white);
    	lblTitle.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
    	storePanel.add(lblTitle,left);
    	JLabel lblMoney = new JLabel("$" + money);
    	lblMoney.setFont(new Font("Sans-Serif", Font.PLAIN, 28));
    	lblMoney.setForeground(Color.white);
    	storePanel.add(lblMoney,right);
    	
    	JLabel specialAttacker = new JLabel("Special Attacker");
    	specialAttacker.setForeground(Color.white);
    	storePanel.add(specialAttacker,left);
    	JButton btnBuyAttacker1 = new JButton("Buy");
        // btnStartTest.addActionListener(new TestEventListener(this, inputField, ta));
         storePanel.add(btnBuyAttacker1,right);
    	
		attacker = this.loader.loadTheImage("tmpLevel.png");

		JLabel normalAttacker = new JLabel("Normal Attacker");
    	normalAttacker.setForeground(Color.white);
    	storePanel.add(normalAttacker, left);
        
        JButton btnBuyAttacker2 = new JButton("Buy");
        // btnStartTest.addActionListener(new TestEventListener(this, inputField, ta));
         storePanel.add(btnBuyAttacker2,right);
        return storePanel;
    }
    
    public void setMoney(int money){
    	this.money = money;
    }
    public int setMoney(){
    	return this.money;
    }
    
}
