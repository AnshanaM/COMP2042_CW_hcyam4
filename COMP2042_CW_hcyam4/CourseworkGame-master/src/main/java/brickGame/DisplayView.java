/**
 * The DisplayView class manages the graphical user interface of the game,
 * including menus, labels, buttons, and visual elements. It provides methods
 * for initializing and updating various components of the display.
 *
 */
package brickGame;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;


public class DisplayView {
    private static DisplayView instance;
    public static VBox mainMenu,gameOverMenu,winMenu,betHighScoreMenu;
    public static HBox gamePlayStats;
    public static Label scoreLabel, heartLabel, levelLabel, title, youWin, gameOver;
    public static Button load, newGame, tutorial, backToMenu, nextLevel, retry;
    public static ImageView tutorialPage = new ImageView(new Image("tutorial.png",Main.sceneWidth , Main.sceneHeight, false, true));

    public static Label highScoreLabel = new Label();
    public static Label  commentLabel = new Label();
    /**
     * Private constructor to enforce the singleton pattern.
     * Initializes the main menu, game over menu, win menu, and other graphical elements.
     */
    private DisplayView(){
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
        tutorialPage.setVisible(false);
        //highScoreMenu();
        //displayPortals();
    }
    /**
     * Gets the instance of the DisplayView class, implementing the singleton pattern.
     *
     * @return The instance of DisplayView.
     */
    public static DisplayView getInstance(){
        if (instance == null) {
            instance = new DisplayView();
        }
        return instance;
    }
    /**
     * Initializes the fonts used in the graphical elements of the game.
     * The font is loaded from an external file (VT323.ttf).
     */
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
    /**
     * Initializes the main menu components, including the title, load game button,
     * new game button, and tutorial button.
     */
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
    /**
     * Initializes the game over menu components, including the game over label,
     * back to menu button, and retry button.
     */
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
    /**
     * Initializes the win menu components, including the "You Win" label,
     * back to menu button, and next level button.
     */
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
    /**
     * Initializes the game play screen components, including the score label,
     * heart label, level label, and high score label.
     */
    private void gamePlayScreenInit() {
        scoreLabel = new Label("Score: " + Score.getScore());
        levelLabel = new Label("Level: " + Main.level);
        heartLabel = new Label("Heart: " + Main.heart);
        highScoreLabel = new Label("Highscore: " +Score.getHighScore());
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
    /**
     * Updates the game play screen labels with the current game statistics.
     *
     * @param level The current level.
     * @param score The current score.
     * @param heart The remaining number of lives (hearts).
     */
    public static void updateObjs(int level, int score, int heart){
        gamePlayStats.setVisible(false);
        gamePlayStats.getChildren().clear();
        levelLabel.setText("Level: " + level);
        scoreLabel.setText("Score: " + score);
        heartLabel.setText("Heart: " + heart);
        gamePlayStats.getChildren().addAll(scoreLabel,highScoreLabel,heartLabel,levelLabel);
        gamePlayStats.setVisible(true);
    }
    /**
     * Initializes the background for the gold ball bonus.
     * The background is initially hidden.
     */
    private void initBgGold(){
        Main.bgGold = new Rectangle();
        Main.bgGold.setWidth(Main.sceneWidth);
        Main.bgGold.setHeight(Main.sceneHeight);
        Main.bgGold.setX(0);
        Main.bgGold.setY(0);
        ImagePattern pattern = new ImagePattern(new Image("bgGold.jpg"));
        Main.bgGold.setFill(pattern);
        Main.bgGold.setVisible(false);
    }
    /**
     * Removes the gold ball bonus elements from the display.
     */
    public void removeGold(){
        Main.bgGold.setVisible(false);
        Main.ball.setFill(new ImagePattern(new Image("ball.png")));
        Main.root.getStyleClass().remove("goldRoot");
    }
    /**
     * Sets the gold ball bonus, changing the ball's appearance and showing the background.
     */
    public void setGold(){
        Main.ball.setFill(new ImagePattern(new Image("goldball.png")));
        Main.bgGold.setVisible(true);
    }
    /**
     * Initialises the ball
     */
    private void initBall() {
        Main.xBall = Main.xBreak + Main.centerBreakX;
        Main.yBall = Main.yBreak + Main.breakHeight;
        Main.ball = new Circle();
        Main.ball.setRadius(Main.ballRadius);
        Main.ball.setFill(new ImagePattern(new Image("ball.png")));
    }
    /**
     * Initialises the break paddle
     */
    private void initBreak() {
        Main.rect = new Rectangle();
        Main.rect.setWidth(Main.breakWidth);
        Main.rect.setHeight(Main.breakHeight);
        Main.rect.setX(Main.xBreak);
        Main.rect.setY(Main.yBreak);
        ImagePattern pattern = new ImagePattern(new Image("block.jpg"));
        Main.rect.setFill(pattern);
    }
    /**
     * Initialises the highscore menu with congratulatory label, comment label
     * and becktomenu and nextlevel buttons into the VBox and visibility is set to false
     */
    public void highScoreMenu(){
        highScoreLabel = new Label("CONGRATULATIONS!");
        highScoreLabel.setStyle("-fx-text-fill: #f5bd45; -fx-font-size: 45px; -fx-font-weight: bold;");
        commentLabel = new Label("YOU HAVE REACHED A NEW HIGHSCORE!");
        commentLabel.setStyle("-fx-text-fill: #f5bd45; -fx-font-size: 25px; -fx-font-weight: bold;");
        //backToMenu = new Button("BACK TO MENU");
        //nextLevel = new Button("NEXT LEVEL");
        betHighScoreMenu = new VBox(20);
        betHighScoreMenu.getChildren().addAll(highScoreLabel,commentLabel,backToMenu,nextLevel);
        betHighScoreMenu.setLayoutX(100);
        betHighScoreMenu.setLayoutY(200);
        betHighScoreMenu.setVisible(false);
    }

    /**
     * Adds the specified label to the JavaFX application thread's queue for execution,
     * ensuring that UI updates are performed on the JavaFX application thread.
     *
     * @param labelToAdd The label to be added to the UI.
     */
    private static void addLabel(final Label labelToAdd) {
        Platform.runLater(() -> Main.root.getChildren().add(labelToAdd));
    }
    /**
     * Displays a message on the screen with a specified text.
     * The message label is animated using the playLabelAnimation method from SpecialEffects.
     *
     * @param msg The message text to be displayed.
     */
    public static void showMessage(String msg) {
        final Label message = new Label(msg);
        message.setTranslateX(220);
        message.setTranslateY(340);
        addLabel(message);
        SpecialEffects.playLabelAnimation(message,Main.root);
    }
    /**
     * Displays a score increment message at a specified position on the screen.
     * The score label is animated using the playLabelAnimation method from SpecialEffects.
     *
     * @param x     The x-coordinate for displaying the score increment.
     * @param y     The y-coordinate for displaying the score increment.
     * @param score The score value to be displayed.
     */
    public static void show(final double x, final double y, int score) {
        String sign = (score >= 0) ? "+" : "";
        final Label scoreIncrement = new Label(sign + score);
        scoreIncrement.setTranslateX(x);
        scoreIncrement.setTranslateY(y);
        addLabel(scoreIncrement);
        Main.specialEffects.playLabelAnimation(scoreIncrement, Main.root);
    }
    /**
     * Sets the display to show the main menu and hide other game components.
     */
    public void setBackToMenu(){
        mainMenu.setVisible(true);
        gamePlayStats.setVisible(false);
        gameOverMenu.setVisible(false);
        winMenu.setVisible(false);
        betHighScoreMenu.setVisible(false);
    }
    /**
     * Sets the display to show the game over menu with appropriate button actions.
     *
     * @param main The Main class instance to perform game reset actions.
     */
    public void setGameOver(Main main){
        gameOverMenu.getChildren().removeAll(backToMenu,retry);
        backToMenu.setOnAction(event -> main.gameReset(main.BACK));
        retry.setOnAction(event -> main.gameReset(main.RETRY));
        gameOverMenu.getChildren().addAll(backToMenu,retry);
        gameOverMenu.setVisible(true);
    }
    /**
     * Sets the display to show the game play screen and hide other menus.
     */
    public void setGamePlay(){
        mainMenu.setVisible(false);
        gamePlayStats.setVisible(true);
    }
    /**
     * Sets the display for starting a new game by hiding the main menu and showing the game play screen.
     */
    public void setNewGame(){
        mainMenu.setVisible(false);
        gamePlayStats.setVisible(true);
    }
    /**
     * Sets the display for the tutorial by showing the tutorial page and back to menu button.
     *
     * @param root The JavaFX Pane where the tutorial page and button are displayed.
     * @param main The Main class instance to perform game reset actions.
     */
    public void setTutorial(Pane root, Main main){
        root.getChildren().add(tutorialPage);
        tutorialPage.setVisible(true);
        root.getChildren().remove(backToMenu);
        backToMenu.setLayoutX(310);
        backToMenu.setLayoutY(30);
        root.getChildren().add(backToMenu);
        backToMenu.setVisible(true);
        backToMenu.setOnAction(menuEvent -> main.gameReset(main.BACK));
    }
    /**
     * Sets the display for the bet high score menu with appropriate button actions.
     *
     * @param root The JavaFX Pane where the bet high score menu is displayed.
     * @param main The Main class instance to perform game reset and level change actions.
     */
    public void setBetHighScoreMenu(Pane root, Main main){
        betHighScoreMenu.setVisible(true);
        nextLevel.setOnAction(event -> {
            highScoreLabel.setVisible(false);
            root.getChildren().remove(betHighScoreMenu);
            //gamePlayStats.getChildren().clear();
            main.nextLevel();
        });
        backToMenu.setOnAction(event -> {
            betHighScoreMenu.setVisible(false);
            root.getChildren().remove(betHighScoreMenu);
            //gamePlayStats.getChildren().clear();
            main.gameReset(main.BACK);
        });

    }
    /**
     * Sets the display for the win menu with appropriate button actions.
     *
     * @param main The Main class instance to perform level change actions.
     */
    public void setWinMenu(Main main){
        winMenu.setVisible(true);
        nextLevel.setOnAction(event -> main.nextLevel());
        backToMenu.setOnAction(event -> main.gameReset(main.BACK));
    }


}
