/**
 * Classname: RunGame.java
 * Version info 1.0
 * Copyright notice:    Masoud Shofahi
 *                      Amanda Dahlin
 *                      Gustav Norlander
 *                      Samuel Bylund Felixon
 * Date: 19/12/2017
 * Course: Applikationsutveckling i Java
 */

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class that controls logic- and render loop.
 */
public class RunGame implements Runnable {

	//amount of attackers to finish level (will load from XML)
	private int victory;

	// Jpanel/GUI information
	private final int WIDTH = 800;
	private final int HEIGHT = 600;
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

	// buttonlistener to store and window
	private ButtonListener buttonListener = new ButtonListener();

	private GenerateLevel generateLvl;

	public static boolean hasTeleporter = false;
	public static boolean specialAlive = false;

	private String teleporterDirection;

	private MouseAdapter mouseAdapter;

    /**
     * Constructor for RunGame
     * @param title Title of program
     */
	public RunGame(String title) {
        TITLE = title;
        generateLvl = new GenerateLevel(20);

        initializeConstructor();
	}

    /**
     * Constructor for RunGame
     * @param title Title of program
     * @param xmlPath Path to xml file
     */
	public RunGame(String title, String xmlPath){
	    TITLE = title;
        generateLvl = new GenerateLevel(20,xmlPath);

        initializeConstructor();
    }

    /**
     * Method that acts as a second constructor. Builds necessary
     * components for the game
     */
    public void initializeConstructor(){
        gamePanel = new JPanel();

        gamePanel.setSize(new Dimension(WIDTH, HEIGHT));
        gamePanel.setBackground(Color.black);

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
	 * This method starts the game loop thread, which is used for
     * rendering and updating the game logic
	 */
	public synchronized void start() {
		System.out.println("Start Tread");
		if (gameThread == null) {
			gameThread = new Thread(this);
			gameThread.start();
			gameRunning = true;
		}
	}

    /**
     * Method will initialize all the objects and attributes needed inorder
     * to start a new level
     */
	public void init() {

        gameImg = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);

        graphics = gameImg.getGraphics();

        generateLvl.loadLevel(1);

        store.setWallet(generateLvl.getStartMoney());

        victory = generateLvl.getAttackersToFinish();

        if(!generateLvl.checkStartAndGoalPosition()){

            JOptionPane.showMessageDialog(null,"The Level is missing a" +
                    "Start Position and Goal Position",
                    "Error",JOptionPane.ERROR_MESSAGE);
        }

		mouseAdapter = new MouseAdapter(generateLvl.getBlocks());
		gamePanel.addMouseListener(mouseAdapter);

		worldHandler.resetNrOfAttackersToGoal();
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

		while(gameRunning) {

			if(reset) {

                init();

                if(store.getWallet() >= store.getSpecialAttackerPrice()){

                    store.getBtnBuySpecial().setEnabled(true);
                }

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
				frames = 0;
				updates = 0;
			}
		}
	}

	/**
	 * The purpose of this method is to update the game logic
	 */
	public void update() {
		if(pause) {

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

			if(!specialAlive) {

                store.getBtnSetTeleporterStart().setEnabled(false);
				store.getBtnSetTeleporterEnd().setEnabled(false);
			}

			if(!checkIfFinished()) {

                store.canAfford(specialAlive);
			}

            checkIfGameOver();
		}
	}

    /**
     * This method will ask the user if he/she want to restart the game
     * when game over.
     */
    public void checkIfGameOver(){
        if(isGameOver()){

            pause = true;

            JDialog.setDefaultLookAndFeelDecorated(true);
            int response = JOptionPane.showConfirmDialog(null,
                    "Restart level?\n", "GAME OVER",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (response == JOptionPane.NO_OPTION) {

                //Do nothing
            } else if (response == JOptionPane.YES_OPTION) {

                restartLevel();
                pause = false;

            } else if (response == JOptionPane.CLOSED_OPTION) {

                //Do nothing
            }
        }
    }

    /**
     * If the user finishes a level. This method will be called to
     * ask the user if he/she wants to restart or continue to next level
     * @return false if user wants to quit the game
     */
    public boolean checkIfFinished() {

        if (didFinishLevel()) {

            disableStoreButtons();

            if (generateLvl.getAttackersList().isEmpty()) {

                if (currentLevel < generateLvl.getAmountOfLevels()) {

                    int dialogButton = JOptionPane.YES_NO_OPTION;

                    int dialogResult = JOptionPane.showConfirmDialog(null,
                            "Continue to next level?", "LEVEL COMPLETED",
                            dialogButton);

                    if ((dialogResult == 0) && (currentLevel
                            < generateLvl.getAmountOfLevels())) {

                        currentLevel++;
                        reset = true;

                    } else {

                        System.out.println("Handle no option");
                    }

                } else {

					checkIfHighScore();

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

        graphics.clearRect(0, 0, WIDTH + store.getSTORE_WIDTH(), HEIGHT);

		worldHandler.render(graphics);

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

    /**
     * Method will created the windows interface and
     * create a new Thread for game loop
     */
	public void startGame() {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

                gui = new Window(TITLE, WIDTH, HEIGHT, buttonListener);

				gui.add(gamePanel, BorderLayout.CENTER);
				gui.add(store.buildStore(), BorderLayout.EAST);

				gui.setVisible(true);

				if (!gameRunning) {

					start();
				}
			}
		});
	}

    /**
     * Method will check if the game is over.
     * The game will be over when the user doesn't have enough money
     * to buy new attackers.
     * @return true if the game is over.
     */
	public boolean isGameOver() {
		if (generateLvl.getAttackersList().isEmpty()
				&& store.getWallet() < 10) {

            checkIfHighScore();

            return true;

        } else {

            return false;
		}
	}

    /**
     * Method will check if the requirements have been filled
     * inorder to finish the level
     * @return true if requirements have been filled
     */
	public boolean didFinishLevel() {

        return worldHandler.getNrOfAttackersToGoal() >= victory;
	}


    /**
     * Method will go through the ButtonListener list.
     * If an event has occurred then the right method will be called
     */
	public void checkActionListenerList() {

		if (!buttonListener.getListOfActions().isEmpty()) {

			for (int i = 0; i < buttonListener.getListOfActions()
					.size(); i++) {

				if (buttonListener.getListOfActions().get(i)
						.getSource() == store.getBtnBuyNormal()) {

					worldHandler
							.createNewAttacker(AttackerType.NORMALATTACKER);

                    store.setWallet(store.getWallet()
							- store.getNormalAttackerPrice());

                    store.getLblMoney()
							.setText(String.valueOf(store.getWallet()));

                    buttonListener.getListOfActions().remove(i);

                } else if (buttonListener.getListOfActions().get(i)
						.getSource() == store.getBtnBuySpecial()) {

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

						pause = true;

                        store.getBtnBuyMuscle().setEnabled(false);
						store.getBtnBuyNormal().setEnabled(false);
						store.getBtnBuySpecial().setEnabled(false);

                        gui.updateButtonText();

                        buttonListener.getListOfActions().remove(i);

                    } else {

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

                    getListOfLevels();

                    buttonListener.getListOfActions().remove(i);

                } else if (buttonListener.getListOfActions().get(i)
						.getSource() == gui.getHelp()) {

                    JOptionPane.showMessageDialog(null,
							"Help:\n\n Play by adding attackers to the\n"
									+ "field. Different attackers have" +
                                    "different \nprices. ETC.");

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

        if(selection != null){

            currentLevel = Integer.parseInt(selection.toString());

            reset = true;
        }
    }

    /**
     * Method will restart the current level
     */
    protected void restartLevel(){

        store.setWallet(generateLvl.getStartMoney());

        reset = true;
        hasTeleporter = false;
    }

    /**
     * Getter for teleporter direction
     * @return The direction
     */
	public String getTeleporterDirection() {
		return teleporterDirection;
	}

    /**
     * Method checks if the level and score for the user-run is a highscore
     * and calls the method to add highscore to database if it is.
     */
	private void checkIfHighScore() {

        ArrayList<DatabaseModel> highScores = database.getThreeHighscores();
		String name = "- NO NAME -";

		if (currentLevel > highScores.get(2).getLevel()
				|| (currentLevel == highScores.get(2).getLevel()
                && store.getWallet() >= highScores.get(2).getScore())) {

            name = JOptionPane
					.showInputDialog("NEW HIGH SCORE\nEnter name:\n");

			if (name != null) {

                database.setHighScore(name, currentLevel, store.getWallet());

                updateHighScorePanel();
			}
		}
	}

    /**
     * Method to update highscores showing in the store menu.
     */
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

    /**
     * Method to disable all store buttons.
     */
	private void disableStoreButtons() {

        store.getBtnBuyMuscle().setEnabled(false);
		store.getBtnBuyNormal().setEnabled(false);
		store.getBtnBuySpecial().setEnabled(false);
	}

    /**
     * Method to enable all store buttons.
     */
	private void enableStoreButtons() {
		store.getBtnBuyMuscle().setEnabled(true);
		store.getBtnBuyNormal().setEnabled(true);
		store.getBtnBuySpecial().setEnabled(true);
	}

    /**
     * getter for Store.
     * @return The Store object
     */
    public Store getStore() {
        return store;
    }

    /**
     * Getter for the WorldHandler
     * @return The WorldHandler object
     */
    public WorldHandler getWorldHandler() {
        return worldHandler;
    }

    /**
     * Getter for GenerateLvl
     * @return The GenerateLvl object
     */
    public GenerateLevel getGenerateLvl() {
        return generateLvl;
    }

}