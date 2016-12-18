import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;


public class MouseAdapter implements MouseListener {

    public boolean clicked = false;
    private LinkedList<Block>blocks;
    public MouseAdapter(LinkedList<Block> blocks){
        this.blocks = blocks;
    }

    /**
     * Method will change the direction of invertible signs
     * @param e Mouse event
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("HI");

        for (int i = 0; i < blocks.size(); i++){

            Rectangle mouseRect = new Rectangle(e.getX(),e.getY(),20,20);

            if(mouseRect.intersects(blocks.get(i).getBound()) && blocks.get(i).getBlockType().equals(BlockType.TURN_Y)){
                blocks.get(i).setyNorth();
            }

            if(mouseRect.intersects(blocks.get(i).getBound()) && blocks.get(i).getBlockType().equals(BlockType.TURN_X)){
                blocks.get(i).setxWest();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


}
