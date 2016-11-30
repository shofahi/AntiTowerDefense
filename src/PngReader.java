import javax.sql.rowset.spi.XmlWriter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;

/**
 * Created by MasoudMac on 30/11/16.
 */
public class PngReader {



    private LoadImage lvlLoad = new LoadImage();
    private LinkedList<BufferedImage> listOfLevels = null;
    private final int BLOCK_SIZE = 20;


    public void loadAllImages(){

        listOfLevels = new LinkedList<>();

        File directory = new File("Levels");

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




    public void loadImageLevel(){/*ska vi döpa om detta till generateLevel?*/

        XmlLevelWriter xmlWriter = new XmlLevelWriter("Dfdf");
        xmlWriter.writeXML("levelList");
        xmlWriter.addLevel(1);

        //läs in banan
        BufferedImage lvl = listOfLevels.get(0);




        int w = lvl.getWidth();
        int h = lvl.getHeight();

        //Looppar genom alla pixlar i bilden
        for (int xx = 0; xx < h; xx++) {
            for (int yy = 0; yy < w; yy++) {

                int pixel = lvl.getRGB(xx,yy);
                int rgbPixel[] = new int[3];

                rgbPixel[0] = (pixel >> 16) & 0xff;
                rgbPixel[1] = (pixel >> 8) & 0xff;
                rgbPixel[2] = (pixel) & 0xff;

                Position pos = new Position(xx *BLOCK_SIZE,yy*BLOCK_SIZE);
                xmlWriter.addElement(rgbPixel,pos);
            }
        }

        xmlWriter.writeXMLToFile();

    }
}
