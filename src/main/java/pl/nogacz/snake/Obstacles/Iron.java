package pl.nogacz.snake.Obstacles;
import java.util.HashMap;
import java.util.Random;
import pl.nogacz.snake.board.Coordinates;
import pl.nogacz.snake.pawn.Pawn;
import pl.nogacz.snake.pawn.PawnClass;

public class Iron extends Obstacle{
    private PawnClass ironClass;
    private Random random;
    private HashMap<Coordinates, PawnClass> gameBoard;
    public Iron(HashMap<Coordinates, PawnClass> board){
        ironClass = new PawnClass(Pawn.IRON);
        random = new Random();
        gameBoard = board;
    }
    public PawnClass getPawnObject(){
        return ironClass;
    }
    public Coordinates[] addObstacle(){
        Coordinates[] coordinates = new Coordinates[2];
        do{
            int x = random.nextInt(21);
            int y = random.nextInt(21);
            coordinates[0] = new Coordinates(x, y);
            coordinates[1] = new Coordinates(x+1, y);
        }while(isFieldNotNull(coordinates[0],gameBoard) | isFieldNotNull(coordinates[1],gameBoard));
        
        return coordinates;
    }
  

}