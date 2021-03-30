package pl.nogacz.snake.Obstacles;
import java.util.HashMap;
import java.util.Random;
import pl.nogacz.snake.board.Coordinates;
import pl.nogacz.snake.pawn.Pawn;
import pl.nogacz.snake.pawn.PawnClass;

public class Gold extends Obstacle{
    private PawnClass goldClass;
    private Random random;
    private HashMap<Coordinates, PawnClass> gameBoard;
    public Gold(HashMap<Coordinates, PawnClass> board){
        goldClass = new PawnClass(Pawn.GOLD);
        gameBoard = board;
        random = new Random();
    }
    public PawnClass getPawnObject(){
        return goldClass;
    }
    public Coordinates[] addObstacle(){
        Coordinates[] coordinates = new Coordinates[3];
        do{
            int x = random.nextInt(21);
            int y = random.nextInt(21);
            coordinates[0] = new Coordinates(x,y);
            coordinates[1] = new Coordinates(x+1, y);
            coordinates[2] = new Coordinates(x, y-1);
        }while(isFieldNotNull(coordinates[0],gameBoard) | isFieldNotNull(coordinates[1],gameBoard) | isFieldNotNull(coordinates[2], gameBoard));
        return coordinates;
    }
  

}