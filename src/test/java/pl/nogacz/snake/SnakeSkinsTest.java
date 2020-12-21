package pl.nogacz.snake;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import pl.nogacz.snake.application.SnakeSkins;
import pl.nogacz.snake.application.SnakeSkins.bodySkinTones;

public class SnakeSkinsTest {
    @Test
    void testBodySkinDefault() {
        SnakeSkins.bodySkinTones body = bodySkinTones.SKIN_TONE_DEFAULT;
        assertEquals(bodySkinTones.SKIN_TONE_DEFAULT, body);
    }

    @Test
    void testBodySkinOrange() {
        SnakeSkins.bodySkinTones body = bodySkinTones.SKIN_TONE_ORANGE;
        assertEquals(bodySkinTones.SKIN_TONE_ORANGE, body);
    }

    @Test
    void testBodySkinGreen() {
        SnakeSkins.bodySkinTones body = bodySkinTones.SKIN_TONE_GREEN;
        assertEquals(bodySkinTones.SKIN_TONE_GREEN, body);
    }

    @Test
    void testBodySkinOrangeAndGreen() {
        SnakeSkins.bodySkinTones body = bodySkinTones.SKIN_TONE_ORANGE;
        assertNotEquals(bodySkinTones.SKIN_TONE_GREEN, body);
    }

    @Test
    void testBodySkinGreenAndDefault() {
        SnakeSkins.bodySkinTones body = bodySkinTones.SKIN_TONE_GREEN;
        assertNotEquals(bodySkinTones.SKIN_TONE_DEFAULT, body);
    }

    @Test
    void testBodySkinDefaultAndOrange() {
        SnakeSkins.bodySkinTones body = bodySkinTones.SKIN_TONE_DEFAULT;
        assertNotEquals(bodySkinTones.SKIN_TONE_ORANGE, body);
    }

    @Test
    void testBodySkinOrangeAndDefault() {
        SnakeSkins.bodySkinTones body = bodySkinTones.SKIN_TONE_ORANGE;
        assertNotEquals(bodySkinTones.SKIN_TONE_DEFAULT, body);
    }

    @Test
    void testBodySkinGreenAndOrange() {
        SnakeSkins.bodySkinTones body = bodySkinTones.SKIN_TONE_GREEN;
        assertNotEquals(bodySkinTones.SKIN_TONE_ORANGE, body);
    }

    @Test
    void testBodySkinDefaultAndGreen() {
        SnakeSkins.bodySkinTones body = bodySkinTones.SKIN_TONE_DEFAULT;
        assertNotEquals(bodySkinTones.SKIN_TONE_GREEN, body);
    }
}
