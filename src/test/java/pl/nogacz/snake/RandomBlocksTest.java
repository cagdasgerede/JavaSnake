package pl.nogacz.snake;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import pl.nogacz.snake.board.Board;
import pl.nogacz.snake.board.Coordinates;
import pl.nogacz.snake.application.Design;


public class RandomBlocksTest {
    
    @Test
    void testSpawnRottenApple1(){

        Design design = mock(Design.class);
        Board b = new Board(design);

        b.getLastSpawnTimesOfApples()[0] = System.currentTimeMillis();
        b.getNewRandomSpawnTimesOfApples()[0] = 0;
        b.spawnRottenApple();
        
        b.getLastSpawnTimesOfApples()[0] = System.currentTimeMillis();
        b.getNewRandomSpawnTimesOfApples()[0] = 0;
        b.spawnRottenApple();
        

        b.getLastSpawnTimesOfApples()[0] = System.currentTimeMillis();
        b.getNewRandomSpawnTimesOfApples()[0] = 0;
        b.spawnRottenApple();
        
        assertEquals(3, b.getRottenApplesBlack().size());
    }

    @Test
    void testSpawnRottenApple2(){

        Design design = mock(Design.class);
        Board b = new Board(design);

        b.getLastSpawnTimesOfApples()[1] = System.currentTimeMillis();
        b.getNewRandomSpawnTimesOfApples()[1] = 0;
        b.spawnRottenApple();
        
        b.getLastSpawnTimesOfApples()[1] = System.currentTimeMillis();
        b.getNewRandomSpawnTimesOfApples()[1] = 0;
        b.spawnRottenApple();

        assertEquals(2, b.getRottenApplesGrey().size());
    }

    @Test
    void testSpawnRottenApple3(){

        Design design = mock(Design.class);
        Board b = new Board(design);

        b.getLastSpawnTimesOfApples()[2] = System.currentTimeMillis();
        b.getNewRandomSpawnTimesOfApples()[2] = 0;
        b.spawnRottenApple();

        assertEquals(1, b.getRottenApplesOrange().size());
    }


    @Test
    void testDissappearRottenApple1(){
        
        Design design = mock(Design.class);
        Board b = new Board(design);

        b.getLastSpawnTimesOfApples()[0] = System.currentTimeMillis();
        b.getNewRandomSpawnTimesOfApples()[0] = 0;
        b.spawnRottenApple();
        
        b.getLastSpawnTimesOfApples()[0] = System.currentTimeMillis();
        b.getNewRandomSpawnTimesOfApples()[0] = 0;
        b.spawnRottenApple();
        

        b.getLastSpawnTimesOfApples()[0] = System.currentTimeMillis();
        b.getNewRandomSpawnTimesOfApples()[0] = 0;
        b.spawnRottenApple();
        

        b.getLastDissappearTimesOfApples()[0] = System.currentTimeMillis();
        b.getNewRandomDissappearTimesOfApples()[0] = 0;
        b.disappearRottenApple();

        assertEquals(2, b.getRottenApplesBlack().size());
    }

    @Test
    void testDissappearRottenApple2(){
        
        Design design = mock(Design.class);
        Board b = new Board(design);

        b.getLastSpawnTimesOfApples()[1] = System.currentTimeMillis();
        b.getNewRandomSpawnTimesOfApples()[1] = 0;
        b.spawnRottenApple();
        
        b.getLastSpawnTimesOfApples()[1] = System.currentTimeMillis();
        b.getNewRandomSpawnTimesOfApples()[1] = 0;
        b.spawnRottenApple();
        
        b.getLastSpawnTimesOfApples()[1] = System.currentTimeMillis();
        b.getNewRandomSpawnTimesOfApples()[1] = 0;
        b.spawnRottenApple();

        b.getLastDissappearTimesOfApples()[1] = System.currentTimeMillis();
        b.getNewRandomDissappearTimesOfApples()[1] = 0;
        b.disappearRottenApple();

        b.getLastDissappearTimesOfApples()[1] = System.currentTimeMillis();
        b.getNewRandomDissappearTimesOfApples()[1] = 0;
        b.disappearRottenApple();

        assertEquals(1, b.getRottenApplesGrey().size());
    }

    @Test
    void testDissappearRottenApple3(){
        
        Design design = mock(Design.class);
        Board b = new Board(design);
   
        b.getLastSpawnTimesOfApples()[2] = System.currentTimeMillis();
        b.getNewRandomSpawnTimesOfApples()[2] = 0;
        b.spawnRottenApple();

        b.getLastDissappearTimesOfApples()[2] = System.currentTimeMillis();
        b.getNewRandomDissappearTimesOfApples()[2] = 0;
        b.disappearRottenApple();

        assertEquals(0, b.getRottenApplesOrange().size());
    }

    @Test
    void testClearRottenApples(){
        Design design = mock(Design.class);
        Board b = new Board(design);
        
        Coordinates testCoordinates = new Coordinates(10, 10);
        
        b.getRottenApplesBlack().add(testCoordinates);
        b.getRottenApplesBlack().add(testCoordinates);
        b.getRottenApplesBlack().add(testCoordinates);

        b.getRottenApplesGrey().add(testCoordinates);
        b.getRottenApplesGrey().add(testCoordinates);
        b.getRottenApplesGrey().add(testCoordinates);
        
        b.getRottenApplesOrange().add(testCoordinates);
 
        b.clearRottenApples();

        assertEquals(b.getRottenApplesBlack().size(),0);
        assertEquals(b.getRottenApplesGrey().size(),0);
        assertEquals(b.getRottenApplesOrange().size(),0);
    }
}