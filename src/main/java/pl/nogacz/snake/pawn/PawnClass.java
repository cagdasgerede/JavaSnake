package pl.nogacz.snake.pawn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pl.nogacz.snake.application.Resources;
import pl.nogacz.snake.application.skinMenu;

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
        int headSkinTone = skinMenu.getHeadSkin();
        String direct = "";
        if(headSkinTone == 1) {
            switch(direction) {
                case 1: direct = "UP"; break;
                case 2: direct = "BOTTOM"; break;
                case 3: direct = "LEFT"; break;
                case 4: direct = "RIGHT"; break;
                default: break;
            }
        }
        else if(headSkinTone == 2) {
            switch(direction) {
                case 1: direct = "UP_SKIN_2"; break;
                case 2: direct = "BOTTOM_SKIN_2"; break;
                case 3: direct = "LEFT_SKIN_2"; break;
                case 4: direct = "RIGHT_SKIN_2"; break;
                default: break;
            }
        }
        else if(headSkinTone == 3) {
            switch(direction) {
                case 1: direct = "UP_SKIN_3"; break;
                case 2: direct = "BOTTOM_SKIN_3"; break;
                case 3: direct = "LEFT_SKIN_3"; break;
                case 4: direct = "RIGHT_SKIN_3"; break;
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
