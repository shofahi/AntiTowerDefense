/**
 * Classname: Store.java
 * Version info 1.0
 * Copyright notice:    Amanda Dahlin
 *                      Gustav Norlander
 * Date: 17/12/2017
 * Course: Applikationsutveckling i Java
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Store {

	public BufferedImage attacker;
	private JButton btnBuyNormal;

    public static JButton btnBuySpecial;
	private JButton btnBuyMuscle;
	private JButton setTeleporterStart;
	private JButton setTeleporterEnd;

    private int normalAttackerPrice = 10;
	private int specialAttackerPrice = 50;
	private int muscleAttackerPrice = 75;
	private int wallet = 150;

    private JLabel lblMoney;
	private ButtonListener buttonListener;
	private JPanel headerPanel;

    private final int STORE_WIDTH = 180;
	private final int STORE_HEIGHT = 600;

	private JLabel firstPlace = new JLabel();
	private JLabel secondPlace = new JLabel();
	private JLabel thirdPlace = new JLabel();

	private int firstHighScore = 0;
	private int secondHighScore = 0;
	private int thirdHighScore = 0;

	/**
	 * Constructor creating instance of Store and button listener
	 * 
	 * @param ButtonListener
	 *            buttonListener
	 */
	public Store(ButtonListener buttonListener) {

        this.buttonListener = buttonListener;
	}

	/**
	 * Creates a panel and appends the different panels
	 * 
	 * @return JPanel storePanel
	 */
	public JPanel buildStore() {

        JPanel storePanel = new JPanel();

        storePanel.setBackground(Color.BLACK);
		storePanel.setBorder(
				BorderFactory.createMatteBorder(0, 1, 0, 0, Color.WHITE));
		storePanel.setPreferredSize(new Dimension(STORE_WIDTH, STORE_HEIGHT));

        storePanel.add(headerPanel());
		storePanel.add(normalAttackerPanel());
		storePanel.add(specialAttackerPanel());
		storePanel.add(teleportPanel());
		storePanel.add(muscleAttackerPanel());
		storePanel.add(highScorePanel());

		return storePanel;
	}

	/**
	 * Creates small header panel showing info about amount of money.
	 * 
	 * @return headerPanel
	 */
	public JPanel headerPanel() {

        headerPanel = new JPanel();
		headerPanel.setPreferredSize(new Dimension(155, 125));
		headerPanel.setBackground(Color.BLACK);

        JLabel store = new JLabel("STORE");

        store.setForeground(Color.WHITE);
		store.setFont(new Font("Sans-Serif", Font.BOLD, 48));

        headerPanel.add(store, BorderLayout.WEST);

        JLabel lblTitle = new JLabel("Money: ");

        lblTitle.setForeground(Color.white);
		lblTitle.setFont(new Font("Sans-Serif", Font.PLAIN, 20));

        headerPanel.add(lblTitle, BorderLayout.WEST);

        lblMoney = new JLabel("$" + getWallet());
		lblMoney.setText(String.valueOf(getWallet()));
		lblMoney.setFont(new Font("Sans-Serif", Font.PLAIN, 28));
		lblMoney.setForeground(Color.white);

        headerPanel.add(lblMoney, BorderLayout.EAST);

		return headerPanel;
	}

	/**
	 * Creates panel to buy special attacker
	 * 
	 * @return JPanel specialAttackerPanel
	 */
	public JPanel specialAttackerPanel() {

        JPanel specialAttackerPanel = new JPanel();

        specialAttackerPanel.setBackground(Color.BLACK);
		specialAttackerPanel.setPreferredSize(new Dimension(175, 60));

        JLabel specialAttacker = new JLabel(
				"Special Attacker, $" + getSpecialAttackerPrice());

        specialAttacker.setForeground(Color.white);
		specialAttackerPanel.add(specialAttacker, BorderLayout.WEST);

        btnBuySpecial = new JButton("Buy Special Attacker");
        btnBuySpecial.addActionListener(buttonListener);

        specialAttackerPanel.add(btnBuySpecial, BorderLayout.EAST);

        return specialAttackerPanel;
	}

	/**
	 * Creates panel to buy normal attacker
	 * 
	 * @return JPanel normalAttackerPanel
	 */
	public JPanel normalAttackerPanel() {

        JPanel normalAttackerPanel = new JPanel();

        normalAttackerPanel.setPreferredSize(new Dimension(175, 60));
		normalAttackerPanel.setBorder(
				BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));
		normalAttackerPanel.setBackground(Color.BLACK);

        JLabel normalAttacker = new JLabel(
				"Normal Attacker, $" + getNormalAttackerPrice());

        normalAttacker.setForeground(Color.white);
		normalAttackerPanel.add(normalAttacker, BorderLayout.WEST);

        btnBuyNormal = new JButton("Buy Normal Attacker");

        btnBuyNormal.addActionListener(buttonListener);

        normalAttackerPanel.add(btnBuyNormal, BorderLayout.EAST);

        return normalAttackerPanel;
	}

	/**
	 * Creates panel to buy muscle attacker
	 * 
	 * @return JPanel muscleAttackerPanel
	 */
	public JPanel muscleAttackerPanel() {

        JPanel muscleAttackerPanel = new JPanel();

        muscleAttackerPanel.setPreferredSize(new Dimension(175, 100));
		muscleAttackerPanel.setBackground(Color.BLACK);

        JLabel normalAttacker = new JLabel(
				"Buy Muscle Attacker, $" + getMuscleAttackerPrice());

        normalAttacker.setForeground(Color.white);

        muscleAttackerPanel.add(normalAttacker, BorderLayout.WEST);

        btnBuyMuscle = new JButton("Buy Muscle Attacker");

        btnBuyMuscle.addActionListener(buttonListener);

        muscleAttackerPanel.add(btnBuyMuscle, BorderLayout.EAST);

        return muscleAttackerPanel;
	}

	/**
	 * Creates panel to place teleporter star & stop
	 * 
	 * @return JPanel telepanel
	 */
	public JPanel teleportPanel() {

        JPanel telepanel = new JPanel();

        telepanel.setPreferredSize(new Dimension(175, 100));
		telepanel.setBackground(Color.BLACK);

        JLabel normalAttacker = new JLabel("place the teleporter");

        normalAttacker.setForeground(Color.white);

        telepanel.add(normalAttacker, BorderLayout.NORTH);

		setTeleporterStart = new JButton("start");
		setTeleporterStart.addActionListener(buttonListener);

        telepanel.add(setTeleporterStart, BorderLayout.WEST);

        setTeleporterStart.setEnabled(false);

        setTeleporterEnd = new JButton("end");
		setTeleporterEnd.addActionListener(buttonListener);

        telepanel.add(setTeleporterEnd, BorderLayout.EAST);

        setTeleporterEnd.setEnabled(false);

        return telepanel;
	}

	/**
	 * Creates panel to show high scores
	 * 
	 * @return JPanel highScorePanel
	 */
	public JPanel highScorePanel() {

        JPanel highScorePanel = new JPanel();

        highScorePanel.setPreferredSize(new Dimension(175, 100));
		highScorePanel.setBackground(Color.BLACK);

        JLabel highScore = new JLabel("High score:\n");

        firstPlace = new JLabel();
		secondPlace = new JLabel();
		thirdPlace = new JLabel();

        highScore.setForeground(Color.white);

        firstPlace.setForeground(Color.white);
		secondPlace.setForeground(Color.white);
		thirdPlace.setForeground(Color.white);

		highScorePanel.add(highScore);
		highScorePanel.add(firstPlace);
		highScorePanel.add(secondPlace);
		highScorePanel.add(thirdPlace);

		return highScorePanel;
	}

	/**
	 * Checks if player can afford different types of attackers
	 */
	public void canAfford(boolean specialAlive) {

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

		if(specialAlive){

            btnBuySpecial.setEnabled(false);
		}
		
		if(wallet >= getSpecialAttackerPrice() && !specialAlive) {

            btnBuySpecial.setEnabled(true);

        } else {

            btnBuySpecial.setEnabled(false);
		}
	}

	/**
	 * Returns button for buying special attacker
	 * 
	 * @return JButton btnBuySpecial
	 */
	public JButton getBtnBuySpecial() {
		return btnBuySpecial;
	}

	/**
	 * Returns button for buying normal attacker
	 * 
	 * @return JButton btnBuyNormal
	 */
	public JButton getBtnBuyNormal() {
		return btnBuyNormal;
	}

	/**
	 * Returns button for buying muscle attacker
	 * 
	 * @return JButton btnBuyMuscle
	 */
	public JButton getBtnBuyMuscle() {
		return btnBuyMuscle;
	}

	/**
	 * Returns button for setting start for teleporter
	 * 
	 * @return JButton setTeleporterStart
	 */
	public JButton getBtnSetTeleporterStart() {
		return setTeleporterStart;
	}

	/**
	 * Returns button for setting end for teleporter
	 * 
	 * @return JButton setTeleporterEnd
	 */
	public JButton getBtnSetTeleporterEnd() {
		return setTeleporterEnd;
	}

	/**
	 * Sets normal attacker price
	 * 
	 * @param int
	 *            newPrice
	 */
	public void setNormalAttackerPrice(int newPrice) {
		this.normalAttackerPrice = newPrice;
	}

	/**
	 * Returns what a normal attacker costs
	 * 
	 * @return int normalAttackerPrice
	 */
	public int getNormalAttackerPrice() {
		return normalAttackerPrice;
	}

	/**
	 * Sets price of special attacker
	 * 
	 * @param int
	 *            newPrice
	 */
	public void setSpecialAttackerPrice(int newPrice) {
		this.specialAttackerPrice = newPrice;
	}

	/**
	 * Returns the price of a special attacker
	 * 
	 * @return int specialAttackerPrice
	 */
	public int getSpecialAttackerPrice() {
		return specialAttackerPrice;
	}

	/**
	 * Sets the price of a muscle attacker
	 * 
	 * @param int
	 *            newPrice
	 */
	public void setMuscleAttackerPrice(int newPrice) {
		this.muscleAttackerPrice = newPrice;
	}

	/**
	 * Returns the price of a normal attacker
	 * 
	 * @return int muscleAttackerPrice
	 */
	public int getMuscleAttackerPrice() {
		return muscleAttackerPrice;
	}

	/**
	 * Sets the amount of money in the wallet
	 * 
	 * @param int
	 *            money
	 */
	public void setWallet(int money) {
		this.wallet = money;
	}

	/**
	 * Returns the value in the wallet
	 * 
	 * @return int wallet
	 */
	public int getWallet() {
		return this.wallet;
	}

	/**
	 * Returns the label showing amount of money
	 * 
	 * @return JLabel lblMoney
	 */
	public JLabel getLblMoney() {
		return lblMoney;
	}

	/**
	 * Returns width of store panel
	 * 
	 * @return int STORE_WIDTH
	 */
	public int getSTORE_WIDTH() {
		return STORE_WIDTH;
	}

	/**
	 * Returns height of store panel
	 * 
	 * @return int STORE_HEIGHT
	 */
	public int getSTORE_HEIGHT() {
		return STORE_HEIGHT;
	}

	/**
	 * Sets information about first place in high score list
	 * 
	 * @param String
	 *            name
	 * @param int
	 *            level
	 * @param int
	 *            points
	 */
	public void setFirstPlace(String name, int level, int points) {

        firstHighScore = points;

        firstPlace.setText(
				"1. " + name + ": " + ". Level: " + level + " : " + points);
	}

	/**
	 * Sets information about first place in high score list
	 * 
	 * @param String
	 *            name
	 * @param int
	 *            level
	 * @param int
	 *            points
	 */
	public void setSecondPlace(String name, int level, int points) {

        secondHighScore = points;

        secondPlace.setText(
				"2. " + name + ": " + ". Level: " + level + " : " + points);
	}

	/**
	 * Sets information about first place in high score list
	 * 
	 * @param String
	 *            name
	 * @param int
	 *            level
	 * @param int
	 *            points
	 */
	public void setThirdPlace(String name, int level, int points) {

        thirdHighScore = points;

        thirdPlace.setText(
				"3. " + name + ": " + ". Level: " + level + " : " + points);
	}

	/**
	 * Returns first place high score
	 * 
	 * @return int firstHighScore
	 */
	public int getFirstPlace() {
		return firstHighScore;
	}

	/**
	 * Returns second place high score
	 * 
	 * @return int secondHighScore
	 */
	public int getSecondPlace() {
		return secondHighScore;
	}

	/**
	 * Returns third place high score
	 * 
	 * @return int thirdHighScore
	 */
	public int getThirdPlace() {
		return thirdHighScore;
	}
}
