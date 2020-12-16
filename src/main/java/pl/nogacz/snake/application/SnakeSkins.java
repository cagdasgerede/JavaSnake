package pl.nogacz.snake.application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

public class SnakeSkins {
    private String message;
    public enum bodySkinTones {
        SKIN_TONE_1,
        SKIN_TONE_2,
        SKIN_TONE_3
    }
    public enum headSkinTones {
        SKIN_TONE_1,
        SKIN_TONE_2,
        SKIN_TONE_3
    }
    private static bodySkinTones body = bodySkinTones.SKIN_TONE_1;
    private static headSkinTones head = headSkinTones.SKIN_TONE_1;

    public SnakeSkins(String message) {
        this.message = message;

        printDialog();
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
        if (result.get() == skin1){
            body = bodySkinTones.SKIN_TONE_1;
            head = headSkinTones.SKIN_TONE_1;
        }
        else if (result.get() == skin2){
            body = bodySkinTones.SKIN_TONE_2;
            head = headSkinTones.SKIN_TONE_2;
        }
        else if (result.get() == skin3){
            body = bodySkinTones.SKIN_TONE_3;
            head = headSkinTones.SKIN_TONE_3;
        }
    }
    public static bodySkinTones getBodySkin() {
        return body;
    }
    public static headSkinTones getHeadSkin(){
        return head;
    }
}
