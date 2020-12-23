package pl.nogacz.snake.application;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class TestAchievement {

    private Achievement trophy;

    @Before void initialize(){
        trophy = new Achievement();
    }

    @Test void testGetAchieved(){
        trophy.setAll(5 , 3);
        assertEquals(5 , trophy.getAchieved());
    }

    @Test void testGetProgress(){
        trophy.setAll(3 , 5);
        assertEquals(3 , trophy.getProgress());
    }

    @Test void testCheckTrue(){
        trophy.setAll(1 , 10);
        trophy.setMilestone(5);
        assertTrue(trophy.check());
    }

    @Test void testCheckFalse(){
        trophy.setAll(2 , 10);
        trophy.setMilestone(5);
        assertFalse(trophy.check());
    }

    @Test void testResetProgress(){
        trophy.setAll(2 , 10);
        trophy.reset();
        assertEquals(0 , trophy.getProgress());
    }

    @Test void testResetAchieved(){
        trophy.setAll(2 , 10);
        trophy.reset();
        assertEquals(0 , trophy.getAchieved());
    }
    
}
