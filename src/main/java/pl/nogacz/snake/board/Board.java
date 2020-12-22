package pl.nogacz.snake.board;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javax.swing.border.EmptyBorder;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import pl.nogacz.snake.application.Design;
import pl.nogacz.snake.application.EndGame;
import pl.nogacz.snake.application.Resources;
import pl.nogacz.snake.pawn.Pawn;
import pl.nogacz.snake.pawn.PawnClass;
/**
 * @author Dawid Nogacz on 19.05.2019
 */
public class Board {
    private HashMap<Coordinates, PawnClass> board = new HashMap<>();
    private Design design;
    private Random random = new Random();

    private boolean isEndGame = false;
    private volatile boolean paused = false;

    private static int direction = 1; // 1 - UP || 2 - BOTTOM || 3 - LEFT || 4 - RIGHT
    private int tailLength = 0;

    private Coordinates snakeHeadCoordinates = new Coordinates(10, 10);

    private PawnClass snakeHeadClass = new PawnClass(Pawn.SNAKE_HEAD);
    private PawnClass snakeBodyClass = new PawnClass(Pawn.SNAKE_BODY);
    private PawnClass foodClass = new PawnClass(Pawn.FOOD);

    private ArrayList<Coordinates> snakeTail = new ArrayList<>();

    public Board(Design design) {
        this.design = design;
        design.setBoard(this);
        addStartEntity();
        mapTask();
    }

    private void addStartEntity() {       
        board.put(snakeHeadCoordinates, snakeHeadClass);
        for(int i = 0; i < 22; i++) {
            board.put(new Coordinates(0, i + 1), new PawnClass(Pawn.BRICK));
            board.put(new Coordinates(21, i), new PawnClass(Pawn.BRICK));
            board.put(new Coordinates(i + 3, 0), new PawnClass(Pawn.BRICK));
            board.put(new Coordinates(i, 21), new PawnClass(Pawn.BRICK));
        }
        addEat();
        design.putMenuButton();
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

                    addEat();
                } else {
                    isEndGame = true;
                    menuFrame();
                }
            } else {
                if((coordinates.getX() == 1 || coordinates.getX() == 2 ) && coordinates.getY() == 0){
                    isEndGame = true;
                    menuFrame();
                }
                board.remove(snakeHeadCoordinates);
                board.put(coordinates, snakeHeadClass);

                snakeHeadCoordinates = coordinates;

                if(tailLength > 0) {
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

    private void addEat() {
        Coordinates foodCoordinates;
        do {
            foodCoordinates = new Coordinates(random.nextInt(21), random.nextInt(21));
        } while(isFieldNotNull(foodCoordinates) || isMenuCoordinate(foodCoordinates)); 

        board.put(foodCoordinates, foodClass);
    }

    private boolean isMenuCoordinate(Coordinates coordinates){
        return ((coordinates.getX() == 0 || coordinates.getX() == 1 || (coordinates.getX() == 2)) && coordinates.getY() == 0);
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

                if(!isEndGame && !paused) {
                    checkMap();
                    mapTask();
                }

                if(paused){
                    while(paused){
                        try {
                            Thread.sleep(100);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
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
            case ESCAPE : menuFrame(); break;
        }
    }

    public void menuFrame() {
        if(!paused){
            paused = true;
            
            JFrame frame = new JFrame("MENU"); 
            JPanel panel = new JPanel();   
    
            BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
            panel.setLayout(boxlayout);
            panel.setBorder(new EmptyBorder(new Insets(50, 100, 50, 100))); 
        
            JLabel tx1 = new JLabel("Menu");
            JLabel point = new JLabel("Your Score is " + tailLength);
            JButton resumeButton = new JButton("RESUME");
            JButton newGameButton = new JButton("NEW GAME");        
            JButton settingsButton = new JButton("CHANGE SETTINGS");
            JButton exitButton = new JButton("EXIT");
        
            tx1.setFont(new Font("Courier", Font.BOLD, 30));
            point.setFont(new Font("ZapfDingbats", Font.BOLD, 20));
            resumeButton.setFont((new Font("ZapfDingbats", Font.BOLD, 20)));
            newGameButton.setFont((new Font("ZapfDingbats", Font.BOLD, 20)));
            settingsButton.setFont((new Font("ZapfDingbats", Font.BOLD, 20)));
            exitButton.setFont((new Font("ZapfDingbats", Font.BOLD, 20)));
            
            resumeButton.addActionListener(new ActionListener(){  
                public void actionPerformed(ActionEvent e){  
                        paused = false;
                        frame.setVisible(false);  
                }  
            }); 
            
            newGameButton.addActionListener(new ActionListener(){  
                public void actionPerformed(ActionEvent e){  
                            paused = false;
                            frame.setVisible(false);
                }  
            }); 
           
            settingsButton.addActionListener(new ActionListener(){  
                public void actionPerformed(ActionEvent e){  
                }  
            }); 
            
            exitButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){  
                System.exit(0);;  
                }  
            });

            tx1.setIcon(new ImageIcon(pathComponent("FOOD.png")));
            tx1.setIconTextGap(3);
            tx1.setHorizontalAlignment(SwingConstants.LEFT);
            tx1.setAlignmentX(Component.CENTER_ALIGNMENT);

            point.setIcon(new ImageIcon(pathComponent("SNAKE_HEAD_UP.png")));
            point.setAlignmentX(Component.CENTER_ALIGNMENT);

            resumeButton.setBackground(new Color(151,94,37));
            resumeButton.setIcon(new ImageIcon(pathComponent("SNAKE_BODY.png")));
            resumeButton.setIconTextGap(3);
            resumeButton.setHorizontalAlignment(SwingConstants.LEFT);
            resumeButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            newGameButton.setBackground(new Color(151,94,37));
            newGameButton.setIcon(new ImageIcon(pathComponent("SNAKE_BODY.png")));
            newGameButton.setIconTextGap(3);
            newGameButton.setHorizontalAlignment(SwingConstants.LEFT);
            newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            settingsButton.setBackground(new Color(151,94,37));
            settingsButton.setIcon(new ImageIcon(pathComponent("SNAKE_BODY.png")));
            settingsButton.setIconTextGap(3);
            settingsButton.setHorizontalAlignment(SwingConstants.LEFT);
            settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            exitButton.setBackground(new Color(151,94,37));
            exitButton.setIcon(new ImageIcon(pathComponent("SNAKE_BODY.png")));
            exitButton.setIconTextGap(3);
            exitButton.setHorizontalAlignment(SwingConstants.LEFT);
            exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                
            panel.add(tx1);
            panel.add(Box.createRigidArea(new Dimension(0, 60))); 
            panel.add(point);
            panel.add(Box.createRigidArea(new Dimension(0, 60)));     
            if(!isEndGame){
                panel.add(resumeButton);     
                panel.add(Box.createRigidArea(new Dimension(0, 60)));
            }     
            panel.add(newGameButton);
            panel.add(Box.createRigidArea(new Dimension(0, 60)));
            panel.add(settingsButton);
            panel.add(Box.createRigidArea(new Dimension(0, 60)));
            panel.add(exitButton);
            
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setAlwaysOnTop(true);
            
            panel.setVisible(true);
            frame.setContentPane(panel);
            frame.pack();
            frame.getContentPane().setBackground(new Color(255, 255, 153));
            frame.validate();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
    }

    public String pathComponent(String filename) {
        String path = Resources.getPath(filename);
        return path.substring(path.indexOf("/") + 1);
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

    public boolean getPaused(){
        return paused;
    }
}
