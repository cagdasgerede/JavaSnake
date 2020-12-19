package pl.nogacz.snake;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import pl.nogacz.snake.board.Board;
//import pl.nogacz.snake.application.SnakeSkins;
import pl.nogacz.snake.application.SnakeSkins.bodySkinTones;

public class SettingsMenuTest {
    @Test
    void testBodySkin() {
        Design design = mock(Design.class);
        Board board = new Board(design);
        SnakeSkins.bodySkinTones bodySkin = bodySkinTones.SKIN_TONE_1;
        board.setBodySkin(bodySkin);
        assertEquals(SnakeSkins.getBodySkin(), bodySkin);
    }

    @Test
    void testHeadSkin() {
        Design design = mock(Design.class);
        Board board = new Board(design);
        SnakeSkins.headSkinTones headSkin = headSkinTones.SKIN_TONE_1;
        board.setHeadSkin(headSkin);
        assertEquals(SnakeSkins.getHeadSkin(), headSkin);
    }
}
