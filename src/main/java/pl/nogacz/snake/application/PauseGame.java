package pl.nogacz.snake.application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

public class PauseGame {
    private String message;

    public PauseGame(String message) {
        this.message = message;
        printDialog();
    }

    public void printDialog() {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Settings");
        alert.setContentText(message);

        ButtonType continueButton = new ButtonType("Continue");
        ButtonType skinButton = new ButtonType("Select Snake Skin");
        ButtonType keyBindButton = new ButtonType("Key Bindings");

        alert.getButtonTypes().setAll(continueButton, skinButton, keyBindButton);

        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == continueButton) {
            alert.close();
        }
        else if(result.get() == skinButton) {
            new SnakeSkins("Select Skin");
        }
        else if(result.get() == keyBindButton) {
            new KeyMap("Choose a direction and press a button to set");
        }
    }
}
