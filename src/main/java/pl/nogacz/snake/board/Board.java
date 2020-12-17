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

    private Coordinates snakeHeadCoordinates = new Coordinates(10, 10);
    
    private Coordinates currentRottenAppleCoordinates[] = new Coordinates[3]; 

    private long lastSpawnTimesOfApples[] = new long[3];
    private long lastDissappearTimesOfApples[] = new long[3];
    
    private int newRandomSpawnTimesOfApples[] = new int[3];
    private int newRandomDissappearTimesOfApples[] = new int[3];

    private Boolean dissappearRottenApplesCheck[] = new Boolean[3];

    private static final int APPLE_SPAWN_LIMIT = 6;

    private PawnClass snakeHeadClass = new PawnClass(Pawn.SNAKE_HEAD);
    private PawnClass snakeBodyClass = new PawnClass(Pawn.SNAKE_BODY);
    private PawnClass foodClass = new PawnClass(Pawn.FOOD);

    private PawnClass rottenAppleClass[] = new PawnClass[3];

    private ArrayList<Coordinates> snakeTail = new ArrayList<>();
    
    private ArrayList<Coordinates> rottenApplesBlack = new ArrayList<>();
    private ArrayList<Coordinates> rottenApplesGrey = new ArrayList<>();
    private ArrayList<Coordinates> rottenApplesOrange = new ArrayList<>();

    private ArrayList<ArrayList<Coordinates>> rottenApples = new ArrayList<>();

    public Coordinates getSnakeHeadCoordinates(){
        return this.snakeHeadCoordinates;
    }
    
    public Coordinates[] getCurrentCoordinates(){
        return this.currentRottenAppleCoordinates;
    }

    public long[] getLastSpawnTimesOfApples(){
        return this.lastSpawnTimesOfApples;
    }

    public long[] getLastDissappearTimesOfApples(){
        return this.lastDissappearTimesOfApples;
    }

    public int[] getNewRandomSpawnTimesOfApples(){
        return this.newRandomSpawnTimesOfApples;
    }
    
    public int[] getNewRandomDissappearTimesOfApples() {
        return this.newRandomDissappearTimesOfApples;
    }

    public ArrayList<Coordinates> getRottenApplesBlack(){
        return rottenApplesBlack;
    }

    public ArrayList<Coordinates> getRottenApplesGrey(){
        return rottenApplesGrey;
    }

    public ArrayList<Coordinates> getRottenApplesOrange(){
        return rottenApplesOrange;
    }

 
    public Board(Design design) {
        this.design = design;

        // three different rotten apple(block) classes initialized
        rottenAppleClass[0] = new PawnClass(Pawn.ROTTEN_APPLE_BLACK);
        rottenAppleClass[1] = new PawnClass(Pawn.ROTTEN_APPLE_GREY);
        rottenAppleClass[2] = new PawnClass(Pawn.ROTTEN_APPLE_ORANGE);
        
        // initial spawn times for three different rotten apple objects when the game starts
        newRandomSpawnTimesOfApples[0] = 5;
        newRandomSpawnTimesOfApples[1] = 10;
        newRandomSpawnTimesOfApples[2] = 7;

        java.util.Arrays.fill(dissappearRottenApplesCheck, true);

        rottenApples.add(rottenApplesBlack);
        rottenApples.add(rottenApplesGrey);
        rottenApples.add(rottenApplesOrange);

        addStartEntity();
        mapTask();
    }

    private void addStartEntity() {
        board.put(snakeHeadCoordinates, snakeHeadClass);

        for(int i = 0; i < rottenApples.size(); i++){
            lastSpawnTimesOfApples[i] = System.currentTimeMillis();
            lastDissappearTimesOfApples[i] = System.currentTimeMillis();
        }
        

        for(int i = 0; i < 22; i++) {
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

    public boolean snakeHitsRottenApple(Coordinates coordinates){ // if snake hits rotten apple game ends.
        if(getPawn(coordinates).getPawn().isRottenAppleBlack() || getPawn(coordinates).getPawn().isRottenAppleGrey() 
               || getPawn(coordinates).getPawn().isRottenAppleOrange()){
                isEndGame = true;
                return true;
            }
        else
            return false;
    }


    private String endGameMessage(int point){
        return "End game...\n" + "You have " + point + " points. \n" + "Maybe try again? :)";
    }

    public void moveSnakeHead(Coordinates coordinates) {
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
                } 
                
                else if(snakeHitsRottenApple(coordinates)){ 
                    new EndGame(endGameMessage(tailLength));
                }
                else {
                    isEndGame = true;
                    new EndGame(endGameMessage(tailLength));
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

    private boolean conditionCheckToSpawnRottenApple(int i){ // control for randomly chosen spawning rotten apple time
        return Math.abs(this.lastSpawnTimesOfApples[i] - System.currentTimeMillis()) / 1000 == this.newRandomSpawnTimesOfApples[i];
    }

    public void spawnRottenApple(){
        for(int i = 0; i < rottenApples.size(); i++){
            if(conditionCheckToSpawnRottenApple(i)){
                addRottenApples(i);
                rottenApples.get(i).add(this.currentRottenAppleCoordinates[i]);
                this.lastSpawnTimesOfApples[i] = System.currentTimeMillis();
                this.newRandomSpawnTimesOfApples[i] = random.nextInt(9) + 1;
            }
        }
    }

    private boolean dissappearRottenAppleForFirstTime(int i){ // control for randomly chosen dissappearing rotten apple for the first time
        return rottenApplesBlack.size() == 1 && dissappearRottenApplesCheck[i];
    }

    private boolean checkToDissappearRottenApple(int i){ // control for randomly chosen dissappearing rotten apple time
        return Math.abs(lastDissappearTimesOfApples[i] - System.currentTimeMillis()) / 1000 == newRandomDissappearTimesOfApples[i];
    }

    public void disappearRottenApple(){
        for(int i = 0; i < rottenApples.size(); i++){
            if(dissappearRottenAppleForFirstTime(i)){
                newRandomDissappearTimesOfApples[i] = random.nextInt(9) + 1;
                lastDissappearTimesOfApples[i] = System.currentTimeMillis();
                dissappearRottenApplesCheck[i] = false;
            }
        
        for(int k = 0; k < rottenApples.size(); k++){
            if(checkToDissappearRottenApple(k))
                if(!rottenApples.get(k).isEmpty()){
                    board.remove(rottenApples.get(k).get(0));
                    design.removePawn(rottenApples.get(k).get(0));
                    
                    rottenApples.get(k).remove(0);
                    newRandomDissappearTimesOfApples[k] = random.nextInt(9) + 1;
                    lastDissappearTimesOfApples[k] = System.currentTimeMillis();
                    dissappearRottenApplesCheck[k] = true;            
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
        }while(isFieldNotNull(foodCoordinates));

        board.put(foodCoordinates, foodClass);
    }

    private void addRottenApples(int i){
        Coordinates rottenAppleCoordinates;

        do{
            rottenAppleCoordinates = new Coordinates(random.nextInt(21),random.nextInt(21));
            this.currentRottenAppleCoordinates[i] = rottenAppleCoordinates;
        }while(isFieldNotNull(rottenAppleCoordinates));

        board.put(rottenAppleCoordinates, rottenAppleClass[i]);
    }

    private int totalRottenAppleSize(){
        int sum = 0;
        for(int i = 0; i < rottenApples.size(); i++){
                sum = sum + rottenApples.get(i).size();
        }
        
        return sum;
    }

    public void clearRottenApples(){ // when rotten apples(blocks) appear at the same time more than the value of objectLimit , board will be cleared when the (objectLimit + 1)th rotten apple spawns.
        int sum = totalRottenAppleSize();
        if(sum > APPLE_SPAWN_LIMIT){
            for(int i = 0; i < rottenApples.size(); i++){
                for(int k = 0; k < rottenApples.get(i).size(); k++){
                    board.remove(rottenApples.get(i).get(k));
                    design.removePawn(rottenApples.get(i).get(k));
                }
    
                lastDissappearTimesOfApples[i] = System.currentTimeMillis();
                dissappearRottenApplesCheck[i] = true;
                rottenApples.get(i).clear();
            }
        }
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
                    spawnRottenApple();
                    disappearRottenApple();
                    clearRottenApples();                   
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
