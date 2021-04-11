package pl.nogacz.snake.application;

import com.sun.org.apache.xalan.internal.xsltc.trax.XSLTCSource;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.event.EventHandler;

import pl.nogacz.snake.Snake;

import java.awt.event.ActionEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * @author Dawid Nogacz on 19.05.2019
 */
public class EndGame {
    private String message;

    public EndGame(String message,ArrayList<String> scr) {
        this.message = message;
        int score;
        score=Integer.parseInt(message.substring(21,message.indexOf("points")-1));
        String tmp;
        int sira=0;
        if(scr.size()==1){
            getPlayerName(1, scr, score);
        }else {
            for (int i = 1; i < scr.size(); i++) {
                tmp = scr.get(i).substring(scr.get(i).lastIndexOf(' ') + 1);
                if (scr.size() <= 5) {
                    if (score >= Integer.parseInt(tmp)) {
                        sira = i;
                        getPlayerName(sira, scr, score);
                        break;
                    }
                    if (i == scr.size()-1) {
                        sira = i+1;
                        getPlayerName(sira, scr, score);
                        break;
                    }
                }

                else if (score >= Integer.parseInt(tmp)) {
                    sira = i;
                    getPlayerName(sira, scr, score);
                    break;
                }
            }
        }
        printDialog();
    }
    public void getPlayerName(int sira,ArrayList<String> scr,int score){
        TextInputDialog dialog = new TextInputDialog("Name");
        dialog.setTitle("Player Information");
        dialog.setHeaderText("Please enter your name:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            scoreBoardChanges(sira,scr,result.get(),score);
        }

    }

    public void scoreBoardChanges(int sira,ArrayList<String> scr,String name,int score){
        if(scr.size()==6) {
            scr.add(sira, sira + "- " + name + "   " + score);
            String tmp;
            for (int i = sira + 1; i < scr.size(); i++) {
                tmp = scr.get(i);
                scr.remove(i);
                tmp = tmp.substring(1);
                tmp = i + tmp;
                scr.add(i, tmp);
            }

            if (scr.size() > 5) {
                scr.remove(6);
            }
        }else{
            scr.add(sira, sira + "- " + name + "   " + score);
            String tmp;
            for (int i = sira + 1; i < scr.size(); i++) {
                tmp = scr.get(i);
                scr.remove(i);
                tmp = tmp.substring(1);
                tmp = i + tmp;
                scr.add(i, tmp);
            }
        }
        try {
            FileWriter myWriter = new FileWriter("HighScores");
            for (int i = 0; i < scr.size(); i++) {
                myWriter.write(scr.get(i)+'\n');
            }

            myWriter.close();
            System.out.println("Successfully wrote to the HighScores file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    public void printDialog() {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("JavaSnake");
        alert.setContentText(message);

        ButtonType newGameButton = new ButtonType("New game");
        ButtonType exitButton = new ButtonType("Exit");

        alert.getButtonTypes().setAll(newGameButton, exitButton);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == newGameButton){
            newGame();
        } else {
            System.exit(0);
        }
    }

    public void newGame() {
        restartApplication();
    }

    private void restartApplication()
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
