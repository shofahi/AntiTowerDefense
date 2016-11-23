import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;


public class RunGame implements Runnable{

    PlayerTMP playerTMP;

    //GUI information
    private final int WIDTH;
    private final int HEIGHT;
    private final String TITLE;
    private JPanel gamePanel;

    //This class will run on its own thread
    private Thread gameThread;
    private boolean gameRunning;

    //We will need these two objects to render
    private Graphics graphics;
    private BufferedImage gameImg;

    //Load the level
    private WorldHandler theLvl;

    public RunGame(String title, int width,int height) {

        TITLE = title;
        WIDTH = width;
        HEIGHT = height;

        gamePanel = new JPanel();
        gamePanel.setPreferredSize(new Dimension(WIDTH,HEIGHT));

        theLvl = new WorldHandler(20);
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

        theLvl.loadAllImages();
        theLvl.loadImageLevel(0);
        playerTMP = new PlayerTMP(theLvl.getStartPosition(),theLvl.getTurns());
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

    public void update(){
        playerTMP.update();
    }

    public void render(){

        //Clear the clear
        graphics.clearRect(0,0,WIDTH,HEIGHT);

        //********************************Draw here***********************/

        theLvl.render(graphics);
        playerTMP.render(graphics);
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
                Window gui=new Window(TITLE,WIDTH,HEIGHT);
                gui.add(gamePanel);
                gui.setVisible(true);
                
                //Store store = new Store();
                //gui.add(store.buildStore(),BorderLayout.EAST);

                //starta tråden
                if(!gameRunning){
                    start();

                }
            }});


    }

}