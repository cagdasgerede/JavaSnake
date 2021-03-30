package pl.nogacz.snake.Obstacles;

import java.util.Random;

import pl.nogacz.snake.board.Coordinates;
import pl.nogacz.snake.pawn.Pawn;
import pl.nogacz.snake.pawn.PawnClass;

public class Lightning extends Obstacle{
    private PawnClass lightningClass;
    private Random random;
    private int remainTime = 10;
    public Lightning(){
        lightningClass = new PawnClass(Pawn.LIGHTNING);
        random = new Random();
    }
    public int getRemainTime(){
        return remainTime;
    }
    public PawnClass getPawnObject(){
        return lightningClass;
    }
    public Coordinates[] addObstacle(){
        Coordinates[] coordinates = new Coordinates[6];
        int limit = 40;
        do{
            if(limit == 0)return null;
            limit--;
            int x = random.nextInt(21);
            int y = random.nextInt(21);
            coordinates[0] = new Coordinates(x, y);
            coordinates[1] = new Coordinates(x-1, y+1);
            coordinates[2] = new Coordinates(x-2, y+2);
            coordinates[3] = new Coordinates(x+1, y);
            coordinates[4] = new Coordinates(x+2, y+1);
            coordinates[5] = new Coordinates(x+3, y+2);
        }while(isFieldNotNull(coordinates[0])|isFieldNotNull(coordinates[1])|isFieldNotNull(coordinates[2])|isFieldNotNull(coordinates[3])|isFieldNotNull(coordinates[4])|isFieldNotNull(coordinates[5]));
        
        return coordinates;
    }
   
}
