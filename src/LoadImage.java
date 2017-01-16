/**
 * Classname: LoadImage.java
 * Version info 1.0
 * Copyright notice:    Masoud Shofahi
 *                      Samuel Bylund Felixon
 * Date: 17/12/2017
 * Course: Applikationsutveckling i Java
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Class for loading images into the game.
 */
public class LoadImage {
    public static int i = 0;
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

            if(url == null){
                JOptionPane.showMessageDialog(null,"Error: " +
                                "Could not load the Image \n",
                        "Error",JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }

            img =ImageIO.read(url);

        } catch (IOException e) {

            JOptionPane.showMessageDialog(null,"Error: " +
                            "Could not load the Image \n" + e.toString(),
                    "Error",JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }


        return img;
    }
}
