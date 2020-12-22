package pl.nogacz.snake.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import pl.nogacz.snake.application.Design;
import pl.nogacz.snake.board.Board;

public class MenuTest {
    Design design;
    Board board;
    
    @BeforeEach
    public void setup(){
        design = new Design();
        board = new Board(design);
    }

    @Test
    void testIsPausedWhenMenuOpened() {
        board.menuFrame();
        assertTrue(board.getPaused());
    }
}