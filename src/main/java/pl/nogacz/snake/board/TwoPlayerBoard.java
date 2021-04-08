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

public class TwoPlayerBoard {
    private HashMap<Coordinates, PawnClass> board = new HashMap<>();
    private TwoPlayerDesign design;
    private Random random = new Random();

    private boolean isFirstPlayerDead = false;
    private boolean isSecondPlayerDead = false;

    private static int direction = 1; // 1 - UP || 2 - BOTTOM || 3 - LEFT || 4 - RIGHT
    private static int direction2 = 1; // 1 - UP || 2 - BOTTOM || 3 - LEFT || 4 - RIGHT

    private int tailLength = 0;
    private int tailLength2 = 0;

    private Coordinates snakeHeadCoordinates = new Coordinates(10, 10);
    private Coordinates snakeHeadCoordinates2 = new Coordinates(32, 10);

    private PawnClass snakeHeadClass = new PawnClass(Pawn.SNAKE_HEAD);
    private PawnClass snakeBodyClass = new PawnClass(Pawn.SNAKE_BODY);
    private PawnClass foodClass = new PawnClass(Pawn.FOOD);

    private PawnClass snakeHeadClass2 = new PawnClass(Pawn.SECOND_SNAKE_HEAD);
    private PawnClass snakeBodyClass2 = new PawnClass(Pawn.SNAKE_BODY);
    private PawnClass foodClass2 = new PawnClass(Pawn.FOOD);

    private ArrayList<Coordinates> snakeTail = new ArrayList<>();
    private ArrayList<Coordinates> snakeTail2 = new ArrayList<>();

    public TwoPlayerBoard(TwoPlayerDesign design) {
        this.design = design;

        addStartEntity();
        mapTask();
    }

    private void addStartEntity() {
        board.put(snakeHeadCoordinates, snakeHeadClass);
        board.put(snakeHeadCoordinates2, snakeHeadClass2);

        for(int i = 0; i < 22; i++) {
            board.put(new Coordinates(0, i), new PawnClass(Pawn.BRICK));
            board.put(new Coordinates(21, i), new PawnClass(Pawn.BRICK));
            board.put(new Coordinates(i, 0), new PawnClass(Pawn.BRICK));
            board.put(new Coordinates(i, 21), new PawnClass(Pawn.BRICK));
        }
        for(int i = 22; i < 44; i++) {
            board.put(new Coordinates(22, i-22), new PawnClass(Pawn.BRICK));
            board.put(new Coordinates(43, i-22), new PawnClass(Pawn.BRICK));
            board.put(new Coordinates(i, 0), new PawnClass(Pawn.BRICK));
            board.put(new Coordinates(i, 21), new PawnClass(Pawn.BRICK));
        }

        addEat();
        addEat2();
        displayAllImage();
    }

    private void checkMap() {
        removeAllImage();

        if(!isFirstPlayerDead)
        moveSnake();
        if(!isSecondPlayerDead)
        moveSnake2();

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

    private void moveSnake2() {
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
                    isFirstPlayerDead = true;
                    if(isFirstPlayerDead && isSecondPlayerDead){
                        new EndGame("End game...\n" +
                        "Player one have: " + tailLength + " points. \n" + 
                        "Player two have: " + tailLength2 + " points. \n" +
                        "Maybe try again? :)");
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
        }
    }

    private void moveSnakeHead2(Coordinates coordinates) {
        if(coordinates.isValidForSecond()) {
            if(isFieldNotNull(coordinates)) {
                if(getPawn(coordinates).getPawn().isFood()) {
                    board.remove(snakeHeadCoordinates2);
                    board.put(snakeHeadCoordinates2, snakeBodyClass2);
                    board.put(coordinates, snakeHeadClass2);
                    snakeTail2.add(snakeHeadCoordinates2);
                    tailLength2++;

                    snakeHeadCoordinates2 = coordinates;

                    addEat2();
                } else {
                    isSecondPlayerDead = true;
                    if(isFirstPlayerDead && isSecondPlayerDead){
                        new EndGame("End game...\n" +
                        "You have " + tailLength + " points. \n" +
                        "Player two have: " + tailLength2 + " points. \n" +
                        "Maybe try again? :)");
                    }
                }
            } else {
                board.remove(snakeHeadCoordinates2);
                board.put(coordinates, snakeHeadClass2);

                snakeHeadCoordinates2 = coordinates;

                if(tailLength2 > 0) {
                    moveSnakeBody2();
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
    }

    private void moveSnakeBody2() {
        switch(direction2) {
            case 1: moveSnakeBodyHandler2(new Coordinates(snakeHeadCoordinates2.getX(), snakeHeadCoordinates2.getY() + 1)); break;
            case 2: moveSnakeBodyHandler2(new Coordinates(snakeHeadCoordinates2.getX(), snakeHeadCoordinates2.getY() - 1)); break;
            case 3: moveSnakeBodyHandler2(new Coordinates(snakeHeadCoordinates2.getX() + 1, snakeHeadCoordinates2.getY())); break;
            case 4: moveSnakeBodyHandler2(new Coordinates(snakeHeadCoordinates2.getX() - 1, snakeHeadCoordinates2.getY())); break;
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

    private void moveSnakeBodyHandler2(Coordinates coordinates) {
        if(tailLength2 == snakeTail2.size()) {
            Coordinates endTail2 = snakeTail2.get(0);
            board.remove(endTail2);
            snakeTail2.remove(endTail2);
        }

        board.put(coordinates, snakeBodyClass2);
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
        Coordinates foodCoordinates2;

        do {
            foodCoordinates2 = new Coordinates(random.nextInt(21)+22, random.nextInt(21));
        } while(isFieldNotNull(foodCoordinates2));

        board.put(foodCoordinates2, foodClass2);
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
                if(!(isFirstPlayerDead && isSecondPlayerDead)) {
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

            case UP: changeDirection2(1); break;
            case DOWN: changeDirection2(2); break;
            case LEFT: changeDirection2(3); break;
            case RIGHT: changeDirection2(4); break;
        }
    }

    private void changeDirection(int newDirection) {
        if(!isFirstPlayerDead){
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
    }

    private void changeDirection2(int newDirection) {
        if(!isSecondPlayerDead){
            if(newDirection == 1 && direction2 != 2) {
                direction2 = 1;
            } else if(newDirection == 2 && direction2 != 1) {
                direction2 = 2;
            } else if(newDirection == 3 && direction2 != 4) {
                direction2 = 3;
            } else if(newDirection == 4 && direction2 != 3) {
                direction2 = 4;
            }
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

    public static int getDirection2() {
        return direction2;
    }
}
