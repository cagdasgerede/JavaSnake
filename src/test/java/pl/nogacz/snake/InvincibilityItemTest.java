package pl.nogacz.snake;

import org.junit.Test;
import org.mockito.internal.verification.checkers.AtLeastDiscrepancy;

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
        Board board = mock(Board.class);

        when(design.getInvincibility()).thenReturn(true);

        board.addInvicibilityItem();
        board.activateInvincibility();

        assertTrue(true == design.getInvincibility());

    }

    @Test
    public void testNotInvincible() {
        Design design = mock(Design.class);
        Board board = mock(Board.class);

        when(design.getInvincibility()).thenReturn(false);

        board.deactivateInvincibility();

        assertTrue(false == design.getInvincibility());

    }

    @Test
    public void testDissappearNotEatenItem() {

        Board board = mock(Board.class);

        when(board.isThereInvicibilityItem()).thenReturn(false);

        board.addInvicibilityItem();
        board.dissappearInvincibilityItem();

        assertFalse(board.isThereInvicibilityItem());

    }

    //could not test the invicibility after 150 frames
    /*@Test
    public void testInvincibilityTimeOut() {

        Board board = mock(Board.class);

        board.activateInvincibility();

        verify(board, atLeast(150)).CountInvincibilityFrame();

    } */

}
