/**
 * Classname: MuscleAttacker.java
 * Version info 1.0
 * Copyright notice:    Masoud Shofahi
 *                      Gustav Norlander
 *                      Samuel Bylund Felixon
 * Date: 17/12/2017
 * Course: Applikationsutveckling i Java
 */
import java.awt.*;
import java.util.LinkedList;

/**
 * Abstract class for Defender type classes.
 */
public abstract class Defender {

    private Position pos;
    private int damage;
    private int range;

    private LinkedList<Attacker> attackersList;

    /**
     * Constructor for Defender
     * @param pos Position to place Defender
     * @param damage Damage of defender
     * @param range Range of defender
     * @param attackersList List that contains spawned attackers
     */
    public Defender(Position pos, int damage, int range,
                    LinkedList<Attacker> attackersList){

        this.pos = new Position(pos.getX(),pos.getY());
    	this.damage = damage;
    	this.range = range;
        this.attackersList = attackersList;
    }

    /**
     * get the current position of the Defender
     * @return The position
     */
    public Position getPos() {
        return pos;
    }

    /**
     * get the damage that the tower will inflict on attackers for every
     * "shot fired"
     * @return
     */
    public int getDamage() {
        return damage;
    }

    /**
     * get the range of the tower
     * @return The range
     */
    public int getRange() {
        return range;
    }

    /**
     * Change how much damage the Defender will make in each "Shot"
     * @param damage The amount of damage
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * Change the range of the Defender
     * @param range The new range
     */
    public void setRange(int range) {
        this.range = range;
    }

    /**
     * Get the list of Attackers currently in range for the Defender
     * @return The list of Attackers
     */
    public LinkedList<Attacker> getAttackerList() {
        return attackersList;
    }

    /**
     * abstract method that will declare what the Defender should do in each
     * frame
     */
    abstract void update();

    /**
     * absract method that will declare what should be rendered to the
     * graphics for each frame (Lazer, pulsing etc)
     * @param g The graphics of the game
     */
    abstract void render(Graphics g);

    /**
     * abstract method to get the bound of the Defender
     * @return The bound x*x
     */
    abstract public Rectangle getBound();

    /**
     * abstract method to get the bound of the Defenders fire-range
     * @return The bound x*x
     */
    abstract public Rectangle getRangeBound();
}
