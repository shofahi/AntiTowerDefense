/**
 * Classname: LoadImage.java
 * Version info 1.0
 * Copyright notice:    Masoud Shofahi
 *                      Amanda Dahlin
 *                      Gustav Norlander
 *                      Samuel Bylund Felixon
 * Date: 17/12/2017
 * Course: Applikationsutveckling i Java
 */

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class LoadImage {

    /**
     * Method that will create a URL from the path to a PNG image
     * and then load the image using ImageIO.read. Necessary for
     * when the game will have .jar structure
     * @param path The path to the image
     * @return The image as a BufferedImage
     */
    public static BufferedImage loadTheImage (String path){

            BufferedImage img = null;

            URL url = LoadImage.class.getResource(path);
        try {
            img =ImageIO.read(url);
            //TODO: handle excpetion. Bad to catch it here.
        } catch (IOException e) {
            e.printStackTrace();
        }

        return img;
    }
}
