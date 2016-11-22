
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class LevelLoader {

    private BufferedImage lvlImg = null;
    private LinkedList<Block> blocks;
    private int blockSize;

    private LoadImage lvlLoad = new LoadImage();

    private Position startPosition;
    private Position goalPosition;


    public LevelLoader(int levelSelect,int blockSize){

        blocks = new LinkedList<>();
        this.blockSize = blockSize;

    }

    public void render(Graphics g){

        for (int i = 0; i < blocks.size();i++){
            blocks.get(i).render(g);
        }

    }

    public void tick(){

    }

    public void loadImageLevel(String path){

        //läs in banan
        lvlImg = lvlLoad.loadTheImage(path);

        //Hämtar bildens storlek
        int w = lvlImg.getWidth();
        int h = lvlImg.getHeight();

        //Looppar genom alla pixlar i bilden
        for (int xx = 0; xx < h; xx++) {
            for (int yy = 0; yy < w; yy++) {

                int pixel = lvlImg.getRGB(xx,yy);
                //hur mycket röd färg på pixel
                int red = (pixel >> 16) & 0xff;
                //hur mycket grön färg på pixel
                int green = (pixel >> 8) & 0xff;
                //hur mycket blå färg på pixel
                int blue = (pixel) & 0xff;


                //*********Kommer göra XML för alla färger**************
                //Exempel på en orange färg
                if (red == 253 && green == 135 && blue == 26){

                    Position pos = new Position(xx*blockSize,yy*blockSize);
                    blocks.add(new LevelBlocks(pos,blockSize,blockSize,BlockType.PATH));
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

}
