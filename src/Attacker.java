/**
 * Classname: Attacker.java
 * Version info 1.0
 * Copyright notice:    Masoud Shofahi
 *                      Samuel Bylund Felixon
 * Date: 19/12/2017
 * Course: Applikationsutveckling i Java
 */

import java.awt.*;
import java.util.LinkedList;

/**
 * Abstract class for Attacker type classes.
 */
abstract public class Attacker implements LandonClass {

    private int health;
    private int moveSpeed;
    private int width;
    private int height;
    private Position pos;
    private Rectangle healthBar;

    String turn = "EAST";

    private LinkedList<Block> directionSign;

    /**
     * Constructor for Attacker
     *
     * @param pos           Position to spawn Attacker
     * @param directionSign List of directionSign Blocks
     * @param health        Start health
     * @param moveSpeed     Movement speed of attacker
     * @param width         Width of attacker bound
     * @param height        Height of attacker bound
     */
    Attacker(Position pos, LinkedList<Block> directionSign,
             int health, int moveSpeed, int width, int height) {

        this.pos = new Position(pos.getX(), pos.getY());
        this.width = width;
        this.height = height;
        this.health = health;
        this.moveSpeed = moveSpeed;
        this.directionSign = directionSign;
        healthBar = new Rectangle(pos.getX(), pos.getY() + 10,
                width, height / 10);
    }

    /**
     * Method that will update the attacker one "step".
     * 1. Check if the attacker should turn
     * 2. Take one step (change position to N/S/E/W)
     */
    abstract public void update();

    /**
     * Method to check if the attackers' rectangle bound has intersected a
     * DiretionSign. If there is a intersect, change the direction that the
     * Attacker is facing.
     */
    public void getTurn() {

        for (int i = 0; i < getDirSign().size(); i++) {

            if (getBound().intersects(getDirSign().get(i).getBound())
                    && getDirSign().get(i).getBlockType()
                    == BlockType.TURNWEST) {

                turn = "WEST";
            } else if (getBound().intersects(getDirSign().get(i).getBound())
                    && getDirSign().get(i).getBlockType()
                    == BlockType.TURNSOUTH) {

                turn = "SOUTH";
            } else if (getBound().intersects(getDirSign().get(i).getBound())
                    && getDirSign().get(i).getBlockType()
                    == BlockType.TURNNORTH) {

                turn = "NORTH";
            } else if (getBound().intersects(getDirSign().get(i).getBound())
                    && getDirSign().get(i).getBlockType()
                    == BlockType.TURNEAST) {

                turn = "EAST";
            } else if (getBound().intersects(getDirSign().get(i).getBound())
                    && getDirSign().get(i).getBlockType()
                    == BlockType.TURN_Y) {
                if (getDirSign().get(i).isyNorth()) {

                    turn = "NORTH";
                } else {

                    turn = "SOUTH";
                }
            } else if (getBound().intersects(getDirSign().get(i).getBound())
                    && getDirSign().get(i).getBlockType()
                    == BlockType.TURN_X) {

                if (getDirSign().get(i).isxWest()) {

                    turn = "WEST";
                } else {

                    turn = "EAST";
                }
            }
        }
    }

    /**
     * Inflict damage on the Attacker.
     *
     * @param dmg The amount of damage to inflict.
     */
    public void inflictDamage(int dmg) { setHealth(getHealth() - dmg);
    }

    /**
     * abstract method for rendering the graphics of the current
     * attacker.
     *
     * @param g The graphics of the game
     */
    abstract public void render(Graphics g);


    /**
     * get current health of attacker
     *
     * @return the health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Change the current health of an attacker.
     *
     * @param newHealth the new healthpoint amount
     */
    public void setHealth(int newHealth) { this.health = newHealth;
    }

    /**
     * get the width of the attacker "Block"
     *
     * @return The width
     */
    public int getWidth() {
        return width;
    }

    /**
     * get the height of the attacker "Block"
     *
     * @return The height
     */
    public int getHeight() {
        return height;
    }

    /**
     * get the current position of the attacker
     *
     * @return The position
     */
    public Position getPos() {
        return pos;
    }

    /**
     * set a new position of the attacker
     *
     * @param pos The new position
     */
    public void setPos(Position pos) {
        this.pos = pos;
    }

    /**
     * Get the direction that the attacker should turn
     * (The attacker has intersected a turn-Block)
     *
     * @return the direction (North/West/South/East)
     */
    public LinkedList<Block> getDirSign() {
        return directionSign;
    }

    /**
     * get the current health bar of an attacker
     *
     * @return the healthBar
     */
    public Rectangle getHealthBar() {
        return healthBar;
    }

    /**
     *  get the current turn value
     *
     * @return
     */
    public String getTurnValue() {
        return turn;
    }

    /**
     * Change turn value
     *
     * @param turn direction to turn
     */
    public void setTurnValue(String turn) {
        this.turn = turn;
    }

    /**
     * Get bound of attacker
     * @return The bound as a Rectangle object
     */
    abstract public Rectangle getBound();

    /**
     * Get move speed of Attacker
     * @return
     */
    public int getMoveSpeed() {
        return moveSpeed;
    }
}