package pl.nogacz.snake.application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class AchievementHandler {
    
    private final String pathToAchievements = "AchievementData/Achievements.txt";
    private final String pathToCounter = "AchievementData/Counter.txt";
    private final int numberOfAchievementTypes = 4;
    private Achievement[] trophies;
    private LogWriter errorLog = new LogWriter();
    Design design;

    public AchievementHandler(Design design){         // Constructor loads achievement status from src/main/resources/AchievementData folder
        this.design = design;
        trophies = new Achievement[numberOfAchievementTypes];

        trophies[0] = new ApplesCollected(); 
        trophies[1] = new NumberOfTurns();
        trophies[2] = new Playtime();
        trophies[3] = new SnakeLength();

        try{
            File achievements = new File(pathToAchievements);
            Scanner sc = new Scanner(achievements);

            File counter = new File(pathToCounter);
            Scanner scnr = new Scanner(counter);

            for(int count = 0 ; count < numberOfAchievementTypes ; count++){
                int done = Integer.parseInt(sc.nextLine());
                double state = Double.parseDouble(scnr.nextLine());
                trophies[count].setAll(done , state);
            }
        }catch(IOException e){
            errorLog.write("Error on AchievementHandler constructor");
            
        }

    }

    public void achievementCheck(){
        boolean change = false;
        
        for(int count = 0 ; count < numberOfAchievementTypes ; count++){
            if(trophies[count].check())
                change = true;
        }

        if(change) 
            updateAchievements();
        
    }

    public void updateAchievements(){                                           // Writes achievement status to Achievements.txt
        String achievements = "";
        for(int count = 0 ; count < numberOfAchievementTypes ; count++){
            achievements += trophies[count].getAchieved() + "\n";
        }

        try{
            File table = new File(pathToAchievements);
            FileWriter fw = new FileWriter(table);
            fw.write(achievements);
            fw.close();

        }catch(IOException ioException){
            errorLog.write("Achievements.txt not found");

        }

    }

    public void updateProgress(){                                               // Writes progress status to Counter.txt
        String progs = "";
        for(int count = 0 ; count < numberOfAchievementTypes ; count++){
            progs += trophies[count].getProgress() + "\n";
        }
    
        try{
            File table = new File(pathToCounter);
            FileWriter fw = new FileWriter(table);
            fw.write(progs);
            fw.close();

        }catch(IOException ioException){
            errorLog.write("Counter.txt not found");
            
        }
    }

    public String toString(){                                   
        String toStr = "";
        
            for( int count = 0 ; count < numberOfAchievementTypes ; count++ ){
                toStr += trophies[count].toString(); 
            }

        return toStr;
    }

    public void resetAchievements(){
        for(int count = 0 ; count < 4 ; count++){
            trophies[count].reset();
        }
        updateAchievements();
        updateProgress();
    }

    public void addProgress(int id){
        trophies[id].add();
    }

}
