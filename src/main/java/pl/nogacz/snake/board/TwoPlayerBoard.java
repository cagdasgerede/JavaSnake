package pl.nogacz.snake.board;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import pl.nogacz.snake.application.TwoPlayerDesign;
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
public class TwoPlayerBoard {
    private HashMap<Coordinates, PawnClass> board = new HashMap<>();
    private TwoPlayerDesign tp_design;
    private Random random = new Random();
    private Random random2 = new Random(); // random for 2nd player

    private boolean isEndGame = false;
    private boolean isEndGame2 = false; // for 2nd player

    private static int direction = 1; // 1 - UP || 2 - BOTTOM || 3 - LEFT || 4 - RIGHT
    private static int direction2 = 1; // 1 - UP || 2 - BOTTOM || 3 - LEFT || 4 - RIGHT **** for 2nd player
    private int tailLength = 0;
    private int tailLength2 = 0;

    private Coordinates snakeHeadCoordinates = new Coordinates(10, 20);
    private Coordinates snakeHeadCoordinates2 = new Coordinates(30, 20); // for 2nd player
    
    private PawnClass snakeHeadClass = new PawnClass(Pawn.SNAKE_HEAD);
    private PawnClass snakeBodyClass = new PawnClass(Pawn.SNAKE_BODY);
    private PawnClass foodClass = new PawnClass(Pawn.FOOD);

    private ArrayList<Coordinates> snakeTail = new ArrayList<>();
    private ArrayList<Coordinates> snakeTail2 = new ArrayList<>(); // for second player


    public TwoPlayerBoard(TwoPlayerDesign tp_design, boolean isGameStarted) {
        this.tp_design = tp_design;

        addStartEntity();
        if(isGameStarted)
            mapTask();
    }

    private void addStartEntity() {
        board.put(snakeHeadCoordinates, snakeHeadClass);
        board.put(snakeHeadCoordinates2, snakeHeadClass);

        for(int i = 0; i < 44; i++) {
            board.put(new Coordinates(0, i), new PawnClass(Pawn.BRICK));
            board.put(new Coordinates(21, i), new PawnClass(Pawn.BRICK));
            board.put(new Coordinates(i, 0), new PawnClass(Pawn.BRICK));
            board.put(new Coordinates(i, 21), new PawnClass(Pawn.BRICK));
            board.put(new Coordinates(43, i), new PawnClass(Pawn.BRICK));
        }

        addEat();
        addEat2();
        displayAllImage();
    }

    private void checkMap() {
        removeAllImage();
        moveSnake();
        displayAllImage();
    }

    private void removeAllImage() {
        for(Map.Entry<Coordinates, PawnClass> entry : board.entrySet()) {
            tp_design.removePawn(entry.getKey());
        }
    }

    private void displayAllImage() {
        for(Map.Entry<Coordinates, PawnClass> entry : board.entrySet()) {
            tp_design.addPawn(entry.getKey(), entry.getValue());
        }
    }

    private void moveSnake() {
        switch(direction) {
            case 1: moveSnakeHead(new Coordinates(snakeHeadCoordinates.getX(), snakeHeadCoordinates.getY() - 1)); break;
            case 2: moveSnakeHead(new Coordinates(snakeHeadCoordinates.getX(), snakeHeadCoordinates.getY() + 1)); break;
            case 3: moveSnakeHead(new Coordinates(snakeHeadCoordinates.getX() - 1, snakeHeadCoordinates.getY())); break;
            case 4: moveSnakeHead(new Coordinates(snakeHeadCoordinates.getX() + 1, snakeHeadCoordinates.getY())); break;
        }

        switch(direction2) {
            case 1: moveSnakeHead2(new Coordinates(snakeHeadCoordinates2.getX(), snakeHeadCoordinates2.getY() - 1)); break;
            case 2: moveSnakeHead2(new Coordinates(snakeHeadCoordinates2.getX(), snakeHeadCoordinates2.getY() + 1)); break;
            case 3: moveSnakeHead2(new Coordinates(snakeHeadCoordinates2.getX() - 1, snakeHeadCoordinates2.getY())); break;
            case 4: moveSnakeHead2(new Coordinates(snakeHeadCoordinates2.getX() + 1, snakeHeadCoordinates2.getY())); break;
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

                    addEat();
                } else {
                    // isEndGame = true;

                    // new EndGame("End game...\n" +
                    //         "You have " + tailLength + " points. \n" +
                    //         "Maybe try again? :)");
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

    private void moveSnakeHead2(Coordinates coordinates) {
        if(coordinates.isValid()) {
            if(isFieldNotNull(coordinates)) {
                if(getPawn(coordinates).getPawn().isFood()) {
                    board.remove(snakeHeadCoordinates2);
                    board.put(snakeHeadCoordinates2, snakeBodyClass);
                    board.put(coordinates, snakeHeadClass);
                    snakeTail.add(snakeHeadCoordinates2);
                    tailLength2++;

                    snakeHeadCoordinates2 = coordinates;

                    addEat2();
                } else {
                    // isEndGame = true;

                    // new EndGame("End game...\n" +
                    //         "You have " + tailLength2 + " points. \n" +
                    //         "Maybe try again? :)");
                }
            } else {
                board.remove(snakeHeadCoordinates2);
                board.put(coordinates, snakeHeadClass);

                snakeHeadCoordinates2 = coordinates;

                if(tailLength2 > 0) {
                    moveSnakeBody();
                }
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
        switch(direction2) {
            case 1: moveSnakeBodyHandler(new Coordinates(snakeHeadCoordinates2.getX(), snakeHeadCoordinates2.getY() + 1)); break;
            case 2: moveSnakeBodyHandler(new Coordinates(snakeHeadCoordinates2.getX(), snakeHeadCoordinates2.getY() - 1)); break;
            case 3: moveSnakeBodyHandler(new Coordinates(snakeHeadCoordinates2.getX() + 1, snakeHeadCoordinates2.getY())); break;
            case 4: moveSnakeBodyHandler(new Coordinates(snakeHeadCoordinates2.getX() - 1, snakeHeadCoordinates2.getY())); break;
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

        if(tailLength2 == snakeTail2.size()) {
            Coordinates endTail = snakeTail2.get(0);
            board.remove(endTail);
            snakeTail.remove(endTail);
        }

        board.put(coordinates, snakeBodyClass);
        snakeTail2.add(coordinates);
    }

    private void addEat() {
        Coordinates foodCoordinates;

        do {
            foodCoordinates = new Coordinates(random.nextInt(21), random.nextInt(21));
        } while(isFieldNotNull(foodCoordinates));

        board.put(foodCoordinates, foodClass);
    }
    
    private void addEat2() {
        Coordinates foodCoordinates;

        do {
            foodCoordinates = new Coordinates(random.nextInt(43), random.nextInt(43));
        } while(isFieldNotNull(foodCoordinates));

        board.put(foodCoordinates, foodClass);
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
            case W: changeDirection(1,direction); break;
            case S: changeDirection(2,direction); break;
            case A: changeDirection(3,direction); break;
            case D: changeDirection(4,direction); break;

            case UP: changeDirection(1,direction2); break;
            case DOWN: changeDirection(2,direction2); break;
            case LEFT: changeDirection(3,direction2); break;
            case RIGHT: changeDirection(4,direction2); break;
        }
    }

    private void changeDirection(int newDirection,int direction) {
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
