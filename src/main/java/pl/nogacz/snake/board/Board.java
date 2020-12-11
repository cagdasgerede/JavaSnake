package pl.nogacz.snake.board;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import pl.nogacz.snake.application.Design;
import pl.nogacz.snake.application.EndGame;
import pl.nogacz.snake.application.SaveGame;
import pl.nogacz.snake.pawn.Pawn;
import pl.nogacz.snake.pawn.PawnClass;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 * @author Dawid Nogacz on 19.05.2019
 */
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

    private JFrame info=new JFrame();

    public Board(Design design) {

        this.design = design;

        addStartEntity();
        mapTask();
    }

    public Board(BoardInfo load, Design design) {

        board = load.getBoard();
        isEndGame = load.getEndGame();
        direction = load.getDirection();
        tailLength = load.getTailLength();
        snakeHeadCoordinates = load.getHeadCoordinates();
        snakeHeadClass = load.getHeadClass();
        snakeBodyClass = load.getBodyClass();
        foodClass = load.getFoodClass();
        snakeTail = load.getSnakeTail();

        displayAllImage();
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

            case T:
                isPaused=true;
                BoardInfo BI=new BoardInfo(board, isEndGame, direction, tailLength, snakeHeadCoordinates, snakeHeadClass, snakeBodyClass, foodClass, snakeTail);
                new SaveGame(BI);               
                break;
            
            case L:
                isPaused=true;

                JFrame frame = new JFrame("Loading the game");

                JPanel panel = new JPanel();

                JLabel label = new JLabel("LOAD GAME:");

                JButton confirm = new JButton("Confirm");

                JTextField input = new JTextField("Enter the destination adress here");

                input.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e) {
                        input.setText("");
                    }

                    @Override
                    public void mousePressed(java.awt.event.MouseEvent e) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void mouseReleased(java.awt.event.MouseEvent e) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void mouseEntered(java.awt.event.MouseEvent e) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void mouseExited(java.awt.event.MouseEvent e) {
                        // TODO Auto-generated method stub
                    }
                });

                confirm.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent e) {

                        String adress = input.getText();

                        if (adress.equals(""))
                            input.setText("Please enter a destination adress");

                        else
                            loadGame(adress);

                    }
                });

                frame.setSize(500, 125);
                frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame.addWindowListener(new WindowListener() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent we) {
                    
                }

				@Override
				public void windowOpened(WindowEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void windowClosed(WindowEvent e) {
                    isPaused=false;
                    mapTask();
					
				}

				@Override
				public void windowIconified(WindowEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void windowDeiconified(WindowEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void windowActivated(WindowEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void windowDeactivated(WindowEvent e) {
					// TODO Auto-generated method stub
					
				}

                });

                input.setPreferredSize(new Dimension(400, 25));
                frame.toFront();

                panel.setPreferredSize(new Dimension(400, 400)); 

                panel.add(label);
                panel.add(input);
                panel.add(confirm);
                
                frame.add(panel);

                frame.setVisible(true);

                break;

            default:
                    break;
        }
    }

    private void loadGame(String adress){



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
