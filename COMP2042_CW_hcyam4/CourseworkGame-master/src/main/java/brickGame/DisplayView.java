package brickGame;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import static brickGame.Main.portals;

public class DisplayView {
    public static VBox mainMenu,gameOverMenu,winMenu,betHighScoreMenu;
    public static HBox gamePlayStats;
    public static Label scoreLabel, heartLabel, levelLabel, title, youWin, gameOver,highScoreLabel,commentLabel;
    public static Button load, newGame, tutorial, backToMenu, nextLevel, retry;

    public DisplayView(){
        mainMenuInit();
        gamePlayScreenInit();
        gameOverMenuInit();
        winMenuInit();
        Main.background = new ImageView(new Image("bg.jpg",Main.sceneWidth , Main.sceneHeight, false, true));
        Main.icon = new Image("ballbricks.png");
        initBgGold();
        setFont();
        Main.primaryStage.setTitle("Brick by Brick");
        Main.primaryStage.setResizable(false);
        Main.primaryStage.setScene(Main.scene);
        initBall();
        initBreak();
    }
    private void setFont(){
        try {
            Font font = Font.loadFont(getClass().getResourceAsStream("/VT323.ttf"), 15);
            if (font != null) {
                System.out.println("Font loaded successfully!");
            } else {
                System.out.println("Font is null");
            }
        } catch (Exception e) {
            System.out.println("Font loading failed");
        }
    }
    private void mainMenuInit(){
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
        gameOverMenu.getChildren().addAll(gameOver);
        gameOverMenu.setLayoutX(100);
        gameOverMenu.setLayoutY(200);
        gameOverMenu.setVisible(false);
    }

    private void winMenuInit(){
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

    private void gamePlayScreenInit() {
        scoreLabel = new Label("Score: " + Main.score.getScore());
        levelLabel = new Label("Level: " + Main.level);
        heartLabel = new Label("Heart: " + Main.heart);
        highScoreLabel = new Label("Highscore: " + Main.score.getHighScore());
        //max width for each label
        scoreLabel.setMaxWidth(75);
        heartLabel.setMaxWidth(75);
        levelLabel.setMaxWidth(75);
        highScoreLabel.setMaxWidth(110);
        //text wrapping
        scoreLabel.setWrapText(true);
        levelLabel.setWrapText(true);
        heartLabel.setWrapText(true);
        highScoreLabel.setWrapText(true);

        gamePlayStats = new HBox(50);
        gamePlayStats.getChildren().addAll(scoreLabel,highScoreLabel,heartLabel,levelLabel);
        gamePlayStats.setVisible(false);
    }

    public static void updateObjs(int level, int score, int heart){
        gamePlayStats.setVisible(false);
        gamePlayStats.getChildren().removeAll(scoreLabel,highScoreLabel,levelLabel,heartLabel);
        levelLabel.setText("Level: " + level);
        scoreLabel.setText("Score: " + score);
        heartLabel.setText("Heart: " + heart);
        gamePlayStats.getChildren().addAll(scoreLabel,highScoreLabel,heartLabel,levelLabel);
        gamePlayStats.setVisible(true);
    }
    private void initBgGold(){
        //setting goldtime background
        Main.bgGold = new Rectangle();
        Main.bgGold.setWidth(Main.sceneWidth);
        Main.bgGold.setHeight(Main.sceneHeight);
        Main.bgGold.setX(0);
        Main.bgGold.setY(0);
        ImagePattern pattern = new ImagePattern(new Image("bgGold.jpg"));
        Main.bgGold.setFill(pattern);
        Main.bgGold.setVisible(false);
    }
    public void noSaves(){
        Label noSaves = new Label("NO GAMES SAVED");
        noSaves.setLayoutX(((double) Main.sceneWidth / 2) - (noSaves.getWidth()));
        noSaves.setLayoutY((double) Main.sceneHeight / 2 - (noSaves.getHeight()));
        Main.root.getChildren().add(noSaves);
        SpecialEffects.playLabelAnimation(noSaves, Main.root);
    }
    public void removeGold(){
        Main.bgGold.setVisible(false);
        Main.ball.setFill(new ImagePattern(new Image("ball.png")));
        Main.root.getStyleClass().remove("goldRoot");
    }

    public void setGold(){
        Main.ball.setFill(new ImagePattern(new Image("goldball.png")));
        Main.bgGold.setVisible(true);
    }
    private void initBall() {
        Main.xBall = Main.xBreak + Main.breakWidth/2;
        Main.yBall = Main.yBreak + Main.breakHeight/4;
        Main.ball = new Circle();
        Main.ball.setRadius(Main.ballRadius);
        Main.ball.setFill(new ImagePattern(new Image("ball.png")));
    }

    private void initBreak() {
        Main.rect = new Rectangle();
        Main.rect.setWidth(Main.breakWidth);
        Main.rect.setHeight(Main.breakHeight);
        Main.rect.setX(Main.xBreak);
        Main.rect.setY(Main.yBreak);
        ImagePattern pattern = new ImagePattern(new Image("block.jpg"));
        Main.rect.setFill(pattern);
    }

    public void highScoreMenu(){
        highScoreLabel = new Label("CONGRATULATIONS!");
        highScoreLabel.setStyle("-fx-text-fill: #f5bd45; -fx-font-size: 45px; -fx-font-weight: bold;");
        commentLabel = new Label("YOU HAVE REACHED A NEW HIGHSCORE!");
        commentLabel.setStyle("-fx-text-fill: #f5bd45; -fx-font-size: 25px; -fx-font-weight: bold;");
        backToMenu = new Button("BACK TO MENU");
        nextLevel = new Button("NEXT LEVEL");
        betHighScoreMenu = new VBox(20);
        betHighScoreMenu.getChildren().addAll(highScoreLabel,commentLabel,backToMenu,nextLevel);
        betHighScoreMenu.setLayoutX(100);
        betHighScoreMenu.setLayoutY(200);
        betHighScoreMenu.setVisible(true);
        Main.root.getChildren().add(betHighScoreMenu);
    }

    public static void displayPortals(){
        portals = new Rectangle();
        portals.setWidth(Main.sceneWidth);
        portals.setHeight(Main.sceneHeight);
        portals.setX(0);
        portals.setY(0);
        ImagePattern pattern = new ImagePattern(new Image("portals.png"));
        portals.setFill(pattern);
        Main.root.getChildren().add(portals);
        Main.portals.setVisible(true);
    }
}
