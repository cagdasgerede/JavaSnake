package pl.nogacz.snake.application;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static pl.nogacz.snake.application.HighScore.*;

public class HighScoreTest {
    private Object[][] defaultHighScores() {
        return new Object[][]{{11, "A"}, {9, "B"}, {8, "C"}, {7, "D"}, {6, "E"}, {5, "F"}, {4, "G"}, {3, "H"}, {2, "I"}, {1, "J"}};
    }

    private Object[][] overflownHighScores() {
        return new Object[][]{{11, "A"}, {10, "K"}, {9, "B"}, {8, "C"}, {7, "D"}, {6, "E"}, {5, "F"}, {4, "G"}, {3, "H"}, {2, "I"}, {1, "J"}};
    }

    private  Object[][] nonFullHighScores() {
        return new Object[][]{{11, "A"}, {9, "B"}, {8, "C"}, {7, "D"}, {6, "E"}, {5, "F"}, {4, "G"}, {3, "H"}};
    }

    private void writeToTestFile() {
        writeToTestFile(defaultHighScores());
    }

    private void writeToTestFile(Object[]... scores) {
        writeToTestFile(-1, scores);
    }

    private void writeToTestFile(int tampered, Object[]... scores) {
        try {
            Field highScoreFile = HighScore.class.getDeclaredField("HIGH_SCORE_FILE");
            highScoreFile.setAccessible(true);
            File scoreFile = (File) highScoreFile.get(null);
            FileWriter writer = new FileWriter(scoreFile, false);
            for (int i = 0; i < scores.length; i++) {
                if (i == tampered) {
                    writer.write("a" + scores[i][0] + "\t" + scores[i][1] + "\n");
                } else {
                    writer.write(scores[i][0] + "\t" + scores[i][1] + "\n");
                }
            }
            writer.close();
            highScoreFile.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @BeforeClass
    public static void setup() throws Exception {
        File temp = File.createTempFile("HIGH_SCORE",".TEST");
        temp.deleteOnExit();
        Field scoreFile = HighScore.class.getDeclaredField("HIGH_SCORE_FILE");
        scoreFile.setAccessible(true);
        scoreFile.set(null, temp);
    }

    @AfterClass
    public static void teardown() throws Exception {
        Field scoreFile = HighScore.class.getDeclaredField("HIGH_SCORE_FILE");
        scoreFile.setAccessible(true);
        scoreFile.set(null, new File(Paths.get(".", "high.scores").toUri()));
        scoreFile.setAccessible(false);
    }

    @Test
    public void readScoresCorrect() {
        writeToTestFile(new Object[][]{{3,"A"}, {2, "B"}, {1, "C"}});
        ArrayList<Object[]> scores = readScores();

        Assert.assertEquals(3, scores.size());
        Assert.assertEquals(3, (int) scores.get(0)[0]);
        Assert.assertEquals("A", scores.get(0)[1]);
        Assert.assertEquals(2, (int) scores.get(1)[0]);
        Assert.assertEquals("B", scores.get(1)[1]);
        Assert.assertEquals(1, (int) scores.get(2)[0]);
        Assert.assertEquals("C", scores.get(2)[1]);
    }

    @Test
    public void readScoresUnsorted() {
        writeToTestFile(new Object[][]{{1,"A"}, {2, "B"}, {3, "C"}});
        ArrayList<Object[]> scores = readScores();

        Assert.assertEquals(3, scores.size());
        Assert.assertEquals(3, (int) scores.get(0)[0]);
        Assert.assertEquals("C", scores.get(0)[1]);
        Assert.assertEquals(2, (int) scores.get(1)[0]);
        Assert.assertEquals("B", scores.get(1)[1]);
        Assert.assertEquals(1, (int) scores.get(2)[0]);
        Assert.assertEquals("A", scores.get(2)[1]);
    }

    @Test
    public void readScoresTampered() {
        writeToTestFile(1, new Object[][]{{3,"A"}, {2, "B"}, {1, "C"}});
        ArrayList<Object[]> scores = readScores();

        Assert.assertEquals(2, scores.size());
        Assert.assertEquals(3, (int) scores.get(0)[0]);
        Assert.assertEquals("A", scores.get(0)[1]);
        Assert.assertEquals(1, (int) scores.get(1)[0]);
        Assert.assertEquals("C", scores.get(1)[1]);
    }

    @Test
    public void readScoresOverflown() {
        writeToTestFile(overflownHighScores());
        ArrayList<Object[]> scores = readScores();

        Assert.assertEquals(10, scores.size());
        Assert.assertEquals(11, (int) scores.get(0)[0]);
        Assert.assertEquals("A", scores.get(0)[1]);
        Assert.assertEquals(10, (int) scores.get(1)[0]);
        Assert.assertEquals("K", scores.get(1)[1]);
        Assert.assertEquals(9, (int) scores.get(2)[0]);
        Assert.assertEquals("B", scores.get(2)[1]);
        Assert.assertEquals(8, (int) scores.get(3)[0]);
        Assert.assertEquals("C", scores.get(3)[1]);
        Assert.assertEquals(7, (int) scores.get(4)[0]);
        Assert.assertEquals("D", scores.get(4)[1]);
        Assert.assertEquals(6, (int) scores.get(5)[0]);
        Assert.assertEquals("E", scores.get(5)[1]);
        Assert.assertEquals(5, (int) scores.get(6)[0]);
        Assert.assertEquals("F", scores.get(6)[1]);
        Assert.assertEquals(4, (int) scores.get(7)[0]);
        Assert.assertEquals("G", scores.get(7)[1]);
        Assert.assertEquals(3, (int) scores.get(8)[0]);
        Assert.assertEquals("H", scores.get(8)[1]);
        Assert.assertEquals(2, (int) scores.get(9)[0]);
        Assert.assertEquals("I", scores.get(9)[1]);
    }

    @Test
    public void truncateScoresMoreThanTen() {
        ArrayList<Object[]> scores = truncateScores(new ArrayList<>(Arrays.asList(overflownHighScores())));

        Assert.assertEquals(10, scores.size());
        Assert.assertEquals(11, (int) scores.get(0)[0]);
        Assert.assertEquals("A", scores.get(0)[1]);
        Assert.assertEquals(10, (int) scores.get(1)[0]);
        Assert.assertEquals("K", scores.get(1)[1]);
        Assert.assertEquals(9, (int) scores.get(2)[0]);
        Assert.assertEquals("B", scores.get(2)[1]);
        Assert.assertEquals(8, (int) scores.get(3)[0]);
        Assert.assertEquals("C", scores.get(3)[1]);
        Assert.assertEquals(7, (int) scores.get(4)[0]);
        Assert.assertEquals("D", scores.get(4)[1]);
        Assert.assertEquals(6, (int) scores.get(5)[0]);
        Assert.assertEquals("E", scores.get(5)[1]);
        Assert.assertEquals(5, (int) scores.get(6)[0]);
        Assert.assertEquals("F", scores.get(6)[1]);
        Assert.assertEquals(4, (int) scores.get(7)[0]);
        Assert.assertEquals("G", scores.get(7)[1]);
        Assert.assertEquals(3, (int) scores.get(8)[0]);
        Assert.assertEquals("H", scores.get(8)[1]);
        Assert.assertEquals(2, (int) scores.get(9)[0]);
        Assert.assertEquals("I", scores.get(9)[1]);
    }

    @Test
    public void truncateScoresMoreThanTenUnsorted() {
        ArrayList<Object[]> scores = new ArrayList<>(Arrays.asList(overflownHighScores()));
        Collections.reverse(scores);
        scores = truncateScores(scores);

        Assert.assertEquals(10, scores.size());
        Assert.assertEquals(1, (int) scores.get(0)[0]);
        Assert.assertEquals("J", scores.get(0)[1]);
        Assert.assertEquals(2, (int) scores.get(1)[0]);
        Assert.assertEquals("I", scores.get(1)[1]);
        Assert.assertEquals(3, (int) scores.get(2)[0]);
        Assert.assertEquals("H", scores.get(2)[1]);
        Assert.assertEquals(4, (int) scores.get(3)[0]);
        Assert.assertEquals("G", scores.get(3)[1]);
        Assert.assertEquals(5, (int) scores.get(4)[0]);
        Assert.assertEquals("F", scores.get(4)[1]);
        Assert.assertEquals(6, (int) scores.get(5)[0]);
        Assert.assertEquals("E", scores.get(5)[1]);
        Assert.assertEquals(7, (int) scores.get(6)[0]);
        Assert.assertEquals("D", scores.get(6)[1]);
        Assert.assertEquals(8, (int) scores.get(7)[0]);
        Assert.assertEquals("C", scores.get(7)[1]);
        Assert.assertEquals(9, (int) scores.get(8)[0]);
        Assert.assertEquals("B", scores.get(8)[1]);
        Assert.assertEquals(10, (int) scores.get(9)[0]);
        Assert.assertEquals("K", scores.get(9)[1]);
    }

    @Test
    public void truncateScoresLessThanTen() {
        ArrayList<Object[]> scores = truncateScores(new ArrayList<>(Arrays.asList(new Object[][]{{2, "B"}, {1, "C"}, {3, "A"}})));

        Assert.assertEquals(3, scores.size());
        Assert.assertEquals(2, (int) scores.get(0)[0]);
        Assert.assertEquals("B", scores.get(0)[1]);
        Assert.assertEquals(1, (int) scores.get(1)[0]);
        Assert.assertEquals("C", scores.get(1)[1]);
        Assert.assertEquals(3, (int) scores.get(2)[0]);
        Assert.assertEquals("A", scores.get(2)[1]);
    }

    @Test
    public void isHighScoreEqual() {
        writeToTestFile();
        Assert.assertTrue(isHighScore(5));
    }

    @Test
    public void isHighScoreNotInBoard() {
        writeToTestFile();
        Assert.assertTrue(isHighScore(10));
    }

    @Test
    public void isNotHighScore() {
        writeToTestFile();
        Assert.assertFalse(isHighScore(0));
    }

    @Test
    public void isNotHighScoreEqual() {
        writeToTestFile();
        Assert.assertFalse(isHighScore(1));
    }

    @Test
    public void isHighScoreAbove() {
        writeToTestFile();
        Assert.assertTrue(isHighScore(12));
    }

    @Test
    public void isHighScoreLessThanTen() {
        writeToTestFile(nonFullHighScores());
        Assert.assertTrue(isHighScore(1));
    }

    @Test
    public void isHighScoreEqualLessThanTen() {
        writeToTestFile(nonFullHighScores());
        Assert.assertTrue(isHighScore(3));
    }

    @Test
    public void writeScoreLessThanTen() {
        writeToTestFile(nonFullHighScores());
        writeScore("TEST", 1);
        ArrayList<Object[]> scores = readScores();

        Assert.assertEquals(9, scores.size());
        Assert.assertEquals(1, (int) scores.get(8)[0]);
        Assert.assertEquals("TEST", scores.get(8)[1]);
    }

    @Test
    public void writeScoreMoreThanTen() {
        writeToTestFile();
        writeScore("TEST", 10);
        ArrayList<Object[]> scores = readScores();

        Assert.assertEquals(10, scores.size());
        Assert.assertEquals(10, (int) scores.get(1)[0]);
        Assert.assertEquals("TEST", scores.get(1)[1]);
        Assert.assertEquals(2, (int) scores.get(9)[0]);
        Assert.assertEquals("I", scores.get(9)[1]);
    }
}