package pl.nogacz.snake;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import pl.nogacz.snake.board.Board;
import pl.nogacz.snake.board.Coordinates;
import pl.nogacz.snake.application.Design;
import pl.nogacz.snake.pawn.Pawn;
import pl.nogacz.snake.pawn.PawnClass;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
  
public class testRottenFoodFeature {
    @Test
    public void testDecreaseLengthAndEatRottenFood(){
        Design design = mock(Design.class);    
        Board board = new Board(design);
        PawnClass snakeBodyClass = new PawnClass(Pawn.SNAKE_BODY);
        PawnClass rottenFoodClass = new PawnClass(Pawn.ROTTENFOOD);
        HashMap<Coordinates, PawnClass> boardHash = new HashMap<>();
        ArrayList<Coordinates> snakeTail = new ArrayList<>();
        
        boardHash.put(new Coordinates(10, 11), snakeBodyClass);
        snakeTail.add(new Coordinates(10, 11));        
        board.setTailLength(1);      
        boardHash.put(new Coordinates(10, 12), rottenFoodClass);
        board.setRottenFoodCoordinates(new Coordinates(10, 12));  
        board.setBoard(boardHash);
        board.setSnakeTail(snakeTail);
        
        try{
            Method method = Board.class.getDeclaredMethod("moveSnakeHead", Coordinates.class); 
            method.setAccessible(true);
            method.invoke(board, new Coordinates(10, 12));   
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        assertEquals(board.getTailLength(), 0);
        assertFalse(board.isRottenFoodExist());
    }
   
    @Test
    public void testDisappearRottenFoodAfterCounting(){   
        Design design = mock(Design.class);       
        Board board = new Board(design);
        Coordinates coordinates = mock(Coordinates.class);

        when(coordinates.isValid()).thenReturn(true);       
        board.setRottenFoodCoordinates(coordinates);       
        board.setDisappearanceCounter(board.getDisappearanceTime());

        try{
            Method method = Board.class.getDeclaredMethod("moveSnakeHead", Coordinates.class); 
            method.setAccessible(true);
            method.invoke(board, coordinates);   
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        assertFalse(board.isRottenFoodExist());
        assertEquals(board.getDisappearanceCounter(), 0);
    }

    @Test
    public void testAddRottenFood(){
        Design design = mock(Design.class);       
        Board board = new Board(design);

        try{  
            Method method = Board.class.getDeclaredMethod("addRottenFood");  
            method.setAccessible(true);  
            method.invoke(board); 
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        assertTrue(board.isRottenFoodExist());
    }
}
