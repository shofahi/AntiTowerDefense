import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

import java.io.File;
import java.io.IOException;

public class XmlReader{

    private String path;
    private NodeList nodeList;

    private GenerateLevel generateLvl;

    public XmlReader(String path,GenerateLevel generateLevel){
        this.path = path;
        generateLvl= generateLevel;
    }

    public XmlReader(GenerateLevel generateLevel){

        path = "XmlFiles/levels.xml";
        generateLvl= generateLevel;
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

                  generateLvl.landOn(xPos,yPos,type);

              }
          }

      }
    }
}
