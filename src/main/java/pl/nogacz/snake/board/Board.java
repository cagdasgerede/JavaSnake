package pl.nogacz.snake.board;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import jdk.internal.util.jar.InvalidJarIndexError;
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

    private boolean isEndGame = false, thereIsFood = false, isThereInvicibilityItem = false;

    private static int direction = 1; // 1 - UP || 2 - BOTTOM || 3 - LEFT || 4 - RIGHT
    private int tailLength = 0;
    private int frameCounterOfInvisibility = -1, invicibilityItemFrameCounter = -1;
    private Coordinates liveInvicibilityItemCoordinates;

    private final int INVINCIBILITY_ITEM_LIFESPAWN_FRAME_COUNT = 100;
    private final int MAX_FRAME_OF_INVINCIBILITY = 150;

    private final int X_TO_APPEAR_ON_TOP = 1;
    private final int X_TO_APPEAR_ON_BOTTOM = 20;
    private final int Y_TO_APPEAR_ON_LEFT = 0;
    private final int Y_TO_APPEAR_ON_RIGHT = 20;

    private Coordinates snakeHeadCoordinates = new Coordinates(10, 10);

    private PawnClass snakeHeadClass = new PawnClass(Pawn.SNAKE_HEAD);
    private PawnClass snakeBodyClass = new PawnClass(Pawn.SNAKE_BODY);
    private PawnClass foodClass = new PawnClass(Pawn.FOOD);
    private PawnClass invicibilityItemClass = new PawnClass(Pawn.INVINCIBILITY_ITEM);

    private ArrayList<Coordinates> snakeTail = new ArrayList<>();

    public Board(Design design) {
        this.design = design;

        addStartEntity();
        mapTask();
    }

    public boolean isThereInvicibilityItem() {
        return isThereInvicibilityItem;
    }

    private void addStartEntity() {
        board.put(snakeHeadCoordinates, snakeHeadClass);

        for(int i = 0; i < 22; i++) {
            board.put(new Coordinates(0, i), new PawnClass(Pawn.BRICK));
            board.put(new Coordinates(21, i), new PawnClass(Pawn.BRICK));
            board.put(new Coordinates(i, 0), new PawnClass(Pawn.BRICK));
            board.put(new Coordinates(i, 21), new PawnClass(Pawn.BRICK));
        }

        addEatOrInvincibilityItem();
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
            if(isFieldNotNull(coordinates)) {
                if(getPawn(coordinates).getPawn().isFood()) {
                    board.remove(snakeHeadCoordinates);
                    board.put(snakeHeadCoordinates, snakeBodyClass);
                    board.put(coordinates, snakeHeadClass);
                    snakeTail.add(snakeHeadCoordinates);
                    tailLength++;

                    snakeHeadCoordinates = coordinates;
                    thereIsFood = false;

                    addEatOrInvincibilityItem();
                }
                else if(getPawn(coordinates).getPawn().isInvincibilityItem()) {

                    board.remove(snakeHeadCoordinates);
                    board.put(coordinates, snakeHeadClass);
                    
                    snakeHeadCoordinates = coordinates;
                    isThereInvicibilityItem = false;

                    if(tailLength > 0) {
                        moveSnakeBody();
                    }
                    
                    activateInvincibility();
                    CountInvincibilityFrame();
                    
                    addEatOrInvincibilityItem();

                } else {
                    if(!isInvincible()) {
                        isEndGame = true;

                        new EndGame("End game...\n" +
                                "You have " + tailLength + " points. \n" +
                                "Maybe try again? :)");
                    }
                    else {
                        if( getPawn(coordinates).getPawn() == Pawn.BRICK ) {
                            int newX = coordinates.getX(), newY = coordinates.getY();
                            if(coordinates.getX() <= 0 ) {
                                newX = X_TO_APPEAR_ON_BOTTOM;
                                newY = coordinates.getY();
                            }
                            else if(coordinates.getX() >= 21) {
                                newX = X_TO_APPEAR_ON_TOP;
                            }
                            else if(coordinates.getY() <= 0) {
                                newY = Y_TO_APPEAR_ON_RIGHT;
                            }
                            else{
                                newY = Y_TO_APPEAR_ON_LEFT;
                            }

                            board.remove(snakeHeadCoordinates);

                            Coordinates newCoordinates = new Coordinates(newX,newY);

                            board.put(newCoordinates, snakeHeadClass);
                            
                            snakeHeadCoordinates = newCoordinates;

                            if(tailLength > 0) {
                                moveSnakeBody();
                            }
                        }
                        else if(getPawn(coordinates).getPawn() == Pawn.SNAKE_BODY) {
                            board.remove(snakeHeadCoordinates);
                            board.put(coordinates, snakeBodyClass);
                            board.put(coordinates, snakeHeadClass);

                            snakeHeadCoordinates = coordinates;

                            if(tailLength > 0) {
                                moveSnakeBody();
                            }
                        }
                    }
                }
            } else {
                board.remove(snakeHeadCoordinates);
                board.put(coordinates, snakeHeadClass);

                snakeHeadCoordinates = coordinates;

                if(tailLength > 0) {
                    moveSnakeBody();
                }
            }
            if(invicibilityItemFrameCounter != -1 && !thereIsFood ) {
                if(invicibilityItemFrameCounter > INVINCIBILITY_ITEM_LIFESPAWN_FRAME_COUNT) {
                    dissappearInvincibilityItem();
                }
                else
                    invicibilityItemFrameCounter++;
            }
            if(isInvincible()) {
                CountInvincibilityFrame();
            }
            else
                deactivateInvincibility();

            addBricksWhileInvincible();
        }
    }

    //Snake removes bricks while going through them when invincible.
    //Need to add them to board again.
    private void addBricksWhileInvincible() {

        for(int i = 0; i < 22; i++) {

            board.put(new Coordinates(0, i), new PawnClass(Pawn.BRICK));
            board.put(new Coordinates(21, i), new PawnClass(Pawn.BRICK));
            board.put(new Coordinates(i, 0), new PawnClass(Pawn.BRICK));
            board.put(new Coordinates(i, 21), new PawnClass(Pawn.BRICK));

        }

        displayAllImage();
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

    private void addEatOrInvincibilityItem() {
        if(shouldAnInvincibleItemAppearOnBoard()) {
            frameCounterOfInvisibility = -1;
            addInvicibilityItem();
        }
        else
            addEat();
    }

    private void addEat() {
        Coordinates foodCoordinates;

        do {
            foodCoordinates = new Coordinates(random.nextInt(21), random.nextInt(21));
        } while(isFieldNotNull(foodCoordinates));

        board.put(foodCoordinates, foodClass);
        thereIsFood = true;
    }

    public void addInvicibilityItem() {
        Coordinates invicibilityItemCoordinates;
        invicibilityItemFrameCounter = 0;

        do {
            invicibilityItemCoordinates = new Coordinates(random.nextInt(21), random.nextInt(21));
        } while(isFieldNotNull(invicibilityItemCoordinates));

        isThereInvicibilityItem = true;

        liveInvicibilityItemCoordinates = invicibilityItemCoordinates;

        board.put(invicibilityItemCoordinates, invicibilityItemClass);
    }

    public void dissappearInvincibilityItem() {
        board.remove(liveInvicibilityItemCoordinates);
                    
        liveInvicibilityItemCoordinates = null;
        
        isThereInvicibilityItem = false;   
        invicibilityItemFrameCounter = -1;    
        addEatOrInvincibilityItem();
    }

    private boolean shouldAnInvincibleItemAppearOnBoard() {
        return (random.nextInt(2) % 2 == 0) && !isThereInvicibilityItem && (!isInvincible());
    }

    public boolean CountInvincibilityFrame() {
        frameCounterOfInvisibility++;
        return frameCounterOfInvisibility > MAX_FRAME_OF_INVINCIBILITY;
    }

    public boolean isInvincible() {
        return (frameCounterOfInvisibility != -1 && !CountInvincibilityFrame());
    }

    public void activateInvincibility() {
        design.setInvincibility(true);
    }

    public void deactivateInvincibility() {
        design.setInvincibility(false);
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
}
