package pl.nogacz.snake.Obstacles;
import java.util.HashMap;
import java.util.Random;
import pl.nogacz.snake.board.Coordinates;
import pl.nogacz.snake.pawn.Pawn;
import pl.nogacz.snake.pawn.PawnClass;

public class Stone extends Obstacle{
    private PawnClass stoneClass;
    private Random random;
    public Stone(){
        stoneClass = new PawnClass(Pawn.STONE);
        random = new Random();
    }
    public PawnClass getPawnObject(){
        return stoneClass;
    }
    public Coordinates[] addObstacle(){
        Coordinates[] coordinates = new Coordinates[2];
        do{
            int x = random.nextInt(21);
            int y = random.nextInt(21);
            coordinates[0] = new Coordinates(x, y);
            coordinates[1] = new Coordinates(x,y-1);
        }while(isFieldNotNull(coordinates[0]) | isFieldNotNull(coordinates[1]));
        
        return coordinates;
    }
  

    
}