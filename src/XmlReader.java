/**
 * Classname: XmlReader.java
 * Version info 1.0
 * Copyright notice:    Masoud Shofahi
 *                      Gustav Norlander
 *                      Samuel Bylund Felixon
 * Date: 17/12/2017
 * Course: Applikationsutveckling i Java
 */
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Class for loading, reading and validating Xml files.
 */
public class XmlReader{

    private String path;
    public NodeList nodeList;

    private GenerateLevel generateLvl;
    public HashMap<LevelInfo,Integer> lvlRules = new HashMap<>();

    /**
     * Constructor will load a specific xml from the given path
     * @param path the path
     */
    public XmlReader(String path,GenerateLevel generateLevel){

        this.path = path;

        generateLvl= generateLevel;
    }

    /**
     * This is the default constructor
     */
    public XmlReader(GenerateLevel generateLevel){

        path = "XmlFiles/test.xml";

        generateLvl= generateLevel;
    }

    /**
     * This method will read all the levels from the xml file and
     * stores them in a NodeList
     */
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
        	JOptionPane.showMessageDialog(null,"Error: " +
                    "DocumentBuilder cannot be created with specified "
                    + "configuration \n" + e.toString(),
            "Error",JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
        	JOptionPane.showMessageDialog(null,"Error: " +
                    "File not found \n" + e.toString(),
            "Error",JOptionPane.ERROR_MESSAGE);
        } catch (org.xml.sax.SAXException e) {
        	JOptionPane.showMessageDialog(null,"Error: " +
                    "SAX Exception \n" + e.toString(),
            "Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Method will validate the .xml file with a .xsd file
     * @param xsd the .xsd file
     * @throws SAXException
     * @throws IOException if something goes wrong while loading the the files
     */
    public boolean validateXMLFile(String xsd){

        SchemaFactory schemaFactory = SchemaFactory.
                newInstance("http://www.w3.org/2001/XMLSchema");

        try {

            ((schemaFactory.newSchema(new File(xsd))).newValidator()).
                    validate(new StreamSource(new File (path)));

        } catch (SAXException e) {
            return false;

        } catch (IOException e) {

            return false;
        }

        return true;
    }

    /**
     * This method will load a specific level from the XML file
     * @param levelNumber the level that should be loaded
     */
    public boolean loadLevelXML(int levelNumber) {

      for(int i = 0; i < nodeList.getLength(); i++){

          Node node = nodeList.item(i);

          Element element = (Element) node;

          if(element.getAttribute("number").
                  equals(Integer.toString(levelNumber))){

              NodeList blockList = element.getElementsByTagName("block");

              for (int j = 0; j < blockList.getLength(); j++){

                  Node blockNode = blockList.item(j);

                  Element ele = (Element) blockNode;

                  int xPos = Integer.parseInt(ele.getElementsByTagName("xPos").
                          item(0).getTextContent());
                  int yPos = Integer.parseInt(ele.getElementsByTagName("yPos").
                          item(0).getTextContent());

                  String type = ele.getAttribute("type");

                  Position pos = new Position(xPos,yPos);

                  generateLvl.landOn(pos,type);
              }

              //Rules
              NodeList rules = element.getElementsByTagName("rules");

              Node ruleNode = rules.item(0);

              Element ele = (Element) ruleNode;

              int money = Integer.parseInt(ele.getElementsByTagName(LevelInfo.
                      STARTING_GOLD.toString()).item(0).getTextContent());

              lvlRules.put(LevelInfo.STARTING_GOLD,money);

              int attackersToFinish = Integer.parseInt(ele.
                      getElementsByTagName(LevelInfo.ATTACKERS_TO_FINISH.
                              toString()).item(0).getTextContent());

              lvlRules.put(LevelInfo.ATTACKERS_TO_FINISH,attackersToFinish);

              int normalDefender = Integer.parseInt(ele.
                      getElementsByTagName(LevelInfo.NORMAL_DEFENDER.
                              toString()).item(0).getTextContent());

              lvlRules.put(LevelInfo.NORMAL_DEFENDER,normalDefender);

              int nuclearDefender = Integer.parseInt(ele.
                      getElementsByTagName(LevelInfo.NUCLEAR_DEFENDER.
                              toString()).item(0).getTextContent());

              lvlRules.put(LevelInfo.NUCLEAR_DEFENDER,nuclearDefender);

              return true;
          }

      }

      return false;
    }

}
