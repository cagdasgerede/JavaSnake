package pl.nogacz.snake.pawn;

import java.io.Serializable;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pl.nogacz.snake.application.Resources;
import pl.nogacz.snake.board.Board.Direction;

/**
 * @author Dawid Nogacz on 19.05.2019
 */
public class PawnClass implements Serializable{
    
    private static final long serialVersionUID = -3297414734292083048L;
    private Pawn pawn;

    public PawnClass(Pawn pawn) {
        this.pawn = pawn;
    }

    public ImageView getImage() {
        Image image = new Image(Resources.getPath(pawn + ".png"));
        return new ImageView(image);
    }

    public ImageView getImageDirection(Direction direction) {
        String direct = "";

        switch(direction) {
            case UP: direct = "UP"; break;
            case BOTTOM: direct = "BOTTOM"; break;
            case LEFT: direct = "LEFT"; break;
            case RIGHT: direct = "RIGHT"; break;
        }

        Image image = new Image(Resources.getPath(pawn + "_" + direct + ".png"));
        return new ImageView(image);
    }

    public Pawn getPawn() {
        return pawn;
    }
}
