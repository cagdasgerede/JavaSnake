package pl.nogacz.snake;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import pl.nogacz.snake.board.Board;
import pl.nogacz.snake.board.Coordinates;
import pl.nogacz.snake.application.Design;
import pl.nogacz.snake.pawn.Pawn;
import pl.nogacz.snake.pawn.PawnClass;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

@TestInstance(Lifecycle.PER_CLASS)
public class testRottenFoodFeature {
    Logger logger;
    Design design;
    Board board;
    PawnClass snakeBodyClass;
    PawnClass rottenFoodClass;
    HashMap<Coordinates, PawnClass> boardHash;
    ArrayList<Coordinates> snakeTail;
    Coordinates coordinates;
    
    @BeforeAll
    public void initialize() {
        logger = LogManager.getLogger(testRottenFoodFeature.class);
        design = mock(Design.class);    
        board = new Board(design);

        // for testDecreaseLengthAndEatRottenFood()
        snakeBodyClass = new PawnClass(Pawn.SNAKE_BODY);
        rottenFoodClass = new PawnClass(Pawn.ROTTENFOOD);
        boardHash = new HashMap<>();
        snakeTail = new ArrayList<>();

        //for testDisappearRottenFoodAfterCounting()
        coordinates = mock(Coordinates.class);
    }

    @Test
    public void testDecreaseLengthAndEatRottenFood() {            
        boardHash.put(new Coordinates(10, 11), snakeBodyClass);
        snakeTail.add(new Coordinates(10, 11));        
        board.setTailLength(1);      
        boardHash.put(new Coordinates(10, 12), rottenFoodClass);
        board.setRottenFoodCoordinates(new Coordinates(10, 12));  
        board.setBoard(boardHash);
        board.setSnakeTail(snakeTail);
        
        try {
            Method method = Board.class.getDeclaredMethod("moveSnakeHead", Coordinates.class); 
            method.setAccessible(true);
            method.invoke(board, new Coordinates(10, 12));   
        } catch(Exception e) {
            logger.fatal(e.getMessage());
        }

        assertEquals(board.getTailLength(), 0);
        assertFalse(board.isRottenFoodExist());
    }
   
    @Test
    public void testDisappearRottenFoodAfterCounting() {
        when(coordinates.isValid()).thenReturn(true);       
        board.setRottenFoodCoordinates(coordinates);       
        board.setDisappearanceCounter(board.getDisappearanceTime());

        try {
            Method method = Board.class.getDeclaredMethod("moveSnakeHead", Coordinates.class); 
            method.setAccessible(true);
            method.invoke(board, coordinates);   
        } catch(Exception e) {
            logger.fatal(e.getMessage());
        }

        assertFalse(board.isRottenFoodExist());
        assertEquals(board.getDisappearanceCounter(), 0);
    }

    @Test
    public void testAddRottenFood() {
        try {  
            Method method = Board.class.getDeclaredMethod("addRottenFood");  
            method.setAccessible(true);  
            method.invoke(board); 
        } catch(Exception e) {
            logger.fatal(e.getMessage());
        }

        assertTrue(board.isRottenFoodExist());
    }
}
