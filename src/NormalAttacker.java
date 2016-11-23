import java.util.LinkedList;

/**
 * NormalAttacker
 * made by saby
 */
public class NormalAttacker extends Attacker {
    static private final int SPEED = 10;            //Default speed for Normal
    static private final int START_HEALTH = 100;    //Default health for Normal


    public NormalAttacker(int ID, Position pos, LinkedList<Block> path){
        super(ID, pos, path, START_HEALTH, SPEED);
    }
}
