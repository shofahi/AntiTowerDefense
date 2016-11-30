
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;
import java.util.Random;

public class WorldHandler {

    private int blockSize;

    private Position startPosition;
    private Rectangle goalPosition;

    //This list will store the directions sign for the level
    private LinkedList<Block> directionList = new LinkedList<>();

    //List of game objects
    private LinkedList<Attacker> attackersList = new LinkedList<>();
    private LinkedList<Defender> defendersList = new LinkedList<>();
    private LinkedList<Block> blocks;

    private int bonus;
    private int nrOfAttackerToGoal;

    XmlReader xmlReader = new XmlReader();


    public WorldHandler(int blockSize){

        blocks = new LinkedList<>();
        this.blockSize = blockSize;

    }

    public void render(Graphics g){

        //for (Block block : blocks)

        for (int i = 0; i < blocks.size();i++){
            blocks.get(i).render(g);
        }

        for (int i = 0; i < defendersList.size(); i++){
            defendersList.get(i).render(g);
        }

        for (int i = 0; i < attackersList.size(); i++){
            attackersList.get(i).render(g);
        }
    }

    /**
     * 60frames per second
     */
    public void update(){

        //defender
        for (int i = 0; i < defendersList.size(); i++){
            defendersList.get(i).update();
        }

        for (int i = 0; i < attackersList.size(); i++){
            attackersList.get(i).update();

            if(attackersList.get(i).getHealth() < 0){
                attackersList.remove(i);
            }
            else if(attackersList.get(i).getBound().intersects(goalPosition)){
            	// Add money to wallet
            	bonus += attackersList.get(i).getHealth() * 5;
            	nrOfAttackerToGoal++;
                System.out.println("Adding money to wallet here");
                attackersList.remove(i);
            }
        }
    }

    public void loadImageLevel(int levelSelect){/*ska vi döpa om detta till generateLevel?*/

        xmlReader.generateXML();
        xmlReader.loadLevelXML(levelSelect);
        blocks = xmlReader.getBlocks();

        for (int i = 0; i < blocks.size(); i++){

            if(blocks.get(i).getBlockType().equals(BlockType.GOALPOSITION)){
                goalPosition = new Rectangle(blocks.get(i).getPos().getX(),blocks.get(i).getPos().getY(),blockSize,blockSize);
            }

            if(blocks.get(i).getBlockType().equals(BlockType.STARTPOSITION)){
                startPosition = new Position(blocks.get(i).getPos().getX(),blocks.get(i).getPos().getY());
            }
        }
        for (int i = 0; i < 100; i++)
            createDefenders();
    }


    private void createDefenders(){

        boolean isCreatable = true;
        Random r = new Random();
        int randomNum = r.nextInt(xmlReader.getZoneList().size());
        Block tmpBlock = xmlReader.getZoneList().get(randomNum);
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

    public void addStaicBlock(Block obj){
        blocks.add(obj);
    }

    public Position getStartPosition() {

        return startPosition;
    }

    public Rectangle getGoalPosition() {
        return goalPosition;
    }


    public LinkedList<Block> getTurns() {
        return directionList;
    }

    public void createNewAttacker(AttackerType type){
        if(type.equals(AttackerType.NORMALATTACKER)){
            attackersList.add(new NormalAttacker(startPosition,blocks));
        } else if(type.equals(AttackerType.SPECIALATTACKER)){
            attackersList.add(new SpecialAttacker(startPosition,blocks));
        }  else if(type.equals(AttackerType.MUSCLEATTACKER)){
            attackersList.add(new MuscleAttacker(startPosition,blocks));
        }
    }

    public LinkedList<Attacker> getAttackersList(){
    	return attackersList;
    }

    public int getBonus(){
    	return bonus;
    }
    public void resetBonus(){
    	bonus = 0;
    }

    public int getNrOfAttackersToGoal(){
    	return nrOfAttackerToGoal;
    }
    public void resetNrOfAttackersToGoal(){
    	this.nrOfAttackerToGoal = 0;
    }
}
