/**
 * Classname: NormalDefender.java
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

public class NormalDefender extends Defender {
    static private final int DAMAGE = 1;
    static private final int RANGE = 300;
    private BufferedImage towerImg;
    private LoadImage loadImage = new LoadImage();

    private Queue <Attacker> enemyList = new LinkedList<Attacker>();


    /**
     * Constructor for Normal Defender
     * @param pos position to place the defender
     * @param attackersList List that contains all enemies on the map
     */
    public NormalDefender(Position pos, LinkedList<Attacker> attackersList) {
        super(pos, DAMAGE, RANGE,attackersList);

        towerImg = loadImage.loadTheImage("Torn1.png");
    }

    /**
     * Override method that checks if any attackers are within range,
     * adds attacker to its' "enemyList" if it is in range, removes
     * if not in range. Inflicts damage to the first attacker in the
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
            enemyList.peek().inflictDamage(DAMAGE);
        }

        if(!enemyList.isEmpty()
                && !enemyList.peek().getBound().intersects(getRangeBound())) {
            enemyList.remove();
        }

        if(!enemyList.isEmpty() && enemyList.peek().getHealth() == 0){
            System.out.println("Removing from the queue");
            enemyList.peek().inflictDamage(1);
            enemyList.remove();
        }

        if(!enemyList.isEmpty()
                && !getAttackerList().contains(enemyList.peek())){
            enemyList.remove();
        }
    }

    /**
     * Render Defender graphics to screen.
     * @param g The graphics of the game
     */
    @Override
    void render(Graphics g) {
        g.drawImage(towerImg,getPos().getX(),getPos().getY(),
                towerImg.getWidth(),towerImg.getHeight(),null );

        g.setColor(Color.blue);
        g.drawRect(getPos().getX() - (getRange() / 2)
                + (towerImg.getWidth() / 2), getPos().getY()
                - (getRange() / 2) + (towerImg.getHeight() / 2),
                getRange(),getRange() );

        if(!enemyList.isEmpty()){
            g.setColor(Color.green);
            g.drawLine(getPos().getX(),getPos().getY(),
                    enemyList.peek().getPos().getX()+10,
                    enemyList.peek().getPos().getY()+10);
        }
    }

    /**
     * Override method to get the defender "Bound".
     * @return the bound as a rectangle
     */
    @Override
    public Rectangle getBound() {

        return new Rectangle(getPos().getX(),getPos().getY(),
                             towerImg.getWidth(),towerImg.getHeight());
    }

    /**
     * Override method to get the defender range bound
     * @return the bound as a rectangle
     */
    @Override
    public Rectangle getRangeBound() {
        return new Rectangle(
                getPos().getX() - (getRange()/2) + (towerImg.getWidth()/2),
                getPos().getY() - (getRange()/2) + (towerImg.getHeight()/2),
                getRange(),getRange());
    }

    public Queue<Attacker> getEnemyList(){
        return enemyList;
    }
}
