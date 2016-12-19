/**
 * Classname: NuclearDefender.java
 * Version info 1.0
 * Copyright notice:    Masoud Shofahi
 *                      Amanda Dahlin
 *                      Gustav Norlander
 *                      Samuel Bylund Felixon
 * Date: 17/12/2017
 * Course: Applikationsutveckling i Java
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 * NuclearDefender
 * @author Masoud Shofahi
 *         Amanda Dahlin
 *         Gustav Norlander
 *         Samuel Bylund Felixon
 */
public class NuclearDefender extends Defender {

    static private final int DAMAGE = 1;
    static private final int RANGE = 200;
    static private final int FIRE_RATE = 2;

    private BufferedImage towerImg;
    private LoadImage loadImage = new LoadImage();
    
    private Queue <Attacker> enemyList = new LinkedList<Attacker>();

    /**
     * Constructor for Nuclear Defender
     * @param pos Position to spawn defender
     * @param attackersList List that will contain all enemies on the map
     */
    public NuclearDefender(Position pos, LinkedList<Attacker> attackersList) {
        super(pos, DAMAGE, RANGE,attackersList);

        towerImg = loadImage.loadTheImage("NuclearTower.png");
    }

    /**
     * Override method that checks if any attackers are within range,
     * adds attacker to its' "enemyList" if it is in range, removes
     * if not in range. Inflicts damage to all attackers in the
     * enemyList. Also removes attacker from the enemyList if the
     * attackers health reaches 0. If another tower killed the attacker,
     * the last if-statement makes sure that the tower will still remove
     * attacker from its enemy list.
     */
    @Override
    void update() {

        for(int i = 0; i< getAttackerList().size(); i++){

            if(getRangeBound().intersects(getAttackerList().get(i).getBound())
                    && !enemyList.contains(getAttackerList().get(i))) {

                enemyList.add(getAttackerList().get(i));
            }
        }

        if(!enemyList.isEmpty()){

            for (Attacker a : enemyList) {

        	       a.inflictDamage(DAMAGE);
        	}
        }

        if(!enemyList.isEmpty()
                && !enemyList.peek().getBound().intersects(getRangeBound())) {

            enemyList.remove();
        }

        if(!enemyList.isEmpty() && enemyList.peek().getHealth() == 0){

            enemyList.peek().inflictDamage(1);
            enemyList.remove();
        }

        if(!enemyList.isEmpty()
                && !getAttackerList().contains(enemyList.peek())){

            enemyList.remove();
        }
    }

    /**
     * Override method to render defender graphics to the screen.
     * @param g The graphics of the game
     */
    @Override
    void render(Graphics g) {

        g.drawImage(towerImg,getPos().getX(),getPos().getY(),
                towerImg.getWidth(),towerImg.getHeight(),null);

        if(!enemyList.isEmpty()){

        	g.setColor(new Color(0, 255, 0, 80));
        	g.fillOval(getPos().getX() - (getRange() / 5)
                    + (towerImg.getWidth() / 5), getPos().getY()
                    - (getRange() / 5) + (towerImg.getHeight() / 5),
                    getRange() / 2,getRange() / 2);
        	
        	g.setColor(new Color(0, 255, 0, 60));
        	g.fillOval(getPos().getX() - (getRange() / 2)
                    + (towerImg.getWidth()/2), getPos().getY()
                    - (getRange() / 2) + (towerImg.getHeight() / 2),
                    getRange(),getRange());
        	
        	g.setColor(new Color(0, 255, 0, 40));
        	g.fillOval(getPos().getX() - getRange()
                    + (towerImg.getWidth()) + 40, (getPos().getY()
                    - (getRange()) + (towerImg.getHeight()) + 40),
                    getRange() + 80,getRange() + 80);
        }
    }

    /**
     * Override method to get defender bound
     * @return The Defenders' bound as a rectangle
     */
    @Override
    public Rectangle getBound() {

        return new Rectangle(getPos().getX(),getPos().getY(),
                towerImg.getWidth(),towerImg.getHeight());
    }

    /**
     * Override method to get the defenders' range bound
     * @return The Defenders' range bound as a rectangle
     */
    @Override
    public Rectangle getRangeBound() {

        return new Rectangle(
                getPos().getX()-(getRange()/2) + (towerImg.getWidth() /2),
                getPos().getY()-(getRange()/2) + (towerImg.getHeight()/2),
                getRange(),getRange());
    }

    /**
     * Getter for the towers enemy list (enemies within range)
     * @return The list
     */
    public Queue<Attacker> getEnemyList(){
        return enemyList;
    }
}
