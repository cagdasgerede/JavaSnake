package pl.nogacz.snake.application;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;


public class AchievementList {
    private AchievementHandler trophy;

    public AchievementList(AchievementHandler trophy) {              
        this.trophy = trophy;

        printDialog();
    }

    public void printDialog() {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("ACHIEVEMENTS");
        alert.setContentText(trophy.toString());

        ButtonType reset = new ButtonType("RESET ACHIEVEMENTS!");
        ButtonType Continue = new ButtonType("Continue");

        alert.getButtonTypes().setAll(reset, Continue);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == reset){
            trophy.resetAchievements();

        } 

    }

}