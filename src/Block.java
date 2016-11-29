import java.awt.*;

/**
 * This is class will be implemented by Objects that are static in game.
 */


abstract public class Block {

    private Position pos;
    private int width;
    private int height;
    private BlockType blockType;

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

    public Position getPos() {
        return pos;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public BlockType getBlockType() {
        return blockType;
    }

    /**
     * Method will render the block to the screen
     * @param g - Graphics
     */
    public abstract void render(Graphics g);
    public abstract Rectangle getBound();
}

