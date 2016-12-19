/**
 * Classname: LevelBlocks.java
 * Version info 1.0
 * Copyright notice:    Masoud Shofahi
 * Date: 17/12/2017
 * Course: Applikationsutveckling i Java
 */

import java.awt.*;

public class LevelBlocks extends Block {

	/**
	 * Constructor for LevelBlocks.
	 * @param pos Position object containing position
	 * @param width Width of block
	 * @param height Height of block
	 * @param blockType What type of block it should be
     */
	public LevelBlocks(Position pos, int width, int height,
			BlockType blockType) {

		super(pos, width, height, blockType);
	}

	/**
	 * Constructor for Levelblocks
	 * @param xPos X-position as integer
	 * @param yPos Y-position as integer
	 * @param width Width of block
	 * @param height Heigh of block
     * @param blockType What type of block it should be
     */
	public LevelBlocks(int xPos, int yPos, int width, int height,
			BlockType blockType) {

		super(xPos, yPos, width, height, blockType);
	}

	/**
	 * Renders graphics with different colors depending on what
	 * block type is used.
	 * 
	 * @param g The graphics
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
