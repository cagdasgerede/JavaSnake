package pl.nogacz.snake;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import pl.nogacz.snake.board.Board;
import pl.nogacz.snake.application.Design;
import pl.nogacz.snake.application.SnakeSkins;
import pl.nogacz.snake.application.SnakeSkins.bodySkinTones;

public class SettingsMenuTest {
    @Test
    void testBodySkin() {
        Design design = mock(Design.class);
        Board board = new Board(design);
        SnakeSkins ss = new SnakeSkins();
        //ss.setBodySkin(SnakeSkins.bodySkinTones.SKIN_TONE_1);
        SnakeSkins.bodySkinTones bodySkin = bodySkinTones.SKIN_TONE_1;
        Board.setBodySkin();
        assertEquals(SnakeSkins.getBodySkin(), bodySkin);
    }

    @Test
    void testHeadSkin() {
        Design design = mock(Design.class);
        Board board = new Board(design);
        SnakeSkins ss = new SnakeSkins();
        //ss.setBodySkin(SnakeSkins.bodySkinTones.SKIN_TONE_2);
        SnakeSkins.bodySkinTones bodySkin = bodySkinTones.SKIN_TONE_2;
        Board.setBodySkin();
        assertEquals(SnakeSkins.getBodySkin(), bodySkin);
    }

    @Test
    void testHeadSkin() {
        Design design = mock(Design.class);
        Board board = new Board(design);
        SnakeSkins ss = new SnakeSkins();
        //ss.setBodySkin(SnakeSkins.bodySkinTones.SKIN_TONE_3);
        SnakeSkins.bodySkinTones bodySkin = bodySkinTones.SKIN_TONE_3;
        Board.setBodySkin();
        assertEquals(SnakeSkins.getBodySkin(), bodySkin);
    }
}