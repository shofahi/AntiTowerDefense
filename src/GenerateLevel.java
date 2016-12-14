import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class GenerateLevel implements CreateBlock {
    private LinkedList<Block> blocks;
    private LinkedList<Block> zoneList;
    private LinkedList<Attacker> attackersList;
    private LinkedList<Defender> defendersList;


    private Position startPosition;
    private Rectangle goalPosition;
    private Rectangle teleporterStartPosition;
    private Position teleporterEndPosition;
    private String teleporterDirection;
    
    private int blockSize;

    private XmlReader xmlReader = new XmlReader(this);

    public GenerateLevel(int blockSize){

        this.blockSize = blockSize;
    }

    private void init(){
        blocks = new LinkedList<>();
        zoneList = new LinkedList<>();
        attackersList = new LinkedList<>();
        defendersList = new LinkedList<>();
    }
    public void loadLevel(int levelSelect){

        init();

        xmlReader.generateXML();
        xmlReader.loadLevelXML(levelSelect);

        

        for (int i = 0; i < blocks.size(); i++){

            if(blocks.get(i).getBlockType().equals(BlockType.GOALPOSITION)){
                goalPosition = new Rectangle(blocks.get(i).getPos().getX(),blocks.get(i).getPos().getY(),blockSize,blockSize);
                teleporterStartPosition = goalPosition;
            }         

            if(blocks.get(i).getBlockType().equals(BlockType.STARTPOSITION)){
                startPosition = new Position(blocks.get(i).getPos().getX(),blocks.get(i).getPos().getY());
            }
        }

        if(!getZoneList().isEmpty()){
            for (int i = 0; i < 1; i++)
                createDefenders();
        }
    }

    private void createDefenders(){

        boolean isCreatable = true;
        Random r = new Random();
        int randomNum = r.nextInt(zoneList.size());
        Block tmpBlock = zoneList.get(randomNum);
        NormalDefender tmp = new NormalDefender(tmpBlock.getPos(),attackersList);

        for (int i = 0; i < blocks.size(); i++){

            if (tmp.getBound().intersects(blocks.get(i).getBound())){
                isCreatable = false;
            }
        }

        for (int i = 0; i < defendersList.size(); i++){

            if (tmp.getBound().intersects(defendersList.get(i).getBound())){
                isCreatable = false;
            }
        }

        if(isCreatable){
            defendersList.add(tmp);
        }
    }

    @Override
    public void landOn(int xPos, int yPos, String type) {

        if(type.equals(BlockType.STARTPOSITION.toString())){
            blocks.add(new LevelBlocks(xPos,yPos,20,20,BlockType.STARTPOSITION));
        }

        if(type.equals(BlockType.GOALPOSITION.toString())){
            blocks.add(new LevelBlocks(xPos,yPos,20,20,BlockType.GOALPOSITION));
        }

        if(type.equals(BlockType.TURNSOUTH.toString())){
            blocks.add(new LevelBlocks(xPos,yPos,20,20,BlockType.TURNSOUTH));
        }

        if(type.equals(BlockType.TURNNORTH.toString())){
            blocks.add(new LevelBlocks(xPos,yPos,20,20,BlockType.TURNNORTH));
        }

        if(type.equals(BlockType.TURNWEST.toString())){
            blocks.add(new LevelBlocks(xPos,yPos,20,20,BlockType.TURNWEST));
        }

        if(type.equals(BlockType.TURNEAST.toString())){
            blocks.add(new LevelBlocks(xPos,yPos,20,20,BlockType.TURNEAST));
        }

        if(type.equals(BlockType.PATH.toString())){
            blocks.add(new LevelBlocks(xPos,yPos,20,20,BlockType.PATH));
        }

        if(type.equals(BlockType.DEFENDER.toString())){
            zoneList.add(new LevelBlocks(xPos,yPos,20,20,BlockType.DEFENDER));
        }

    }

    public LinkedList<Block> getBlocks() {
        return blocks;
    }

    public LinkedList<Block> getZoneList() {
        return zoneList;
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public Rectangle getGoalPosition() {
        return goalPosition;
    }
    
    public Rectangle getTeleporterStartPosition(){
    	return teleporterStartPosition;
    }
    
    public Position getTeleporterEndPosition(){
    	return teleporterEndPosition;
    }
    
    public void setTeleporterEndPosition(Position pos){
    	System.out.println("NEW ENDPOSITIO");
    	teleporterEndPosition = pos;
    }
    
    public void setTeleporterStartPosition(Position pos){
    	teleporterStartPosition = new Rectangle(pos.getX(),pos.getY(),blockSize,blockSize);
    }
    
    public void setTeleporterDirection(String value){
    	teleporterDirection = value;
    }
    
    public String getTeleporterDirection(){
    	return teleporterDirection;
    }

    public LinkedList<Attacker> getAttackersList() {
        return attackersList;
    }

    public LinkedList<Defender> getDefendersList() {
        return defendersList;
    }
}
