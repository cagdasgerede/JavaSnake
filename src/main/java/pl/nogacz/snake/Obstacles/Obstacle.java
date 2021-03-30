package pl.nogacz.snake.Obstacles;
import java.util.HashMap;
import java.util.Random;

import pl.nogacz.snake.board.Coordinates;
import pl.nogacz.snake.pawn.PawnClass;

public class Obstacle {
    private Obstacle obstacle;
    private PawnClass pawn;
    private Random random = new Random();
    private Coordinates[] coordinates;
    private HashMap<Coordinates, PawnClass> gameBoard;
    public Obstacle(){
        // I don't know why I have to implement this construct
    }
    public Obstacle(HashMap<Coordinates, PawnClass> board){
        gameBoard = board;
    }
    public Coordinates[] addObstacle(){
        determineObstacleType();
        coordinates = obstacle.addObstacle();
        pawn = obstacle.getPawnObject();
        for(Coordinates coordinate : coordinates){
            gameBoard.put(coordinate, pawn);
        }
        return null;
    }
    public PawnClass getPawnObject(){
        return null;
    }
    public void determineObstacleType(){
        int rand = random.nextInt(4);
        switch(rand){
            case 0:
                obstacle = new Stone(gameBoard);
                break;
            case 1:
                obstacle = new Iron(gameBoard);
                break;
            case 2:
                obstacle = new Gold(gameBoard);
                break;
            case 3:
                obstacle = new Diamond(gameBoard);
        }
    }
    
    public boolean isFieldNotNull(Coordinates coordinates, HashMap<Coordinates, PawnClass> board) {
        return board.get(coordinates) != null;
    }
}