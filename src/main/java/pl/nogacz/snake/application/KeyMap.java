package pl.nogacz.snake.application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

public class KeyMap {
    private String message;

    public KeyMap(String message) {
        this.message = message;

        printDialog();
    }

    public void printDialog() {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("JavaSnake");
        alert.setContentText(message);

        ButtonType upButton = new ButtonType("Up");
        ButtonType rightButton = new ButtonType("Right");
        ButtonType downButton = new ButtonType("Down");
        ButtonType leftButton = new ButtonType("Left");

        alert.getButtonTypes().setAll(upButton, rightButton, downButton, leftButton);

        Optional<ButtonType> result = alert.showAndWait();
        /*
        if (result.get() == upButton) {
            alert.getButtonTypes().
        }
        else if(result.get() == rightButton) {

        }
        else if(result.get() == downButton) {
            
        }
        else if(result.get() == leftButton) {
            
        }
        */
    }

}
