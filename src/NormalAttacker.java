/**
 * Classname: NormalAttacker.java
 * Version info 1.0
 * Copyright notice:    Masoud Shofahi
 *                      Samuel Bylund Felixon
 * Date: 19/12/2017
 * Course: Applikationsutveckling i Java
 */
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 * Normal type attacker class
 */
public class NormalAttacker extends Attacker {
    static private final int SPEED = 1;
    static private final int START_HEALTH = 1200;
    static private final int ATTACKER_WIDTH = 20;
    static private final int ATTACKER_HEIGHT = 20;
    private int speedTrigger = 0;

    private BufferedImage normalAttackerImage;
    private LoadImage loadImage = new LoadImage();

    /**
     * Constructor for Normal Attacker
     * @param pos - Position to spawn attacker at
     * @param directionSign - List with DirectionSigns for navigating
     *                        the path
     */
    public NormalAttacker(Position pos, LinkedList<Block> directionSign) {

        super(pos, directionSign,
              START_HEALTH, SPEED,
              ATTACKER_WIDTH,ATTACKER_HEIGHT);

        normalAttackerImage = loadImage.loadTheImage("Monster1.png");
    }

    /**
     * Override method to update the attacker
     */
    @Override
    public void update() {

        speedTrigger++;

        if(speedTrigger == getMoveSpeed()){

            getTurn();
            landOn(getPos(),turn);

            speedTrigger = 0;
        }
    }

    /**
     * Override method toRender Attacker graphics to screen.
     * Attacker image + health bar
     * @param g The graphics of the game
     */
    @Override
    public void render(Graphics g){

        g.drawImage(normalAttackerImage,getPos().getX(),
                getPos().getY(),
                getWidth(),getHeight(),null);

        g.setColor(Color.RED);
        g.drawRect(getPos().getX(),getPos().getY()-10,
                    getHealthBar().width-1,getHealthBar().height-1);

        g.setColor(Color.GREEN);
        g.fillRect(getPos().getX(),getPos().getY()-getWidth()/2,
                    getHealthBar().width*getHealth()/START_HEALTH,
                    getHealthBar().height);
    }

    /**
     * Override method to get the attackers "Bound".
     * @return
     */
    @Override
    public Rectangle getBound() {

        return new Rectangle(getPos().getX(),getPos().getY(),
                             getWidth(),getHeight());
    }

    /**
     * Method will moves attackers depending on value of turn
     * @param pos the position of attacker
     * @param turn the direction
     */
    @Override
    public void landOn(Position pos, String turn) {

        if (turn.equals("WEST")) {

            getPos().setX(getPos().getX()-1);

        } else if (turn.equals("SOUTH")) {

            getPos().setY(getPos().getY()+1);

        } else if (turn.equals("NORTH")) {

            getPos().setY(getPos().getY()-1);

        } else if (turn.equals("EAST")) {

            getPos().setX(getPos().getX()+1);

        }
    }
}