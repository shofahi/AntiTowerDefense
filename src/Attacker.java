import java.awt.image.BufferedImage;
import java.awt.*;
import java.util.LinkedList;

/**
 * Attacker
 * made by saby
 */
abstract class Attacker {

    private int health;
    private int moveSpeed;
    private int width;
    private int height;
    private Position pos;

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
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int newHealth) {
        this.health = newHealth;
    }

    public int getMoveSpeed() {
        return moveSpeed;
    }

    private void setMoveSpeed(int newSpeed) {
        this.moveSpeed = newSpeed;
    }

    private boolean isAlive() {
        return this.health > 0;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Position getPos() {
        return pos;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    public LinkedList<Block> getDirectionSign() {
        return directionSign;
    }

    /**
     * move
     * abstract method for moving Attacker
     */
    abstract public void getTurn();


    abstract public void update();

    abstract public void render(Graphics g);

    abstract public void inflictDamage(int dmg);

    abstract public Rectangle getBound();

}