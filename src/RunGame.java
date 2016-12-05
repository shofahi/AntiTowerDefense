import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.awt.BorderLayout.CENTER;


public class RunGame implements Runnable{

	// TEMPORARY (SHOULD COME FROM XML-FILE)
	private final int VICTORY = 2;
	
    //Jpanel/GUI information
    private final int WIDTH;
    private final int HEIGHT;
    private final String TITLE;
    private JPanel gamePanel;
    private Store store;
    Window gui;
    //This class will run on its own thread
    private Thread gameThread;
    private boolean gameRunning;
    private boolean pause;

    //We will need these two objects to render
    private Graphics graphics;
    private BufferedImage gameImg;

    //Load the level
    private WorldHandler worldHandler;

    boolean updateGui = false;

    //NEW - skickar till store och window
    private ButtonListener buttonListener = new ButtonListener();

    GenerateLevel generateLvl = new GenerateLevel(20);


    public RunGame(String title, int width,int height) {

        TITLE = title;
        WIDTH = width;
        HEIGHT = height;

        gamePanel = new JPanel();
        gamePanel.setSize(new Dimension(WIDTH,HEIGHT));
        gamePanel.setBackground(Color.black);

        //20 is the size of a block, this is just temporary
        worldHandler = new WorldHandler(generateLvl);
        
        store = new Store(buttonListener);
    }

    /**
     * This method starts the game loop thread, which is used for rendering and updating the game logic
     */
    public synchronized void start() {
        System.out.println("Start Tread");
        if (gameThread==null){
            gameThread = new Thread(this);
            gameThread.start();
            gameRunning = true;
        }
    }

    public void init(){

        gameImg = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
        graphics = gameImg.getGraphics();

        generateLvl.loadLevel(1);
    }

    /**
     * Method overrides the thread method run
     */
    @Override
    public void run() {

        init();

        final double TARGET_FPS = 60.0;
        final double OPTIMAL_TIME= 1000000000 / TARGET_FPS;
        long lastTime = System.nanoTime();

        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;

        while(gameRunning){

            long now = System.nanoTime();
            delta += (now - lastTime) / OPTIMAL_TIME;
            lastTime = now;
            while(delta >= 1){
                update();
                updates++;
                delta--;
            }
            render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println("FPS: " + frames + " TICKS: " + updates);
                frames = 0;
                updates = 0;

                // NEW
                if(isGameOver()){
                    pause = true;
                    JDialog.setDefaultLookAndFeelDecorated(true);
                    int response = JOptionPane.showConfirmDialog(null, "Restart level?\n", "GAME OVER",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (response == JOptionPane.NO_OPTION) {
                        System.out.println("No button clicked");
                    } else if (response == JOptionPane.YES_OPTION) {
                        restartLevel();
                        pause = false;
                    } else if (response == JOptionPane.CLOSED_OPTION) {
                        System.out.println("JOptionPane closed");
                    }
                }
                // NEW
                if(didFinishLevel()){
                    int dialogButton = JOptionPane.YES_NO_OPTION;
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Continue to next level?", "LEVEL COMPLETED", dialogButton);
                    if(dialogResult == 0) {
                        System.out.println("Continue to next level here");
                    } else {
                        System.out.println("Handle no option");
                    }
                    worldHandler.resetNrOfAttackersToGoal();
                    gameRunning = false;
                }
            }
        }
    }

    /**
     * The purpose of this method is to update the game logic
     */
    public void update(){
        if(pause){
            checkActionListenerList();
            store.getBtnBuyMuscle().setEnabled(false);
            store.getBtnBuyNormal().setEnabled(false);
            store.getBtnBuySpecial().setEnabled(false);
        }
        else {
            worldHandler.update();
            checkActionListenerList();
            store.setWallet(store.getWallet()+worldHandler.getBonus());
            store.getLblMoney().setText(String.valueOf(store.getWallet()));
            worldHandler.resetBonus();

            store.canAfford();
        }
    }

    /**
     * Method will render all the objects
     */
    public void render(){

        //Clear the clear
        graphics.clearRect(0,0,WIDTH+store.getSTORE_WIDTH(),HEIGHT);

        //********************************Draw here***********************/

        worldHandler.render(graphics);
        //*************************************************************
        drawGameImage();

    }

    /**
     * Method draws the Buffered image, which is used to draw the objects on to the screen
     */
    public void drawGameImage(){
        Graphics g =  gamePanel.getGraphics();
        if (gameImg != null){
            g.drawImage(gameImg,0,0,null);
        }

        g.dispose();
    }

    public void startGame(){

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                gui=new Window(TITLE,WIDTH,HEIGHT,buttonListener);

                gui.add(gamePanel,BorderLayout.CENTER);
                gui.add(store.buildStore(),BorderLayout.EAST);

                gui.setVisible(true);

                //starta tråden
                if(!gameRunning){
                    start();
                }
            }});
    }
    
    // NEW
    public boolean isGameOver(){
    	if(generateLvl.getAttackersList().isEmpty() && store.getWallet() < 10){
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    public boolean didFinishLevel(){
    	return worldHandler.getNrOfAttackersToGoal() > VICTORY;
    }

    public void checkActionListenerList(){

        if(!buttonListener.getListOfActions().isEmpty()){

            for(int i = 0; i < buttonListener.getListOfActions().size(); i++){

                if(buttonListener.getListOfActions().get(i).getSource() == store.getBtnBuyNormal()){
                    System.out.println("Buying Normal Attacker & Subtracting Money");
                    worldHandler.createNewAttacker(AttackerType.NORMALATTACKER);
                    store.setWallet(store.getWallet()-store.getNormalAttackerPrice());
                    store.getLblMoney().setText(String.valueOf(store.getWallet()));
                    buttonListener.getListOfActions().remove(i);
                }
                else if(buttonListener.getListOfActions().get(i).getSource() == store.getBtnBuySpecial()){
                    System.out.println("Buying Special Attacker & Subtracting Money");
                    worldHandler.createNewAttacker(AttackerType.SPECIALATTACKER);
                    store.setWallet(store.getWallet()-store.getSpecialAttackerPrice());
                    store.getLblMoney().setText(String.valueOf(store.getWallet()));
                    buttonListener.getListOfActions().remove(i);
                }
                else if(buttonListener.getListOfActions().get(i).getSource() == store.getBtnBuyMuscle()){
                    System.out.println("Buying Muscle Attacker & Subtracting Money");
                    worldHandler.createNewAttacker(AttackerType.MUSCLEATTACKER);
                    store.setWallet(store.getWallet()-store.getMuscleAttackerPrice());
                    store.getLblMoney().setText(String.valueOf(store.getWallet()));
                    buttonListener.getListOfActions().remove(i);
                }
                else if(buttonListener.getListOfActions().get(i).getSource() == gui.getAbout()){
                    JOptionPane.showMessageDialog(null, "Authors:\n\nAmanda Dahlin\n"
                            + "Gustav Nordlander\nSamuel Bylund Felixson\nMasoud Shofahi\n\n\u00a9 2016");
                    buttonListener.getListOfActions().remove(i);
                }
                else if(buttonListener.getListOfActions().get(i).getSource() == gui.getQuit()) {
                    gameRunning = false;
                    buttonListener.getListOfActions().remove(i);
                    System.exit(0);
                }
                else if(buttonListener.getListOfActions().get(i).getSource() == gui.getPause()) {
                    if (!pause) {
                        System.out.println("PAUSING GAME");
                        pause = true;
                        store.getBtnBuyMuscle().setEnabled(false);
                        store.getBtnBuyNormal().setEnabled(false);
                        store.getBtnBuySpecial().setEnabled(false);
                        gui.updateButtonText();
                        buttonListener.getListOfActions().remove(i);
                    }
                    else {
                        System.out.println("RESUMING GAME");
                        store.getBtnBuyMuscle().setEnabled(true);
                        store.getBtnBuyNormal().setEnabled(true);
                        store.getBtnBuySpecial().setEnabled(true);
                        gui.updateButtonText();
                        pause = false;
                        buttonListener.getListOfActions().remove(i);
                    }
                }
                else if(buttonListener.getListOfActions().get(i).getSource() == gui.getRestart()) {
                    restartLevel();
                    buttonListener.getListOfActions().remove(i);
                }
                else if(buttonListener.getListOfActions().get(i).getSource() == gui.getHelp()) {
                    JOptionPane.showMessageDialog(null, "Help:\n\n Play by adding attackers to the\n"
                            + "field. Different attackers have different \nprices. ETC.");
                    buttonListener.getListOfActions().remove(i);
                }
            }
        }
    }

    private void restartLevel(){
        System.out.println("RESET EVERYTHING AND RESTART LEVEL HERE");
        store.setWallet(100); // TODO SET TO LEVEL VALUE
    }

}