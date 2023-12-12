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
    static final int NEXT = -1;
    static final int RETRY = 1;
    static final int BACK = 0;
    protected static int retryFlag = BACK;
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
            root.getChildren().clear();
            root.getChildren().addAll(background,bgGold, rect, ball, displayView.mainMenu, displayView.gamePlayStats, displayView.gameOverMenu, displayView.winMenu);
            for (Block block : blocks) {
                root.getChildren().add(block.rect);
            }
        });
    }
    private boolean checkLoad(){
        if (loadSave.loadGame()){
            loadFromSave = true;
            System.out.printf("\nscore loaded: %d\n",score.getScore());
            List<Block> loadedBlocks = new ArrayList<>();
            for (BlockSerializable ser : loadSave.blocks) {
                loadedBlocks.add(new Block(ser.row, ser.j, "concrete.jpg", ser.type));
            }
            Platform.runLater(() -> {
                blocks.clear();
                bonuses.clear();
                displayView.updateObjs(level,score.getScore(),heart);
                blocks.addAll(loadedBlocks);
                displayView.setGamePlay();
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
        Platform.runLater(()->gameController.checkFinishAllLevels(this));
        if (!loadFromSave) {
            if ((level > 1 && level < 7) || (retryFlag == RETRY)) {
                Platform.runLater(() -> {
                    displayView.setGamePlay();
                });
                gameController.startGameEngine(this);
            }
            displayView.load.setOnAction(event -> {
                root.getChildren().clear();
                if (!checkLoad()){
                    try {
                        root.getChildren().clear();
                        gameReset(BACK);
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
                gameController.startGameEngine(this);
                displayView.setNewGame();
            });
            displayView.tutorial.setOnAction(event -> {
                displayView.setTutorial(root,this);

            });
        } else {
            loadFromSave = false;
            gameController.startGameEngine(this);
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
                        displayView.setGameOver(this);
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
            if (score.checkHighScore()){
                System.out.println("\nnew highscore!");
                Platform.runLater(()->{
                    displayView.highScoreMenu();
                    root.getChildren().add(displayView.betHighScoreMenu);
                    displayView.setBetHighScoreMenu(root,this);
                });
                //gameEngine.start();
            }
            else{
                Platform.runLater(()->{
                    displayView.setWinMenu(this);
                });
                //gameEngine.start();
            }
            gameEngine.stop();
        }
    }
    protected void nextLevel() {
        try {
            displayView.winMenu.setVisible(false);
            gameController.nextResetCommonSetup(NEXT);
            start(primaryStage);
        } catch (Exception e) {
            System.out.println("Issue in loading the next level");
        }
    }
    protected void gameReset(int isRetry) {
        try {
            displayView.gameOver.setVisible(false);
            gameEngine.stop();
            gameController.nextResetCommonSetup(isRetry);
            start(primaryStage);
            displayView.setBackToMenu();
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