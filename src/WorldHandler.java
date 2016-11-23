
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
    private Position goalPosition;
    private LinkedList<Position> path;



    public WorldHandler(int blockSize){

        blocks = new LinkedList<>();
        this.blockSize = blockSize;
        path = new LinkedList<>();

    }

    public void loadAllImages(){
        listOfLevels = new LinkedList<>();

        File  directory = new File("Images");

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

    }

    public void tick(){

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
                    for (int i = 0; i < blockSize; i++){
                        for (int j = 0; j < blockSize; j++){
                            Position tmp  = new Position(pos.getX()+i,pos.getY()+j);
                            System.out.println("X: "+tmp.getX() + " Y: " + tmp.getY());
                            path.add(tmp);
                        }
                    }
                }

                if (red == 251 && green == 0 && blue == 7){

                    Position pos = new Position(xx*blockSize,yy*blockSize);
                    blocks.add(new LevelBlocks(pos,blockSize,blockSize,BlockType.GOALPOSITION));
                    goalPosition = pos;
                }

                if (red == 27 && green == 209 && blue == 30){

                    Position pos = new Position(xx*blockSize,yy*blockSize);
                    blocks.add(new LevelBlocks(pos,blockSize,blockSize,BlockType.STARTPOSITION));
                    startPosition = pos;
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

    public Position getGoalPosition() {
        return goalPosition;
    }

    public LinkedList<Position> getPath() {
        return path;
    }


}
