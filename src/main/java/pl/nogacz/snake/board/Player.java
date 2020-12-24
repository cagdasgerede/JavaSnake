package pl.nogacz.snake.board;

import pl.nogacz.snake.pawn.Pawn;
import pl.nogacz.snake.pawn.PawnClass;

import java.util.ArrayList;

public class Player {
    private boolean isEndGame;
    private Direction direction;
    private int tailLength;
    private Coordinates2P snakeHeadCoordinates;
    private PawnClass snakeHeadClass;
    private PawnClass snakeBodyClass;
    private PawnClass foodClass;
    private ArrayList<Coordinates> snakeTail;

    public Player(Coordinates2P snakeHeadCoordinates, int playerNumber) {
        isEndGame = false;
        direction = Direction.UP; // initial direction
        tailLength = 0;
        this.snakeHeadCoordinates = snakeHeadCoordinates;
        editSnakeByPlayerNumber(playerNumber);
        snakeTail = new ArrayList<>();
    }

    private void editSnakeByPlayerNumber(int playerNumber) {
        if (playerNumber == 1) {
            snakeHeadClass = new PawnClass(Pawn.SNAKE_HEAD);
            snakeBodyClass = new PawnClass(Pawn.SNAKE_BODY);
            foodClass = new PawnClass(Pawn.FOOD);
        } else if (playerNumber == 2) {
            snakeHeadClass = new PawnClass(Pawn.SNAKE_HEAD_SECOND_PLAYER);
            snakeBodyClass = new PawnClass(Pawn.SNAKE_BODY_SECOND_PLAYER);
            foodClass = new PawnClass(Pawn.FOOD_SECOND_PLAYER);
        }
    }

    //getters and setters
    boolean isEndGame() {
        return isEndGame;
    }

    void setEndGame(boolean endGame) {
        isEndGame = endGame;
    }

    Direction getDirection() {
        return direction;
    }

    void setDirection(Direction direction) {
        this.direction = direction;
    }

    int getTailLength() {
        return tailLength;
    }

    void incrementTailLength() {
        tailLength++;
    }

    Coordinates2P getSnakeHeadCoordinates() {
        return snakeHeadCoordinates;
    }

    void setSnakeHeadCoordinates(Coordinates2P snakeHeadCoordinates) {
        this.snakeHeadCoordinates = snakeHeadCoordinates;
    }

    PawnClass getSnakeHeadClass() {
        return snakeHeadClass;
    }

    PawnClass getSnakeBodyClass() {
        return snakeBodyClass;
    }

    PawnClass getFoodClass() {
        return foodClass;
    }

    ArrayList<Coordinates> getSnakeTail() {
        return snakeTail;
    }
}
