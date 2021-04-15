package pl.nogacz.snake.application.achievements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class AchievementControl {

    public static String content = "";
    public MovesTotal movesTotal;
    public ApplesTotal applesTotal;
    public TurnsTotal turnsTotal;
    public Deaths deaths;

    public Moves movesInOneGame;
    public Apples applesInOneGame;
    public Turns turnsInOneGame;

    public Spiral driftScore; // number of consecutive turns in the same side;

    public static ArrayList<Achievement> achievements = new ArrayList<>();


    public int apples = -1;

    public int driftRight = 0;
    public int driftLeft = 0;

    int [] tiers;
    int [] stats;

    public AchievementControl() throws FileNotFoundException {
        initializeAchievements();
    }
    public void snakeMoved(){
        movesTotal.updateProgress();
        movesInOneGame.updateProgress();
    }
    public void gameOver(){
        deaths.updateProgress();
    }
    public void eatApple(){
        applesTotal.updateProgress();
        applesInOneGame.updateProgress();
    }
    public void turned(){
        turnsTotal.updateProgress();
        turnsInOneGame.updateProgress();
    }public void handleDrift(int left, int right){
        if(left > driftScore.progress || right > driftScore.progress){
            driftScore.updateProgress();
        }
    }
    public void loadTiers() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("src/main/java/pl/nogacz/snake/application/achievements/Tiers.txt"));
        int i = 0;
        while(sc.hasNext()){
            tiers[i++]=sc.nextInt();
        }
        sc.close();
    }public void loadStats() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("src/main/java/pl/nogacz/snake/application/achievements/Stats.txt"));
        int i = 0;
        while(sc.hasNext()){
            stats[i++]=sc.nextInt();
        }
        sc.close();
    }
    public void initializeAchievements() throws FileNotFoundException {
        achievements = new ArrayList<>();
        achievements.add(movesTotal = new MovesTotal("Traveller Snake",false, 1, new int[]{500,2500,10000}, 0 ));
        achievements.add(applesTotal = new ApplesTotal("Eater of Worlds",false, 1, new int[]{15,45,225}, 0 ));
        achievements.add(turnsTotal = new TurnsTotal("Adventurer Snake",false, 1, new int[]{20,80,300}, 0 ));
        achievements.add(deaths = new Deaths("Zombie Worm",false, 1, new int[]{3,10,90}, 0 ));
        achievements.add(movesInOneGame = new Moves("Survivor Snake",false, 1, new int[]{25,400,2000}, 0 ));
        achievements.add(applesInOneGame = new Apples("Apple Worm",false, 1, new int[]{5,25,75}, 0 ));
        achievements.add(turnsInOneGame = new Turns("Snakes and Ladders",false, 1, new int[]{8,64,512}, 0 ));
        achievements.add(driftScore = new Spiral("Drift Worm",false, 1, new int[]{4,9,16}, 0 ));
        tiers = new int[achievements.size()];
        stats = new int[achievements.size()];
        loadTiers();
        loadStats();
        for (int i = 0; i <achievements.size() ; i++) {
            achievements.get(i).progress = stats[i];
            achievements.get(i).tier = tiers[i];
            if(tiers[i] > 3){
                achievements.get(i).achieved = true;
            }
            achievements.get(i).initializeDescription(achievements.get(i));
        }

    }public static void updateContent(){
        content = "";
        for (int i = 0; i < achievements.size(); i++) {
            content += achievements.get(i).toString()+"\n";
        }
    }
    public void deleteInOneGameStats(){
        movesInOneGame.progress = 0;
        applesInOneGame.progress = 0;
        turnsInOneGame.progress = 0;
    }

    public void saveStats() throws IOException {

        deleteInOneGameStats();
        FileWriter fr = new FileWriter("src/main/java/pl/nogacz/snake/application/achievements/Stats.txt");
        String outputContent = "";
        for (int i = 0; i < achievements.size(); i++) {
            outputContent += achievements.get(i).progress+"\n";
        }
        fr.write(outputContent);
        fr.close();
    }
    public void saveTiers() throws IOException {

        FileWriter fr = new FileWriter("src/main/java/pl/nogacz/snake/application/achievements/Tiers.txt");
        String outputContent = "";
        for (int i = 0; i < achievements.size(); i++) {
            outputContent += achievements.get(i).tier+"\n";
        }
        fr.write(outputContent);
        fr.close();
    }
}
