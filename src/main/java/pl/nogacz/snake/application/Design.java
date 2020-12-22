package pl.nogacz.snake.application;

import java.awt.Font;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import pl.nogacz.snake.board.Board;
import pl.nogacz.snake.board.Coordinates;
import pl.nogacz.snake.pawn.PawnClass;

/**
 * @author Dawid Nogacz on 19.05.2019
 */
public class Design {
    private GridPane gridPane = new GridPane();
    private Board board;

    public Design() {
        createBoardBackground();
        generateEmptyBoard();
    }

    private void createBoardBackground() {
        Image background = new Image(Resources.getPath("background.jpg"));
        BackgroundSize backgroundSize = new BackgroundSize(715, 715, false, false, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
        gridPane.setBackground(new Background(backgroundImage));
    }

    private void generateEmptyBoard() {
        gridPane.setMinSize(715, 715);
        gridPane.setMaxSize(715, 715);

        for(int i = 0; i < 22; i++) {
            ColumnConstraints column = new ColumnConstraints(32);
            column.setHgrow(Priority.ALWAYS);
            column.setHalignment(HPos.CENTER);
            gridPane.getColumnConstraints().add(column);
            RowConstraints row = new RowConstraints(32);
            row.setVgrow(Priority.ALWAYS);
            row.setValignment(VPos.CENTER);
            gridPane.getRowConstraints().add(row);
        }
        gridPane.setPadding(new Insets(10, 0, 0, 10));      
    }

    public void putMenuButton(){
        Button menuButton = new Button("    Menu    ");
        menuButton.setVisible(true);
        gridPane.add(menuButton, 0, 0, 3,1);
        menuButton.setStyle("-fx-text-alignment: center; -fx-border-color: black; -fx-text-fill: CORNFLOWERBLUE; -fx-font-weight: bold; -fx-background-color: #975e25");
        
        menuButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if(!board.getPaused()){
                    board.menuFrame();
                }
        	}  
        });
    }

    public void addPawn(Coordinates coordinates, PawnClass pawn) {
        if(pawn.getPawn().isHead()) {
            gridPane.add(pawn.getImageDirection(Board.getDirection()), coordinates.getX(), coordinates.getY());
        } else {
            gridPane.add(pawn.getImage(), coordinates.getX(), coordinates.getY());
        }
    }

    public void removePawn(Coordinates coordinates) {
        gridPane.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == coordinates.getX() && GridPane.getRowIndex(node) == coordinates.getY());
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    
    public void setBoard(Board b){
        board = b;
    }

}
