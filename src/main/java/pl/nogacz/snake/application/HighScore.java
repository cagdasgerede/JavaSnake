package pl.nogacz.snake.application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class HighScore {
    private static final File HIGH_SCORE_FILE = new File(Paths.get(".", "high.scores").toUri()); //text file, score and name per line, tab seperated
    private static final int HIGH_SCORE_COUNT = 10;

    private HighScore() { }

    private static ArrayList<Object[]> readScores() { // returns an ArrayList of [Integer, String]
        if (!HIGH_SCORE_FILE.exists()) {
            try {
                HIGH_SCORE_FILE.createNewFile();
            } catch (IOException e) {
                Logger.getLogger(HighScore.class.getName()).severe(e.getMessage());
            }
        }

        ArrayList<Object[]> scores = new ArrayList<>(HIGH_SCORE_COUNT);
        try {
            Scanner reader = new Scanner(HIGH_SCORE_FILE);
            Pattern pattern = Pattern.compile("[0-9]*\t.*");
            for (int i = 1; reader.hasNextLine(); i++) {
                String data = reader.nextLine();
                if (!pattern.matcher(data).matches()){
                    Logger.getLogger(HighScore.class.getName()).warning("Ignored line " + i + " from scoreboard file: \"" + data + "\"");
                    continue;
                }
                scores.add(new Object[] {Integer.valueOf(data.split("\t", 2)[0]), data.split("\t", 2)[1]});
            }
            reader.close();
        } catch (Exception e) {
            Logger.getLogger(HighScore.class.getName()).severe(e.getMessage());
        }
        return truncateScores(scores);
    }

    private static ArrayList<Object[]> truncateScores(ArrayList<Object[]> scores) {
        if (scores.size() > HIGH_SCORE_COUNT)
            scores = new ArrayList<>(scores.subList(0, HIGH_SCORE_COUNT));
        return scores;
    }

    public static boolean isHighScore(int score) {
        return readScores().stream().anyMatch(i -> (int) i[0] < score) || readScores().size() < HIGH_SCORE_COUNT;
    }

    public static void writeScore(String name, int score) {
        ArrayList<Object[]> scores = readScores();
        scores.add(new Object[] {score, name});
        scores.sort((o1, o2) -> (int) o2[0] - (int) o1[0]);
        scores = truncateScores(scores);
        try {
            FileWriter writer = new FileWriter(HIGH_SCORE_FILE, false);
            for (Object[] o : scores) {
                writer.write(o[0] + "\t" + o[1] + "\n");
            }
            writer.close();
        } catch (Exception e) {
            Logger.getLogger(HighScore.class.getName()).severe(e.getMessage());
        }
    }

    public static void showScores() {
        ArrayList<Object[]> scores = readScores();
        String stringOfScores = "";
        if (scores.size() > 0) {
            int numberOfDigits = (int) Math.floor(Math.log10((int) scores.get(0)[0])) + 1;
            stringOfScores = scores.stream().map(x -> String.format("[%0"+numberOfDigits+"d] %s", x)).collect(Collectors.joining("\n"));
        }

        Alert highscores = new Alert(Alert.AlertType.NONE, stringOfScores, ButtonType.CLOSE);
        highscores.setTitle("Highscores");
        highscores.showAndWait();
    }
}
