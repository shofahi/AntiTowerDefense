import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class RunGame implements Runnable{

    PlayerTMP playerTMP = new PlayerTMP();

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
    private LevelLoader theLvl;
    
    //Parameters used for GameLoop
	final int TARGET_FPS = 60;
	final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

    public RunGame(String title, int width,int height) {

        TITLE = title;
        WIDTH = width;
        HEIGHT = height;

        gamePanel = new JPanel();
        gamePanel.setPreferredSize(new Dimension(WIDTH,HEIGHT));

        gameImg = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
        graphics = gameImg.getGraphics();

        theLvl = new LevelLoader(1,20);
    }

    /**
     * This
     */
    public synchronized void start() {
        System.out.println("Start Tread");
        if (gameThread==null){
            gameThread = new Thread(this);
            gameThread.start();
            gameRunning = true;
        }
    }
   
    @Override
    public void run() {
    	//Set the lastLoopTime to system time when program starts.
    	long lastLoopTime = System.nanoTime();
    	long lastFpsTime = 0;
    	int fps = 0;
    	
        while (gameRunning){
            long time = System.nanoTime();
            long updateLength = time - lastLoopTime;
            lastLoopTime = time;

            lastFpsTime += updateLength;
            fps++;

            //Update update variables if more than a second passed
            if (lastFpsTime >= 1000000000)
            {
               System.out.println("FPS: "+fps);
               lastFpsTime = 0;
               fps = 0;
            }
        	
            update();
            render();
            
            //Make the thread sleep long enough to reach our TARGET_DPS
            try {
				Thread.sleep((lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000 );
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }

    public void update(){
        playerTMP.update();
    }

    public void render(){

        //ta bort allt från skärmen("gameImg")
        graphics.clearRect(0,0,WIDTH,HEIGHT);

        //********************************Draw here***********************/

        theLvl.render(graphics);
        playerTMP.render(graphics);
        //*************************************************************
        drawGameImage();

    }

    public void drawGameImage(){
        Graphics g =  gamePanel.getGraphics();
        //Om gameImg existerar inte
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
                
                MainMenu menu = new MainMenu();
                gui.add(menu.createMenu());
                
                Store store = new Store();
                gui.add(store.buildStore());
                
                gui.setVisible(true);
                theLvl.loadImageLevel("tmpLevel.png");

                //starta tråden
                start();
            }});
    }

}
