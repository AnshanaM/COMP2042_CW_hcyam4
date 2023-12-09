//version 0.0
//version 1.0
//version 1.1

package brickGame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main extends Application implements EventHandler<KeyEvent>, GameEngine.OnAction {

    protected static int level = 1;

    protected static double xBreak = 250.0f;
    protected static double centerBreakX;
    protected static double yBreak = 640.0f;

    private final int breakWidth     = 130;
    private final int breakHeight    = 30;
    private final int halfBreakWidth = breakWidth/2;

    public static final int sceneWidth = 500;
    public static final int sceneHeigt = 700;

    private static final int LEFT  = 1;
    private static final int RIGHT = 2;

    private Circle ball;
    protected static double xBall = xBreak;
    protected static double yBall = yBreak;

    protected static boolean isGoldStauts      = false;
    protected static boolean isExistHeartBlock = false;

    private Rectangle rect, bgGold;
    private final int       ballRadius = 10;
    protected static int destroyedBlockCount = 0;

    private final double v = 1.000;

    protected static int  heart    = 3;
    public ImageView heartImage = new ImageView("heartImage.jpg");
    protected static Score  score    = new Score();
    protected static long time     = 0;
    private long hitTime  = 0;
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

    private boolean loadFromSave = false;

    static Stage  primaryStage;

    //main menu scene
    Scene scene = new Scene(root, sceneWidth, sceneHeigt);

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

    protected static double vX = 1.000;
    protected static double vY = 1.000;

//initialising menus
static MenuPage initMenus = new MenuPage();

    @Override
    public void start(Stage primaryStage) {
        Main.primaryStage = primaryStage;

        scene.getStylesheets().add("style.css");
        scene.setOnKeyPressed(this);

        if (!loadFromSave) {
            //setting goldtime background
            bgGold = new Rectangle();
            bgGold.setWidth(sceneWidth);
            bgGold.setHeight(sceneHeigt);
            bgGold.setX(0);
            bgGold.setY(0);
            ImagePattern pattern = new ImagePattern(new Image("bgGold.jpg"));
            bgGold.setFill(pattern);
            bgGold.setVisible(false);

            initBall();
            initBreak();
            Block.initBoard(level);

            MenuPage.nextLevel.setOnAction(event -> nextLevel());
            MenuPage.backToMenu.setOnAction(event -> gameReset(0));
            MenuPage.retry.setOnAction(event -> gameReset(1));
        }

        //setting scene background image
        ImageView background = new ImageView(new Image("bg.jpg",sceneWidth , sceneHeigt, false, true));
        root.getChildren().add(background);

        root.getChildren().addAll(bgGold, rect, ball);

        for (Block block : blocks) {
            root.getChildren().add(block.rect);
        }

        //window icon
        Image icon = new Image("ballbricks.png");
        primaryStage.getIcons().add(icon);

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

        //changed title
        primaryStage.setTitle("Brick by Brick");
        //making the window a fixed size
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();

        root.getChildren().addAll(initMenus.mainMenu,initMenus.gamePlayStats,initMenus.gameOverMenu,initMenus.winMenu);
        heartImage.setVisible(false);
        Animation.addImage(heartImage,root);

        if (level >1){
            score.showMessage("Level Up!", this);
        }
        if (level > 7) {
            root.getChildren().remove(initMenus.youWin);
            root.getChildren().add(initMenus.youWin);
            initMenus.youWin.setVisible(true);
            //engine.stop();
            return;
        }
        if (!loadFromSave) {
            if (level > 1 && level < 7) {
                initMenus.mainMenu.setVisible(false);
                initMenus.gamePlayStats.setVisible(true);
                engine = new GameEngine();
                engine.setOnAction(this);
                engine.setFps(150);
                engine.start();
            }

            initMenus.load.setOnAction(event -> {
                root.getChildren().clear();
                if (loadSave.loadGame()){
                    loadFromSave = true;
                    blocks.clear();
                    bonuses.clear();
                    //temporary blocks array to prevent concurrent modification of the blocks during runtime
                    List<Block> loadedBlocks = new ArrayList<>();
                    for (BlockSerializable ser : loadSave.blocks) {
                        int r = new Random().nextInt(200);
                        loadedBlocks.add(new Block(ser.row, ser.j, colors[r % colors.length], ser.type));
                    }
                    initMenus.updateLevel(level);
                    blocks.addAll(loadedBlocks);
                    try {
                        initMenus.mainMenu.setVisible(false);
                        initMenus.gamePlayStats.setVisible(true);
                        loadFromSave=true;
                        start(primaryStage);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else{
                    try {
                        initMenus.mainMenu.setVisible(true);
                        initMenus.gamePlayStats.setVisible(false);
                        loadFromSave=false;
                        start(primaryStage);
                        Label noSaves = new Label("NO GAMES SAVED");
                        noSaves.setLayoutX(((double) sceneWidth / 2) - (noSaves.getWidth()));
                        noSaves.setLayoutY((double) sceneHeigt / 2 - (noSaves.getHeight()));
                        root.getChildren().add(noSaves);
                        Animation.playAnimation(noSaves, root);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            initMenus.newGame.setOnAction(event -> {
                loadFromSave=false;
                engine = new GameEngine();
                engine.setOnAction(Main.this);
                engine.setFps(150);
                engine.start();

                initMenus.mainMenu.setVisible(false);
                initMenus.gamePlayStats.setVisible(true);

            });
        } else {
            engine = new GameEngine();
            engine.setOnAction(this);
            engine.setFps(150);
            loadFromSave = false;
            engine.start();

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

        }
    }

    private void movePaddle(final int direction) {
        new Thread(() -> {
            int sleepTime = 3;
            for (int i = 0; i < 30; i++) {
                if (xBreak == (sceneWidth - breakWidth) && direction == RIGHT) {
                    return;
                }
                if (xBreak == 0 && direction == LEFT) {
                    return;
                }
                xBreak = (direction == RIGHT) ? xBreak+1 : xBreak-1;
                centerBreakX = xBreak + halfBreakWidth;
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    return;
                    //e.printStackTrace();
                }
                if (i >= 20) {
                    sleepTime = i;
                }
            }
        }).start();
    }

    private void initBall() {
        xBall = xBreak + breakWidth/2;
        yBall = yBreak + breakHeight/4;
        ball = new Circle();
        ball.setRadius(ballRadius);
        ball.setFill(new ImagePattern(new Image("ball.png")));
    }

    private void initBreak() {
        rect = new Rectangle();
        rect.setWidth(breakWidth);
        rect.setHeight(breakHeight);
        rect.setX(xBreak);
        rect.setY(yBreak);
        ImagePattern pattern = new ImagePattern(new Image("block.jpg"));
        rect.setFill(pattern);
    }

    private void resetColideFlags() {
        colideToBreak = false;
        colideToBreakAndMoveToRight = false;
        colideToRightWall = false;
        colideToLeftWall = false;
        colideToRightBlock = false;
        colideToBottomBlock = false;
        colideToLeftBlock = false;
        colideToTopBlock = false;
    }

    private void setPhysicsToBall() {
        yBall = goDownBall ? yBall+vY : yBall-vY;
        xBall = goRightBall ? xBall+vX : xBall-vX;
        if (yBall - ballRadius <= 0) {//ball hits the top of the screen
            resetColideFlags();
            goDownBall = true;
            return;
        }
        if (yBall+ballRadius >= sceneHeigt) {//ball hits bottom of screen
            goDownBall = false;
            if (!isGoldStauts) {
                heart--;
                score.show((double) sceneWidth / 2, (double) sceneHeigt / 2, -1, this);
                if (heart == 0) {
                    initMenus.gameOverMenu.setVisible(true);
                    engine.stop();
                }
            }
        }
        if (yBall >= yBreak - ballRadius && xBall+ballRadius >= xBreak && xBall <= xBreak + breakWidth) {//ball touching the paddle
            hitTime = time;
            resetColideFlags();
            colideToBreak = true;
            goDownBall = false;
            //since ball was moving too fast, update it by a small fraction
            vX*=1.05;
            colideToBreakAndMoveToRight = xBall - centerBreakX > 0;
        }
        //Wall Colide
        if (xBall+ballRadius >= sceneWidth) {
            resetColideFlags();
            goRightBall = false;
        }
        if (xBall-ballRadius <= 0) {
            resetColideFlags();
            goRightBall = true;
        }
        if (colideToBreak) {goRightBall = colideToBreakAndMoveToRight;}
    }

    private void checkDestroyedCount() {
        if (destroyedBlockCount == blocks.size()) {
            System.out.println("You Win");
            initMenus.winMenu.setVisible(true);
            engine.stop();
        }
    }

    public void nextLevel() {
        try {
            engine.stop();
            loadFromSave=false;
            initMenus.winMenu.setVisible(false);
            initMenus.gameOverMenu.setVisible(false);
            initMenus.mainMenu.setVisible(false);
            level++;
            initMenus.updateLevel(level);
            score.setScore(0);
            vX = 1.000;
            vY = 1.000;
            destroyedBlockCount = 0;
            resetColideFlags();
            goDownBall = true;

            isGoldStauts = false;
            isExistHeartBlock = false;
            hitTime = 0;
            time = 0;
            goldTime = 0;

            blocks.clear();
            bonuses.clear();
            root.getChildren().clear();

            start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gameReset(int isRetry) {
        try {
            loadFromSave=false;
            initMenus.mainMenu.setVisible(true);
            initMenus.gameOverMenu.setVisible(false);
            initMenus.winMenu.setVisible(false);
            initMenus.gamePlayStats.setVisible(false);
            if (isRetry == 1) {//if retry button clicked
                heart = 3;//give hearts but level remains unchanged
            }//hearts remain same if player won previous game
            else{
                level=1;
                initMenus.updateLevel(level);
            }
            score.setScore(0);
            vX = 1.000;
            vY = 1.000;
            destroyedBlockCount = 0;
            resetColideFlags();
            goDownBall = true;

            isGoldStauts = false;
            isExistHeartBlock = false;
            hitTime = 0;
            time = 0;
            goldTime = 0;

            blocks.clear();
            bonuses.clear();
            root.getChildren().clear();

            start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onUpdate() {
        Platform.runLater(() -> {
            if (!isGoldStauts){
                MenuPage.heartLabel.setVisible(true);
            }
            MenuPage.scoreLabel.setText("Score: " + score.getScore());
            MenuPage.heartLabel.setText("Heart: " + heart);

            rect.setX(xBreak);
            rect.setY(yBreak);
            ball.setCenterX(xBall);
            ball.setCenterY(yBall);

            for (Bonus bonusObj : bonuses) {
                bonusObj.bonus.setY(bonusObj.y);
            }
        });

        if (yBall-ballRadius >= Block.getPaddingTop() && yBall-ballRadius <= (Block.getHeight() * (level + 1)) + Block.getPaddingTop()) {
            for (final Block block : blocks) {
                int hitCode = block.checkHitToBlock(xBall, yBall, ballRadius);
                if (hitCode != Block.NO_HIT) {
                    score.incScore(1);
                    score.show(block.x, block.y, 1, this);
                    block.rect.setVisible(false);
                    block.isDestroyed = true;
                    destroyedBlockCount++;
                    resetColideFlags();
                    if (block.type == Block.BLOCK_CHOCO) {
                        final Bonus newBonus = new Bonus(block.row, block.column);
                        newBonus.timeCreated = time;
                        Platform.runLater(() -> root.getChildren().add(newBonus.bonus));
                        bonuses.add(newBonus);
                    }
                    if (block.type == Block.BLOCK_STAR) {
                        goldTime = time;
                        ball.setFill(new ImagePattern(new Image("goldball.png")));
                        System.out.println("gold ball");
                        bgGold.setVisible(true);
                        isGoldStauts = true;
                    }
                    if (block.type == Block.BLOCK_HEART) {
                        heart++;
                        System.out.println("\nheart increase");
                        Animation.playHeartAnimation(heartImage,block.x+(double) (Block.getWidth()) /2,block.y+(double) (Block.getHeight()) /2,root);
                    }
                    if (hitCode == Block.HIT_RIGHT) {
                        goRightBall = true;
                    } else if (hitCode == Block.HIT_BOTTOM) {
                        goDownBall = true;
                    } else if (hitCode == Block.HIT_LEFT) {
                        goRightBall=false;
                    } else if (hitCode == Block.HIT_TOP) {
                        goDownBall = false;
                    }
                }
            }
        }
    }

    @Override
    public void onInit() {

    }

    @Override
    public void onPhysicsUpdate() {
        checkDestroyedCount();
        setPhysicsToBall();

        if (time - goldTime > 5000) {
            //remove bgGold
            bgGold.setVisible(false);
            ball.setFill(new ImagePattern(new Image("ball.png")));
            root.getStyleClass().remove("goldRoot");
            isGoldStauts = false;
        }

        for (Bonus bonusObj : bonuses) {
            if (bonusObj.y > sceneHeigt || bonusObj.taken) {
                continue;
            }
            if (bonusObj.y >= yBreak && bonusObj.y <= yBreak + breakHeight && bonusObj.x >= xBreak && bonusObj.x <= xBreak + breakWidth) {
                System.out.println("You Got it and +3 score for you");
                bonusObj.taken = true;
                bonusObj.bonus.setVisible(false);
                score.incScore(3);
                score.show(bonusObj.x, bonusObj.y, 3, this);
            }
            bonusObj.y += ((time - bonusObj.timeCreated) / 1000.000) + 1.000;
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