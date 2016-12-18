/**
 * Classname: GenerateLevel.java
 * Version info 1.0
 * Copyright notice:    Masoud Shofahi
 *                      Amanda Dahlin
 *                      Gustav Norlander
 *                      Samuel Bylund Felixon
 * Date: 17/12/2017
 * Course: Applikationsutveckling i Java
 */
import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class GenerateLevel implements LandonClass {
    private LinkedList<Block> blocks;
    private LinkedList<Block> zoneList;
    private LinkedList<Attacker> attackersList;
    private LinkedList<Defender> defendersList;

	private Rectangle teleporterStartPosition;
	private Position teleporterEndPosition;
	private String teleporterDirection;

    private Position startPosition = null;
    private Rectangle goalPosition = null;
    private int amountOfLevels;
    private int blockSize;

	private XmlReader xmlReader;

    /**
     * Constructor for GenerateLevel with specified blocksize
     * @param blockSize Diameter of block
     */
    public GenerateLevel(int blockSize){

        xmlReader = new XmlReader(this);
        this.blockSize = blockSize;
        xmlReader.generateXML();
        amountOfLevels = xmlReader.nodeList.getLength();
    }

    /**
     * Constructor for GenerateLevel with specified blocksize and path
     * @param blockSize Diameter of block
     */
    public GenerateLevel(int blockSize,String path){
        xmlReader = new XmlReader(path,this);
        this.blockSize = blockSize;
        xmlReader.generateXML();
        amountOfLevels = xmlReader.nodeList.getLength();
    }

    /**
     * Method will Initialize all the objects and data structures
     */
    protected void init(){
        blocks = new LinkedList<>();
        zoneList = new LinkedList<>();
        attackersList = new LinkedList<>();
        defendersList = new LinkedList<>();
    }

    /**
     * Method will load a specific level from the .xml file.
     * @param levelSelect the level to be loaded
     */
    public void loadLevel(int levelSelect) {

        if (!xmlReader.validateXMLFile("XmlFiles/levelList.xsd")) {
            JOptionPane.showMessageDialog(null,
                    "The file format is not correct",
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        init();


        if(!xmlReader.loadLevelXML(levelSelect)){
            JOptionPane.showMessageDialog(null,
                    "Could not load level: " + levelSelect,
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);

        }

        for (int i = 0; i < blocks.size(); i++) {

            if (blocks.get(i).getBlockType().equals(BlockType.GOALPOSITION)) {
                goalPosition = new Rectangle(blocks.get(i).getPos().getX(),
                        blocks.get(i).getPos().getY(), blockSize, blockSize);
                teleporterStartPosition = goalPosition;
            }

            if (blocks.get(i).getBlockType().equals(BlockType.STARTPOSITION)) {
                startPosition = new Position(blocks.get(i).getPos().getX(),
                        blocks.get(i).getPos().getY());
            }
        }

        createDefenders();
    }

    /**
     * Method will  create and place the defenders on random available zones.
     */
    public void createDefenders(){
        if(!getZoneList().isEmpty()){
            for (int i = 0;
                 i < xmlReader.lvlRules.get(LevelInfo.NORMAL_DEFENDER); i++)
                createNormalDefender();
            for (int i = 0;
                 i < xmlReader.lvlRules.get(LevelInfo.NUCLEAR_DEFENDER); i++)
                createNuclearDefender();
        }
    }

    /**
     * Get a Random zone (Blocks where a defender can be placed) from zoneList
     * @return a random zone
     */
    private Block getRandomBlock (){
        Random r = new Random();
        int randomNum = r.nextInt(zoneList.size());
        return zoneList.get(randomNum);
    }

    /**
     * Method checks if the defender can be placed on the current zone
     * @param defender the Defender
     * @return true if it can be placed with out colliding with other objects
     */
    protected boolean isCreatable(Defender defender){

        if(zoneList.isEmpty()){
            return false;
        }

        for (int j = 0; j < blocks.size(); j++){
            if (defender.getBound().intersects(blocks.get(j).getBound())){
                return false;
            }
        }
        for (int j = 0; j < defendersList.size(); j++){

            if (defender.getBound().intersects(defendersList.get(j).
                    getBound())){
                return false;
            }
        }
        return true;
    }

    /**
     * Create and place a NormalDefender if there is
     * any available place on the level
     */
    private void createNormalDefender(){

        for (int i = 0; i < zoneList.size(); i++){

            NormalDefender tmp = new NormalDefender(getRandomBlock().getPos(),
                    attackersList);

            if(isCreatable(tmp)){
                defendersList.add(tmp);
                return;
            }
        }
    }

    /**
     * Create and place a NuclearDefender if there is any
     * available place on the level
     */
    private void createNuclearDefender(){

        for (int k = 0; k < zoneList.size(); k++){

            NuclearDefender tmp = new NuclearDefender(getRandomBlock().getPos(),
                    attackersList);

            if(isCreatable(tmp)){
                defendersList.add(tmp);
                return;
            }
        }
    }

    /**
     * Method will create a level object (Block)
     * @param pos the position where the object should be created
     * @param type which type of level object should be created
     */
    @Override
    public void landOn(Position pos, String type) {


		if (type.equals(BlockType.STARTPOSITION.toString())) {
			blocks.add(new LevelBlocks(pos, 20, 20,
					BlockType.STARTPOSITION));
		}

		if (type.equals(BlockType.GOALPOSITION.toString())) {
			blocks.add(new LevelBlocks(pos, 20, 20,
					BlockType.GOALPOSITION));
		}

		if (type.equals(BlockType.TURNSOUTH.toString())) {
			blocks.add(
					new LevelBlocks(pos, 20, 20, BlockType.TURNSOUTH));
		}

		if (type.equals(BlockType.TURNNORTH.toString())) {
			blocks.add(
					new LevelBlocks(pos, 20, 20, BlockType.TURNNORTH));
		}

		if (type.equals(BlockType.TURNWEST.toString())) {
			blocks.add(new LevelBlocks(pos, 20, 20, BlockType.TURNWEST));
		}

		if (type.equals(BlockType.TURNEAST.toString())) {
			blocks.add(new LevelBlocks(pos, 20, 20, BlockType.TURNEAST));
		}

        if (type.equals(BlockType.TURN_Y.toString())) {
            blocks.add(new LevelBlocks(pos, 20, 20, BlockType.TURN_Y));
        }

        if (type.equals(BlockType.TURN_X.toString())) {
            blocks.add(new LevelBlocks(pos, 20, 20, BlockType.TURN_X));
        }

		if (type.equals(BlockType.PATH.toString())) {
			blocks.add(new LevelBlocks(pos, 20, 20, BlockType.PATH));
		}

		if (type.equals(BlockType.DEFENDER.toString())) {
			zoneList.add(
					new LevelBlocks(pos, 20, 20, BlockType.DEFENDER));
		}

    }

    /**
     * Method to check if start and goal position are null
     * @return Boolean
     */
    public boolean checkStartAndGoalPosition(){

        if(startPosition == null || goalPosition == null){
            return false;
        }
        return true;
    }

    /**
     * A getter for blocks, which contains objects such as path,
     * start, and goal
     * @return blocks
     */
    public LinkedList<Block> getBlocks() {
        return blocks;
    }

    /**
     * A getter for zone list, which contains the positions where a
     * defender can be created
     * @return blocks
     */
    public LinkedList<Block> getZoneList() {
        return zoneList;
    }

    /**
     * A getter for the start position
     * @return startPosition
     */
    public Position getStartPosition() {
        return startPosition;
    }

    /**
     * A getter for the goal position
     * @return startPosition
     */
    public Rectangle getGoalPosition() {
        return goalPosition;
    }

    /**
     * A getter for the attackerList. All the attackers created
     * will be stored in this list
     * @return attackerList
     */
    public LinkedList<Attacker> getAttackersList() {
        return attackersList;
    }

    /**
     * A getter for the teleporter start position.
     * @return teleporterStartPostition as a Rectangle object
     */
	public Rectangle getTeleporterStartPosition() {
		return teleporterStartPosition;
	}

    /**
     * A getter for the teleporter end position.
     * @return teleporterEndPostition as a Rectangle object
     */
	public Position getTeleporterEndPosition() {
		return teleporterEndPosition;
	}

    /**
     * A setter for the teleporter end position.
     */
	public void setTeleporterEndPosition(Position pos) {
		teleporterEndPosition = pos;
	}

    /**
     *
     * @param pos
     */
	public void setTeleporterStartPosition(Position pos) {
		teleporterStartPosition = new Rectangle(pos.getX(), pos.getY(),
				blockSize, blockSize);
	}

	public void setTeleporterDirection(String value) {
		teleporterDirection = value;
	}

	public String getTeleporterDirection() {
		return teleporterDirection;
	}



    /**
     * A getter for the defenderList. All the defenders created
     * will be stored in this list
     * @return defenderList
     */
    public LinkedList<Defender> getDefendersList() {
        return defendersList;
    }

    /**
     * A getter for amountOfLevels. The value represents the amount
     * of levels that is available
     * @return amountOfLevels
     */
    public int getAmountOfLevels() {
        return amountOfLevels;
    }

    /**
     * A getter for startMoney. The value represents the amount of
     * money user will begin with
     * @return startMoney
     */
    public int getStartMoney(){
        return xmlReader.lvlRules.get(LevelInfo.STARTING_GOLD);
    }

    /**
     * A getter for attackersToFinish. The value represents how many
     * attackers should reach the goal
     * inorder to finish the level
     * @return attackersToFinish
     */
    public int getAttackersToFinish(){
        return xmlReader.lvlRules.get(LevelInfo.ATTACKERS_TO_FINISH);
    }

}
