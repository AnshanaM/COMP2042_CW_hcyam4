package brickGame;

import javafx.application.Platform;
import javafx.scene.control.Label;
//import sun.plugin2.message.Message;

public class Score {
    private int score = 0;
    public Score(){
        score = 0;
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
    private void removeLabel(final Label labelToRemove,final Main main){
        // Remove the label from the scene after the animation
        Platform.runLater(() -> main.root.getChildren().remove(labelToRemove));
    }
    private void addLabel(final Label labelToAdd,final Main main){
        // Remove the label from the scene after the animation
        Platform.runLater(() -> main.root.getChildren().add(labelToAdd));
    }
    public void show(final double x, final double y, int score, final Main main) {
        String sign;
        if (score >= 0) {
            sign = "+";
        } else {
            sign = "";
        }
        final Label scoreIncrement = new Label(sign + score);
        scoreIncrement.setTranslateX(x);
        scoreIncrement.setTranslateY(y);

        addLabel(scoreIncrement,main);
        Animation.playAnimation(scoreIncrement,main.root);

    }

    public void showMessage(String msg, final Main main) {
        final Label message = new Label(msg);
        message.setTranslateX(220);
        message.setTranslateY(340);

        addLabel(message,main);
        Animation.playAnimation(message,main.root);

    }
}
