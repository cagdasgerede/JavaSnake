package pl.nogacz.snake.application;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class NewGame {
    private final String message;

    public NewGame(String message) {
        this.message = message;
    }

    public int printDialog() {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Select Game Mode");
        alert.setContentText(message);

        //buttons
        ButtonType singlePlayer = new ButtonType("Single Player");
        ButtonType twoPlayer = new ButtonType("Two player");
        alert.getButtonTypes().setAll(singlePlayer, twoPlayer);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == singlePlayer) {
            return 0;
        } else if (result.get() == twoPlayer) {
            return 1;
        }
        return -1;
    }
}
