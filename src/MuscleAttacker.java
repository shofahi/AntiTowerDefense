//import com.sun.corba.se.impl.orbutil.graph.Graph;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 * MuscleAttacker
 */
public class MuscleAttacker extends Attacker {
    static private final int SPEED = 5;            //Default speed for Muscle
    static private final int START_HEALTH = 250;    //Default health for Muscle
    static private final int ATTACKER_WIDTH = 25;
    static private final int ATTACKER_HEIGHT = 25;


    private BufferedImage muscleAttackerImg;

    private LoadImage loadImage = new LoadImage();
    public MuscleAttacker(Position pos, LinkedList<Block> directionSign){
        super(pos, directionSign, START_HEALTH, SPEED,ATTACKER_WIDTH,ATTACKER_HEIGHT);
        muscleAttackerImg = loadImage.loadTheImage("MuscleMonster.png");
    }

    public void render(Graphics g){
        g.drawImage(muscleAttackerImg,getPos().getX(),getPos().getY(),getWidth(),getHeight(),null);

        g.setColor(Color.RED);
        g.drawRect(getPos().getX(),getPos().getY()-10,getHealthBar().width-1,getHealthBar().height-1);
        g.setColor(Color.GREEN);
        g.fillRect(getPos().getX(),getPos().getY()-getWidth()/2,getHealthBar().width*getHealth()/START_HEALTH,getHealthBar().height);
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

        setHealth(getHealth()-dmg);

    }

    @Override
    public Rectangle getBound() {

        return new Rectangle(getPos().getX(),getPos().getY(),getWidth(),getHeight());
    }
}