package pl.nogacz.snake.application;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Priority;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.Background;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;

import pl.nogacz.snake.board.Coordinates2P;
import pl.nogacz.snake.board.TwoPlayerBoard;
import pl.nogacz.snake.pawn.PawnClass;

public class TwoPlayerDesign {
    private GridPane gridPane = new GridPane();

    public TwoPlayerDesign() {
        createBoardBackground();
        generateEmptyBoard();
    }

    private void createBoardBackground() {
        Image background = new Image(Resources.getPath("background2p.jpg"));
        BackgroundSize backgroundSize = new BackgroundSize(1430, 715, false, false, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
        gridPane.setBackground(new Background(backgroundImage));
    }

    private void generateEmptyBoard() {
        gridPane.setMinSize(1430, 715);
        gridPane.setMaxSize(1430, 715);

        final int edgeLength = 22;
        for(int i = 0; i < edgeLength; i++) {
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

    public void addPawn(Coordinates2P coordinates, PawnClass pawn, TwoPlayerBoard twoPlayerBoard) {
        if(pawn.getPawn().isHead() ) {
            gridPane.add(pawn.getImageDirection(twoPlayerBoard.getDirectionPlayerOne()), coordinates.getX(), coordinates.getY());
        } else if (pawn.getPawn().isHeadOfSecondPlayer() ) {
            gridPane.add(pawn.getImageDirection(twoPlayerBoard.getDirectionPlayerTwo()), coordinates.getX(), coordinates.getY());
        }
        else {
            gridPane.add(pawn.getImage(), coordinates.getX(), coordinates.getY());
        }
    }

    public void removePawn(Coordinates2P coordinates) {
        gridPane.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == coordinates.getX() && GridPane.getRowIndex(node) == coordinates.getY());
    }

    public GridPane getGridPane() {
        return gridPane;
    }
}
