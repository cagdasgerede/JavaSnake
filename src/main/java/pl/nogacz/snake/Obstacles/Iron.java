package pl.nogacz.snake.Obstacles;
import java.util.Random;
import pl.nogacz.snake.board.Coordinates;
import pl.nogacz.snake.pawn.Pawn;
import pl.nogacz.snake.pawn.PawnClass;

public class Iron extends Obstacle{
    private PawnClass ironClass;
    private Random random;
    public Iron(){
        ironClass = new PawnClass(Pawn.IRON);
        random = new Random();
    }
    public PawnClass getPawnObject(){
        return ironClass;
    }
    public Coordinates[] addObstacle(){
        return null;
    }
  

}