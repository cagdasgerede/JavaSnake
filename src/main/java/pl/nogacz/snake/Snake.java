package pl.nogacz.snake;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pl.nogacz.snake.application.Design;
import pl.nogacz.snake.application.NewGame;
import pl.nogacz.snake.application.TwoPlayerDesign;
import pl.nogacz.snake.application.UserKeySelect;
import pl.nogacz.snake.board.Board;
import pl.nogacz.snake.board.TwoPlayerBoard;

/**
 * @author Dawid Nogacz on 19.05.2019
 */
public class Snake extends Application {
    Design design;
    Board board;
    TwoPlayerDesign tp_design;
    TwoPlayerBoard tp_board;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        NewGame newGame = new NewGame("Please select your game mode");
        int gameMode = newGame.printDialog();
        
        if(gameMode == 0){
            design = new Design();
            board = new Board(design,true);
            Scene scene = new Scene(design.getGridPane(), 715, 715, Color.BLACK);
            scene.setOnKeyReleased(event -> board.readKeyboard(event));

            primaryStage.setTitle("JavaSnake");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        }
        else if(gameMode == 1){
            UserKeySelect keyCheck = new UserKeySelect("Please define user keys");
            boolean check;
            do {
                check = keyCheck.printDialog();
            }while (!check);
            char[] usr1 = keyCheck.getUser1Conrols();
            char[] usr2 = keyCheck.getUser2Conrols();


            tp_design = new TwoPlayerDesign();
            tp_board = new TwoPlayerBoard(tp_design,true, usr1, usr2);
            Scene scene = new Scene(tp_design.getGridPane(), 1430, 715, Color.BLACK);
            scene.setOnKeyReleased(event -> tp_board.readKeyboard(event));



            primaryStage.setTitle("JavaSnake 2 player");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        }
        else if(gameMode == -1) // exitGame
            System.exit(888);

    }
}
