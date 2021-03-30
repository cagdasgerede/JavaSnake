package pl.nogacz.snake.Obstacles;
import java.util.Random;
import pl.nogacz.snake.board.Coordinates;
import pl.nogacz.snake.pawn.Pawn;
import pl.nogacz.snake.pawn.PawnClass;

public class Diamond extends Obstacle{
    private PawnClass diamondClass;
    private Random random;
    public Diamond(){
        diamondClass = new PawnClass(Pawn.STONE);
        random = new Random();
    }
    public PawnClass getPawnObject(){
        return diamondClass;
    }
    public Coordinates[] addObstacle(){ 
        return null;
    }
  

}