package pl.nogacz.snake.board;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import pl.nogacz.snake.application.Design;
import pl.nogacz.snake.application.EndGame;
import pl.nogacz.snake.application.LoadGame;
import pl.nogacz.snake.application.SaveGame;
import pl.nogacz.snake.pawn.Pawn;
import pl.nogacz.snake.pawn.PawnClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Board {

    private HashMap<Coordinates, PawnClass> board = new HashMap<>();
    private Design design;
    private Random random = new Random();

    private boolean isEndGame = false;
    private boolean isPaused = false;

    public enum Direction {
        UP,
        BOTTOM,
        LEFT,
        RIGHT
    }

    private static Direction direction = Direction.UP;
    private int tailLength = 0;

    private final static int AUTO_SAVE_FRAME_COUNT = 30;
    private int remainingFrameCountForAutoSave = 0;

    private int manuelSaveCount=0;

    private Coordinates snakeHeadCoordinates = new Coordinates(10, 10);

    private PawnClass snakeHeadClass = new PawnClass(Pawn.SNAKE_HEAD);
    private PawnClass snakeBodyClass = new PawnClass(Pawn.SNAKE_BODY);
    private PawnClass foodClass = new PawnClass(Pawn.FOOD);

    private ArrayList<Coordinates> snakeTail = new ArrayList<>();

    public Board(Design design) {

        this.design = design;
        addStartEntity();
        mapTask();
    }

    private void addStartEntity() {
        board.put(snakeHeadCoordinates, snakeHeadClass);

        for (int i = 0; i < 22; i++) {
            board.put(new Coordinates(0, i), new PawnClass(Pawn.BRICK));
            board.put(new Coordinates(21, i), new PawnClass(Pawn.BRICK));
            board.put(new Coordinates(i, 0), new PawnClass(Pawn.BRICK));
            board.put(new Coordinates(i, 21), new PawnClass(Pawn.BRICK));
        }

        addEat();
        displayAllImage();
    }

    private void checkMap() {
        removeAllImage();
        moveSnake();
        displayAllImage();
    }

    private void removeAllImage() {
        for (Map.Entry<Coordinates, PawnClass> entry : board.entrySet()) {
            design.removePawn(entry.getKey());
        }
    }

    private void displayAllImage() {
        for (Map.Entry<Coordinates, PawnClass> entry : board.entrySet()) {
            design.addPawn(entry.getKey(), entry.getValue());
        }
    }

    private void moveSnake() {
        switch (direction) {
            case UP:
                moveSnakeHead(new Coordinates(snakeHeadCoordinates.getX(), snakeHeadCoordinates.getY() - 1));
                break;
            case BOTTOM:
                moveSnakeHead(new Coordinates(snakeHeadCoordinates.getX(), snakeHeadCoordinates.getY() + 1));
                break;
            case LEFT:
                moveSnakeHead(new Coordinates(snakeHeadCoordinates.getX() - 1, snakeHeadCoordinates.getY()));
                break;
            case RIGHT:
                moveSnakeHead(new Coordinates(snakeHeadCoordinates.getX() + 1, snakeHeadCoordinates.getY()));
                break;
        }
    }

    private void moveSnakeHead(Coordinates coordinates) {
        if (coordinates.isValid()) {
            if (isFieldNotNull(coordinates)) {
                if (getPawn(coordinates).getPawn().isFood()) {
                    board.remove(snakeHeadCoordinates);
                    board.put(snakeHeadCoordinates, snakeBodyClass);
                    board.put(coordinates, snakeHeadClass);
                    snakeTail.add(snakeHeadCoordinates);
                    tailLength++;

                    snakeHeadCoordinates = coordinates;

                    addEat();
                } else {
                    isEndGame = true;

                    new EndGame("End game...\n" + "You have " + tailLength + " points. \n" + "Maybe try again? :)");
                }
            } else {
                board.remove(snakeHeadCoordinates);
                board.put(coordinates, snakeHeadClass);

                snakeHeadCoordinates = coordinates;

                if (tailLength > 0) {
                    moveSnakeBody();
                }
            }
        }
    }

    private void moveSnakeBody() {
        switch (direction) {
            case UP:
                moveSnakeBodyHandler(new Coordinates(snakeHeadCoordinates.getX(), snakeHeadCoordinates.getY() + 1));
                break;
            case BOTTOM:
                moveSnakeBodyHandler(new Coordinates(snakeHeadCoordinates.getX(), snakeHeadCoordinates.getY() - 1));
                break;
            case LEFT:
                moveSnakeBodyHandler(new Coordinates(snakeHeadCoordinates.getX() + 1, snakeHeadCoordinates.getY()));
                break;
            case RIGHT:
                moveSnakeBodyHandler(new Coordinates(snakeHeadCoordinates.getX() - 1, snakeHeadCoordinates.getY()));
                break;
        }
    }

    private void moveSnakeBodyHandler(Coordinates coordinates) {
        if (tailLength == snakeTail.size()) {
            Coordinates endTail = snakeTail.get(0);
            board.remove(endTail);
            snakeTail.remove(endTail);
        }

        board.put(coordinates, snakeBodyClass);
        snakeTail.add(coordinates);
    }

    private void addEat() {
        Coordinates foodCoordinates;

        do {
            foodCoordinates = new Coordinates(random.nextInt(21), random.nextInt(21));
        } while (isFieldNotNull(foodCoordinates));

        board.put(foodCoordinates, foodClass);
    }

    private void mapTask() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(140);
                    if(++remainingFrameCountForAutoSave>=AUTO_SAVE_FRAME_COUNT){

                        remainingFrameCountForAutoSave = 0;
                        BoardInfo BI = new BoardInfo(board, direction, tailLength, snakeHeadCoordinates, snakeHeadClass, snakeBodyClass, foodClass, snakeTail);

                        new SaveGame(BI, "AutoSave").autoSave();                            
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                return null;
            }
        };

        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                if (!isEndGame && !isPaused) {
                    checkMap();
                    mapTask();
                }
            }
        });

        new Thread(task).start();
    }

    public void readKeyboard(KeyEvent event) {
        switch(event.getCode()) {
            case W: changeDirection(Direction.UP); break;
            case S: changeDirection(Direction.BOTTOM); break;
            case A: changeDirection(Direction.LEFT); break;
            case D: changeDirection(Direction.RIGHT); break;
            case UP: changeDirection(Direction.UP); break;
            case DOWN: changeDirection(Direction.BOTTOM); break;
            case LEFT: changeDirection(Direction.LEFT); break;
            case RIGHT: changeDirection(Direction.RIGHT); break;
            
            case T:
            
                isPaused = true;

                saveTheGame();

                break;
            
            case L:
                
                removeAllImage();

                isPaused = true;

                loadGame();

                break;

            default:
                    break;
        }
    } 

    private void changeDirection(Direction newDirection) {
        if(newDirection == Direction.UP && direction != Direction.BOTTOM) {
            direction = Direction.UP;
        } else if(newDirection == Direction.BOTTOM && direction != Direction.UP) {
            direction = Direction.BOTTOM;
        } else if(newDirection == Direction.LEFT && direction != Direction.RIGHT) {
            direction = Direction.LEFT;
        } else if(newDirection == Direction.RIGHT && direction != Direction.LEFT) {
            direction = Direction.RIGHT;
        }
    }

    private boolean isFieldNotNull(Coordinates coordinates) {
        return getPawn(coordinates) != null;
    }

    private PawnClass getPawn(Coordinates coordinates) {
        return board.get(coordinates);
    }

    public static Direction getDirection() {
        return direction;
    }

    public boolean isPaused(){

        return isPaused;
    }

    public void setIsPaused(boolean isPaused){

        this.isPaused = isPaused;
    }

    public void resume(){

        mapTask();    
    }

    public void saveTheGame(){

        BoardInfo BI = new BoardInfo(board, direction, tailLength, snakeHeadCoordinates, snakeHeadClass, snakeBodyClass, foodClass, snakeTail);

        new SaveGame(BI, "SaveGame" + ++manuelSaveCount).startSave(this);
    }

    public void loadGame(){

        new LoadGame(this).startLoad();
    }

    public void setParameters(HashMap<Coordinates, PawnClass> board, Direction directionIN, int tailLength, Coordinates snakeHeadCoordinates, PawnClass snakeHeadClass, PawnClass snakeBodyClass, PawnClass foodClass, ArrayList<Coordinates> snakeTail){

        this.board = board;
        direction = directionIN;
        this.tailLength = tailLength;
        this.snakeHeadCoordinates = snakeHeadCoordinates;
        this.snakeHeadClass = snakeHeadClass;
        this.snakeBodyClass = snakeBodyClass;
        this.foodClass = foodClass;
        this.snakeTail = snakeTail;
    }

    public void setManualSaveCount(int manuelSaveCount){

        this.manuelSaveCount = manuelSaveCount;
    }

    public int getManualSaveCount(){

        return manuelSaveCount;
    }
}
