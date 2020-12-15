package pl.nogacz.snake;

import org.junit.Test;

import jdk.jfr.Timestamp;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import pl.nogacz.snake.board.Board;
import pl.nogacz.snake.board.Coordinates;
import pl.nogacz.snake.application.Design;


public class InvincibilityItemTest {

    @Test
    public void testSpawnInvincibilityItem() {
        
        Design design = mock(Design.class);
        Board board = new Board(design);

        board.addItem();
        
        assertFalse(-1 == board.itemTimer);
        
    }

    @Test
    public void testEatInvincibilityItem() {
        
        Design design = mock(Design.class);
        Board board = new Board(design);

        board.addItem();

        board.activateSuperPower();

        assertTrue(true == design.superPower);

    }

    @Test
    public void testDissappearNotEatenItem() {
        
        Design design = mock(Design.class);
        Board board = new Board(design);

        board.addItem();
        
        board.dissappearItem();

        assertFalse(board.thereIsItem);

    }

}