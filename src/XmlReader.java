import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class XmlReader {

    private LinkedList<Block> zoneList = new LinkedList<>();

    private LinkedList<Block> blocks;
    private String path;
    private NodeList nodeList;

    public XmlReader(String path){
        this.path = path;
        blocks = new LinkedList<>();
    }

    public XmlReader(){

        path = "XmlFiles/levels.xml";
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

    public static void validateXMLFile(String xml, String xsd) throws SAXException, IOException{
    	SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
    	((schemaFactory.newSchema(new File(xsd))).newValidator()).validate(new StreamSource(new File (xml)));
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

                  if(type.equals(BlockType.STARTPOSITION.toString())){
                    addBlocksToList(new LevelBlocks(xPos,yPos,20,20,BlockType.STARTPOSITION));
                  }

                  if(type.equals(BlockType.GOALPOSITION.toString())){
                      addBlocksToList(new LevelBlocks(xPos,yPos,20,20,BlockType.GOALPOSITION));
                  }

                  if(type.equals(BlockType.TURNSOUTH.toString())){
                      addBlocksToList(new LevelBlocks(xPos,yPos,20,20,BlockType.TURNSOUTH));
                  }

                  if(type.equals(BlockType.TURNNORTH.toString())){
                      addBlocksToList(new LevelBlocks(xPos,yPos,20,20,BlockType.TURNNORTH));
                  }

                  if(type.equals(BlockType.TURNWEST.toString())){
                      addBlocksToList(new LevelBlocks(xPos,yPos,20,20,BlockType.TURNWEST));
                  }

                  if(type.equals(BlockType.TURNEAST.toString())){
                      addBlocksToList(new LevelBlocks(xPos,yPos,20,20,BlockType.TURNEAST));
                  }

                  if(type.equals(BlockType.PATH.toString())){
                      addBlocksToList(new LevelBlocks(xPos,yPos,20,20,BlockType.PATH));
                  }

                  if(type.equals(BlockType.DEFENDER.toString())){
                      zoneList.add(new LevelBlocks(xPos,yPos,20,20,BlockType.DEFENDER));
                  }
              }
          }

      }
    }


    public void addBlocksToList(Block obj){
        blocks.add(obj);
    }


    public LinkedList<Block> getZoneList() {
        return zoneList;
    }

    public LinkedList<Block> getBlocks() {
        return blocks;
    }


}
