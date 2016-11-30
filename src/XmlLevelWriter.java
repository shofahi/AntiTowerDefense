import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class XmlLevelWriter {

    private Document xmlLevels;
    private Element rootElement;
    private Element blockList;

    private String outputFile;

    public XmlLevelWriter(String outputFile){
        this.outputFile = outputFile;
        this.outputFile = "/Users/MasoudMac/Desktop/xmlTestLevel.xml";
    }
    /**
     * Write xml
     */
    public void writeXML(String rootName){
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;

        try {
            docBuilder = docFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        xmlLevels = docBuilder.newDocument();

        //LevelList
        rootElement = xmlLevels.createElement(rootName);
        xmlLevels.appendChild(rootElement);
    }

    private BlockType getType(int rgbPixel[]){
        if(rgbPixel[0] == 0 && rgbPixel[1] == 255 && rgbPixel[2] == 0 ){
            return BlockType.STARTPOSITION;
        }

        if (rgbPixel[0] == 255 && rgbPixel[1] == 128 && rgbPixel[2] == 0){
            return BlockType.PATH;
        }

        if (rgbPixel[0] == 255 && rgbPixel[1] == 0 && rgbPixel[2] == 0){
            return BlockType.GOALPOSITION;
        }
        //Defender
        if(rgbPixel[0] == 255 && rgbPixel[1] == 0 && rgbPixel[2] == 255){
            return BlockType.DEFENDER;
        }
        //Get the turns
        if(rgbPixel[0] == 0 && rgbPixel[1] == 0 && rgbPixel[2] == 255){
            return BlockType.TURNSOUTH;
        }
        if(rgbPixel[0] == 255 && rgbPixel[1] == 255 && rgbPixel[2] == 0){
            return BlockType.TURNEAST;
        }
        if(rgbPixel[0] == 128 && rgbPixel[1] == 0 && rgbPixel[2] == 128){
            return BlockType.TURNNORTH;

        }
        if(rgbPixel[0] == 0 && rgbPixel[1] == 255 && rgbPixel[2] == 255){
            return BlockType.TURNWEST;
        }

        return BlockType.INVALID;
    }

    public void addLevel(int levelNumber){
        //Level
        Element newLevel = xmlLevels.createElement("level");
        String levelNum = String.valueOf(levelNumber);
        newLevel.setAttribute("number", levelNum);
        rootElement.appendChild(newLevel);

        //blockList
        blockList = xmlLevels.createElement("blockList");
        newLevel.appendChild(blockList);

    }

    public void addElement(int rgbValue[],Position pos){

        //block
        Element block = xmlLevels.createElement("block");

        BlockType theType = getType(rgbValue);

        if(theType.equals(BlockType.INVALID)){
            //System.out.println("Invalid");
            return;
        }

        block.setAttribute("type",theType.toString());
        blockList.appendChild(block);

        //
       /* Element redValue = xmlLevels.createElement("redValue");
        redValue.appendChild(xmlLevels.createTextNode(Integer.toString(rgbValue[0])));
        block.appendChild(redValue);
        Element greenValue = xmlLevels.createElement("greenValue");
        greenValue.appendChild(xmlLevels.createTextNode(Integer.toString(rgbValue[1])));
        block.appendChild(greenValue);

        Element blueValue = xmlLevels.createElement("blueValue");
        blueValue.appendChild(xmlLevels.createTextNode(Integer.toString(rgbValue[2])));
        block.appendChild(blueValue);*/

        Element xPos = xmlLevels.createElement("xPos");
        xPos.appendChild(xmlLevels.createTextNode(Integer.toString(pos.getX())));
        block.appendChild(xPos);

        Element yPos = xmlLevels.createElement("yPos");
        yPos.appendChild(xmlLevels.createTextNode(Integer.toString(pos.getY())));
        block.appendChild(yPos);
    }

    /**
     * Write to file
     */
    public void writeXMLToFile(){
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;


        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }

        DOMSource source = new DOMSource(xmlLevels);
        StreamResult result;

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        result = new StreamResult(new File(outputFile));

        try {
            transformer.transform(source,result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }

    }
}





































