package pl.nogacz.snake.application;


import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import pl.nogacz.snake.board.TwoPlayerBoard;
import pl.nogacz.snake.board.Coordinates;
import pl.nogacz.snake.pawn.PawnClass;

public class TwoPlayerDesign {
    private GridPane gridPane = new GridPane();

    public TwoPlayerDesign() {
        createBoardBackground();
        generateEmptyBoard();
    }

    private void createBoardBackground() {
        Image background = new Image(Resources.getPath("twoPlayerBackground.jpg"));
        BackgroundSize backgroundSize = new BackgroundSize(1430, 715, false, false, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
        gridPane.setBackground(new Background(backgroundImage));
    }

    private void generateEmptyBoard() {
        gridPane.setMinSize(1430, 715);
        gridPane.setMaxSize(1430, 715);

        for(int i = 0; i < 44; i++) {
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

    public void addPawn(Coordinates coordinates, PawnClass pawn) {
        if(pawn.getPawn().isHead()) {
            gridPane.add(pawn.getImageDirection(TwoPlayerBoard.getDirection()), coordinates.getX(), coordinates.getY());
        } else if(pawn.getPawn().isHead2()) {
            gridPane.add(pawn.getImageDirection(TwoPlayerBoard.getDirection2()), coordinates.getX(), coordinates.getY());
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
}
