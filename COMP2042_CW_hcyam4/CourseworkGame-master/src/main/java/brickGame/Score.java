package brickGame;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.io.*;
//import sun.plugin2.message.Message;

public class Score {
    private int score = 0;
    private int highScore;
    public String highScoreFile = "./highScore.txt";

    public Score() {
        score = 0;
        readHighScore();
    }
    public int getHighScore() {
        return highScore;
    }
    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }
    public void setScore(int newScore){
        score = newScore;
    }
    public int getScore(){
        return score;
    }
    public void incScore(int inc){
        score += inc;
    }
    private void readHighScore() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(highScoreFile));
            highScore = Integer.parseInt(reader.readLine());
            reader.close();
        } catch (FileNotFoundException e) {
            createHighScoreFile();
        } catch (IOException e) {
            System.out.println("Error in reading from file");
        }
    }
    private void createHighScoreFile() {
        try {
            FileWriter writer = new FileWriter(highScoreFile);
            writer.write(String.valueOf(highScore));
            writer.close();
        } catch (IOException e) {
            System.out.println("Error creating high score file");
        }
    }

    public boolean checkHighScore(int currentScore){
        if (currentScore>=highScore){
            highScore = currentScore;
            //display highscore menu
            try{
                BufferedWriter writer = new BufferedWriter(new FileWriter(highScoreFile));
                writer.write(Integer.toString(highScore));//CONVERT TO STRING
                writer.close();
            }catch(IOException e){
                System.out.println("Error in writing to file");
            }
            return true;
        }
    return false;
    }
    private void addLabel(final Label labelToAdd){
        // Remove the label from the scene after the animation
        Platform.runLater(() -> Main.root.getChildren().add(labelToAdd));
    }
    public void show(final double x, final double y, int score) {
        String sign;
        if (score >= 0) {
            sign = "+";
        } else {
            sign = "";
        }
        final Label scoreIncrement = new Label(sign + score);
        scoreIncrement.setTranslateX(x);
        scoreIncrement.setTranslateY(y);
        addLabel(scoreIncrement);
        SpecialEffects.playLabelAnimation(scoreIncrement,Main.root);

    }

    public void showMessage(String msg, final Main main) {
        final Label message = new Label(msg);
        message.setTranslateX(220);
        message.setTranslateY(340);

        addLabel(message);
        SpecialEffects.playLabelAnimation(message,main.root);

    }
}
