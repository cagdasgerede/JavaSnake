package pl.nogacz.snake;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import pl.nogacz.snake.application.TwoPlayerDesign;
import pl.nogacz.snake.board.Coordinates2P;
import pl.nogacz.snake.board.TwoPlayerBoard;
import pl.nogacz.snake.pawn.Pawn;
import pl.nogacz.snake.pawn.PawnClass;

public class TwoPlayerDesignTest {
    @Test void addPawnTest(){
        TwoPlayerDesign twoPlayerDesign = mock(TwoPlayerDesign.class);
        Coordinates2P coordinates2P = new Coordinates2P(12,12);
        PawnClass pawnClass = new PawnClass(Pawn.SNAKE_HEAD);
        String[] userKeys1 = {"W","S","A","D"};
        String[] userKeys2 = {"Z","X","C","V"};
        TwoPlayerBoard twoPlayerBoard = new TwoPlayerBoard(twoPlayerDesign, true, userKeys1, userKeys2);
        assertDoesNotThrow(
                () -> twoPlayerDesign.addPawn(coordinates2P,pawnClass,twoPlayerBoard));
    }

    @Test void removePawnTest(){
        TwoPlayerDesign twoPlayerDesign = mock(TwoPlayerDesign.class);
        Coordinates2P coordinates2P = new Coordinates2P(12,12);
        PawnClass pawnClass = new PawnClass(Pawn.SNAKE_HEAD);
        String[] userKeys1 = {"W","S","A","D"};
        String[] userKeys2 = {"Z","X","C","V"};
        TwoPlayerBoard twoPlayerBoard = new TwoPlayerBoard(twoPlayerDesign, true, userKeys1, userKeys2);
        twoPlayerDesign.addPawn(coordinates2P,pawnClass,twoPlayerBoard);
        assertDoesNotThrow(
                () -> twoPlayerDesign.removePawn(coordinates2P));
    }
}