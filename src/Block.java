/**
 * Classname: Block.java
 * Version info 1.0
 * Copyright notice:    Masoud Shofahi
 *                      Amanda Dahlin
 *                      Gustav Norlander
 *                      Samuel Bylund Felixon
 * Date: 19/12/2017
 * Course: Applikationsutveckling i Java
 */
import java.awt.*;

/**
 * This is class will be implemented by Objects that are static in game.
 */


abstract public class Block{

    private Position pos;
    private int width;
    private int height;
    private BlockType blockType;

    boolean yNorth = true;
    boolean xWest = true;

    public Block(Position pos, int width, int height,BlockType blockType){
        this.pos = pos;
        this.width = width;
        this.height = height;
        this.blockType = blockType;
    }

    public Block (int xPos, int yPos, int width,int height, BlockType blockType){

        pos = new Position(xPos,yPos);
        this.width = width;
        this.height = height;
        this.blockType = blockType;
    }

    /**
     * Returns the position of the Block
     * @return position of the block
     */
    public Position getPos() {
        return pos;
    }

    /**
     * Return the width of the block
     * @return width of the block
     */
    public int getWidth() {
        return width;
    }

    /**
     * Return the height of the block
     * @return
     */
    public int getHeight() {
        return height;
    }

    /**
     * Return the "type" of Block
     * @return "type" of Block
     */
    public BlockType getBlockType() {
        return blockType;
    }

    /**
     * Method will render the block to the screen
     * @param g - Graphics
     */
    public abstract void render(Graphics g);
    public abstract Rectangle getBound();

    /**
     * Return the the block type for a specific position
     * @param pos the position
     * @return block type for a specific position
     */
    public boolean getTypeByPosition(Position pos){

        if (this.pos == pos && blockType.equals(BlockType.DEFENDER)){
            return true;
        }
        return false;
    }

    /**
     * Change the direction sign to west/east
     */
    public void setxWest() {

        xWest ^= true;
    }

    /**
     * Change the direction sign to south/north
     */
    public void setyNorth() {
        yNorth ^= true;
    }
}

