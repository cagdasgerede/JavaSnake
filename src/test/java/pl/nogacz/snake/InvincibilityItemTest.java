package pl.nogacz.snake;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import pl.nogacz.snake.board.Board;
import pl.nogacz.snake.application.Design;


public class InvincibilityItemTest {

    @Test
    public void testSpawnInvincibilityItem() {

        Design design = mock(Design.class);
        Board board = new Board(design);

        board.addInvicibilityItem();
        
        assertTrue(board.isThereInvicibilityItem());
        
    }

    //tests if become invincible after eating invincibility item
    @Test
    public void testInvincibility() {

        Design design = mock(Design.class);
        Board board = new Board(design);

        board.addInvicibilityItem();
        board.activateInvincibility();

        assertTrue(false == board.CountInvincibilityFrame());

    }

    /*@Test
    public void testDissappearNotEatenItem() {

        Design design = mock(Design.class);
        Board board = new Board(design);

        board.addInvicibilityItem();
        
        board.dissappearInvincibilityItem();

        assertFalse(board.isThereInvicibilityItem());

    }

    @Test
    public void testInvincibilityTimeOut() {

        Design design = mock(Design.class);
        Board board = new Board(design);
        
        int countFrameOfInvincibility = 0;

        board.activateInvincibility();

        while(board.CountInvincibilityFrame()) {
            countFrameOfInvincibility++;
        }

        countFrameOfInvincibility++;

        assertTrue(countFrameOfInvincibility == 149);

    }*/

}
