/**
 * Created by Samuel on 2016-11-24.
 */

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestNormalAttacker {

    private NormalAttacker nA;

    @Before
    public void setUp(){
        this.nA = new NormalAttacker(new Position(1,1), null);
    }

    @After
    public void tearDown(){
        this.nA = null;
    }


    @Test
    public void testPosition(){
        Position expected = new Position(1,1);

        Assert.assertEquals(expected, nA.getPos());

    }

    @Test
    public void testChangePosition(){
        Position expected = new Position(2,2);

        Assert.assertNotEquals(nA.getPos(), expected);

        nA.setPos(expected);

        Assert.assertEquals(expected, nA.getPos());
    }

    @Test
    public void testChangeHealth(){
        nA.setHealth(555);

        Assert.assertEquals(555, nA.getHealth());
    }

    @Test
    public void testInflictDamage(){
        nA.setHealth(100);

        nA.inflictDamage(10);

        Assert.assertEquals(90, nA.getHealth());
    }
}
