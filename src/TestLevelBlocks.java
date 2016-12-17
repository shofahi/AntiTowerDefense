import java.awt.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class TestLevelBlocks {

	private LevelBlocks levelBlocks;

	@Before
	public void setUp() {
		this.levelBlocks = new LevelBlocks(new Position(1, 1), 10, 10,
				BlockType.PATH);
	}

	@After
	public void tearDown() {
		this.levelBlocks = null;
	}

	@Test
	public void testConstructors() {
		LevelBlocks positionLevelBlocks = new LevelBlocks(new Position(1, 1),
				10, 10, BlockType.STARTPOSITION);
		LevelBlocks coordinatesLevelBlocks = new LevelBlocks(1, 1, 10, 10,
				BlockType.GOALPOSITION);

		Assert.assertNotNull(
				"Could not create instance of LevelBlocks with Position",
				positionLevelBlocks);
		Assert.assertNotNull(
				"Could not create instance of LevelBlocks with Coordinates",
				coordinatesLevelBlocks);
	}

	@Test
	public void testGetBound() {
		Rectangle expected = new Rectangle(1, 1, 10, 10);

		Assert.assertEquals(expected, levelBlocks.getBound());
	}
}
