
import java.awt.*;

public class WorldHandler {

	private int bonus;
	private int nrOfAttackerToGoal;

	private GenerateLevel generateLvl;

	private Attacker specialID = null;

	public WorldHandler(GenerateLevel generateLvl) {
		this.generateLvl = generateLvl;
	}

	public void render(Graphics g) {
		// for (Block block : blocks)
		for (int i = 0; i < generateLvl.getBlocks().size(); i++) {
			generateLvl.getBlocks().get(i).render(g);
		}

		for (int i = 0; i < generateLvl.getDefendersList().size(); i++) {
			generateLvl.getDefendersList().get(i).render(g);
		}

		for (int i = 0; i < generateLvl.getAttackersList().size(); i++) {
			generateLvl.getAttackersList().get(i).render(g);
		}
	}

	/**
	 * 60frames per second
	 */
	public void update() {

		// defender
		for (int i = 0; i < generateLvl.getDefendersList().size(); i++) {
			generateLvl.getDefendersList().get(i).update();
		}

		for (int i = 0; i < generateLvl.getAttackersList().size(); i++) {
			generateLvl.getAttackersList().get(i).update();

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
				System.out.println("Adding money to wallet here");
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

	public int getBonus() {
		return bonus;
	}

	public void resetBonus() {
		bonus = 0;
	}

	public int getNrOfAttackersToGoal() {
		return nrOfAttackerToGoal;
	}

	public void resetNrOfAttackersToGoal() {
		this.nrOfAttackerToGoal = 0;
	}

	public Attacker getSpecialID() {
		return specialID;
	}
}
