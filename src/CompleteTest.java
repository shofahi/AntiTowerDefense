import java.io.IOException;

import org.xml.sax.SAXException;

public class CompleteTest {

    public static void main(String[] args) {

        /*RunGame game = new RunGame("Anti Tower Defense Beta",800,600);
        game.startGame();*/


        /*PngReader pngReader = new PngReader();
        pngReader.loadAllImages();
        pngReader.loadImageLevel();*/
    	
    	
    	String xml = "XmlFiles/levels.xml";
    	String xsd = "XmlFiles/levelList.xsd";
    	Boolean bool = true;
    	
    	try {
			XmlReader.validateXMLFile(xml, xsd);
		} catch (SAXException e) {
			bool = false;
			
		} catch (IOException e) {
			bool = false;
		}
    	System.out.println(bool);

    }

}
