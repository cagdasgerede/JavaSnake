package pl.nogacz.snake.pawn;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pl.nogacz.snake.application.Resources;

/**
 * @author Dawid Nogacz on 19.05.2019
 */
public class PawnClass extends PawnSuperClass {
    private Pawn pawn;

    public PawnClass(Pawn pawn) {
        this.pawn = pawn;
    }

    public ImageView getImage() {
        Image image = new Image(Resources.getPath(pawn + ".png"));
        return new ImageView(image);
    }

    public ImageView getImageDirection(int direction) {
        String direct = "";

        switch(direction) {
            case 1: direct = "UP"; break;
            case 2: direct = "BOTTOM"; break;
            case 3: direct = "LEFT"; break;
            case 4: direct = "RIGHT"; break;
        }

        Image image = new Image(Resources.getPath(pawn + "_" + direct + ".png"));
        return new ImageView(image);
    }

    public Pawn getPawn() {
        return pawn;
    }
}
