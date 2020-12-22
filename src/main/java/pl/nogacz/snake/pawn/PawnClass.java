package pl.nogacz.snake.pawn;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pl.nogacz.snake.application.Resources;
import pl.nogacz.snake.application.SnakeSkins;

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

    public ImageView getImageDirection(int direction) {
        String direct = "";
        
        SnakeSkins.bodySkinTones myVar = SnakeSkins.getBodySkin();
        if(myVar == SnakeSkins.bodySkinTones.SKIN_TONE_DEFAULT) {
            switch(direction) {
                case 1: direct = "UP"; break;
                case 2: direct = "BOTTOM"; break;
                case 3: direct = "LEFT"; break;
                case 4: direct = "RIGHT"; break;
                default: break;
            }
        }
        else if(myVar == SnakeSkins.bodySkinTones.SKIN_TONE_ORANGE) {
            switch(direction) {
                case 1: direct = "UP_SKIN_ORANGE"; break;
                case 2: direct = "BOTTOM_SKIN_ORANGE"; break;
                case 3: direct = "LEFT_SKIN_ORANGE"; break;
                case 4: direct = "RIGHT_SKIN_ORANGE"; break;
                default: break;
            }
        }
        else if(myVar == SnakeSkins.bodySkinTones.SKIN_TONE_GREEN) {
            switch(direction) {
                case 1: direct = "UP_SKIN_GREEN"; break;
                case 2: direct = "BOTTOM_SKIN_GREEN"; break;
                case 3: direct = "LEFT_SKIN_GREEN"; break;
                case 4: direct = "RIGHT_SKIN_GREEN"; break;
                default: break;
            }
        }
        Image image = new Image(Resources.getPath(pawn + "_" + direct + ".png"));
        return new ImageView(image);
    }

    public Pawn getPawn() {
        return pawn;
    }
}
