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
import java.util.SplittableRandom;

public class Main extends Application implements EventHandler<KeyEvent>, GameEngine.OnAction {

    protected static int level = 1;
    protected static double xBreak = 250.0f;
    protected static double centerBreakX;
    protected static double yBreak = 640.0f;
    public static int breakWidth     = 130;
    public static int breakHeight    = 30;
    private final int halfBreakWidth = breakWidth/2;
    public static final int sceneWidth = 500;
    public static final int sceneHeight = 700;
    private static final int LEFT  = 1;
    private static final int RIGHT = 2;
    public static Circle ball;
    protected static double xBall = xBreak;
    protected static double yBall = yBreak;
    protected static boolean isGoldStauts      = false;
    protected static boolean isExistHeartBlock = false;
    public static Rectangle rect;
    public static Rectangle bgGold;
    public static int ballRadius = 10;
    protected static int destroyedBlockCount = 0;
    private final double v = 2.000;
    protected static int  heart    = 3;
    protected static Score  score = new Score();;
    protected static long time     = 0;
    protected static long goldTime = 0;
    protected static GameEngine engine;
    protected static ArrayList<Block> blocks = new ArrayList<>();
    protected static ArrayList<Bonus> bonuses = new ArrayList<Bonus>();
    protected static String[]          colors = new String[]{
            "green.jpg",
            "indigo.jpg",
            "mustard.jpg",
            "purple.jpg",
            "teal.jpg"
    };
    public static Pane             root = new Pane();
    LoadSave loadSave = new LoadSave();
    private static boolean loadFromSave = false;
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
    public static int retryFlag = 0;
    public static DisplayView displayView;
    private static boolean isPaused = false;
    public static boolean swap = false;


    private void initStage(Stage primaryStage){
        Main.primaryStage = primaryStage;
        scene.getStylesheets().add("style.css");
        scene.setOnKeyPressed(this);
        if (!loadFromSave) {
            Block.initBoard(level);
        }
        displayView = new DisplayView();
        primaryStage.getIcons().add(icon);
        Platform.runLater(() -> {
            primaryStage.show();
            root.getChildren().addAll(background,bgGold, displayView.mainMenu, displayView.gamePlayStats, displayView.gameOverMenu, displayView.winMenu, rect, ball);
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
                score.showMessage("Level Up!", this);
            });
        }
        if (level > 7) {
            Platform.runLater(() -> {
//                root.getChildren().remove(displayView.youWin);
//                root.getChildren().add(displayView.youWin);
                displayView.youWin.setVisible(true);//CHAMPION?
            });
            engine.stop();
            return;
        }
        if (!loadFromSave) {
            if ((level > 1 && level < 7) || (retryFlag == 1)) {
                Platform.runLater(() -> {
                    displayView.mainMenu.setVisible(false);
                    displayView.gamePlayStats.setVisible(true);
                });
                engine = new GameEngine();
                engine.setOnAction(this);
                engine.setFps(150);
                engine.start();
            }

            displayView.load.setOnAction(event -> {
                root.getChildren().clear();
                if (!checkLoad()){
                    try {
                        displayView.mainMenu.setVisible(true);
                        displayView.gamePlayStats.setVisible(false);
                        loadFromSave=false;
                        start(primaryStage);
                        displayView.noSaves();
                    }
                    catch (Exception e) {
                        System.out.println("issue in loading from save");
                    }
                }
            });

            displayView.newGame.setOnAction(event -> {
                loadFromSave=false;
                engine = new GameEngine();
                engine.setOnAction(Main.this);
                engine.setFps(150);
                engine.start();

                displayView.mainMenu.setVisible(false);
                displayView.gamePlayStats.setVisible(true);

            });
        } else {
            engine = new GameEngine();
            engine.setOnAction(this);
            engine.setFps(150);
            loadFromSave = false;
            engine.start();

        }
    }

    private void handleExit(){
        try {
            engine.stop();
            Stage stage = (Stage)primaryStage.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            System.out.println("Issue in handling exit");
        }
    }
    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
                movePaddle(LEFT);
                break;
            case RIGHT:
                movePaddle(RIGHT);
                break;
            case S:
                ArrayList<BlockSerializable> blockSerializables = new ArrayList<>();
                for (Block block : blocks) {
                    if (block.isDestroyed) {
                        continue;
                    }
                    blockSerializables.add(new BlockSerializable(block.row, block.column, block.type));
                }
                loadSave.saveGame(this,blockSerializables);
                break;
            case ESCAPE:
                handleExit();
                break;
            case P:
                isPaused = !isPaused;
                break;
        }
    }

    private void movePaddle(final int direction) {
        new Thread(() -> {
            int sleepTime = 3;
            for (int i = 0; i < 30; i++) {
                if (xBreak == (sceneWidth - breakWidth) && direction == RIGHT) {
                    wrapPaddle(true);
                    return;
                }
                if (xBreak == 0 && direction == LEFT) {
                    wrapPaddle(false);
                    return;
                }

                xBreak = (direction == RIGHT) ? xBreak + 1 : xBreak - 1;
                centerBreakX = xBreak + halfBreakWidth;

                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    return;
                }

                if (i >= 20) {
                    sleepTime = i;
                }
            }
        }).start();
    }

    private void wrapPaddle(boolean toRight) {
        if (swap) {
            if (toRight) {
                xBreak = 0;
            } else {
                xBreak = sceneWidth - breakWidth;
            }
            centerBreakX = xBreak + halfBreakWidth;
        }
    }

    public static void resetColideFlags() {
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
        }
        if (yBall + ballRadius >= sceneHeight) {//ball hits bottom wall
            goDownBall = false;
            if (!isGoldStauts) {
                heart--;
                score.show((double) sceneWidth / 2, (double) sceneHeight / 2, -1);
                if (heart < 1) {
                    Platform.runLater(()->{
                        displayView.retry.setOnAction(event -> gameReset(1));
                        displayView.backToMenu.setOnAction(event -> gameReset(0));
                        displayView.gameOverMenu.getChildren().addAll(displayView.backToMenu,displayView.retry);
                        displayView.gameOverMenu.setVisible(true);
                    });
                    engine.stop();
                }
            }
        }
        if (xBall + ballRadius >= sceneWidth) {//ball hits right wall
            resetColideFlags();
            goRightBall = false;
            xBall = sceneWidth - ballRadius;
        }
        if (xBall - ballRadius <= 0) {
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
            if (score.checkHighScore(score.getScore())){
                System.out.println("\nnew highscore!");
                Platform.runLater(()->{
                    displayView.highScoreMenu();
                    displayView.nextLevel.setOnAction(event -> nextLevel());
                    displayView.backToMenu.setOnAction(event -> gameReset(0));
                });
                engine.start();
            }
            else{
                Platform.runLater(()->{
                    displayView.winMenu.setVisible(true);
                    displayView.nextLevel.setOnAction(event -> nextLevel());
                    displayView.backToMenu.setOnAction(event -> gameReset(0));
                });
//                engine.start();
            }
            engine.stop();
        }
    }
    private void nextResetCommonSetup(int isRetry) {
        loadFromSave = false;
        displayView.winMenu.setVisible(false);
        displayView.gameOverMenu.setVisible(false);
        displayView.mainMenu.setVisible(false);
        vX = 2.000;
        vY = 2.000;
        destroyedBlockCount = 0;
        resetColideFlags();
        goDownBall = true;
        isGoldStauts = false;
        isExistHeartBlock = false;
        time = 0;
        goldTime = 0;
        blocks.clear();
        bonuses.clear();
        root.getChildren().clear();
        if (isRetry == 0){//if going back to menu
            level = 1;
            retryFlag = 0;
        }else{//if retry current level, or go to next level, no changes made to level
            retryFlag = 1;
        }
        swap=false;
        heart = 3;
        start(primaryStage);
    }
    public void nextLevel() {
        try {
            level++;
            nextResetCommonSetup(-1);
        } catch (Exception e) {
            System.out.println("Issue in loading the next level");
        }
    }
    public void gameReset(int isRetry) {
        try {
            engine.stop();
            nextResetCommonSetup(isRetry);
            score.setScore(0);
            displayView.mainMenu.setVisible(true);
            displayView.gamePlayStats.setVisible(false);
        } catch (Exception e) {
            System.out.println("Issue in going back to the menu");
        }
    }

    public void updateGamePlayObjs(){
        Platform.runLater(() -> {
            DisplayView.updateObjs(level,score.getScore(),heart);
            rect.setX(xBreak);
            rect.setY(yBreak);
            ball.setCenterX(xBall);
            ball.setCenterY(yBall);
            for (Bonus bonusObj : bonuses) {
                bonusObj.bonus.setY(bonusObj.y);
            }
        });
    }

    public void updateBallDirection(int hitCode){
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
            updateGamePlayObjs();
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
        if (block.hits >= 3) {
            score.incScore(1);
            score.show(block.x, block.y, 1);
            Platform.runLater(() -> {
                block.blockIsHit();
                SpecialEffects.playBlockDebris(block.x,block.y,root);
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