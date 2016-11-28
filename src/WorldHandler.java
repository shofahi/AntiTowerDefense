
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;

public class WorldHandler {

    private int blockSize;

    private LoadImage lvlLoad = new LoadImage();
    private LinkedList<BufferedImage> listOfLevels = null;

    private Position startPosition;
    private Rectangle goalPosition;

    //This list will store the directions sign for the level
    private LinkedList<Block> directionList = new LinkedList<>();

    //List of game objects
    private LinkedList<Attacker> attackersList = new LinkedList<>();
    private LinkedList<Defender> defendersList = new LinkedList<>();
    private LinkedList<Block> blocks;

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

        for (int i = 0; i < defendersList.size(); i++){
            defendersList.get(i).render(g);
        }

        for (int i = 0; i < attackersList.size(); i++){
            attackersList.get(i).render(g);
        }
    }

    /**
     * 60frames per second
     */
    public void update(){

        //defender
        for (int i = 0; i < defendersList.size(); i++){
            defendersList.get(i).update();
        }

        for (int i = 0; i < attackersList.size(); i++){
            attackersList.get(i).update();
            if(attackersList.get(i).getBound().intersects(goalPosition) || attackersList.get(i).getHealth() < 0){
                attackersList.remove(i);
            }

        }
    }

    public void loadImageLevel(int levelSelect){/*ska vi döpa om detta till generateLevel?*/

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

                //Path          Color = orange
                if (red == 255 && green == 128 && blue == 0){
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

                //DEFENDER
                if(red == 223 && green == 0 && blue == 255){
                    Position pos = new Position(xx*blockSize,yy*blockSize);
                    defendersList.add(new NormalDefender(pos,attackersList));
                }
                if(red == 255 && green == 50 && blue == 255){
                    Position pos = new Position(xx*blockSize,yy*blockSize);
                    defendersList.add(new NuclearDefender(pos,attackersList));
                }
                

                //Get the turns
                if(red == 0 && green == 0 && blue == 255){
                    Position pos = new Position(xx*blockSize,yy*blockSize);
                    blocks.add(new LevelBlocks(pos,blockSize,blockSize,BlockType.TURNSOUTH));
                    directionList.add(new LevelBlocks(pos,blockSize,blockSize,BlockType.TURNSOUTH));
                }
                if(red == 255 && green == 255 && blue == 0){
                    Position pos = new Position(xx*blockSize,yy*blockSize);
                    blocks.add(new LevelBlocks(pos,blockSize,blockSize,BlockType.TURNEAST));
                    directionList.add(new LevelBlocks(pos,blockSize,blockSize,BlockType.TURNEAST));
                }
                if(red == 128 && green == 0 && blue == 128){
                    Position pos = new Position(xx*blockSize,yy*blockSize);
                    blocks.add(new LevelBlocks(pos,blockSize,blockSize,BlockType.TURNNORTH));
                    directionList.add(new LevelBlocks(pos,blockSize,blockSize,BlockType.TURNNORTH));

                }
                if(red == 0 && green == 255 && blue == 255){
                    Position pos = new Position(xx*blockSize,yy*blockSize);
                    blocks.add(new LevelBlocks(pos,blockSize,blockSize,BlockType.TURNWEST));
                    directionList.add(new LevelBlocks(pos,blockSize,blockSize,BlockType.TURNWEST));
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
        return directionList;
    }

    public void createNewAttacker(AttackerType type){
        if(type.equals(AttackerType.NORMALATTACKER)){
            attackersList.add(new NormalAttacker(startPosition,directionList));
        } else if(type.equals(AttackerType.SPECIALATTACKER)){
            attackersList.add(new SpecialAttacker(startPosition,directionList));
        }  else if(type.equals(AttackerType.MUSCLEATTACKER)){
            attackersList.add(new MuscleAttacker(startPosition,directionList));
        }
    }
}
