
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;

public class WorldHandler {

    private LinkedList<Block> blocks;
    private int blockSize;

    private LoadImage lvlLoad = new LoadImage();
    private LinkedList<BufferedImage> listOfLevels = null;

    private Position startPosition;
    private Rectangle goalPosition;

    //This list will store the directions sign for the level
    private LinkedList<Block> turns = new LinkedList<>();
    private LinkedList<Attacker> attackersList = new LinkedList<>();

    //This is just temporary and should be replaced with Attack class

    public WorldHandler(int blockSize){

        blocks = new LinkedList<>();
        this.blockSize = blockSize;

    }

    public void loadAllImages(){

        listOfLevels = new LinkedList<>();

        File  directory = new File("Levels");

        for (File file : directory.listFiles())
        {
            // could also use a FileNameFilter
            if(file.getName().toLowerCase().endsWith(".png"))
            {
                BufferedImage tmp = lvlLoad.loadTheImage(file.getName());
                listOfLevels.add(tmp);
            }
        }
    }

    public void render(Graphics g){

        for (int i = 0; i < blocks.size();i++){
            blocks.get(i).render(g);
        }
        for (int i = 0; i < attackersList.size(); i++){
            attackersList.get(i).render(g);
        }

    }

    public void update(){

        for (int i = 0; i < attackersList.size(); i++){
            attackersList.get(i).update();
            if(attackersList.get(i).getBound().intersects(goalPosition)){
                attackersList.remove(i);
            }
        }
    }

    public void loadImageLevel(int levelSelect){

        //läs in banan
        BufferedImage lvl = listOfLevels.get(levelSelect);

        //Hämtar bildens storlek
        int w = lvl.getWidth();
        int h = lvl.getHeight();

        //Looppar genom alla pixlar i bilden
        for (int xx = 0; xx < h; xx++) {
            for (int yy = 0; yy < w; yy++) {

                int pixel = lvl.getRGB(xx,yy);
                //hur mycket röd färg på pixel
                int red = (pixel >> 16) & 0xff;
                //hur mycket grön färg på pixel
                int green = (pixel >> 8) & 0xff;
                //hur mycket blå färg på pixel
                int blue = (pixel) & 0xff;

                //*********Kommer göra XML för alla färger**************
                //Path          Color = orange
                if (red == 253 && green == 135 && blue == 26){

                    Position pos = new Position(xx*blockSize,yy*blockSize);
                    blocks.add(new LevelBlocks(pos,blockSize,blockSize,BlockType.PATH));
                }

                if (red == 251 && green == 0 && blue == 7){

                    Position pos = new Position(xx*blockSize,yy*blockSize);
                    blocks.add(new LevelBlocks(pos,blockSize,blockSize,BlockType.GOALPOSITION));
                    goalPosition = new Rectangle(pos.getX(),pos.getY(),blockSize,blockSize);
                }

                if (red == 27 && green == 209 && blue == 30){

                    Position pos = new Position(xx*blockSize,yy*blockSize);
                    blocks.add(new LevelBlocks(pos,blockSize,blockSize,BlockType.STARTPOSITION));
                    startPosition = pos;
                }

                //Get the turns
                if(red == 0 && green == 0 && blue == 255){
                    Position pos = new Position(xx*blockSize,yy*blockSize);
                    blocks.add(new LevelBlocks(pos,blockSize,blockSize,BlockType.TURNSOUTH));
                    turns.add(new LevelBlocks(pos,blockSize,blockSize,BlockType.TURNSOUTH));
                }
                if(red == 255 && green == 255 && blue == 0){
                    Position pos = new Position(xx*blockSize,yy*blockSize);
                    blocks.add(new LevelBlocks(pos,blockSize,blockSize,BlockType.TURNEAST));
                    turns.add(new LevelBlocks(pos,blockSize,blockSize,BlockType.TURNEAST));
                }
                if(red == 128 && green == 0 && blue == 128){
                    Position pos = new Position(xx*blockSize,yy*blockSize);
                    blocks.add(new LevelBlocks(pos,blockSize,blockSize,BlockType.TURNNORTH));
                    turns.add(new LevelBlocks(pos,blockSize,blockSize,BlockType.TURNNORTH));

                }
                if(red == 0 && green == 255 && blue == 255){
                    Position pos = new Position(xx*blockSize,yy*blockSize);
                    blocks.add(new LevelBlocks(pos,blockSize,blockSize,BlockType.TURNWEST));
                    turns.add(new LevelBlocks(pos,blockSize,blockSize,BlockType.TURNWEST));
                }
            }
        }

    }

    public void addStaicBlock(Block obj){
        blocks.add(obj);
    }

    public Position getStartPosition() {

        return startPosition;
    }

    public Rectangle getGoalPosition() {
        return goalPosition;
    }


    public LinkedList<Block> getTurns() {
        return turns;
    }

    public void createNewAttacker(AttackerType type){

        if(type.equals(AttackerType.NORMALATTACKER)){
            attackersList.add(new NormalAttacker(startPosition,turns));
        }

    }
}
