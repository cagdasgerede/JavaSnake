package pl.nogacz.snake.application;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import pl.nogacz.snake.board.BoardInfo;

public class SaveGame {

    BoardInfo BI;
    String fileName;

    public SaveGame(BoardInfo BI, String fileName){

        this.BI = BI;
        this.fileName = fileName;
    }

    public boolean startSave(){

        FileOutputStream fo;
        ObjectOutputStream out;
        
        String path = "src\\main\\SavedGames\\" + fileName;

        try{
            fo = new FileOutputStream(path);
            out = new ObjectOutputStream(fo);
            out.writeObject(BI);

            fo.close();
            out.close();
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }

        return true;
    }
    
}
