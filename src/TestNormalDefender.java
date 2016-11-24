import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Samuel on 2016-11-24.
 */
public class TestNormalDefender {

    private NormalDefender nD;

    @Before
    public void setUp(){
        nD = new NormalDefender(new Position(1,1), null);
    }

    @After
    public void tearDown(){
        nD = null;
    }

    @Test
    public void checkDamage(){
        int expectedDmg = 1;

        Assert.assertEquals(expectedDmg, nD.getDamage());
    }

    @Test
    public void checkRange(){
        int expectedRange = 300;

        Assert.assertEquals(expectedRange, nD.getRange());
    }

    @Test
    public void changeDamage(){
        nD.setDamage(123);

        Assert.assertEquals(123, nD.getDamage());
    }

    @Test
    public void changeRange(){
        nD.setRange(321);

        Assert.assertEquals(321, nD.getRange());
    }

}
