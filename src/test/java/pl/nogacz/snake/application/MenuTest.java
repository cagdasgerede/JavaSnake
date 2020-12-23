package pl.nogacz.snake.application;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import pl.nogacz.snake.application.Design;
import pl.nogacz.snake.board.Board;
import pl.nogacz.snake.board.Coordinates;

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

    @Test
    void testIsNotPausedWhenMenuNotOpened() {
        assertFalse(board.getPaused());
    }

    @Test 
    void testMenuCoordinate(){
        assertTrue(board.isMenuCoordinate(new Coordinates(1, 1)));
    }

    @Test 
    void testNotMenuCoordinate(){
        assertFalse(board.isMenuCoordinate(new Coordinates(3, 4)));
    }

    @AfterEach
    public void tearDown() {
        board = null;
        design = null;
    }
}