import jdk.internal.org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.LinkedList;

public class XmlReader {

    private LinkedList<Block> zoneBlock = new LinkedList<>(); //??????

    private LinkedList<Block> blocks;
    private String path;
    private NodeList nodeList;

    public XmlReader(String path){
        this.path = path;
        blocks = new LinkedList<>();
    }

    public XmlReader(){
        path = "/Users/MasoudMac/Desktop/xmlTestLevel.xml";
        blocks = new LinkedList<>();

    }

    public void generateXML(){

        nodeList = null;
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(path);
            Element data = doc.getDocumentElement();
            data.normalize();
            nodeList = doc.getElementsByTagName("level");

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.xml.sax.SAXException e) {
            e.printStackTrace();
        }
    }

    public void validateXMLFile(){

    }


    public void loadLevelXML(int levelNumber) {

      for(int i = 0; i < nodeList.getLength(); i++){
          Node node = nodeList.item(i);
          Element element = (Element) node;

          if(element.getAttribute("number").equals(Integer.toString(levelNumber))){

              NodeList blockList = element.getElementsByTagName("block");

              for (int j = 0; j < blockList.getLength(); j++){

                  Node blockNode = blockList.item(j);
                  Element ele = (Element) blockNode;

                  int xPos = Integer.parseInt(ele.getElementsByTagName("xPos").item(0).getTextContent());
                  int yPos = Integer.parseInt(ele.getElementsByTagName("yPos").item(0).getTextContent());
                  String type = ele.getAttribute("type");

                  if(type.equals("start")){
                    addBlocksToList(new LevelBlocks(xPos,yPos,20,20,BlockType.STARTPOSITION));
                  }

                  if(type.equals("goal")){
                      addBlocksToList(new LevelBlocks(xPos,yPos,20,20,BlockType.GOALPOSITION));
                  }

                  if(type.equals("turnSouth")){
                      addBlocksToList(new LevelBlocks(xPos,yPos,20,20,BlockType.TURNSOUTH));
                  }

                  if(type.equals("turnNorth")){
                      addBlocksToList(new LevelBlocks(xPos,yPos,20,20,BlockType.TURNNORTH));
                  }

                  if(type.equals("turnWest")){
                      addBlocksToList(new LevelBlocks(xPos,yPos,20,20,BlockType.TURNWEST));
                  }

                  if(type.equals("turnEast")){
                      addBlocksToList(new LevelBlocks(xPos,yPos,20,20,BlockType.TURNEAST));
                  }

                  if(type.equals("path")){
                      addBlocksToList(new LevelBlocks(xPos,yPos,20,20,BlockType.PATH));
                  }

              }
          }


        }
    }


    public void addBlocksToList(Block obj){
        blocks.add(obj);
    }


    public LinkedList<Block> getBlocks() {
        return blocks;
    }


}
