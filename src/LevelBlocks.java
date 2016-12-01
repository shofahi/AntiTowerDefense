import java.awt.*;


public class LevelBlocks extends Block {

    public LevelBlocks(Position pos, int width, int height, BlockType blockType) {
        super(pos, width, height, blockType);
    }

    public LevelBlocks(int xPos, int yPos, int width, int height, BlockType blockType) {
        super(xPos,yPos, width, height, blockType);
    }



    @Override
    public void render(Graphics g) {

        if(getBlockType() == BlockType.PATH){
            g.setColor(Color.ORANGE);
            g.fillRect(getPos().getX(),getPos().getY(),getWidth(),getHeight());
        }
        else if(getBlockType() == BlockType.GOALPOSITION){
            g.setColor(Color.RED);
            g.fillRect(getPos().getX(),getPos().getY(),getWidth(),getHeight());

        }
        else if(getBlockType() == BlockType.STARTPOSITION){
            g.setColor(Color.GREEN);
            g.fillRect(getPos().getX(),getPos().getY(),getWidth(),getHeight());

        }

    }

    @Override
    public Rectangle getBound() {
        return new Rectangle(getPos().getX(),getPos().getY(),getWidth(),getHeight());
    }

    @Override
    public void landOn(Graphics g){
        //Do something here......
    }

}
