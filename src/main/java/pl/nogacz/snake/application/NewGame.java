package pl.nogacz.snake.application;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import pl.nogacz.snake.Snake;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;


public class NewGame {
    
    private String message;

    public NewGame(String message) {
        this.message = message;
    }

    public int printDialog() {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Select Game Mode");
        alert.setContentText(message);

        ButtonType singlePlayer = new ButtonType("Single Player");
        ButtonType twoPlayer = new ButtonType("Two player");

        alert.getButtonTypes().setAll(singlePlayer, twoPlayer);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == singlePlayer){
            return 0;
        }else if (result.get() == twoPlayer){
            return 1;
        } 
        
        return -1;
    }

    public void newGame() {
        startApplication();
    }

    private void startApplication()
    {
        try {
            final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
            final File currentJar = new File(Snake.class.getProtectionDomain().getCodeSource().getLocation().toURI());

            /* is it a jar file? */
            if(!currentJar.getName().endsWith(".jar"))
                return;

            /* Build command: java -jar application.jar */
            final ArrayList<String> command = new ArrayList<String>();
            command.add(javaBin);
            command.add("-jar");
            command.add(currentJar.getPath());

            final ProcessBuilder builder = new ProcessBuilder(command);
            builder.start();
            System.exit(0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
