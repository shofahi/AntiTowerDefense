import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.awt.BorderLayout.CENTER;


public class RunGame implements Runnable{


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

    //We will need these two objects to render
    private Graphics graphics;
    private BufferedImage gameImg;

    //Load the level
    private WorldHandler worldHandler;

    boolean updateGui = false;

    //NEW - skickar till store och window
    private ButtonListener buttonListener = new ButtonListener();

    public RunGame(String title, int width,int height) {

        TITLE = title;
        WIDTH = width;
        HEIGHT = height;

        gamePanel = new JPanel();
        gamePanel.setPreferredSize(new Dimension(WIDTH,HEIGHT));

        //20 is the size of a block, this is just temporary
        worldHandler = new WorldHandler(20);
        
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

        worldHandler.loadAllImages();
        worldHandler.loadImageLevel(0);
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
            }
        }
    }

    /**
     * The purpose of this method is to update the game logic
     */
    public void update(){
        worldHandler.update();
        checkActionListenerList();

        store.canAfford();

    }

    /**
     * Method will render all the objects
     */
    public void render(){

        //Clear the clear
        graphics.clearRect(0,0,WIDTH,HEIGHT);

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
                else if(buttonListener.getListOfActions().get(i).getSource() == gui.getAbout()){

                }
            }
        }


       /* else if(e.getSource() == Store.btnBuySpecial) {
            System.out.println("Buying Special Attacker & Subtracting Money");
        }
        //WINDOW BUTTONS
        else if(e.getSource() == Window.pause){
            System.out.println("Pausar ..");
        }
        else if(e.getSource() == Window.start){
            System.out.println("Start Game ..");

        }
        else if(e.getSource() == Window.restart){
            System.out.println("Restart level ..");
        }
        else if(e.getSource() == Window.quit){
            System.out.println("Quitting ..");

        }
        else if(e.getSource() == Window.about){
            System.out.println("About ..");
            JOptionPane.showMessageDialog(null, "Authors:\n\nAmanda Dahlin\n"
                    + "Gustav Nordlander\nSamuel Bylund Felixson\nMasoud Shofahi\n\n\u00a9 2016");
        }
        else if(e.getSource() == Window.about){
            System.out.println("Helping ..");
            JOptionPane.showMessageDialog(null, "Help:\n\n Play by adding attackers to the\n"
                    + "field. Different attackers have different \nprices. ETC.");
        }
        else if(e.getSource() == Window.changeLevel){
            System.out.println("Changing Level ..");

        }*/
    }

}