/**
 * Classname: CompleteTest.java
 * Version info 1.0
 * Copyright notice:    Masoud Shofahi
 *                      Amanda Dahlin
 *                      Gustav Norlander
 *                      Samuel Bylund Felixon
 * Date: 17/12/2017
 * Course: Applikationsutveckling i Java
 */

import java.io.IOException;

import org.xml.sax.SAXException;

public class CompleteTest {

    public static void main(String[] args) {

        RunGame game = new RunGame("Anti Tower Defense Beta",800,600);
        game.startGame();

    	
    	/*String xml = "XmlFiles/levels.xml";
    	String xsd = "XmlFiles/levelList.xsd";
    	Boolean bool = true;


        try {
            XmlReader.validateXMLFile(xml, xsd);
        } catch (SAXException e) {
            bool = false;
            e.printStackTrace();
        } catch (IOException e) {
            bool = false;
            e.printStackTrace();
        }

        System.out.println(bool);*/

    }

}
