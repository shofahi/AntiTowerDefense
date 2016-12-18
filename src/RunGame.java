import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.ArrayList;

public class RunGame implements Runnable {

	// TEMPORARY (SHOULD COME FROM XML-FILE)
	private final int VICTORY = 2;

	// Jpanel/GUI information
	private final int WIDTH;
	private final int HEIGHT;
	private final String TITLE;
	private JPanel gamePanel;
	private Store store;
	private Database database;
	private Window gui;

	// This class will run on its own thread
	private Thread gameThread;
	private boolean gameRunning;
	private boolean pause;
	private boolean reset = true;
	private int currentLevel = 1;
	private Object[] selectionValues;

	// We will need these two objects to render
	private Graphics graphics;
	private BufferedImage gameImg;

	// Load the level
	private WorldHandler worldHandler;

	boolean updateGui = false;

	// NEW - skickar till store och window
	private ButtonListener buttonListener = new ButtonListener();

	GenerateLevel generateLvl = new GenerateLevel(20);

	public static boolean hasTeleporter = false;
	public static boolean specialAlive = false;
	private String teleporterDirection;

	private MouseAdapter mouseAdapter;

	public RunGame(String title, int width, int height) {

		TITLE = title;
		WIDTH = width;
		HEIGHT = height;

		gamePanel = new JPanel();

		gamePanel.setSize(new Dimension(WIDTH, HEIGHT));
		gamePanel.setBackground(Color.black);

		// try {
		try {
			database = new Database();
			database.setHighScore(" ", 0, 0);
			database.setHighScore(" ", 0, 0);
			database.setHighScore(" ", 0, 0);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * } catch {(SQLException | ClassNotFoundException e) {
		 * e.printStackTrace(); }
		 */
		store = new Store(buttonListener);
		this.updateHighScorePanel();

		// 20 is the size of a block, this is just temporary
		worldHandler = new WorldHandler(generateLvl);

		selectionValues = new Object[generateLvl.getAmountOfLevels()];

		for (int i = 0; i < selectionValues.length; i++) {
			selectionValues[i] = i + 1;
		}
	}

	/**
	 * This method starts the game loop thread, which is used for rendering and
	 * updating the game logic
	 */
	public synchronized void start() {
		System.out.println("Start Tread");
		if (gameThread == null) {
			gameThread = new Thread(this);
			gameThread.start();
			gameRunning = true;
		}
	}

	public void init() {

		gameImg = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		graphics = gameImg.getGraphics();
		generateLvl.loadLevel(currentLevel);
		store.setWallet(generateLvl.getStartMoney());
		if (!generateLvl.checkStartAndGoalPosition()) {
			JOptionPane.showMessageDialog(null,
					"The Level is missing a Start Position"
							+ "and Goal Position",
					"Error", JOptionPane.ERROR_MESSAGE);
		}

		mouseAdapter = new MouseAdapter(generateLvl.getBlocks());
		gamePanel.addMouseListener(mouseAdapter);

	}

	/**
	 * Method overrides the thread method run
	 */
	@Override
	public void run() {

		final double TARGET_FPS = 60.0;
		final double OPTIMAL_TIME = 1000000000 / TARGET_FPS;
		long lastTime = System.nanoTime();

		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;

		while (gameRunning) {

			if (reset) {
				System.out.println("check");
				init();
				reset = false;
			}

			long now = System.nanoTime();
			delta += (now - lastTime) / OPTIMAL_TIME;
			lastTime = now;
			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				// System.out.println("FPS: " + frames + " TICKS: " + updates);
				frames = 0;
				updates = 0;
			}
		}
	}

	/**
	 * The purpose of this method is to update the game logic
	 */
	public void update() {
		if (pause) {
			checkActionListenerList();
			store.getBtnBuyMuscle().setEnabled(false);
			store.getBtnBuyNormal().setEnabled(false);
			store.getBtnBuySpecial().setEnabled(false);
		} else {
			worldHandler.update();
			checkActionListenerList();
			store.setWallet(store.getWallet() + worldHandler.getBonus());
			store.getLblMoney().setText(String.valueOf(store.getWallet()));
			worldHandler.resetBonus();

			if (!specialAlive) {
				store.getBtnSetTeleporterStart().setEnabled(false);
				store.getBtnSetTeleporterEnd().setEnabled(false);
			}

			if (!checkIfFinished()) {
				store.canAfford(specialAlive);
			}
			checkIfGameOver();
		}
	}

	public void checkIfGameOver() {
		if (isGameOver()) {
			pause = true;
			JDialog.setDefaultLookAndFeelDecorated(true);
			int response = JOptionPane.showConfirmDialog(null,
					"Restart level?\n", "GAME OVER", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.NO_OPTION) {
				System.out.println("No button clicked");
			} else if (response == JOptionPane.YES_OPTION) {
				restartLevel();
				pause = false;
			} else if (response == JOptionPane.CLOSED_OPTION) {
				System.out.println("JOptionPane closed");
			}
		}
	}

	public boolean checkIfFinished() {

		if (didFinishLevel()) {

			disableStoreButtons();
			if (generateLvl.getAttackersList().isEmpty()) {
				if (currentLevel < generateLvl.getAmountOfLevels()) {
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog(null,
							"Continue to next level?", "LEVEL COMPLETED",
							dialogButton);
					System.out.println("CURRENT LEVEL IS: " + currentLevel
							+ "NEXT LEVEL" + generateLvl.getAmountOfLevels());
					System.out.println(
							"============================== " + dialogResult);

					if ((dialogResult == 0) && (currentLevel < generateLvl
							.getAmountOfLevels())) {
						System.out.println("Continue to next level here");
						currentLevel++;
						reset = true;
					} else {
						System.out.println("Handle no option");
					}
				} else {
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog(null,
							"Congratulation you finished all the levels",
							"Would you like to restart", dialogButton);

					if (dialogResult == 0) {
						currentLevel = 1;
						reset = true;
					}
				}
				worldHandler.resetNrOfAttackersToGoal();
			}

			return true;
		}
		return false;
	}

	/**
	 * Method will render all the objects
	 */
	public void render() {

		// Clear the clear
		graphics.clearRect(0, 0, WIDTH + store.getSTORE_WIDTH(), HEIGHT);

		// ********************************Draw here***********************/
		worldHandler.render(graphics);

		// *************************************************************
		drawGameImage();

	}

	/**
	 * Method draws the Buffered image, which is used to draw the objects on to
	 * the screen
	 */
	public void drawGameImage() {
		Graphics g = gamePanel.getGraphics();
		if (gameImg != null) {
			g.drawImage(gameImg, 0, 0, null);
		}

		g.dispose();
	}

	public void startGame() {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				gui = new Window(TITLE, WIDTH, HEIGHT, buttonListener);

				gui.add(gamePanel, BorderLayout.CENTER);
				gui.add(store.buildStore(), BorderLayout.EAST);

				gui.setVisible(true);

				// starta tråden
				if (!gameRunning) {
					start();
				}
			}
		});
	}

	public boolean isGameOver() {
		if (generateLvl.getAttackersList().isEmpty()
				&& store.getWallet() < 10) {
			return true;
		} else {
			return false;
		}
	}

	public boolean didFinishLevel() {
		return worldHandler.getNrOfAttackersToGoal() > VICTORY;
	}

	public void checkActionListenerList() {

		if (!buttonListener.getListOfActions().isEmpty()) {

			for (int i = 0; i < buttonListener.getListOfActions()
					.size(); i++) {

				if (buttonListener.getListOfActions().get(i)
						.getSource() == store.getBtnBuyNormal()) {
					System.out.println(
							"Buying Normal Attacker & Subtracting Money");
					worldHandler
							.createNewAttacker(AttackerType.NORMALATTACKER);
					store.setWallet(store.getWallet()
							- store.getNormalAttackerPrice());
					store.getLblMoney()
							.setText(String.valueOf(store.getWallet()));
					buttonListener.getListOfActions().remove(i);
				} else if (buttonListener.getListOfActions().get(i)
						.getSource() == store.getBtnBuySpecial()) {
					System.out.println(
							"Buying Special Attacker & Subtracting Money");
					worldHandler
							.createNewAttacker(AttackerType.SPECIALATTACKER);
					store.setWallet(store.getWallet()
							- store.getSpecialAttackerPrice());
					store.getLblMoney()
							.setText(String.valueOf(store.getWallet()));

					store.getBtnBuySpecial().setEnabled(false);
					store.getBtnSetTeleporterStart().setEnabled(true);
					store.getBtnSetTeleporterEnd().setEnabled(false);
					buttonListener.getListOfActions().remove(i);
				} else if (buttonListener.getListOfActions().get(i)
						.getSource() == store.getBtnBuyMuscle()) {
					System.out.println(
							"Buying Muscle Attacker & Subtracting Money");
					worldHandler
							.createNewAttacker(AttackerType.MUSCLEATTACKER);
					store.setWallet(store.getWallet()
							- store.getMuscleAttackerPrice());
					store.getLblMoney()
							.setText(String.valueOf(store.getWallet()));
					buttonListener.getListOfActions().remove(i);
				} else if (buttonListener.getListOfActions().get(i)
						.getSource() == gui.getAbout()) {
					JOptionPane.showMessageDialog(null,
							"Authors:\n\nAmanda Dahlin\n"
									+ "Gustav Nordlander\n"
									+ "Samuel Bylund Felixson\n"
									+ "Masoud Shofahi\n\n\u00a9 2016");
					buttonListener.getListOfActions().remove(i);
				} else if (buttonListener.getListOfActions().get(i)
						.getSource() == gui.getQuit()) {
					gameRunning = false;
					buttonListener.getListOfActions().remove(i);
					System.exit(0);
				} else if (buttonListener.getListOfActions().get(i)
						.getSource() == gui.getPause()) {
					if (!pause) {
						System.out.println("PAUSING GAME");
						pause = true;
						store.getBtnBuyMuscle().setEnabled(false);
						store.getBtnBuyNormal().setEnabled(false);
						store.getBtnBuySpecial().setEnabled(false);
						gui.updateButtonText();
						buttonListener.getListOfActions().remove(i);
					} else {
						System.out.println("RESUMING GAME");
						store.getBtnBuyMuscle().setEnabled(true);
						store.getBtnBuyNormal().setEnabled(true);
						store.getBtnBuySpecial().setEnabled(true);
						gui.updateButtonText();
						pause = false;
						buttonListener.getListOfActions().remove(i);
					}
				} else if (buttonListener.getListOfActions().get(i)
						.getSource() == gui.getRestart()) {
					restartLevel();
					buttonListener.getListOfActions().remove(i);
				} else if (buttonListener.getListOfActions().get(i)
						.getSource() == gui.getQuit()) {
					gameRunning = false;
					buttonListener.getListOfActions().remove(i);
					System.exit(0);
				} else if (buttonListener.getListOfActions().get(i)
						.getSource() == gui.getChangeLevel()) {
					System.out.println("Changing level");
					getListOfLevels();
					buttonListener.getListOfActions().remove(i);
				} else if (buttonListener.getListOfActions().get(i)
						.getSource() == gui.getHelp()) {
					JOptionPane.showMessageDialog(null,
							"Help:\n\n Play by adding attackers to the\n"
									+ "field. Different attackers have different"
									+ " \nprices. ETC.");
					buttonListener.getListOfActions().remove(i);
				} else if (buttonListener.getListOfActions().get(i)
						.getSource() == store.getBtnSetTeleporterStart()) {

					int specialID = generateLvl.getAttackersList()
							.indexOf(worldHandler.getSpecialID());
					Attacker sA = generateLvl.getAttackersList()
							.get(specialID);
					int xPos = sA.getPos().getX();
					int yPos = sA.getPos().getY();
					Position pos = new Position(xPos, yPos);

					generateLvl.setTeleporterStartPosition(pos);

					store.getBtnSetTeleporterStart().setEnabled(false);
					store.getBtnSetTeleporterEnd().setEnabled(true);
					buttonListener.getListOfActions().remove(i);
				} else if (buttonListener.getListOfActions().get(i)
						.getSource() == store.getBtnSetTeleporterEnd()) {

					int specialID = generateLvl.getAttackersList()
							.indexOf(worldHandler.getSpecialID());
					Attacker sA = generateLvl.getAttackersList()
							.get(specialID);
					int xPos = sA.getPos().getX();
					int yPos = sA.getPos().getY();
					Position pos = new Position(xPos, yPos);

					generateLvl.setTeleporterEndPosition(pos);
					generateLvl.setTeleporterDirection(sA.getTurnValue());

					hasTeleporter = true;
					store.getBtnSetTeleporterEnd().setEnabled(false);
					buttonListener.getListOfActions().remove(i);

				}
			}
		}
	}

	/**
	 * This method will show all the available level, so the user can choose
	 * from It there is no Levels available, new window will pop up with an
	 * error message
	 */
	private void getListOfLevels() {

		if (generateLvl.getAmountOfLevels() == 0) {
			JOptionPane.showMessageDialog(null,
					"There are no Levels available", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		Object selection = JOptionPane.showInputDialog(null, "Select level",
				"Change Level", JOptionPane.QUESTION_MESSAGE, null,
				selectionValues, String.valueOf(currentLevel));

		currentLevel = Integer.parseInt(selection.toString());
		reset = true;

	}

	/**
	 * Method will restart the current level
	 */
	private void restartLevel() {
		System.out.println("RESET EVERYTHING AND RESTART LEVEL HERE");
		store.setWallet(generateLvl.getStartMoney()); // TODO SET TO LEVEL VALUE
		reset = true;
	}

	public String getTeleporterDirection() {
		return teleporterDirection;
	}

	private void checkIfHighScore() {
		ArrayList<DatabaseModel> highScores = database.getThreeHighscores();
		String name = "- NO NAME -";

		if (currentLevel > highScores.get(2).getLevel()
				|| (currentLevel == highScores.get(2).getLevel() && store
						.getWallet() >= highScores.get(2).getScore())) {
			name = JOptionPane
					.showInputDialog("NEW HIGH SCORE\nEnter name:\n");
			System.out.printf("The user's name is '%s'.\n", name);

			if (name != null) {
				database.setHighScore(name, currentLevel, store.getWallet());
				updateHighScorePanel();
			}
		}
	}

	private void updateHighScorePanel() {
		ArrayList<DatabaseModel> highScores = database.getThreeHighscores();
		DatabaseModel first = highScores.get(0);
		DatabaseModel second = highScores.get(1);
		DatabaseModel third = highScores.get(2);

		store.setFirstPlace(first.getName(), first.getLevel(),
				first.getScore());
		store.setSecondPlace(second.getName(), second.getLevel(),
				second.getScore());
		store.setThirdPlace(third.getName(), third.getLevel(),
				third.getScore());
	}

	private void disableStoreButtons() {
		store.getBtnBuyMuscle().setEnabled(false);
		store.getBtnBuyNormal().setEnabled(false);
		store.getBtnBuySpecial().setEnabled(false);
	}

	private void enableStoreButtons() {
		store.getBtnBuyMuscle().setEnabled(true);
		store.getBtnBuyNormal().setEnabled(true);
		store.getBtnBuySpecial().setEnabled(true);
	}
}