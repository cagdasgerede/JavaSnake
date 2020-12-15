package pl.nogacz.snake.application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

public class KeyBindMenu {
    private String message;
    private static boolean isArrowKeymap = false;

    public KeyBindMenu(String message) {
        this.message = message;

        printDialog();
    }

    public void printDialog() {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("JavaSnake");
        alert.setContentText(message);

        ButtonType keymap1 = new ButtonType("WASD Keymap");
        ButtonType keymap2 = new ButtonType("Arrows Keymap");

        alert.getButtonTypes().setAll(keymap1, keymap2);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == keymap1) {
            isArrowKeymap = false;
            
        }
        else if(result.get() == keymap2) {
            isArrowKeymap = true;
        }
    }

    public static boolean getKeymap() {return isArrowKeymap;}
}
