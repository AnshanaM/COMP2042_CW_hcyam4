/**
 * handles score modification and getting
 * also handles file manipulation for highscore
 */
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

    /**
     * Gets the singleton instance of the Score class.
     *
     * @return The singleton instance of the Score class
     */
    public static Score getInstance() {
        if (instance == null) {
            instance = new Score();
        }
        return instance;
    }
    /**
     * Sets the current score to a new value.
     *
     * @param newScore The new score value
     */
    public static void setScore(int newScore) {
        score = newScore;
    }
    /**
     * Gets the current score.
     *
     * @return The current score
     */
    public static int getScore() {
        return score;
    }
    /**
     * Increases the current score by a specified amount.
     *
     * @param inc The amount by which to increase the score
     */
    public static void incScore(int inc) {
        score += inc;
    }
    /**
     * Gets the current high score.
     *
     * @return The current high score
     */
    public static int getHighScore() {
        return highScore;
    }
    /**
     * Sets the high score to a new value.
     *
     * @param newHighScore The new high score value
     */
    public static void setHighScore(int newHighScore) {
        highScore = newHighScore;
    }

    /**
     * Reads the high score from the high score file.
     * If the file is not found, creates the file with the default high score value.
     */
    private static void readHighScore() {
        try (BufferedReader reader = new BufferedReader(new FileReader(highScoreFile))) {
            highScore = Integer.parseInt(reader.readLine());
        } catch (FileNotFoundException e) {
            createHighScoreFile();
        } catch (IOException e) {
            System.out.println("Error in reading from file");
        }
    }

    /**
     * Creates the high score file with the default high score value.
     */
    private static void createHighScoreFile() {
        try (FileWriter writer = new FileWriter(highScoreFile)) {
            writer.write(String.valueOf(highScore));
        } catch (IOException e) {
            System.out.println("Error creating high score file");
        }
    }
    /**
     * Checks if the current score is greater than or equal to the high score.
     * If true, updates the high score and writes it to the high score file.
     *
     * @return True if the current score is a new high score, false otherwise
     */
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
