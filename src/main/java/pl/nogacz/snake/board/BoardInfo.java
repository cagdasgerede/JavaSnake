package pl.nogacz.snake.board;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import pl.nogacz.snake.board.Board.Direction;
import pl.nogacz.snake.pawn.PawnClass;

public class BoardInfo implements Serializable{

    private static final long serialVersionUID = 8558092013837790635L;

    private HashMap<Coordinates, PawnClass> board;

    private Direction direction;
    private int tailLength;

    private Coordinates snakeHeadCoordinates;

    private PawnClass snakeHeadClass;
    private PawnClass snakeBodyClass;
    private PawnClass foodClass;

    private ArrayList<Coordinates> snakeTail;
    
    public BoardInfo(HashMap<Coordinates, PawnClass> board,Direction direction,int tailLength,Coordinates snakeHeadCoordinates,PawnClass snakeHeadClass,PawnClass snakeBodyClass,PawnClass foodClass,ArrayList<Coordinates> snakeTail){

        this.board = board;
        this.direction = direction;
        this.tailLength = tailLength;
        this.snakeHeadCoordinates = snakeHeadCoordinates;
        this.snakeHeadClass = snakeHeadClass;
        this.snakeBodyClass = snakeBodyClass;
        this.foodClass = foodClass;
        this.snakeTail = snakeTail;

    }

    public HashMap<Coordinates, PawnClass> getBoard(){return board;}
    public Direction getDirection(){return direction;}
    public int getTailLength(){return tailLength;}
    public Coordinates getHeadCoordinates(){return snakeHeadCoordinates;}
    public PawnClass getHeadClass(){return snakeHeadClass;}
    public PawnClass getBodyClass(){return snakeBodyClass;}
    public PawnClass getFoodClass(){return foodClass;}
    public ArrayList<Coordinates> getSnakeTail(){return snakeTail;}


    


    
}
