package pl.nogacz.snake;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pl.nogacz.snake.application.Design;
import pl.nogacz.snake.board.Board;

/**
 * @author Dawid Nogacz on 19.05.2019
 */
public class Snake extends Application {
    Design design = new Design();
    Board board = new Board(design);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(design.getGridPane(), 715, 715, Color.BLACK);
        scene.setOnKeyReleased(event -> board.readKeyboard(event));

        primaryStage.setTitle("JavaSnake");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
