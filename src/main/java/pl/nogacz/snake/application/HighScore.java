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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;
import javafx.util.Callback;

public class HighScore {
    private static File HIGH_SCORE_FILE = new File(Paths.get(".", "high.scores").toUri()); //text file, score and name per line, tab seperated
    private static final int HIGH_SCORE_COUNT = 10;

    private HighScore() { }

    public static class Score {
        private int score;
        private String name;

        public Score(int score, String name) {
            this.score = score;
            this.name = name;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    static ArrayList<Object[]> readScores() { // returns an ArrayList of [Integer, String]
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
        scores.sort((o1, o2) -> (int) o2[0] - (int) o1[0]);
        return truncateScores(scores);
    }

    static ArrayList<Object[]> truncateScores(ArrayList<Object[]> scores) {
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

    private static TableView<Score> getScoreTable() {
        TableView<Score> table = new TableView<>();
        table.setEditable(true);

        TableColumn<Score, Integer> scoreColumn = new TableColumn<>("Score");
        TableColumn<Score, String> nameColumn = new TableColumn<>("Name");
        table.getColumns().addAll(scoreColumn, nameColumn);

        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        scoreColumn.setCellFactory(new Callback<TableColumn<Score, Integer>, TableCell<Score, Integer>>() {
            public TableCell<Score, Integer> call(TableColumn<Score, Integer> param) {
                return new TableCell<Score, Integer>() {
                    public void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!isEmpty()) {
                            setFont(new Font(20));
                            setText(item.toString());
                        }
                    }
                };
            }
        });
        nameColumn.setCellFactory(new Callback<TableColumn<Score, String>, TableCell<Score, String>>() {
            public TableCell<Score, String> call(TableColumn<Score, String> param) {
                return new TableCell<Score, String>() {
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!isEmpty()) {
                            setFont(new Font(20));
                            setText(item);
                        }
                    }
                };
            }
        });

        scoreColumn.setMinWidth(80);
        nameColumn.setMinWidth(250);

        ArrayList<Score> scores = (ArrayList<Score>) readScores().stream().map(o -> new Score((Integer) o[0], (String) o[1])).collect(Collectors.toList());
        table.getItems().addAll(scores);

        table.setPrefWidth(350);
        table.setPrefHeight(444);
        table.setEditable(false);

        return table;
    }

    public static void showScores() {
        Alert highscores = new Alert(Alert.AlertType.NONE, "", ButtonType.CLOSE);
        highscores.getDialogPane().setContent(getScoreTable());
        highscores.setTitle("Highscores");
        highscores.showAndWait();
    }
}
