import java.awt.image.BufferedImage;

/**
 * Attacker
 * made by saby
 */
abstract class Attacker {
    private int health;
    private int moveSpeed;
    private boolean reachedGoal;
    private Block currentBlock;
    private Block nextBlock;
    private BufferedImage img;


    //Spawns an Attacker.
    Attacker(Block spawnOnBlock, BufferedImage img, int health, int moveSpeed){
        this.reachedGoal = false;
        this.currentBlock = spawnOnBlock;
        this.nextBlock = null;
        this.img = img;
        this.health = health;
        this.moveSpeed = moveSpeed;
    }


    public boolean getReachedGoal(){
        return reachedGoal;
    }

    private void setReachedGoal(boolean b){
        this.reachedGoal = b;
    }

    private int getHealth(){
        return health;
    }

    private void setHealth(int newHealth){
        this.health = newHealth;
    }

    public int getMoveSpeed(){
        return moveSpeed;
    }

    private void setMoveSpeed(int newSpeed){
        this.moveSpeed = newSpeed;
    }

    protected void inflictDamage(int dmg){
        int currentHP = this.getHealth();
        int newHP = currentHP - dmg;

        if(newHP <= 0){
            //TODO: KILL the attacker somehow
            System.out.println("DEAD");
        }else{
            this.setHealth(newHP);
        }
    }

    private boolean isAlive(){
        return this.health > 0;
    }

    /*
    private Block getNextBlock(){
        //TODO: next block the Attacker should walk to
    }*/


    /**
     * move
     * abstract method for moving Attacker
     */
    protected void move(){
        if(!reachedGoal && isAlive()){
            System.out.println("MOVE");
            //TODO: check how move should be decided
        }else{
            System.out.println("DONT MOVE");
        }
    }
}
