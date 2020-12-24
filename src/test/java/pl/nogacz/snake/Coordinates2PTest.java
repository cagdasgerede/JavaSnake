package pl.nogacz.snake;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import pl.nogacz.snake.board.Coordinates2P;

public class Coordinates2PTest {
    @Test void testNewCoordinates2P(){
        Coordinates2P coordinates2P = new Coordinates2P(12,13);
        assertEquals(12,coordinates2P.getX());
        assertEquals(13,coordinates2P.getY());
    }

    @Test void isValidWithValid(){
        Coordinates2P coordinates2P = new Coordinates2P(12,13);
        assertTrue(coordinates2P.isValid());
    }

    @Test void isValidWithInvalid(){
        Coordinates2P coordinates2P = new Coordinates2P(50,50);
        assertFalse(coordinates2P.isValid());
    }
}
