package pl.nogacz.snake.board;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import pl.nogacz.snake.application.Design;
import pl.nogacz.snake.application.EndGame;
import pl.nogacz.snake.pawn.Pawn;
import pl.nogacz.snake.pawn.PawnClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author Dawid Nogacz on 19.05.2019
 */
public class Board {
    private HashMap<Coordinates, PawnClass> board = new HashMap<>();
    private Design design;
    private Random random = new Random();

    private boolean isEndGame = false;

    private static int direction = 1; // 1 - UP || 2 - BOTTOM || 3 - LEFT || 4 - RIGHT
    private int tailLength = 0;

    private final static int ROTTEN_FOOD_MAX_FRAME_COUNT = 20;
    private int currentRottenFoodAgeAsFrameCount = 0;

    private Coordinates snakeHeadCoordinates = new Coordinates(10, 10);
    private Coordinates rottenFoodCoordinates = null;

    private PawnClass snakeHeadClass = new PawnClass(Pawn.SNAKE_HEAD);
    private PawnClass snakeBodyClass = new PawnClass(Pawn.SNAKE_BODY);
    private PawnClass freshFoodClass = new PawnClass(Pawn.FRESHFOOD);
    private PawnClass rottenFoodClass = new PawnClass(Pawn.ROTTENFOOD);


    private ArrayList<Coordinates> snakeTail = new ArrayList<>();

    public Board(Design design) {
        this.design = design;

        addStartEntity();
        mapTask();
    }

    private void addStartEntity() {
        board.put(snakeHeadCoordinates, snakeHeadClass);

        for(int i = 0; i < 22; i++) {
            board.put(new Coordinates(0, i), new PawnClass(Pawn.BRICK));
            board.put(new Coordinates(21, i), new PawnClass(Pawn.BRICK));
            board.put(new Coordinates(i, 0), new PawnClass(Pawn.BRICK));
            board.put(new Coordinates(i, 21), new PawnClass(Pawn.BRICK));
        }

        addFreshFood(); 
        displayAllImage();
    }

    private void checkMap() {
        removeAllImage();
        moveSnake();
        displayAllImage();
    }

    private void removeAllImage() {
        for(Map.Entry<Coordinates, PawnClass> entry : board.entrySet()) {
            design.removePawn(entry.getKey());
        }
    }

    private void displayAllImage() {
        for(Map.Entry<Coordinates, PawnClass> entry : board.entrySet()) {
            design.addPawn(entry.getKey(), entry.getValue());
        }
    }

    private void moveSnake() {
        switch(direction) {
            case 1: moveSnakeHead(new Coordinates(snakeHeadCoordinates.getX(), snakeHeadCoordinates.getY() - 1)); break;
            case 2: moveSnakeHead(new Coordinates(snakeHeadCoordinates.getX(), snakeHeadCoordinates.getY() + 1)); break;
            case 3: moveSnakeHead(new Coordinates(snakeHeadCoordinates.getX() - 1, snakeHeadCoordinates.getY())); break;
            case 4: moveSnakeHead(new Coordinates(snakeHeadCoordinates.getX() + 1, snakeHeadCoordinates.getY())); break;
        }
    }

    private void moveSnakeHead(Coordinates coordinates) {
        if(coordinates.isValid()) {
            addRottenFoodIfNotExistAndCount(); 
            if(isFieldNotNull(coordinates)) {
                if(getPawn(coordinates).getPawn().isFreshFood()) {
                    board.remove(snakeHeadCoordinates);
                    board.put(snakeHeadCoordinates, snakeBodyClass);
                    board.put(coordinates, snakeHeadClass);
                    snakeTail.add(snakeHeadCoordinates);
                    tailLength++;

                    snakeHeadCoordinates = coordinates;

                    addFreshFood();
                } else if(getPawn(coordinates).getPawn().isRottenFood()) {
                    if(tailLength == 0) {
                        isEndGame = true;

                        new EndGame("End game...\n" +
                            "You have " + tailLength + " points. \n" +
                            "Maybe try again? :)");
                    }
                                       
                    board.remove(snakeHeadCoordinates);
                    board.put(snakeHeadCoordinates, snakeBodyClass);
                    board.put(coordinates, snakeHeadClass);
                    snakeTail.add(snakeHeadCoordinates);
                    board.remove(snakeTail.get(0));
                    snakeTail.remove(snakeTail.get(0)); 
                    board.remove(snakeTail.get(0));                 
                    snakeTail.remove(snakeTail.get(0));
                    tailLength--;

                    snakeHeadCoordinates = coordinates;
                    currentRottenFoodAgeAsFrameCount = 0;
                    rottenFoodCoordinates = null; 
                } else {
                    isEndGame = true;
                    new EndGame("End game...\n" +
                            "You have " + tailLength + " points. \n" +
                            "Maybe try again? :)");
                }
            } else {
                board.remove(snakeHeadCoordinates);
                board.put(coordinates, snakeHeadClass);

                snakeHeadCoordinates = coordinates;

                if(tailLength > 0) {
                    moveSnakeBody();
                }
            }
        }
    }

    private void addRottenFoodIfNotExistAndCount() {
        if(!isRottenFoodExist()) {
            int randomnum = random.nextInt(10);
            if(randomnum == 1) { //percentage of addRottenFood is %10
                addRottenFood();
            } 
        } else {   
            if(currentRottenFoodAgeAsFrameCount < ROTTEN_FOOD_MAX_FRAME_COUNT) {
                currentRottenFoodAgeAsFrameCount++;
            }     
            if(currentRottenFoodAgeAsFrameCount == ROTTEN_FOOD_MAX_FRAME_COUNT) { 
                currentRottenFoodAgeAsFrameCount = 0;
                board.remove(rottenFoodCoordinates);
                rottenFoodCoordinates = null;
            }
        }
    }

    private void moveSnakeBody() {
        switch(direction) {
            case 1: moveSnakeBodyHandler(new Coordinates(snakeHeadCoordinates.getX(), snakeHeadCoordinates.getY() + 1)); break;
            case 2: moveSnakeBodyHandler(new Coordinates(snakeHeadCoordinates.getX(), snakeHeadCoordinates.getY() - 1)); break;
            case 3: moveSnakeBodyHandler(new Coordinates(snakeHeadCoordinates.getX() + 1, snakeHeadCoordinates.getY())); break;
            case 4: moveSnakeBodyHandler(new Coordinates(snakeHeadCoordinates.getX() - 1, snakeHeadCoordinates.getY())); break;
        }
    }

    private void moveSnakeBodyHandler(Coordinates coordinates) {
        if(tailLength == snakeTail.size()) {
            Coordinates endTail = snakeTail.get(0);
            board.remove(endTail);
            snakeTail.remove(endTail);
        }

        board.put(coordinates, snakeBodyClass);
        snakeTail.add(coordinates);
    }

    private void addEat(boolean isFresh) {
        Coordinates foodCoordinates;

        do {
            foodCoordinates = new Coordinates(random.nextInt(21), random.nextInt(21));
        } while(isFieldNotNull(foodCoordinates));

        if(isFresh) {
            board.put(foodCoordinates, freshFoodClass);
        }    
        else {    
            board.put(foodCoordinates, rottenFoodClass);  
            rottenFoodCoordinates = foodCoordinates;
        }      
    }
    private void addFreshFood() {
        addEat(true);
    }

    private void addRottenFood() {
        addEat(false);
    }

    private void mapTask() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(140);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                return null;
            }
        };

        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                if(!isEndGame) {
                    checkMap();
                    mapTask();
                }
            }
        });

        new Thread(task).start();
    }

    public void readKeyboard(KeyEvent event) {
        switch(event.getCode()) {
            case W: changeDirection(1); break;
            case S: changeDirection(2); break;
            case A: changeDirection(3); break;
            case D: changeDirection(4); break;

            case UP: changeDirection(1); break;
            case DOWN: changeDirection(2); break;
            case LEFT: changeDirection(3); break;
            case RIGHT: changeDirection(4); break;
        }
    }

    private void changeDirection(int newDirection) {
        if(newDirection == 1 && direction != 2) {
            direction = 1;
        } else if(newDirection == 2 && direction != 1) {
            direction = 2;
        } else if(newDirection == 3 && direction != 4) {
            direction = 3;
        } else if(newDirection == 4 && direction != 3) {
            direction = 4;
        }
    }

    private boolean isFieldNotNull(Coordinates coordinates) {
        return getPawn(coordinates) != null;
    }

    private PawnClass getPawn(Coordinates coordinates) {
        return board.get(coordinates);
    }

    public static int getDirection() {
        return direction;
    }

    public boolean isRottenFoodExist() {
        return rottenFoodCoordinates != null;
    }

    public void setRottenFoodCoordinates(Coordinates rottenFoodCoordinates) {
        this.rottenFoodCoordinates = rottenFoodCoordinates;
    }

    public int getROTTEN_FOOD_MAX_FRAME_COUNT() {
        return ROTTEN_FOOD_MAX_FRAME_COUNT;
    }

    public int getCurrentRottenFoodAgeAsFrameCount() {
        return currentRottenFoodAgeAsFrameCount;
    }

    public void setCurrentRottenFoodAgeAsFrameCount(int disappearanceCounter) {
        this.currentRottenFoodAgeAsFrameCount = disappearanceCounter;
    }

    public void setBoard(HashMap<Coordinates, PawnClass> board) {
        this.board = board;
    }

    public void setSnakeTail(ArrayList<Coordinates> snakeTail) {
        this.snakeTail = snakeTail;
    }

    public int getTailLength() {
        return tailLength;
    }

    public void setTailLength(int tailLength) {
        this.tailLength = tailLength;
    }
}
