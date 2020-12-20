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

import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board {

    private HashMap<Coordinates, PawnClass> board = new HashMap<>();
    private Design design;
    private Random random = new Random();

    private boolean isEndGame = false;
    private boolean isPaused = false;

    private static int direction = 1; // 1 - UP || 2 - BOTTOM || 3 - LEFT || 4 - RIGHT
    private int tailLength = 0;

    private Coordinates snakeHeadCoordinates = new Coordinates(10, 10);

    private PawnClass snakeHeadClass = new PawnClass(Pawn.SNAKE_HEAD);
    private PawnClass snakeBodyClass = new PawnClass(Pawn.SNAKE_BODY);
    private PawnClass foodClass = new PawnClass(Pawn.FOOD);

    private ArrayList<Coordinates> snakeTail = new ArrayList<>();

    private JFrame info=new JFrame("Info");
    private JPanel infoPanel=new JPanel();
    private JLabel infoLabel=new JLabel();
    private JButton infoButton=new JButton("OK");

    public Board(Design design) {

        this.design = design;
        addStartEntity();
        mapTask();
    }

    public void getMessage(int a,int b){

        info = new JFrame("Info");
        info.setAlwaysOnTop(true);
        infoPanel = new JPanel();
        infoLabel = new JLabel();
        infoButton = new JButton("OK");

        String operation = (a==0) ? "Save" : "Load";
        String success = (b==0) ? "successful" : "failed";

        infoLabel.setText(operation+" "+success+". Press OK to resume the game.");

        infoButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {

                info.setVisible(false);
                isPaused = false;
                mapTask();

            }
        });

        
        info.setSize(300,100);
        infoPanel.add(infoLabel);
        infoPanel.add(infoButton);
        info.add(infoPanel);
        info.setLocationRelativeTo(null);
        
        info.setVisible(true);

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
            case 1:
                moveSnakeHead(new Coordinates(snakeHeadCoordinates.getX(), snakeHeadCoordinates.getY() - 1));
                break;
            case 2:
                moveSnakeHead(new Coordinates(snakeHeadCoordinates.getX(), snakeHeadCoordinates.getY() + 1));
                break;
            case 3:
                moveSnakeHead(new Coordinates(snakeHeadCoordinates.getX() - 1, snakeHeadCoordinates.getY()));
                break;
            case 4:
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
            case 1:
                moveSnakeBodyHandler(new Coordinates(snakeHeadCoordinates.getX(), snakeHeadCoordinates.getY() + 1));
                break;
            case 2:
                moveSnakeBodyHandler(new Coordinates(snakeHeadCoordinates.getX(), snakeHeadCoordinates.getY() - 1));
                break;
            case 3:
                moveSnakeBodyHandler(new Coordinates(snakeHeadCoordinates.getX() + 1, snakeHeadCoordinates.getY()));
                break;
            case 4:
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
            case W: changeDirection(1); break;
            case S: changeDirection(2); break;
            case A: changeDirection(3); break;
            case D: changeDirection(4); break;

            case UP: changeDirection(1); break;
            case DOWN: changeDirection(2); break;
            case LEFT: changeDirection(3); break;
            case RIGHT: changeDirection(4); break;

            case Q: System.exit(0); break;           

            case T:

                isPaused=true;
                BoardInfo BI=new BoardInfo(board, direction, tailLength, snakeHeadCoordinates, snakeHeadClass, snakeBodyClass, foodClass, snakeTail);

                if(new SaveGame(BI, "test").startSave()){

                    getMessage(0, 0);
                }

                else
                    getMessage(0, 1);

                break;
            
            case L:
                
                removeAllImage();
                isPaused=true;

                if(new LoadGame(this).startLoad()){

                    getMessage(1, 0);
                }

                else
                    getMessage(1, 1);

                break;

            default:
                    break;
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

    public void setParameters(HashMap<Coordinates, PawnClass> board, int directionIN, int tailLength, Coordinates snakeHeadCoordinates, PawnClass snakeHeadClass, PawnClass snakeBodyClass, PawnClass foodClass, ArrayList<Coordinates> snakeTail){

        this.board = board;
        direction = directionIN;
        this.tailLength = tailLength;
        this.snakeHeadCoordinates = snakeHeadCoordinates;
        this.snakeHeadClass = snakeHeadClass;
        this.snakeBodyClass = snakeBodyClass;
        this.foodClass = foodClass;
        this.snakeTail = snakeTail;
    }
}
