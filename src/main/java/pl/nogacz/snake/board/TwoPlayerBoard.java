package pl.nogacz.snake.board;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import pl.nogacz.snake.application.EndGame;
import pl.nogacz.snake.application.TwoPlayerDesign;
import pl.nogacz.snake.pawn.Pawn;
import pl.nogacz.snake.pawn.PawnClass;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TwoPlayerBoard {
    public String[] usr1; // user1 control keys
    public String[] usr2; // user2 control keys
    public String user1Up, user1Down, user1Left, user1Right, user2Up, user2Down, user2Left, user2Right; // user control constants
    private HashMap<Coordinates2P, PawnClass> board = new HashMap<>();
    private TwoPlayerDesign twoPlayerDesign;
    private Random randomGeneratorForFirstPlayer = new Random();
    private Random randomGeneratorForSecondPlayer = new Random();
    private Player firstPlayer;
    private Player secondPlayer;

    public TwoPlayerBoard(TwoPlayerDesign twoPlayerDesign, boolean isGameStarted, String[] usr1, String[] usr2) {
        this.twoPlayerDesign = twoPlayerDesign;
        firstPlayer = new Player(new Coordinates2P(10, 18), 1);
        secondPlayer = new Player(new Coordinates2P(29, 18), 2);
        initializeUserConstants(usr1[0], usr1[1], usr1[2], usr1[3], usr2[0], usr2[1], usr2[2], usr2[3]);
        this.usr1 = usr1;
        this.usr2 = usr2;
        addStartEntity();
        if (isGameStarted)
            mapTask();
    }

    private static int directionToNumber(Direction direction) {
        switch (direction) {
            case UP:
                return 1;
            case DOWN:
                return 2;
            case LEFT:
                return 3;
            case RIGHT:
                return 4;
        }
        return -1; // to return error if none of the switch cases
    }

    private void initializeUserConstants(String user1Up, String user1Down, String user1Left, String user1Right, String user2Up, String user2Down, String user2Left, String user2Right) {
        this.user1Up = user1Up;
        this.user1Down = user1Down;
        this.user1Left = user1Left;
        this.user1Right = user1Right;
        this.user2Up = user2Up;
        this.user2Down = user2Down;
        this.user2Left = user2Left;
        this.user2Right = user2Right;
    }

    private void addStartEntity() {
        board.put(firstPlayer.getSnakeHeadCoordinates(), firstPlayer.getSnakeHeadClass());
        board.put(secondPlayer.getSnakeHeadCoordinates(), secondPlayer.getSnakeHeadClass());

        for (int i = 0; i < 44; i++) {
            board.put(new Coordinates2P(0, i), new PawnClass(Pawn.BRICK));
            board.put(new Coordinates2P(21, i), new PawnClass(Pawn.BRICK));
            board.put(new Coordinates2P(i, 0), new PawnClass(Pawn.BRICK));
            board.put(new Coordinates2P(i, 21), new PawnClass(Pawn.BRICK));
            board.put(new Coordinates2P(43, i), new PawnClass(Pawn.BRICK));
        }
        addEat();
        addEatSecondPlayer();
        displayAllImage();
    }

    private void checkMap() {
        removeAllImage();
        if (!firstPlayer.isEndGame())
            moveSnake(firstPlayer);
        if (!secondPlayer.isEndGame())
            moveSnake(secondPlayer);
        displayAllImage();
    }

    private void removeAllImage() {
        for (Map.Entry<Coordinates2P, PawnClass> entry : board.entrySet()) {
            twoPlayerDesign.removePawn(entry.getKey());
        }
    }

    private void displayAllImage() {
        for (Map.Entry<Coordinates2P, PawnClass> entry : board.entrySet()) {
            twoPlayerDesign.addPawn(entry.getKey(), entry.getValue(), this);
        }
    }

    private void moveSnake(Player player) {
        Direction playerDirection = player.getDirection();
        switch (playerDirection) {
            case UP:
                moveSnakeHead(new Coordinates2P(player.getSnakeHeadCoordinates().getX(), player.getSnakeHeadCoordinates().getY() - 1), player);
                break;
            case DOWN:
                moveSnakeHead(new Coordinates2P(player.getSnakeHeadCoordinates().getX(), player.getSnakeHeadCoordinates().getY() + 1), player);
                break;
            case LEFT:
                moveSnakeHead(new Coordinates2P(player.getSnakeHeadCoordinates().getX() - 1, player.getSnakeHeadCoordinates().getY()), player);
                break;
            case RIGHT:
                moveSnakeHead(new Coordinates2P(player.getSnakeHeadCoordinates().getX() + 1, player.getSnakeHeadCoordinates().getY()), player);
                break;
        }
    }

    private void moveSnakeHead(Coordinates2P coordinates, Player player) {
        if (player.equals(firstPlayer) && (!firstPlayer.isEndGame())) {
            if (coordinates.isValid()) {
                if (isFieldNotNull(coordinates)) {
                    if (getPawn(coordinates).getPawn().isFood()) {
                        board.remove(firstPlayer.getSnakeHeadCoordinates());
                        board.put(firstPlayer.getSnakeHeadCoordinates(), firstPlayer.getSnakeBodyClass());
                        board.put(coordinates, firstPlayer.getSnakeHeadClass());
                        firstPlayer.getSnakeTail().add(firstPlayer.getSnakeHeadCoordinates());
                        firstPlayer.incrementTailLength();
                        firstPlayer.setSnakeHeadCoordinates(coordinates);
                        addEat();
                    } else {
                        firstPlayer.setEndGame(true);
                        checkAndEndGame();
                    }
                } else {
                    board.remove(firstPlayer.getSnakeHeadCoordinates());
                    board.put(coordinates, firstPlayer.getSnakeHeadClass());
                    firstPlayer.setSnakeHeadCoordinates(coordinates);
                    if (firstPlayer.getTailLength() > 0) {
                        moveSnakeBody(firstPlayer);
                    }
                }
            }
        } else if (player.equals(secondPlayer) && (!secondPlayer.isEndGame())) {
            if (coordinates.isValid()) {
                if (isFieldNotNull(coordinates)) {
                    if (getPawn(coordinates).getPawn().isFoodOfSecondPlayer()) {
                        board.remove(secondPlayer.getSnakeHeadCoordinates());
                        board.put(secondPlayer.getSnakeHeadCoordinates(), secondPlayer.getSnakeBodyClass());
                        board.put(coordinates, secondPlayer.getSnakeHeadClass());
                        secondPlayer.getSnakeTail().add(secondPlayer.getSnakeHeadCoordinates());
                        secondPlayer.incrementTailLength();
                        secondPlayer.setSnakeHeadCoordinates(coordinates);
                        addEatSecondPlayer();
                    } else {
                        secondPlayer.setEndGame(true);
                        checkAndEndGame();
                    }
                } else {
                    board.remove(secondPlayer.getSnakeHeadCoordinates());
                    board.put(coordinates, secondPlayer.getSnakeHeadClass());
                    secondPlayer.setSnakeHeadCoordinates(coordinates);
                    if (secondPlayer.getTailLength() > 0) {
                        moveSnakeBody(secondPlayer);
                    }
                }
            }
        }
    }

    private void checkAndEndGame() {
        if (firstPlayer.isEndGame() && secondPlayer.isEndGame()) {
            if (firstPlayer.getTailLength() > secondPlayer.getTailLength()) {
                new EndGame(
                        "Player 1 wins!\n\n" +
                        "Player 1 collected " + firstPlayer.getTailLength() + " points. \n" +
                        "Player 2 collected " + secondPlayer.getTailLength() + " points. \n" +
                        "Thank you for playing :)");
            } else if (secondPlayer.getTailLength() > firstPlayer.getTailLength()) {
                new EndGame(
                        "Player 2 wins!\n\n" +
                        "Player 1 collected " + firstPlayer.getTailLength() + " points. \n" +
                        "Player 2 collected " + secondPlayer.getTailLength() + " points. \n" +
                        "Thank you for playing :)");
            } else {
                new EndGame(
                        "Draw. No winner\n\n" +
                        "Player 1 collected " + firstPlayer.getTailLength() + " points. \n" +
                        "Player 2 collected " + secondPlayer.getTailLength() + " points. \n" +
                        "Thank you for playing :)");
            }
        }
    }

    private void moveSnakeBody(Player player) {
        Direction playerDirection = player.getDirection();
        switch (playerDirection) {
            case UP:
                moveSnakeBodyHandler(new Coordinates2P(player.getSnakeHeadCoordinates().getX(), player.getSnakeHeadCoordinates().getY() + 1), player);
                break;
            case DOWN:
                moveSnakeBodyHandler(new Coordinates2P(player.getSnakeHeadCoordinates().getX(), player.getSnakeHeadCoordinates().getY() - 1), player);
                break;
            case LEFT:
                moveSnakeBodyHandler(new Coordinates2P(player.getSnakeHeadCoordinates().getX() + 1, player.getSnakeHeadCoordinates().getY()), player);
                break;
            case RIGHT:
                moveSnakeBodyHandler(new Coordinates2P(player.getSnakeHeadCoordinates().getX() - 1, player.getSnakeHeadCoordinates().getY()), player);
                break;
        }
    }

    private void moveSnakeBodyHandler(Coordinates2P coordinates, Player player) {
        if (player.getTailLength() == player.getSnakeTail().size()) {
            Coordinates endTail = player.getSnakeTail().get(0);
            board.remove(endTail);
            player.getSnakeTail().remove(endTail);
        }

        board.put(coordinates, player.getSnakeBodyClass());
        player.getSnakeTail().add(coordinates);
    }

    private void addEat() {
        Coordinates2P firstPlayerFoodCoordinates;
        do {
            firstPlayerFoodCoordinates = new Coordinates2P(randomGeneratorForFirstPlayer.nextInt(21), randomGeneratorForFirstPlayer.nextInt(21));
        } while (isFieldNotNull(firstPlayerFoodCoordinates));

        board.put(firstPlayerFoodCoordinates, firstPlayer.getFoodClass());
    }

    private void addEatSecondPlayer() {
        Coordinates2P secondPlayerFoodCoordinates;
        do {
            secondPlayerFoodCoordinates = new Coordinates2P(22 + randomGeneratorForSecondPlayer.nextInt(20), 1 + randomGeneratorForSecondPlayer.nextInt(19));
        } while (isFieldNotNull(secondPlayerFoodCoordinates));

        board.put(secondPlayerFoodCoordinates, secondPlayer.getFoodClass());
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
                if (!firstPlayer.isEndGame() || !secondPlayer.isEndGame()) {
                    checkMap();
                    mapTask();
                }
            }
        });

        new Thread(task).start();
    }

    public void readKeyboard(KeyEvent event) {
        String keyRead = event.getCode().toString();

        if (keyRead.equals(user1Up))
            changeDirection(Direction.UP, firstPlayer);
        else if (keyRead.equals(user1Down))
            changeDirection(Direction.DOWN, firstPlayer);
        else if (keyRead.equals(user1Left))
            changeDirection(Direction.LEFT, firstPlayer);
        else if (keyRead.equals(user1Right))
            changeDirection(Direction.RIGHT, firstPlayer);
        else if (keyRead.equals(user2Up))
            changeDirection(Direction.UP, secondPlayer);
        else if (keyRead.equals(user2Down))
            changeDirection(Direction.DOWN, secondPlayer);
        else if (keyRead.equals(user2Left))
            changeDirection(Direction.LEFT, secondPlayer);
        else if (keyRead.equals(user2Right))
            changeDirection(Direction.RIGHT, secondPlayer);
    }

    private void changeDirection(Direction newDirection, Player player) {
        if (player.isEndGame()) // this check is needed because, snake's only head keeps playing even player is dead
            return;
        Direction playerDirection = player.getDirection();
        if (newDirection == Direction.UP && playerDirection != Direction.DOWN) {
            player.setDirection(Direction.UP);
        } else if (newDirection == Direction.DOWN && playerDirection != Direction.UP) {
            player.setDirection(Direction.DOWN);
        } else if (newDirection == Direction.LEFT && playerDirection != Direction.RIGHT) {
            player.setDirection(Direction.LEFT);
        } else if (newDirection == Direction.RIGHT && playerDirection != Direction.LEFT) {
            player.setDirection(Direction.RIGHT);
        }
    }

    private boolean isFieldNotNull(Coordinates2P coordinates) {
        return getPawn(coordinates) != null;
    }

    private PawnClass getPawn(Coordinates2P coordinates) {
        return board.get(coordinates);
    }

    public int getDirectionPlayerOne() {
        return directionToNumber(firstPlayer.getDirection());
    }

    public int getDirectionPlayerTwo() {
        return directionToNumber(secondPlayer.getDirection());
    }
}
