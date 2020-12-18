package pl.nogacz.snake.application;


import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class AchievementHandler {

    private final String pathToAchievements = "src/main/resources/AchievementData/Achievements.txt";
    private final String pathToCounter = "src/main/resources/AchievementData/Counter.txt";
    private final int numberOfAchievementTypes=4;
    private Achievement[] trophies;
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
            Scanner sc =new Scanner(achievements);

            File counter = new File(pathToCounter);
            Scanner scnr = new Scanner(counter);

            for(int count = 0 ; count < numberOfAchievementTypes ; count++){
                int done = Integer.parseInt(sc.nextLine());
                double state = Double.parseDouble(scnr.nextLine());
                trophies[count].setAll(done , state);

            }

        }catch (Exception e){e.printStackTrace();}



    }
    public void achievementCheck(){
        boolean change = false;
        
        for(int count=0 ; count < numberOfAchievementTypes ; count++){
            if(trophies[count].check())
                change=true;

        }

        if(change) 
            updateAchievements();
        
    }

    public void updateAchievements(){                                           // Writes achievement status to Achievements.txt
        String achievements="";
        for(int count = 0 ; count < numberOfAchievementTypes ; count++){
            achievements += trophies[count].getAchieved() + "\n";

        }

        System.out.println(achievements);
        try{
            File table = new File(pathToAchievements);
            FileWriter fw = new FileWriter(table);
            fw.write(achievements);
            fw.close();
        }catch(Exception e){e.printStackTrace();}

    }
    public void updateProgress(){                                               // Writes progress status to Counter.txt
        String progs = "";
        for(int count = 0 ; count < numberOfAchievementTypes ; count++){
            progs += trophies[count].getProgress() + "\n";

        }

        System.out.println(progs);
        try{
            File table = new File(pathToCounter);
            FileWriter fw = new FileWriter(table);
            fw.write(progs);
            fw.close();
        }catch(Exception e){e.printStackTrace();}
    }


    public String toString(){                                   
        String toStr= "";
        
            for( int count = 0 ; count < numberOfAchievementTypes ; count++ ){

                toStr+=trophies[count].toString();
                
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