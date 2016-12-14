import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

public class NuclearDefender extends Defender {
    static private final int DAMAGE = 1;
    static private final int RANGE = 200;
    static private final int FIRE_RATE = 2;
    private BufferedImage towerImg;
    private LoadImage loadImage = new LoadImage();
    
    private Queue <Attacker> enemyList = new LinkedList<Attacker>();

    public NuclearDefender(Position pos, LinkedList<Attacker> attackersList) {
        super(pos, DAMAGE, RANGE, FIRE_RATE,attackersList);

        towerImg = loadImage.loadTheImage("NuclearTower.png");
    }

    @Override
    void update() {

        for(int i = 0; i< getAttackersList().size(); i++){

            if(getRangeBound().intersects(getAttackersList().get(i).getBound()) && !enemyList.contains(getAttackersList().get(i))) {
                enemyList.add(getAttackersList().get(i));
            }
        }

        if(!enemyList.isEmpty()){
        	for (Attacker a : enemyList) {
        	    a.inflictDamage(DAMAGE);
        	}
        }
        if(!enemyList.isEmpty() && !enemyList.peek().getBound().intersects(getRangeBound())) {
            enemyList.remove();
        }
        if(!enemyList.isEmpty() && enemyList.peek().getHealth() == 0){
            System.out.println("Removing from the queue");
            enemyList.peek().inflictDamage(1);
            enemyList.remove();
        }
    }

    @Override
    void render(Graphics g) {
        g.drawImage(towerImg,getPos().getX(),getPos().getY(),towerImg.getWidth(),towerImg.getHeight(),null);

        if(!enemyList.isEmpty()){
        	g.setColor(new Color(0, 255, 0, 80));
        	g.fillOval(getPos().getX()-(getRange()/5)+(towerImg.getWidth()/5),getPos().getY()-(getRange()/5)+(towerImg.getHeight()/5),getRange()/2,getRange()/2);
        	
        	g.setColor(new Color(0, 255, 0, 60));
        	g.fillOval(getPos().getX()-(getRange()/2)+(towerImg.getWidth()/2),getPos().getY()-(getRange()/2)+(towerImg.getHeight()/2),getRange(),getRange());
        	
        	g.setColor(new Color(0, 255, 0, 40));
        	g.fillOval(getPos().getX()-(getRange())+(towerImg.getWidth())+40,(getPos().getY()-(getRange())+(towerImg.getHeight())+40),getRange()+80,getRange()+80);
        }
    }

    @Override
    public Rectangle getBound() {
        return null;
    }

    @Override
    public Rectangle getRangeBound() {
        return new Rectangle(getPos().getX()-(getRange()/2)+(towerImg.getWidth()/2),getPos().getY()-(getRange()/2)+
                    (towerImg.getHeight()/2),getRange(),getRange());
    }
}
