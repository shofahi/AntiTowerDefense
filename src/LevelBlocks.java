/**
 * Extends the abstract class Blocks and is used to render graphics
 * depending on the enum BlockType.
 * 
 * @version 17 December 2016
 * @authors Amanda Dahlin, Gustav Nordlander, Samuel Bylund Felixson, 
 * 			Masoud Shofahi
 */

import java.awt.*;

public class LevelBlocks extends Block {
	public LevelBlocks(Position pos, int width, int height,
			BlockType blockType) {
		super(pos, width, height, blockType);
	}

	public LevelBlocks(int xPos, int yPos, int width, int height,
			BlockType blockType) {
		super(xPos, yPos, width, height, blockType);
	}

	/**
	 * Renders graphics with different colors depending on what
	 * block type is used.
	 * 
	 * @param Graphics g
	 */
	@Override
	public void render(Graphics g) {
		if (getBlockType() == BlockType.PATH) {
			g.setColor(Color.ORANGE);
			g.fillRect(getPos().getX(), getPos().getY(), getWidth(),
					getHeight());
			
		} else if (getBlockType() == BlockType.GOALPOSITION) {
			g.setColor(Color.RED);
			g.fillRect(getPos().getX(), getPos().getY(), getWidth(),
					getHeight());

		} else if (getBlockType() == BlockType.STARTPOSITION) {
			g.setColor(Color.GREEN);
			g.fillRect(getPos().getX(), getPos().getY(), getWidth(),
					getHeight());

		}
	}

	/**
	 * Function to get bounds of the rectangle.
	 * 
	 * @return Rectangle
	 */
	@Override
	public Rectangle getBound() {
		return new Rectangle(getPos().getX(), getPos().getY(), getWidth(),
				getHeight());
	}

}
