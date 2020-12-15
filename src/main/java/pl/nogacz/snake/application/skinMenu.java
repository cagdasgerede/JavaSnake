package pl.nogacz.snake.application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

public class SkinMenu {
    private String message;
    private static int skinTone = 1;
    private static int headSkin = 1;

    public SkinMenu(String message) {
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
            skinTone = 1;
            headSkin = 1;
        }
        else if (result.get() == skin2){
            skinTone = 2;
            headSkin = 2;
        }
        else if (result.get() == skin3){
            skinTone = 3;
            headSkin = 3;
        }
    }
    public static int getSkinTone() {
        return skinTone;
    }
    public static int getHeadSkin(){
        return headSkin;
    }
}
