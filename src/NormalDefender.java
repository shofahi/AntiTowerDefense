import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

public class NormalDefender extends Defender {
    static private final int DAMAGE = 1;
    static private final int RANGE = 300;
    static private final int FIRE_RATE = 3;
    private BufferedImage towerImg;
    private LoadImage loadImage = new LoadImage();

    private Queue <Attacker> enemyList = new LinkedList<>();


    public NormalDefender(Position pos, LinkedList<Attacker> attackersList) {
        super(pos, DAMAGE, RANGE, FIRE_RATE,attackersList);

        towerImg = loadImage.loadTheImage("Torn1.png");
    }

    @Override
    void update() {


        for(int i = 0; i< getAttackersList().size(); i++){

            if(getRageBound().intersects(getAttackersList().get(i).getBound()) && !enemyList.contains(getAttackersList().get(i))) {
                enemyList.add(getAttackersList().get(i));
            }

            if(!enemyList.isEmpty() && !enemyList.peek().getBound().intersects(getRageBound())) {
                enemyList.remove();
            }

            if(!enemyList.isEmpty() &&enemyList.peek().getHealth() == 0){
                System.out.println("Removing from the queue");
                enemyList.peek().inflictDamage(1);
                enemyList.remove();
            }

            if(!enemyList.isEmpty()){
                enemyList.peek().inflictDamage(DAMAGE);
                break;
            }
        }
    }

    @Override
    void render(Graphics g) {
        g.drawImage(towerImg,getPos().getX(),getPos().getY(),towerImg.getWidth(),towerImg.getHeight(),null);

        g.setColor(Color.blue);
        g.drawRect(getPos().getX()-(getRange()/2)+(towerImg.getWidth()/2),getPos().getY()-(getRange()/2)+(towerImg.getHeight()/2),getRange(),getRange());

        if(!enemyList.isEmpty()){
            g.drawLine(getPos().getX(),getPos().getY(),enemyList.peek().getPos().getX()+10,enemyList.peek().getPos().getY()+10);
        }

        //System.out.println("Size of Queue is: " + enemyList.size());



    }

    @Override
    public Rectangle getBound() {
        return null;
    }

    @Override
    public Rectangle getRageBound() {
        return new Rectangle(getPos().getX()-(getRange()/2)+(towerImg.getWidth()/2),getPos().getY()-(getRange()/2)+
                    (towerImg.getHeight()/2),getRange(),getRange());
    }
}
