import java.awt.*;


public class PlayerTMP {

    Position pos = new Position(60,60);

    public void render(Graphics g){
        g.setColor(Color.pink);
        g.fillRect(pos.getX(),pos.getY(),20,20);
    }

    public void update(){
        int currentX = pos.getX();
        currentX++;
        pos.setX(currentX);
    }
}
