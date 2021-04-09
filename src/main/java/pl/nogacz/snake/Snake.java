package pl.nogacz.snake;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pl.nogacz.snake.application.Design;
import pl.nogacz.snake.application.TwoPlayerDesign;
import pl.nogacz.snake.application.UserKeyDefiner;
import pl.nogacz.snake.board.Board;
import pl.nogacz.snake.board.TwoPlayerBoard;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

/**
 * @author Dawid Nogacz on 19.05.2019
 */
public class Snake extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("ModeSellection");
        alert.setContentText("Please select how do you want to play");

        ButtonType singlePlayerButton = new ButtonType("SinglePlayer");
        ButtonType twoPlayerButton = new ButtonType("TwoPlayer");

        alert.getButtonTypes().setAll(singlePlayerButton, twoPlayerButton);

        Optional<ButtonType> result = alert.showAndWait();

        Scene scene;

        if (result.get() == singlePlayerButton){
            Design design = new Design();
            Board board = new Board(design);
            scene = new Scene(design.getGridPane(), 715, 715, Color.BLACK);
            scene.setOnKeyReleased(event -> board.readKeyboard(event));
        } else {
            UserKeyDefiner userKeyDefiner = new UserKeyDefiner();
            userKeyDefiner.approver();
            char[] newKeys = userKeyDefiner.getNewKeys();    
            TwoPlayerDesign design = new TwoPlayerDesign();
            TwoPlayerBoard board = new TwoPlayerBoard(design, newKeys);
            scene = new Scene(design.getGridPane(), 1430, 715, Color.BLACK);
            scene.setOnKeyReleased(event -> board.readKeyboard(event));
        }
        primaryStage.setTitle("JavaSnake");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
