import java.awt.image.BufferedImage;

/**
 * NormalAttacker
 * made by saby
 */
public class NormalAttacker extends Attacker {
    static private final int SPEED = 10;            //Default speed for Normal
    static private final int START_HEALTH = 100;    //Default health for Normal

    public NormalAttacker(Block spawnOnBlock, BufferedImage img){
        this(spawnOnBlock, img, START_HEALTH, SPEED);
    }

    private NormalAttacker(Block spawnOnBlock, BufferedImage img, int health,
                          int moveSpeed){
        super(spawnOnBlock, img, health, moveSpeed);
    }
}
