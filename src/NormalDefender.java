import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 * Created by MasoudMac on 24/11/16.
 */
public class NormalDefender extends Defender {
    static private final int DAMAGE = 10;
    static private final int RANGE = 300;
    static private final int FIRE_RATE = 3;
    private BufferedImage towerImg;
    private LoadImage loadImage = new LoadImage();

    public NormalDefender(Position pos, LinkedList<Attacker> attackersList) {
        super(pos, DAMAGE, RANGE, FIRE_RATE,attackersList);

        towerImg = loadImage.loadTheImage("Torn1.png");
    }

    @Override
    void update() {

        for(int i = 0; i< getAttackersList().size(); i++){
            if(getBound().intersects(getAttackersList().get(i).getBound())){
                System.out.println("Pang pang");
            }
        }
    }

    @Override
    void render(Graphics g) {
        g.drawImage(towerImg,getPos().getX(),getPos().getY(),towerImg.getWidth(),towerImg.getHeight(),null);
        g.setColor(Color.blue);
        g.drawRect(getPos().getX()-(getRange()/2)+(towerImg.getWidth()/2),getPos().getY()-(getRange()/2)+(towerImg.getHeight()/2),getRange(),getRange());
    }

    @Override
    public Rectangle getBound() {
        return null;
    }

    @Override
    public Rectangle getRageBound() {
        return new Rectangle(getPos().getX(),getPos().getY(),getRange(),getRange());
    }
}
