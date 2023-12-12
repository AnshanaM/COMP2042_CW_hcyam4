package brickGame;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.io.*;

public class Score {
    private static Score instance;
    private static int score;
    private static int highScore;
    private static final String highScoreFile = "./highScore.txt";

    private Score() {
        readHighScore();
        score = 0;
    }

    public static Score getInstance() {
        if (instance == null) {
            instance = new Score();
        }
        return instance;
    }

    public static void setScore(int newScore) {
        score = newScore;
    }

    public static int getScore() {
        return score;
    }

    public static void incScore(int inc) {
        score += inc;
    }

    public static int getHighScore() {
        return highScore;
    }

    public static void setHighScore(int newHighScore) {
        highScore = newHighScore;
    }

    private static void readHighScore() {
        try (BufferedReader reader = new BufferedReader(new FileReader(highScoreFile))) {
            highScore = Integer.parseInt(reader.readLine());
        } catch (FileNotFoundException e) {
            createHighScoreFile();
        } catch (IOException e) {
            System.out.println("Error in reading from file");
        }
    }

    private static void createHighScoreFile() {
        try (FileWriter writer = new FileWriter(highScoreFile)) {
            writer.write(String.valueOf(highScore));
        } catch (IOException e) {
            System.out.println("Error creating high score file");
        }
    }

    public static boolean checkHighScore() {
        if (score >= highScore) {
            setHighScore(score);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(highScoreFile))) {
                writer.write(Integer.toString(highScore));
            } catch (IOException e) {
                System.out.println("Error in writing to file");
            }
            return true;
        }
        return false;
    }
}
