package pl.nogacz.snake.Obstacles;
import java.util.Random;
import pl.nogacz.snake.board.Coordinates;
import pl.nogacz.snake.pawn.Pawn;
import pl.nogacz.snake.pawn.PawnClass;

public class Gold extends Obstacle{
    private PawnClass goldClass;
    private Random random;
    public Gold(){
        goldClass = new PawnClass(Pawn.GOLD);
        random = new Random();
    }
    public PawnClass getPawnObject(){
        return goldClass;
    }
    public Coordinates[] addObstacle(){
       
        return null;
    }
  

}