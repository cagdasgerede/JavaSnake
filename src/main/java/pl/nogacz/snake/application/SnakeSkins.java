package pl.nogacz.snake.application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

public class SnakeSkins {
    private String message;
    public enum bodySkinTones {
        SKIN_TONE_DEFAULT,
        SKIN_TONE_ORANGE,
        SKIN_TONE_GREEN
    }
    public enum headSkinTones {
        SKIN_TONE_DEFAULT,
        SKIN_TONE_ORANGE,
        SKIN_TONE_GREEN
    }
    private static bodySkinTones body = bodySkinTones.SKIN_TONE_DEFAULT;
    private static headSkinTones head = headSkinTones.SKIN_TONE_DEFAULT;

    public SnakeSkins(String message) {
        this.message = message;

        printDialog();
    }
    
    public SnakeSkins() {
        super();
    }

    public void printDialog() {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("JavaSnake");
        alert.setContentText(message);

        ButtonType skin1 = new ButtonType("Skin 1");
        ButtonType skin2 = new ButtonType("Skin 2");
        ButtonType skin3 = new ButtonType("Skin 3");

        alert.getButtonTypes().setAll(skin1, skin2, skin3);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == skin1) {
            body = bodySkinTones.SKIN_TONE_DEFAULT;
            head = headSkinTones.SKIN_TONE_DEFAULT;
        }
        else if (result.get() == skin2) {
            body = bodySkinTones.SKIN_TONE_ORANGE;
            head = headSkinTones.SKIN_TONE_ORANGE;
        }
        else if (result.get() == skin3) {
            body = bodySkinTones.SKIN_TONE_GREEN;
            head = headSkinTones.SKIN_TONE_GREEN;
        }
    }

    public void setBodySkin(bodySkinTones skin) {
        SnakeSkins.body = skin;
    }

    public void setHeadSkin(headSkinTones skin) {
        SnakeSkins.head = skin;
    }

    public static bodySkinTones getBodySkin() {
        return body;
    }

    public static headSkinTones getHeadSkin() {
        return head;
    }

}
