import java.awt.*;
import java.util.LinkedList;

public abstract class Defender {

    private Position pos;
    private int damage;
    private int range;
    private int fireRate;

    private LinkedList<Attacker> attackersList;

    public Defender(Position pos, int damage, int range, int fireRate,LinkedList<Attacker> attackersList){

        this.pos = new Position(pos.getX(),pos.getY());
    	this.damage = damage;
    	this.range = range;
    	this.fireRate = fireRate;
        this.attackersList = attackersList;
    }

    public Position getPos() {
        return pos;
    }

    public int getDamage() {
        return damage;
    }

    public int getRange() {
        return range;
    }

    public int getFireRate() {
        return fireRate;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public LinkedList<Attacker> getAttackersList() {
        return attackersList;
    }

    abstract void update();

    abstract void render(Graphics g);

    abstract public Rectangle getBound();

    abstract public Rectangle getRageBound();

}
