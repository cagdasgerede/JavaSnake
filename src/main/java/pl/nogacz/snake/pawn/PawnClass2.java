package pl.nogacz.snake.pawn;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pl.nogacz.snake.application.Resources;

/**
 * @author Dawid Nogacz on 19.05.2019
 */
public class PawnClass2 extends PawnSuperClass {
    private Pawn2P pawn2P;

    public PawnClass2(Pawn2P pawn2P) {
        this.pawn2P = pawn2P;
    }

    public ImageView getImage() {
        Image image = new Image(Resources.getPath(pawn2P + ".png"));
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

        Image image = new Image(Resources.getPath(pawn2P + "_" + direct + ".png"));
        return new ImageView(image);
    }

    public Pawn2P getPawn() {
        return pawn2P;
    }
}
