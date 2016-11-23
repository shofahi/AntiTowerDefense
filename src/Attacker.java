import java.awt.image.BufferedImage;
import java.awt.*;
import java.util.LinkedList;

/**
 * Attacker
 * made by saby
 */
abstract class Attacker {
    private int ID;
    private int health;
    private int moveSpeed;
    private boolean reachedGoal;
    private BufferedImage img;
    Position pos;
    Position nextPos;
    String turn = "EAST";

    private LinkedList<Block> turns;
    private Rectangle playerRect;


    //Spawns an Attacker.
    Attacker(int ID, Position pos, LinkedList <Block> path,
             int health, int moveSpeed) {
        this.reachedGoal = false;
        this.health = health;
        this.moveSpeed = moveSpeed;
        this.pos = pos;
        this.turns = path;
        nextPos = null;
        playerRect = new Rectangle(pos.getX(),pos.getY(),20,20);
    }


    public boolean getReachedGoal() {
        return reachedGoal;
    }

    private void setReachedGoal(boolean b) {
        this.reachedGoal = b;
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

    protected void inflictDamage(int dmg) {
        int currentHP = this.getHealth();
        int newHP = currentHP - dmg;

        if (newHP <= 0) {
            //TODO: KILL the attacker somehow
            System.out.println("DEAD");
        } else {
            this.setHealth(newHP);
        }
    }

    private boolean isAlive() {
        return this.health > 0;
    }


    /**
     * move
     * abstract method for moving Attacker
     */
    public void getTurn() {
        if (!reachedGoal && isAlive()) {
            for (int i = 0; i < turns.size(); i++) {
                if(playerRect.intersects(turns.get(i).getBound()) && turns.get(i).getBlockType() == BlockType.GOALPOSITION) {
                    System.out.println("Attacker " + this.ID + "IS in goal!!!");
                }
                else if (playerRect.intersects(turns.get(i).getBound()) && turns.get(i).getBlockType() == BlockType.TURNWEST) {
                    turn = "WEST";
                } else if (playerRect.intersects(turns.get(i).getBound()) && turns.get(i).getBlockType() == BlockType.TURNSOUTH) {
                    turn = "SOUTH";
                } else if (playerRect.intersects(turns.get(i).getBound()) && turns.get(i).getBlockType() == BlockType.TURNNORTH) {
                    turn = "NORTH";
                } else if (playerRect.intersects(turns.get(i).getBound()) && turns.get(i).getBlockType() == BlockType.TURNEAST) {
                    turn = "EAST";
                }
            }
        } else {
            System.out.println("DONT MOVE");
        }
    }

    public void update() {

        getTurn();

        if (turn.equals("WEST")) {
            playerRect.x--;

        } else if (turn.equals("SOUTH")) {
            playerRect.y++;
        } else if (turn.equals("NORTH")) {
            playerRect.y--;
        } else if (turn.equals("EAST")) {
            playerRect.x++;
        }
    }

    public void render(Graphics g){
        g.setColor(Color.BLUE);
        g.fillRect(playerRect.x,playerRect.y,playerRect.width,playerRect.height);
    }
}
