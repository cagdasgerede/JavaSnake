package pl.nogacz.snake.application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

public class KeyMap {
    private String message;
    private static boolean isWASD = true;

    public KeyMap(String message) {
        this.message = message;

        printDialog();
    }

    public void printDialog() {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("JavaSnake");
        alert.setContentText(message);

        ButtonType wasdKeymap = new ButtonType("WASD Keymap");
        ButtonType arrowsKeymap = new ButtonType("Arrows Keymap");

        alert.getButtonTypes().setAll(wasdKeymap, arrowsKeymap);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == wasdKeymap) {
            isWASD = true;
        }
        else if(result.get() == arrowsKeymap) {
            isWASD = false;
        }
    }

    public static boolean getIsWASD() {
        return isWASD;
    }

}
