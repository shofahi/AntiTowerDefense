
import java.awt.*;

public class WorldHandler{

    private int bonus;
    private int nrOfAttackerToGoal;

    private GenerateLevel generateLvl;

    public WorldHandler(GenerateLevel generateLvl){

        this.generateLvl = generateLvl;
    }

    public void render(Graphics g){

        //for (Block block : blocks)

        for (int i = 0; i < generateLvl.getBlocks().size();i++){
            generateLvl.getBlocks().get(i).render(g);
        }

        for (int i = 0; i < generateLvl.getDefendersList().size(); i++){
            generateLvl.getDefendersList().get(i).render(g);
        }

        for (int i = 0; i < generateLvl.getAttackersList().size(); i++){
            generateLvl.getAttackersList().get(i).render(g);
        }
    }

    /**
     * 60frames per second
     */
    public void update(){

        //defender
        for (int i = 0; i < generateLvl.getDefendersList().size(); i++){
            generateLvl.getDefendersList().get(i).update();
        }

        for (int i = 0; i < generateLvl.getAttackersList().size(); i++){
            generateLvl.getAttackersList().get(i).update();

            if(generateLvl.getAttackersList().get(i).getHealth() < 0){
                generateLvl.getAttackersList().remove(i);
            }
            else if(generateLvl.getAttackersList().get(i).getBound().intersects(generateLvl.getGoalPosition())){
            	// Add money to wallet
            	bonus += generateLvl.getAttackersList().get(i).getHealth() * 5;
            	nrOfAttackerToGoal++;
                System.out.println("Adding money to wallet here");
                generateLvl.getAttackersList().remove(i);
            }
        }
    }


    public void createNewAttacker(AttackerType type){
        if(type.equals(AttackerType.NORMALATTACKER)){
            generateLvl.getAttackersList().add(new NormalAttacker(generateLvl.getStartPosition(),generateLvl.getBlocks()));
        } else if(type.equals(AttackerType.SPECIALATTACKER)){
            generateLvl.getAttackersList().add(new SpecialAttacker(generateLvl.getStartPosition(),generateLvl.getBlocks()));
        }  else if(type.equals(AttackerType.MUSCLEATTACKER)){
            generateLvl.getAttackersList().add(new MuscleAttacker(generateLvl.getStartPosition(),generateLvl.getBlocks()));
        }
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
