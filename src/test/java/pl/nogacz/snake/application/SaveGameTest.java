import pl.nogacz.snake.application.Design;
import pl.nogacz.snake.board.Board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SaveGameTest{

    Design design;
    Board board;

    @BeforeEach
    public void initiate(){

        design = new Design();
        board = new Board(design);
    }
    
    /*@Test
    void testIsPausedWhenSaved(){

        board.saveTheGame();
        assertTrue(board.isPaused());
    }*/
}