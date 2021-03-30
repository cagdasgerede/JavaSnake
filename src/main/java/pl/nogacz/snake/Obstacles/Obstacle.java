package pl.nogacz.snake.Obstacles;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import pl.nogacz.snake.board.Coordinates;
import pl.nogacz.snake.pawn.PawnClass;

public class Obstacle {
    private Obstacle obstacle;
    private PawnClass pawn;
    private Random random = new Random();
    private Coordinates[] coordinates;
    private static int number_of_obstacles = 0;
    private static Map<Coordinates, Integer> map = new HashMap<>();
    private static HashMap<Coordinates, PawnClass> gameBoard;
    public Obstacle(){
        
    }
    public Obstacle(HashMap<Coordinates, PawnClass> board){
        gameBoard = board;
    }
    public void deleteObstacle(){
        boolean state = true;
        int count = 0;
        HashMap<Coordinates, Integer> hashmap = new HashMap<>();
        for(Map.Entry<Coordinates, Integer> entry : map.entrySet()){
            if(entry.getValue() == 0){
                gameBoard.remove(entry.getKey());
                if(state){
                    count++;
                    state = false;
                }
            }
            else{
                state = true;
                entry.setValue(entry.getValue()-1);
                hashmap.put(entry.getKey(), entry.getValue()); 
            }
        }
        map = hashmap;
        number_of_obstacles -= count;
    }
    public Coordinates[] addObstacle(){
        if(number_of_obstacles > 5)return null;
        determineObstacleType();
        coordinates = obstacle.addObstacle();
        if(coordinates == null)return null;
        pawn = obstacle.getPawnObject();
        for(Coordinates coordinate : coordinates){
            gameBoard.put(coordinate, pawn);
            map.put(coordinate, obstacle.getRemainTime());
        }
        number_of_obstacles++;
        return null;
    }
    public int getRemainTime(){
        return 0;
    }
    public PawnClass getPawnObject(){
        return null;
    }
    private void determineObstacleType(){
        int rand = random.nextInt(5);
        switch(rand){
            case 0:
                obstacle = new Stone();
                break;
            case 1:
                obstacle = new Iron();
                break;
            case 2:
                obstacle = new Gold();
                break;
            case 3:
                obstacle = new Diamond();
                break;
            case 4:
                obstacle = new Fire();
        }
    }

    public boolean isFieldNotNull(Coordinates coordinates) {
        return gameBoard.get(coordinates) != null;
    }
}