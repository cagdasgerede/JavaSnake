package pl.nogacz.snake.application;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Scanner;

import pl.nogacz.snake.board.Board;
import pl.nogacz.snake.board.BoardInfo;

public class LoadGame {

    Board board;

    public LoadGame(Board board){

        this.board = board;
    }

    public boolean startLoad(){

        FileInputStream fi;
        ObjectInputStream in;

        String[] fileNames;
        File f = new File("src\\main\\SavedGames");

        fileNames = f.list();

        int place = 1;

        for(String name : fileNames){

            System.out.println(place++ + ".  "+name);
        }

        String choosedFile = fileNames[0];
        String path = "src\\main\\SavedGames\\" + choosedFile;

        try{

            fi = new FileInputStream(path);
            in = new ObjectInputStream(fi);

            BoardInfo BI = (BoardInfo)in.readObject();

            in.close();
            fi.close();

            board.setParameters(BI.getBoard(), BI.getDirection(), BI.getTailLength(), BI.getHeadCoordinates(), BI.getHeadClass(), BI.getBodyClass(), BI.getFoodClass(), BI.getSnakeTail());
        } catch (Exception e){

            return false;
        }

        return true;
        
    }
    
}
