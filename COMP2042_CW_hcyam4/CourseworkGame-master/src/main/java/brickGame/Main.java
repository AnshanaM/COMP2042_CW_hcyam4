package brickGame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.ceil;

public class Main extends Application implements EventHandler<KeyEvent>, GameEngine.OnAction {
    protected static int level = 1;
    protected static double xBreak = 250.0f;
    protected static double centerBreakX;
    protected static double yBreak = 640.0f;
    protected static int breakWidth     = 130;
    protected static int breakHeight    = 30;
    protected static final int halfBreakWidth = breakWidth/2;
    protected static final int sceneWidth = 500;
    protected static final int sceneHeight = 700;
    protected static Circle ball;
    protected static double xBall = xBreak;
    protected static double yBall = yBreak;
    protected static boolean isGoldStauts      = false;
    protected static boolean isExistHeartBlock = false;
    protected static Rectangle rect;
    protected static Rectangle bgGold;
    protected static int ballRadius = 10;
    protected static int destroyedBlockCount = 0;
    protected final double v = 2.000;
    protected static int  heart    = 3;
    protected static long time     = 0;
    protected static long goldTime = 0;
    protected static ArrayList<Block> blocks = new ArrayList<>();
    protected static ArrayList<Bonus> bonuses = new ArrayList<Bonus>();
    protected static String[]          colors = new String[]{
            "green.jpg",
            "indigo.jpg",
            "mustard.jpg",
            "purple.jpg",
            "teal.jpg"
    };
    protected static Pane             root = new Pane();
    protected static boolean loadFromSave = false;
    static ImageView background;
    static Image icon;
    static Stage  primaryStage;
    static Scene scene = new Scene(root, sceneWidth, sceneHeight);
    protected static boolean goDownBall                  = false;
    protected static boolean goRightBall                 = true;
    protected static boolean colideToBreak               = false;
    protected static boolean colideToBreakAndMoveToRight = true;
    protected static boolean colideToRightWall           = false;
    protected static boolean colideToLeftWall            = false;
    protected static boolean colideToRightBlock          = false;
    protected static boolean colideToBottomBlock         = false;
    protected static boolean colideToLeftBlock           = false;
    protected static boolean colideToTopBlock            = false;
    protected static double vX = 2.000;
    protected static double vY = 2.000;
    protected static int retryFlag = 0;
    protected static boolean isPaused = false;
    protected static boolean teleport = false;
    static DisplayView displayView;
    static Score score = Score.getInstance();
    static GameEngine gameEngine = GameEngine.getInstance();
    static LoadSave loadSave = LoadSave.getInstance();
    static SpecialEffects specialEffects = SpecialEffects.getInstance();
    static GameController gameController = GameController.getInstance();

    private void initStage(Stage primaryStage){
        Main.primaryStage = primaryStage;
        displayView = DisplayView.getInstance();
        scene.getStylesheets().add("style.css");
        scene.setOnKeyPressed(this);
        if (!loadFromSave) {
            Block.initBoard(level);
        }
        primaryStage.getIcons().add(icon);
        Platform.runLater(() -> {
            primaryStage.show();
            root.getChildren().addAll(background,bgGold, rect, ball, displayView.mainMenu, displayView.gamePlayStats, displayView.gameOverMenu, displayView.winMenu);
            for (Block block : blocks) {
                root.getChildren().add(block.rect);
            }
            if (retryFlag == 1){
                displayView.mainMenu.setVisible(false);
            }

        });
    }

    private boolean checkLoad(){
        if (loadSave.loadGame()){
            loadFromSave = true;
            System.out.printf("\nscore loaded: %d\n",score.getScore());
            //temporary blocks array to prevent concurrent modification of the blocks during runtime
            List<Block> loadedBlocks = new ArrayList<>();
            for (BlockSerializable ser : loadSave.blocks) {
                loadedBlocks.add(new Block(ser.row, ser.j, "concrete.jpg", ser.type));
            }
            Platform.runLater(() -> {
                blocks.clear();
                bonuses.clear();
                displayView.updateObjs(level,score.getScore(),heart);
                blocks.addAll(loadedBlocks);
                displayView.mainMenu.setVisible(false);
                displayView.gamePlayStats.setVisible(true);
                if (teleport) {
                    displayView.showMessage("TELEPORT");
                }
            });

            try {
                start(primaryStage);
                return true;
            }
            catch (Exception e) {
                System.out.println("issue in loading game to window");
            }
        }
        return false;
    }
    @Override
    public void start(Stage primaryStage) {
        initStage(primaryStage);
        if (level>1){
            Platform.runLater(() -> {
                displayView.showMessage("Level Up!");
            });
        }
        if (level > 7) {
            Platform.runLater(() -> {
                //DISPLAY CHAMPION PAGE
                displayView.backToMenu.setOnAction(event -> gameReset(0));
                displayView.winMenu.getChildren().removeAll(displayView.nextLevel,displayView.backToMenu);
                displayView.winMenu.getChildren().add(displayView.backToMenu);
            });
            return;
        }
        if (!loadFromSave) {
            if ((level > 1 && level < 7) || (retryFlag == 1)) {
                Platform.runLater(() -> {
                    displayView.mainMenu.setVisible(false);
                    displayView.gamePlayStats.setVisible(true);
                });
                gameEngine.getInstance();
                gameEngine.setOnAction(this);
                gameEngine.setFps(150);
                gameEngine.start();
            }

            displayView.load.setOnAction(event -> {
                root.getChildren().clear();
                if (!checkLoad()){
                    try {
                        displayView.mainMenu.setVisible(true);
                        displayView.gamePlayStats.setVisible(false);
                        loadFromSave=false;
                        start(primaryStage);
                    }
                    catch (Exception e) {
                        System.out.println("issue in loading from save");
                    }
                }
            });

            displayView.newGame.setOnAction(event -> {
                loadFromSave=false;
                gameEngine.getInstance();
                gameEngine.setOnAction(Main.this);
                gameEngine.setFps(150);
                gameEngine.start();
                displayView.mainMenu.setVisible(false);
                displayView.gamePlayStats.setVisible(true);

            });

            displayView.tutorial.setOnAction(event -> {
                root.getChildren().add(displayView.tutorialPage);
                displayView.tutorialPage.setVisible(true);
                root.getChildren().remove(displayView.backToMenu);
                displayView.backToMenu.setLayoutX(310);
                displayView.backToMenu.setLayoutY(30);
                root.getChildren().add(displayView.backToMenu);
                displayView.backToMenu.setVisible(true);
                displayView.backToMenu.setOnAction(menuEvent -> gameReset(0));

            });
        } else {
            gameEngine.getInstance();
            gameEngine.setOnAction(this);
            gameEngine.setFps(150);
            loadFromSave = false;
            gameEngine.start();

        }
    }

    @Override
    public void handle(KeyEvent event) {
        gameController.handle(event);
    }

    protected static void wrapPaddle(boolean toRight) {
        if (teleport) {
            displayView.showMessage("TELEPORT");
            if (toRight) {
                xBreak = 0;
            } else {
                xBreak = sceneWidth - breakWidth;
            }
            //centerBreakX = xBreak + halfBreakWidth;
        }
    }

    protected static void resetColideFlags() {
        colideToBreak = false;
        colideToBreakAndMoveToRight = false;
        colideToRightWall = false;
        colideToLeftWall = false;
        colideToRightBlock = false;
        colideToBottomBlock = false;
        colideToLeftBlock = false;
        colideToTopBlock = false;
    }
    private void checkWallCollisions(){
        if (yBall<= 0) {//ball hits top wall
            resetColideFlags();
            goDownBall = true;
            Platform.runLater(()->specialEffects.playSound("/ball_wall.wav"));
        }
        if (yBall + ballRadius >= sceneHeight) {//ball hits bottom wall
            goDownBall = false;
            if (!isGoldStauts) {
                Platform.runLater(()->specialEffects.playSound("/ball_out.wav"));
                heart--;
                displayView.show((double) sceneWidth / 2, (double) sceneHeight / 2, -1);
                if (heart < 1) {
                    Platform.runLater(()->{
                        specialEffects.playSound("/gameover.wav");
                        displayView.gameOverMenu.getChildren().removeAll(displayView.backToMenu,displayView.retry);
                        displayView.retry.setOnAction(event -> gameReset(1));
                        displayView.backToMenu.setOnAction(event -> gameReset(0));
                        displayView.gameOverMenu.getChildren().addAll(displayView.backToMenu,displayView.retry);
                        displayView.gameOverMenu.setVisible(true);
                    });
                    gameEngine.stop();
                }
            }
        }
        if (xBall + ballRadius >= sceneWidth) {//ball hits right wall
            Platform.runLater(()->specialEffects.playSound("/ball_wall.wav"));
            resetColideFlags();
            goRightBall = false;
            xBall = sceneWidth - ballRadius;
        }
        if (xBall - ballRadius <= 0) {
            Platform.runLater(()->specialEffects.playSound("/ball_wall.wav"));
            resetColideFlags();
            goRightBall = true;
            xBall = ballRadius;
        }
    }
    private void checkBreakCollisions(){
        if (yBall >= yBreak - ballRadius && yBall <= yBreak + breakHeight + ballRadius &&
                xBall + ballRadius >= xBreak && xBall - ballRadius <= xBreak + breakWidth) {
            resetColideFlags();
            colideToBreak = true;
            goDownBall = false;
            colideToBreakAndMoveToRight = xBall+(ballRadius) - centerBreakX >= 0;
        }
        if (colideToBreak) {
            goRightBall = colideToBreakAndMoveToRight;
        }
    }
    private void setPhysicsToBall() {
        yBall = goDownBall ? yBall + vY : yBall - vY;
        xBall = goRightBall ? xBall + vX : xBall - vX;
        checkWallCollisions();
        checkBreakCollisions();
    }
    private void checkDestroyedCount() {
        if (destroyedBlockCount == blocks.size()) {
            System.out.println("You Win");
            Platform.runLater(()->specialEffects.playSound("/youwin.wav"));
            if (score.checkHighScore(score.getScore())){
                System.out.println("\nnew highscore!");
                Platform.runLater(()->{
                    displayView.highScoreMenu();
                    displayView.nextLevel.setOnAction(event -> {
                        displayView.highScoreLabel.setVisible(false);
                        root.getChildren().remove(displayView.betHighScoreMenu);
                        //root.getChildren().add(displayView.gamePlayStats);
                        nextLevel();
                    });
                    displayView.backToMenu.setOnAction(event -> {
                        displayView.betHighScoreMenu.setVisible(false);
                        root.getChildren().remove(displayView.betHighScoreMenu);
                        //root.getChildren().add(displayView.gamePlayStats);
                        gameReset(0);
                    });
                    displayView.tutorial.setOnAction(event -> {
                        displayView.tutorialPage.setVisible(true);
                        //gameReset(0);
                    });
                });
                gameEngine.start();
            }
            else{
                Platform.runLater(()->{
                    displayView.winMenu.setVisible(true);
                    displayView.nextLevel.setOnAction(event -> nextLevel());
                    displayView.backToMenu.setOnAction(event -> gameReset(0));
                });
                gameEngine.start();
            }
            gameEngine.stop();
        }
    }
    private void nextLevel() {
        try {
            gameController.nextResetCommonSetup(-1);
            start(primaryStage);
        } catch (Exception e) {
            System.out.println("Issue in loading the next level");
        }
    }
    protected void gameReset(int isRetry) {
        try {
            gameEngine.stop();
            gameController.nextResetCommonSetup(isRetry);
            start(primaryStage);
            displayView.mainMenu.setVisible(true);
            displayView.gamePlayStats.setVisible(false);
        } catch (Exception e) {
            System.out.println("Issue in going back to the menu");
        }
    }

    private void updateGameRunningObjs(){
        Platform.runLater(() -> {
            DisplayView.updateObjs(level,Score.getScore(),heart);
            rect.setX(xBreak);
            rect.setY(yBreak);
            ball.setCenterX(xBall);
            ball.setCenterY(yBall);
            for (Bonus bonusObj : bonuses) {
                bonusObj.bonus.setY(bonusObj.y);
            }
        });
    }

    private void updateBallDirection(int hitCode){
        if (hitCode == Block.HIT_RIGHT) {
            goRightBall = false;
        } else if (hitCode == Block.HIT_BOTTOM) {
            goDownBall = false;
        } else if (hitCode == Block.HIT_LEFT) {
            goRightBall = true;
        } else if (hitCode == Block.HIT_TOP) {
            goDownBall = true;
        }
    }
    @Override
    public void onUpdate() {
        if (!isPaused) {
            updateGameRunningObjs();
            if (isBallCollidingWithBlocks()) {
                for (Block block : blocks) {
                    int hitCode = block.checkHitToBlock(xBall, yBall, ballRadius);
                    if (hitCode != Block.NO_HIT) {
                        handleBlockHit(block, hitCode);
                    }
                }
            }
        }
    }
    private boolean isBallCollidingWithBlocks() {
        return yBall + ballRadius >= Block.getPaddingTop()
                && yBall <= (Block.getHeight() * (level + 2)) + Block.getPaddingTop();
    }
    private void handleBlockHit(Block block, int hitCode) {
        Platform.runLater(()->specialEffects.playSound("/ball_block.wav"));
        if (block.hits >= 3) {
            score.incScore(1);
            displayView.show(block.x, block.y, 1);
            Platform.runLater(() -> {
                block.blockIsHit();
                specialEffects.playBlockDebris(block.x,block.y,root);
            });
            block.isDestroyed = true;
            Platform.runLater(() -> {
                block.rect.setVisible(false);
                root.getChildren().remove(block.rect);
            });
            destroyedBlockCount++;
            resetColideFlags();
        } else {
            block.hits++;
            System.out.printf("\nhits: %d", block.hits);
            Platform.runLater(block::setBlockFill);
        }
        updateBallDirection(hitCode);
    }
    @Override
    public void onInit() {
        //no implementation needed here
    }

    @Override
    public void onPhysicsUpdate() {
        if (!isPaused){
            checkDestroyedCount();
            setPhysicsToBall();
            if (time - goldTime > 5000) {
                displayView.removeGold();
                isGoldStauts = false;
            }
            for (Bonus bonusObj : bonuses) {
                bonusObj.checkIsTaken();
                bonusObj.y += ((time - bonusObj.timeCreated) / 1000.000) + 1.000;
            }
        }
    }
    @Override
    public void onTime(long time) {
        Main.time = time;
    }
    public static void main(String[] args) {
        launch(args);
    }
}