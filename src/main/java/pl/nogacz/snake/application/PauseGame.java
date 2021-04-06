package pl.nogacz.snake.application;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import pl.nogacz.snake.board.Board;
import pl.nogacz.snake.board.Coordinates;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.zip.CRC32;

import static pl.nogacz.snake.board.Board.isPressedP;

public class PauseGame {
    Board board;

    public PauseGame(Board board){
        this.board=board;
        saveOrExit();
    }
    public void saveOrExit(){
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Paused game");
        alert.setContentText("You have paused the game. Do you want to save?");
        ButtonType saveButton = new ButtonType("Save");
        ButtonType resumeButton = new ButtonType("Resume");
        alert.getButtonTypes().setAll(saveButton, resumeButton);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == saveButton){
            final DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Choose directory to save");
            final File selectedDirectory = directoryChooser.showDialog(new Stage());
            String path="";
            if (selectedDirectory != null) {
                path=selectedDirectory.getAbsolutePath();
            }
            System.out.println(path);
            try {
                FileWriter writer=new FileWriter(path+"/save.txt");
                //Data starts
                int direction =Board.getDirection();
                Coordinates foodCoordinates= board.getFoodCoordinates();
                int tailLength = board.getTailLength();
                Coordinates snakeHeadCoordinates = board.getSnakeHeadCoordinates();
                ArrayList<Coordinates> snakeTail = board.getSnakeTail();
                //Data ends

                CRC32 crc= new CRC32();

                crc.update(direction);
                String data=direction+"_"+crc.getValue()+"_";

                crc.update(foodCoordinates.getX());
                data+=foodCoordinates.getX()+"_"+crc.getValue()+"_";

                crc.update(foodCoordinates.getY());
                data+=foodCoordinates.getY()+"_"+crc.getValue()+"_";

                crc.update(tailLength);
                data+=tailLength+"_"+crc.getValue()+"_";


                crc.update(snakeHeadCoordinates.getX());
                data+=snakeHeadCoordinates.getX()+"_"+crc.getValue()+"_";

                crc.update(snakeHeadCoordinates.getY());
                data+=snakeHeadCoordinates.getY()+"_"+crc.getValue()+"_";

                for(Coordinates c: snakeTail){
                    crc.update(c.getX());
                    data+=c.getX()+"_"+crc.getValue()+"_";
                    crc.update(c.getY());
                    data+=c.getY()+"_"+crc.getValue()+"_";
                }
                data=data.substring(0,data.length()-1);
                writer.write(data);
                writer.close();
                System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            isPressedP =false;
        }
    }
}
