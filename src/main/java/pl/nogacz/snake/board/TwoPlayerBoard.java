package pl.nogacz.snake.board;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import pl.nogacz.snake.application.TwoPlayerDesign;
import pl.nogacz.snake.application.EndGame;
import pl.nogacz.snake.pawn.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author Dawid Nogacz on 19.05.2019
 */
public class TwoPlayerBoard {
    private HashMap<Coordinates2P, PawnClass> board = new HashMap<>();
    private TwoPlayerDesign tp_design;
    private Random random = new Random();
    private Random random2 = new Random(); // random for 2nd player

    private boolean isEndGame = false;
    private boolean isEndGame2 = false; // for 2nd player

    private static int direction = 1; // 1 - UP || 2 - BOTTOM || 3 - LEFT || 4 - RIGHT
    private static int direction2 = 1; // 1 - UP || 2 - BOTTOM || 3 - LEFT || 4 - RIGHT **** for 2nd player
    private int tailLength = 0;
    private int tailLength2 = 0;

    private Coordinates2P snakeHeadCoordinates = new Coordinates2P(10, 18);
    private Coordinates2P snakeHeadCoordinates2 = new Coordinates2P(29, 18); // for 2nd player

    private PawnClass snakeHeadClass = new PawnClass(Pawn.SNAKE_HEAD);
    private PawnClass snakeBodyClass = new PawnClass(Pawn.SNAKE_BODY);
    private PawnClass foodClass = new PawnClass(Pawn.FOOD);

    private PawnClass snakeHeadClass2 = new PawnClass(Pawn.SNAKE_HEAD2);
    private PawnClass snakeBodyClass2 = new PawnClass(Pawn.SNAKE_BODY2);
    private PawnClass foodClass2 = new PawnClass(Pawn.FOOD2);

    private ArrayList<Coordinates> snakeTail = new ArrayList<>();
    private ArrayList<Coordinates> snakeTail2 = new ArrayList<>(); // for second player

    public char[] usr1; // user1 control keys
    public char[] usr2; // user2 control keys


    public TwoPlayerBoard(TwoPlayerDesign tp_design, boolean isGameStarted, char[] usr1, char[] usr2) {
        this.tp_design = tp_design;

        this.usr1 = usr1;
        this.usr2 = usr2;

        addStartEntity();
        if(isGameStarted)
            mapTask();
    }

    private void addStartEntity() {
        board.put(snakeHeadCoordinates, snakeHeadClass);
        board.put(snakeHeadCoordinates2, snakeHeadClass2);

        for(int i = 0; i < 44; i++) {
            board.put(new Coordinates2P(0, i), new PawnClass(Pawn.BRICK));
            board.put(new Coordinates2P(21, i), new PawnClass(Pawn.BRICK));
            board.put(new Coordinates2P(i, 0), new PawnClass(Pawn.BRICK));
            board.put(new Coordinates2P(i, 21), new PawnClass(Pawn.BRICK));
            board.put(new Coordinates2P(43, i), new PawnClass(Pawn.BRICK));
        }

        addEat();
        addEat2();
        displayAllImage();
    }

    private void checkMap() {
        removeAllImage();
        if(!isEndGame )
            moveSnake();
        if(!isEndGame2)
            moveSnake2();
        displayAllImage();
    }

    private void removeAllImage() {
        for(Map.Entry<Coordinates2P, ? super PawnClass> entry : board.entrySet()) {
            tp_design.removePawn(entry.getKey());
        }

    }

    private void displayAllImage() {
        for(Map.Entry<Coordinates2P,PawnClass> entry : board.entrySet()) {
                tp_design.addPawn(entry.getKey(), entry.getValue());
        }
    }

    private void moveSnake() {
        switch(direction) {
            case 1: moveSnakeHead(new Coordinates2P(snakeHeadCoordinates.getX(), snakeHeadCoordinates.getY() - 1)); break;
            case 2: moveSnakeHead(new Coordinates2P(snakeHeadCoordinates.getX(), snakeHeadCoordinates.getY() + 1)); break;
            case 3: moveSnakeHead(new Coordinates2P(snakeHeadCoordinates.getX() - 1, snakeHeadCoordinates.getY())); break;
            case 4: moveSnakeHead(new Coordinates2P(snakeHeadCoordinates.getX() + 1, snakeHeadCoordinates.getY())); break;
        }
    }
    private void moveSnake2(){
        switch(direction2) {
            case 1: moveSnakeHead2(new Coordinates2P(snakeHeadCoordinates2.getX(), snakeHeadCoordinates2.getY() - 1)); break;
            case 2: moveSnakeHead2(new Coordinates2P(snakeHeadCoordinates2.getX(), snakeHeadCoordinates2.getY() + 1)); break;
            case 3: moveSnakeHead2(new Coordinates2P(snakeHeadCoordinates2.getX() - 1, snakeHeadCoordinates2.getY())); break;
            case 4: moveSnakeHead2(new Coordinates2P(snakeHeadCoordinates2.getX() + 1, snakeHeadCoordinates2.getY())); break;
        }
    }
    private void moveSnakeHead(Coordinates2P coordinates) {
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
                     isEndGame = true;

                    if(isEndGame && isEndGame2) {
                        if(tailLength > tailLength2 ) {
                            new EndGame(
                                    "   >>>>  Player 1 wins  <<<<\n\n" +
                                            "Player 1 collected " + tailLength + " points. \n" +
                                            "Player 2 collected " + tailLength2 + " points. \n" +
                                            "Thank you for playing :)");
                        }
                        else if(tailLength2 > tailLength ) {
                            new EndGame(
                                    "   >>>>  Player 2 wins  <<<<\n\n" +
                                            "Player 1 collected " + tailLength + " points. \n" +
                                            "Player 2 collected " + tailLength2 + " points. \n" +
                                            "Thank you for playing :)");
                        }
                        else{
                            new EndGame("   >>>>  Game is draw. No winner  <<<<\n\n" +
                                    "Player 1 collected " + tailLength + " points. \n" +
                                    "Player 2 collected " + tailLength2 + " points. \n" +
                                    "Thank you for playing :)");
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
        }
    }

    private void moveSnakeHead2(Coordinates2P coordinates) {
        if(coordinates.isValid()) {
            if(isFieldNotNull(coordinates)) {
                if(getPawn(coordinates).getPawn().isFood2()) {
                    board.remove(snakeHeadCoordinates2);
                    board.put(snakeHeadCoordinates2, snakeBodyClass2);
                    board.put(coordinates, snakeHeadClass2);
                    snakeTail2.add(snakeHeadCoordinates2);
                    tailLength2++;

                    snakeHeadCoordinates2 = coordinates;

                    addEat2();
                } else {
                     isEndGame2 = true;
                        if(isEndGame && isEndGame2) {
                            if(tailLength > tailLength2 ) {
                                new EndGame(
                                "   >>>>  Player 1 wins  <<<<\n\n" +
                                "Player 1 collected " + tailLength + " points. \n" +
                                "Player 2 collected " + tailLength2 + " points. \n" +
                                "Thank you for playing :)");
                            }
                            else if(tailLength2 > tailLength ) {
                                new EndGame(
                                "   >>>>  Player 2 wins  <<<<\n\n" +
                                "Player 1 collected " + tailLength + " points. \n" +
                                "Player 2 collected " + tailLength2 + " points. \n" +
                                "Thank you for playing :)");
                            }
                            else{
                                new EndGame("   >>>>  Game is draw. No winner  <<<<\n\n" +
                                        "Player 1 collected " + tailLength + " points. \n" +
                                        "Player 2 collected " + tailLength2 + " points. \n" +
                                        "Thank you for playing :)");
                            }

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
            case 1: moveSnakeBodyHandler(new Coordinates2P(snakeHeadCoordinates.getX(), snakeHeadCoordinates.getY() + 1)); break;
            case 2: moveSnakeBodyHandler(new Coordinates2P(snakeHeadCoordinates.getX(), snakeHeadCoordinates.getY() - 1)); break;
            case 3: moveSnakeBodyHandler(new Coordinates2P(snakeHeadCoordinates.getX() + 1, snakeHeadCoordinates.getY())); break;
            case 4: moveSnakeBodyHandler(new Coordinates2P(snakeHeadCoordinates.getX() - 1, snakeHeadCoordinates.getY())); break;
        }
    }

    private void moveSnakeBody2() {
        switch(direction2) {
            case 1: moveSnakeBodyHandler2(new Coordinates2P(snakeHeadCoordinates2.getX(), snakeHeadCoordinates2.getY() + 1)); break;
            case 2: moveSnakeBodyHandler2(new Coordinates2P(snakeHeadCoordinates2.getX(), snakeHeadCoordinates2.getY() - 1)); break;
            case 3: moveSnakeBodyHandler2(new Coordinates2P(snakeHeadCoordinates2.getX() + 1, snakeHeadCoordinates2.getY())); break;
            case 4: moveSnakeBodyHandler2(new Coordinates2P(snakeHeadCoordinates2.getX() - 1, snakeHeadCoordinates2.getY())); break;
        }
    }

    private void moveSnakeBodyHandler(Coordinates2P coordinates) {
        if(tailLength == snakeTail.size()) {
            Coordinates endTail = snakeTail.get(0);
            board.remove(endTail);
            snakeTail.remove(endTail);
        }

        board.put(coordinates, snakeBodyClass);
        snakeTail.add(coordinates);
    }

    private void moveSnakeBodyHandler2(Coordinates2P coordinates) {
        if(tailLength2 == snakeTail2.size()) {
            Coordinates endTail = snakeTail2.get(0);
            board.remove(endTail);
            snakeTail2.remove(endTail);
        }

        board.put(coordinates, snakeBodyClass2);
        snakeTail2.add(coordinates);
    }


    private void addEat() {
        Coordinates2P foodCoordinates;

        do {
            foodCoordinates = new Coordinates2P(random.nextInt(21), random.nextInt(21));
        } while(isFieldNotNull(foodCoordinates));

        board.put(foodCoordinates, foodClass);
    }

    private void addEat2() {
        Coordinates2P foodCoordinates;

        do {
            foodCoordinates = new Coordinates2P(22+ random2.nextInt(20) ,1+ random2.nextInt(19));
        } while(isFieldNotNull(foodCoordinates));

        board.put(foodCoordinates, foodClass2);
    }

    private void mapTask() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(200);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                return null;
            }
        };

        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                if(!isEndGame || !isEndGame2) {
                    checkMap();
                    mapTask();
                }
            }
        });

        new Thread(task).start();
    }

    public void readKeyboard(KeyEvent event) {
        char incoming = event.getCode().toString().charAt(0);

        if(incoming == usr1[0])
            changeDirection1(1);
        else if(incoming == usr1[1])
            changeDirection1(2);
        else if(incoming == usr1[2])
            changeDirection1(3);
        else if(incoming == usr1[3])
            changeDirection1(4);
        else if(incoming == usr2[0])
            changeDirection2(1);
        else if(incoming == usr2[1])
            changeDirection2(2);
        else if(incoming == usr2[2])
            changeDirection2(3);
        else if(incoming == usr2[3])
            changeDirection2(4);

//        switch(event.getCode()) {
//            case W: changeDirection1(1); break;
//            case S: changeDirection1(2); break;
//            case A: changeDirection1(3); break;
//            case D: changeDirection1(4); break;
//
//            case UP: changeDirection2(1); break;
//            case DOWN: changeDirection2(2); break;
//            case LEFT: changeDirection2(3); break;
//            case RIGHT: changeDirection2(4); break;
//        }
    }


    private void changeDirection1(int newDirection) {
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
    private void changeDirection2(int newDirection) {
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

    private boolean isFieldNotNull(Coordinates2P coordinates) {
        return getPawn(coordinates) != null;
    }

    private PawnClass getPawn(Coordinates2P coordinates) {
        return board.get(coordinates);
    }

    public static int getDirection() {
        return direction;
    }
    public static int getDirection2() {
        return direction2;
    }
}
