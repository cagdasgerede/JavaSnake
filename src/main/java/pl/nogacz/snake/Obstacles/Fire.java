package pl.nogacz.snake.Obstacles;

import java.util.Random;

import pl.nogacz.snake.board.Coordinates;
import pl.nogacz.snake.pawn.Pawn;
import pl.nogacz.snake.pawn.PawnClass;

public class Fire extends Obstacle{
    private PawnClass fireClass;
    private Random random;
    private int remainTime = 10;
    public Fire(){
        fireClass = new PawnClass(Pawn.FIRE);
        random = new Random();
    }
    public int getRemainTime(){
        return remainTime;
    }
    public PawnClass getPawnObject(){
        return fireClass;
    }
    public Coordinates[] addObstacle(){
        Coordinates[] coordinates = new Coordinates[4];
        do{
            int x = random.nextInt(21);
            int y = random.nextInt(21);
            coordinates[0] = new Coordinates(x, y);
            coordinates[1] = new Coordinates(x,y+1);
            coordinates[2] = new Coordinates(x+1, y);
            coordinates[3] = new Coordinates(x+1,y-1);
        }while(isFieldNotNull(coordinates[0])|isFieldNotNull(coordinates[1])|isFieldNotNull(coordinates[2])|isFieldNotNull(coordinates[3]));
        
        return coordinates;
    }
  
}
