package pl.nogacz.snake.Obstacles;
import java.util.Random;
import pl.nogacz.snake.board.Coordinates;
import pl.nogacz.snake.pawn.Pawn;
import pl.nogacz.snake.pawn.PawnClass;

public class Iron extends Obstacle{
    private PawnClass ironClass;
    private Random random;
    int remainTime = 30;
    public Iron(){
        ironClass = new PawnClass(Pawn.IRON);
        random = new Random();
    }
    public int getRemainTime(){
        return remainTime;
    }
    public PawnClass getPawnObject(){
        return ironClass;
    }
    public Coordinates[] addObstacle(){
        Coordinates[] coordinates = new Coordinates[3];
        do{
            int x = random.nextInt(21);
            int y = random.nextInt(21);
            coordinates[0] = new Coordinates(x, y);
            coordinates[1] = new Coordinates(x+1, y);
            coordinates[2] = new Coordinates(x+2, y);
        }while(isFieldNotNull(coordinates[0]) | isFieldNotNull(coordinates[1]) | isFieldNotNull(coordinates[2]));
        
        return coordinates;
    }
  

}