/**
 * Classname: WorldHandler.java
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
import java.awt.image.BufferedImage;

/**
 * Class handling world-events.
 */
public class WorldHandler{

	private int bonus;
	private int nrOfAttackerToGoal;
	private BufferedImage[] yImg;
	private BufferedImage[] xImg;

	private GenerateLevel generateLvl;

	private Attacker specialID = null;

	/**
	 * Sets this generateLvl to the parameter
	 *
	 * @param generateLvl
	 *
	 */
	public WorldHandler(GenerateLevel generateLvl) {
		this.generateLvl = generateLvl;
		init();
	}


	/**
	 * Method to initalize the array containing pictures of direction-
	 * signs
	 */
	private void init(){
        yImg = new BufferedImage[2];
		xImg = new BufferedImage[2];

		yImg[0] = LoadImage.loadTheImage("north_trans.png");
		yImg[1] = LoadImage.loadTheImage("south_trans.png");
		
		xImg[0] = LoadImage.loadTheImage("west_trans.png");
		xImg[1] = LoadImage.loadTheImage("east_trans.png");
	}

	/**
	 * Renders different block types, defender types and and attackers
	 * @param  g
	 */
	public void render(Graphics g) {
		for (int i = 0; i < generateLvl.getBlocks().size(); i++) {
            if (generateLvl.getBlocks().get(i).getBlockType().equals(BlockType
                    .TURN_Y)) {
                if (generateLvl.getBlocks().get(i).isyNorth()) {
                    g.drawImage(yImg[0], generateLvl.getBlocks().get(i)
                            .getPos().getX(), generateLvl.getBlocks().get(i)
                            .getPos().getY(), yImg[0].getWidth(),

                            yImg[0].getHeight(), null);

                } else {
                    g.drawImage(yImg[1], generateLvl.getBlocks().get(i)
                            .getPos().getX(), generateLvl.getBlocks().get(i)
                            .getPos().getY(), yImg[1].getWidth(),

                            yImg[1].getHeight(), null);
                }
            } else if (generateLvl.getBlocks().get(i).getBlockType().equals
                    (BlockType.TURN_X)) {
                if (generateLvl.getBlocks().get(i).isxWest()) {
                    g.drawImage(xImg[0], generateLvl.getBlocks().get(i)
                            .getPos().getX(), generateLvl.getBlocks().get(i)
                            .getPos().getY(), xImg[0].getWidth(),

                            xImg[0].getHeight(), null);
                } else {
                    g.drawImage(xImg[1], generateLvl.getBlocks().get(i)
                            .getPos().getX(), generateLvl.getBlocks().get(i)
                            .getPos().getY(), xImg[1].getWidth(),

                            xImg[1].getHeight(), null);
                }
            } else {
                generateLvl.getBlocks().get(i).render(g);
            }
        }

		for (int i = 0; i < generateLvl.getDefendersList().size(); i++) {
            generateLvl.getDefendersList().get(i).render(g);
		}

		for (int i = 0; i < generateLvl.getAttackersList().size(); i++) {
            generateLvl.getAttackersList().get(i).render(g);
		}
	}

	/**
	 * Updates 60 frames per second. Loops through all of the attackers and
	 * checks if each attacker has less than 0 in health. Also checks if
	 * attacker intersects with goal position and handles teleporter attacker
	 * if reached goal position controls teleporter direction.
     * Also checks if the attacker has walked outside of the world,
     * program exits and user is prompted about this.
	 */
	public void update() {

        //Defender
		for (int i = 0; i < generateLvl.getDefendersList().size(); i++) {
			generateLvl.getDefendersList().get(i).update();
		}

		for (int i = 0; i < generateLvl.getAttackersList().size(); i++) {
			generateLvl.getAttackersList().get(i).update();
			//Goes out of range
			if(generateLvl.getAttackersList().get(i).getPos().getX() > 800 ||
                    generateLvl.getAttackersList().get(i).getPos().getX() < 0 ){

                JOptionPane.showMessageDialog(null, "Attacker out of range",
                        "Error", JOptionPane.ERROR_MESSAGE);

                System.exit(1);
            }

            //Goes out of range
            if(generateLvl.getAttackersList().get(i).getPos().getY() > 600 ||
                    generateLvl.getAttackersList().get(i).getPos().getY() < 0 ){
                JOptionPane.showMessageDialog(null, "Attacker out of range",
                        "Error", JOptionPane.ERROR_MESSAGE);

                System.exit(1);
            }

			if (generateLvl.getAttackersList().get(i).getHealth() < 0) {
                if (generateLvl.getAttackersList().get(i)
                        .equals(getSpecialID())) {

                    RunGame.specialAlive = false;
				}

                generateLvl.getAttackersList().remove(i);

            } else if (generateLvl.getAttackersList().get(i).getBound()
					.intersects(generateLvl.getGoalPosition())) {
                // Add money to wallet
				bonus += generateLvl.getAttackersList().get(i).getHealth() * 2;

                nrOfAttackerToGoal++;

				generateLvl.getAttackersList().remove(i);
            } else if (RunGame.hasTeleporter && generateLvl.getAttackersList()
					.get(i).getBound()
					.intersects(generateLvl.getTeleporterStartPosition())) {
                Position pos = new Position(
						generateLvl.getTeleporterEndPosition().getX(),
						generateLvl.getTeleporterEndPosition().getY());

                generateLvl.getAttackersList().get(i).setPos(pos);
                generateLvl.getAttackersList().get(i)
						.setTurnValue(generateLvl.getTeleporterDirection());
			}
		}
	}

	/**
	 * Checks what type of attacker is sent in as parameters and creates either
	 * SpecialAttacker, NormalAttacker or MuscleAttacker.
	 * @param type type
	 */
	public void createNewAttacker(AttackerType type) {
        if (type.equals(AttackerType.NORMALATTACKER)) {
            generateLvl.getAttackersList().add(new NormalAttacker(
					generateLvl.getStartPosition(), generateLvl.getBlocks()));
        } else if (type.equals(AttackerType.SPECIALATTACKER)) {
            generateLvl.getAttackersList().add(new SpecialAttacker(
					generateLvl.getStartPosition(), generateLvl.getBlocks()));

            specialID = generateLvl.getAttackersList().getLast();

            Position endpos = new Position(generateLvl.getGoalPosition().x,
					generateLvl.getGoalPosition().y);

            RunGame.specialAlive = true;

			generateLvl.setTeleporterStartPosition(endpos);
			generateLvl.setTeleporterEndPosition(null);

            RunGame.hasTeleporter = false;
        } else if (type.equals(AttackerType.MUSCLEATTACKER)) {
            generateLvl.getAttackersList().add(new MuscleAttacker(
					generateLvl.getStartPosition(), generateLvl.getBlocks()));
		}
	}

	/**
	 * Returns value of bonus
	 * @return int bonus
	 */
	public int getBonus() {
		return bonus;
	}

	/**
	 * Sets bonus to 0
	 */
	public void resetBonus() {
		bonus = 0;
	}

	/**
	 * Returns value of number of attacker to goal
	 * @return int nrOfAttackerToGoal
	 */
	public int getNrOfAttackersToGoal() {
		return nrOfAttackerToGoal;
	}

	/**
	 * Resets the number of attackers that has reached goal
	 */
	public void resetNrOfAttackersToGoal() {
		this.nrOfAttackerToGoal = 0;
	}

	/**
	 * Returns the id of the special attacker
	 * @return Attacker
	 */
	public Attacker getSpecialID() {
		return specialID;
	}

}
