import javafx.geometry.Pos;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 * NormalAttacker
 * made by saby
 */
public class NormalAttacker extends Attacker {
    static private final int SPEED = 10;            //Default speed for Normal
    static private final int START_HEALTH = 100;    //Default health for Normal
    static private final int ATTACKER_WIDTH = 20;
    static private final int ATTACKER_HEIGHT = 20;

    private BufferedImage normatAttackerImg;

    private LoadImage loadImage = new LoadImage();
    public NormalAttacker(Position pos, LinkedList<Block> directionSign){
        super(pos, directionSign, START_HEALTH, SPEED,ATTACKER_WIDTH,ATTACKER_HEIGHT);
        normatAttackerImg = loadImage.loadTheImage("Monster1.png");
    }

    public void render(Graphics g){
        g.drawImage(normatAttackerImg,getPos().getX(),getPos().getY(),getWidth(),getHeight(),null);
    }

    public void getTurn() {

        for (int i = 0; i < getDirectionSign().size(); i++) {

            if (getBound().intersects(getDirectionSign().get(i).getBound()) &&
                    getDirectionSign().get(i).getBlockType() == BlockType.TURNWEST) {

                turn = "WEST";
            } else if (getBound().intersects(getDirectionSign().get(i).getBound())
                    && getDirectionSign().get(i).getBlockType() == BlockType.TURNSOUTH) {

                turn = "SOUTH";
            } else if (getBound().intersects(getDirectionSign().get(i).getBound())
                    && getDirectionSign().get(i).getBlockType() == BlockType.TURNNORTH) {

                turn = "NORTH";
            } else if (getBound().intersects(getDirectionSign().get(i).getBound())
                    && getDirectionSign().get(i).getBlockType() == BlockType.TURNEAST) {

                turn = "EAST";
            }
        }
    }
    public void update() {

        getTurn();

        if (turn.equals("WEST")) {
            getPos().setX(getPos().getX()-1);

        } else if (turn.equals("SOUTH")) {
            getPos().setY(getPos().getY()+1);
        } else if (turn.equals("NORTH")) {
            getPos().setY(getPos().getY()-1);
        } else if (turn.equals("EAST")) {
            getPos().setX(getPos().getX()+1);

        }
    }

    public void inflictDamage(int dmg) {
        int currentHP = this.getHealth();
        int newHP = currentHP - dmg;

        if (newHP <= 0) {
            //TODO: KILL the attacker somehow
            System.out.println("DEAD");
        } else {
            this.setHealth(newHP);
        }
    }

    @Override
    public Rectangle getBound() {
        return new Rectangle(getPos().getX(),getPos().getY(),getWidth(),getHeight());
    }
}