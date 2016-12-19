/**
 * Classname: MouseAdapter.java
 * Version info 1.0
 * Copyright notice:    Masoud Shofahi
 *                      Amanda Dahlin
 *                      Gustav Norlander
 *                      Samuel Bylund Felixon
 * Date: 17/12/2017
 * Course: Applikationsutveckling i Java
 */
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

            if(mouseRect.intersects(blocks.get(i).getBound())
                    && blocks.get(i).getBlockType().equals(BlockType.TURN_Y)){
                blocks.get(i).setyNorth();
            }

            if(mouseRect.intersects(blocks.get(i).getBound())
                    && blocks.get(i).getBlockType().equals(BlockType.TURN_X)){
                blocks.get(i).setxWest();
            }
        }
    }

    /**
     * Empty override
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * Empty override
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Empty override
     * @param e
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Empty override
     * @param e
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }
}
