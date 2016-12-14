import java.awt.*;
import java.util.LinkedList;

/**
 * Abstract class Attacker
 * This class contains the basic properties of an attacker aswell as
 * abstract methods that specific attackers want to have different properties.
 */
abstract class Attacker {

    private int health;
    private int moveSpeed;
    private int width;
    private int height;
    private Position pos;
    private Rectangle healthBar;

    String turn = "EAST";

    private LinkedList<Block> directionSign;

    //Spawns an Attacker.
    Attacker(Position pos, LinkedList <Block> directionSign, int health, int moveSpeed, int width, int height) {
        this.pos = new Position(pos.getX(),pos.getY());
        this.width = width;
        this.height = height;
        this.health = health;
        this.moveSpeed = moveSpeed;
        this.directionSign = directionSign;
        healthBar = new Rectangle(pos.getX(),pos.getY()+10,width,height/10);

    }

    /**
     * get current health of attacker
     * @return the health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Change the current health of an attacker.
     * @param newHealth the new healthpoint amount
     */
    public void setHealth(int newHealth) {
        this.health = newHealth;
    }

    /**
     * get the width of the attacker "Block"
     * @return The width
     */
    public int getWidth() {
        return width;
    }

    /**
     * get the height of the attacker "Block"
     * @return The height
     */
    public int getHeight() {
        return height;
    }

    /**
     * get the current position of the attacker
     * @return The position
     */
    public Position getPos() {
        return pos;
    }

    /**
     * set a new position of the attacker
     * @param pos The new position
     */
    public void setPos(Position pos) {
        this.pos = pos;
    }

    /**
     * Get the direction that the attacker should turn
     * (The attacker has intersected a turn-Block)
     * @return the direction (North/West/South/East)
     */
    public LinkedList<Block> getDirectionSign() {
        return directionSign;
    }

    /**
     * get the current health bar of an attacker
     * @return the healthBar
     */
    public Rectangle getHealthBar() {
        return healthBar;
    }

    /**
     * move
     * abstract method for moving Attacker
     */
    abstract public void getTurn();

    /**
     * abstract method that will make the attacker
     * check necessary information of how it will
     * take the next move.
     */
    abstract public void update();

    /**
     * abstract method for rendering the graphics of the current
     * attacker.
     * @param g The graphics of the game
     */
    abstract public void render(Graphics g);

    /**
     * abstract method for inflicting damage om the attacker
     * @param dmg The amount of damage to inflict.
     */
    abstract public void inflictDamage(int dmg);

    /**
     * abstract method for getting the bounds of the "AttackerBlock"
     * @return The rectangle with bounds
     */
    abstract public Rectangle getBound();

    public int getMoveSpeed() {
        return moveSpeed;
    }

    private void setMoveSpeed(int newSpeed) {
        this.moveSpeed = newSpeed;
    }

    private boolean isAlive() {
        return this.health > 0;
    }
    
    public String getTurnValue(){
    	return turn;
    }
    
    public void setTurnValue(String turn){
    	this.turn = turn;
    }

}