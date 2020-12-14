package pl.nogacz.snake.pawn;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pl.nogacz.snake.application.Resources;

/**
 * @author Dawid Nogacz on 19.05.2019
 */
public class PawnClass {
    private Pawn pawn;

    public PawnClass(Pawn pawn) {
        this.pawn = pawn;
    }

    public ImageView getImage() {
        Image image = new Image(Resources.getPath(pawn + ".png"));
        return new ImageView(image);
    }

    public ImageView getSuperSnakeImage() {
        if(pawn == Pawn.SNAKE_BODY) {
            Image image = new Image(Resources.getPath(pawn + "_INVINCIBLE.png"));
            return new ImageView(image);
        }
        else {
            Image image = new Image(Resources.getPath(pawn + ".png"));
            return new ImageView(image);
        }
    }

    public ImageView getItemImage() {
        Image image = new Image(Resources.getPath("ITEM.png"));
        return new ImageView(image);
    }

    public ImageView getBrickImage() {
        Image image = new Image(Resources.getPath("BRICK.png"));
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

    public ImageView getSuperSnakeDirection(int direction) {
        String direct = "";

        switch(direction) {
            case 1: direct = "UP"; break;
            case 2: direct = "BOTTOM"; break;
            case 3: direct = "LEFT"; break;
            case 4: direct = "RIGHT"; break;
        }
    
        Image image = new Image(Resources.getPath(pawn + "_" + direct + "_INVINCIBLE.png"));
        return new ImageView(image);
    }

    public Pawn getPawn() {
        return pawn;
    }
}
