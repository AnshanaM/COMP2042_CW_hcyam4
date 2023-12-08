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
//        new Thread(() -> {
//            for (int i = 0; i < 21; i++) {
//                try {
//                    scoreIncrement.setScaleX(i);
//                    scoreIncrement.setScaleY(i);
//                    scoreIncrement.setOpacity((20 - i) / 20.0);
//                    Thread.sleep(5);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            removeLabel(scoreIncrement,main);
//        }).start();

    }

    public void showMessage(String msg, final Main main) {
        final Label message = new Label(msg);
        message.setTranslateX(220);
        message.setTranslateY(340);

        addLabel(message,main);
        Animation.playAnimation(message,main.root);

    }

//    public void showGameOver(final Main main) {
//        Platform.runLater(() -> {
//            Label gameOver = new Label("Game Over :(");
//            gameOver.setTranslateX(200);
//            gameOver.setTranslateY(250);
//            gameOver.setScaleX(2);
//            gameOver.setScaleY(2);
////            backToMenu.setVisible(true);
////            main.retry.setVisible(true);
//            main.root.getChildren().addAll(gameOver);
//
//        });
//    }

//    public void showWin(final Main main) {
//        Platform.runLater(() -> {
//            Label youWin = new Label("You Win :)");
//            youWin.setTranslateX(200);
//            youWin.setTranslateY(250);
//            youWin.setScaleX(2);
//            youWin.setScaleY(2);
//
//            main.initMenus.mainMenu.setVisible(true);
//            main.root.getChildren().add(youWin);
//
//        });
//    }
}
