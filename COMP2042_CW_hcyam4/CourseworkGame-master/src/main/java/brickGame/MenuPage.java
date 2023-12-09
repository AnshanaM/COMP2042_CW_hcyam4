package brickGame;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.stage.Stage;

public class MenuPage {
    public static VBox mainMenu;
    public VBox gameOverMenu;
    public VBox winMenu;
    public static HBox gamePlayStats;
    public static Label scoreLabel, heartLabel, levelLabel, title, youWin, gameOver;
    public static Button load, newGame, tutorial, backToMenu, nextLevel, retry;

    public MenuPage(){
        mainMenuInit();
        gamePlayScreenInit();
        gameOverMenuInit();
        winMenuInit();
    }
    public void mainMenuInit(){
        title = new Label("BRICK BY BRICK");
        title.setStyle("-fx-text-fill: #f5bd45; -fx-font-size: 45px; -fx-font-weight: bold;");
        load = new Button("LOAD GAME");
        newGame = new Button("NEW GAME");
        tutorial = new Button("TUTORIAL");
        mainMenu = new VBox(20);
        mainMenu.getChildren().addAll(title,load,newGame,tutorial);
        mainMenu.setLayoutX(100);
        mainMenu.setLayoutY(200);
    }

    public void gameOverMenuInit(){
        gameOver = new Label("GAME OVER!");
        gameOver.setStyle("-fx-text-fill: #f5bd45; -fx-font-size: 45px; -fx-font-weight: bold;");
        backToMenu = new Button("BACK TO MENU");
        retry = new Button("RETRY");
        gameOverMenu = new VBox(20);
        gameOverMenu.getChildren().addAll(gameOver,backToMenu,retry);
        gameOverMenu.setLayoutX(100);
        gameOverMenu.setLayoutY(200);
        gameOverMenu.setVisible(false);
    }

    public void winMenuInit(){
        youWin = new Label("YOU WIN!");
        youWin.setStyle("-fx-text-fill: #f5bd45; -fx-font-size: 45px; -fx-font-weight: bold;");
        backToMenu = new Button("BACK TO MENU");
        nextLevel = new Button("NEXT LEVEL");
        winMenu = new VBox(20);
        winMenu.getChildren().addAll(youWin,backToMenu,nextLevel);
        winMenu.setLayoutX(100);
        winMenu.setLayoutY(200);
        winMenu.setVisible(false);
    }

    public void gamePlayScreenInit() {
        scoreLabel = new Label("Score: " + Main.score.getScore());
        levelLabel = new Label("Level: " + Main.level);
        heartLabel = new Label("Heart: " + Main.heart);
        //max width for each label
        scoreLabel.setMaxWidth(75);
        heartLabel.setMaxWidth(75);
        levelLabel.setMaxWidth(75);
        //text wrapping
        scoreLabel.setWrapText(true);
        levelLabel.setWrapText(true);
        heartLabel.setWrapText(true);
        gamePlayStats = new HBox(100); // Adjust the spacing as needed
        gamePlayStats.getChildren().addAll(scoreLabel, heartLabel, levelLabel);
        gamePlayStats.setVisible(false);
    }



    public void updateLevel(int level){
        gamePlayStats.setVisible(false);
        gamePlayStats.getChildren().remove(levelLabel);
        levelLabel = new Label("Level: " + level);
        gamePlayStats.getChildren().remove(levelLabel);
        gamePlayStats.getChildren().add(levelLabel);
        gamePlayStats.setVisible(true);
    }

}
