//version 0.0
//version 1.0
//version 1.1

package brickGame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main extends Application implements EventHandler<KeyEvent>, GameEngine.OnAction {

    protected static int level = 0;

    protected static double xBreak = 0.0f;
    protected static double centerBreakX;
    protected static double yBreak = 640.0f;

    private final int breakWidth     = 130;
    private final int breakHeight    = 30;
    private final int halfBreakWidth = breakWidth/2;

    private final int sceneWidth = 500;
    private final int sceneHeigt = 700;

    private static final int LEFT  = 1;
    private static final int RIGHT = 2;

    private Circle ball;
    protected static double xBall;
    protected static double yBall;

    protected static boolean isGoldStauts      = false;
    protected static boolean isExistHeartBlock = false;

    private Rectangle rect;
    private Rectangle bgGold;
    private final int       ballRadius = 10;
    protected static int destroyedBlockCount = 0;

    private final double v = 1.000;

    protected static int  heart    = 3;
    protected static Score  score    = new Score();
    protected static long time     = 0;
    private long hitTime  = 0;
    protected static long goldTime = 0;

    protected static GameEngine engine;
    public static String savePath    = "./save/save.mdds";
    public static String savePathDir = "./save";

    protected static ArrayList<Block> blocks = new ArrayList<>();
    protected static ArrayList<Bonus> bonuses = new ArrayList<Bonus>();
    protected static Color[]          colors = new Color[]{
            Color.MAGENTA,
            Color.RED,
            Color.GOLD,
            Color.CORAL
    };
    public  Pane             root;
    private Label            scoreLabel;
    private Label            heartLabel;

    private Label heartFreeze;

    private boolean loadFromSave = false;

    Stage  primaryStage;
    Button load    = null;
    Button newGame = null;
    Button tutorial = null;

    Button nextLevel = null;
    Button mainMenu = null;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        if (!loadFromSave) {
            level++;
            if (level >1){
                score.showMessage("Level Up!", this);
            }
            if (level == 18) {
                score.showWin(this);
                return;
            }

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
            initBoard();

            //main menu

            //button method provided in original version
            load = new Button("LOAD GAME");
            newGame = new Button("NEW GAME");
            tutorial = new Button("TUTORIAL");
            mainMenu = new Button("BACK TO MENU");
            nextLevel = new Button("NEXT LEVEL");
            load.setTranslateX(220);
            load.setTranslateY(280);
            newGame.setTranslateX(220);
            newGame.setTranslateY(300);
            tutorial.setTranslateX(220);
            tutorial.setTranslateY(340);
            mainMenu.setTranslateX(220);
            mainMenu.setTranslateY(300);
            nextLevel.setTranslateX(220);
            nextLevel.setTranslateY(380);
            nextLevel.setVisible(false);
            mainMenu.setVisible(false);
            nextLevel.setOnAction(event -> nextLevel());
            mainMenu.setOnAction(event -> restartGame());

        }

        root = new Pane();

        //setting scene background image
        ImageView background = new ImageView(new Image("bg.jpg",sceneWidth , sceneHeigt, false, true));
        root.getChildren().add(background);

        scoreLabel = new Label("Score: " + score.getScore());
        Label levelLabel = new Label("Level: " + level);
        levelLabel.setTranslateY(20);
        heartLabel = new Label("Heart : " + heart);
        heartFreeze = new Label("FREEZE");
        heartFreeze.setTranslateX(sceneWidth - 100);
        heartLabel.setTranslateX(sceneWidth - 100);
        heartFreeze.setVisible(false);
        if (!loadFromSave) {
            root.getChildren().addAll(
                    bgGold,
                    rect,
                    ball,
                    scoreLabel,
                    heartFreeze,
                    heartLabel,
                    levelLabel,
                    newGame,
                    load,
                    tutorial,
                    mainMenu,
                    nextLevel
            );
        } else {
            root.getChildren().addAll(
                    bgGold,
                    rect,
                    ball,
                    scoreLabel,
                    heartFreeze,
                    heartLabel,
                    levelLabel

            );
        }
        for (Block block : blocks) {
            root.getChildren().add(block.rect);
        }
        //main menu scene
        Scene scene = new Scene(root, sceneWidth, sceneHeigt);
        scene.getStylesheets().add("style.css");
        scene.setOnKeyPressed(this);

        //window icon
        Image icon = new Image("ballbricks.png");
        primaryStage.getIcons().add(icon);

        //buttons init and layout
        try {
            Font font = Font.loadFont(getClass().getResourceAsStream("/VT323.ttf"), 15);
            if (font != null) {
                System.out.println("Font loaded successfully!");
            } else {
                System.out.println("Font loading failed!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        VBox vbox = new VBox();
        vbox.getChildren().addAll(load,newGame,tutorial);
        vbox.setSpacing(10);
        //vbox.setAlignment(Pos.BASELINE_CENTER);

        root.getChildren().addAll(vbox);

        //changed title
        primaryStage.setTitle("Brick by Brick");
        //making the window a fixed size
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();


        if (!loadFromSave) {
            if (level > 1 && level < 18) {
                load.setVisible(false);
                newGame.setVisible(false);
                tutorial.setVisible(false);
                engine = new GameEngine();
                engine.setOnAction(this);
                engine.setFps(150);
                engine.start();
            }

            load.setOnAction(event -> {
                LoadSave loadSave = new LoadSave();
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
                    blocks.addAll(loadedBlocks);
                }
                else{
                    return;
                }
                try {
                    load.setVisible(false);
                    newGame.setVisible(false);
                    tutorial.setVisible(false);
                    start(primaryStage);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            });

            newGame.setOnAction(event -> {
                engine = new GameEngine();
                engine.setOnAction(Main.this);
                engine.setFps(150);
                engine.start();

                load.setVisible(false);
                newGame.setVisible(false);
                tutorial.setVisible(false);

            });
        } else {
            engine = new GameEngine();
            engine.setOnAction(this);
            engine.setFps(150);
            engine.start();
            loadFromSave = false;
        }


    }

    private void initBoard() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < level + 1; j++) {
                int r = new Random().nextInt(500); // adding 1 to remove empty spaces
                //if (r % 5 == 0) {
                    //continue;
                //}
                int type;
                if (r % 10 == 1) {
                    type = Block.BLOCK_CHOCO;
                } else if (r % 10 == 2) {
                    if (!isExistHeartBlock) {
                        type = Block.BLOCK_HEART;
                        isExistHeartBlock = true;
                    } else {
                        type = Block.BLOCK_NORMAL;
                    }
                } else if (r % 10 == 3) {
                    type = Block.BLOCK_STAR;
                } else {
                    type = Block.BLOCK_NORMAL;
                }
                blocks.add(new Block(j, i, colors[r % (colors.length)], type));
                //System.out.println("colors " + r % (colors.length));
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
                move(LEFT);
                break;
            case RIGHT:
                move(RIGHT);
                break;
            case DOWN:
                //setPhysicsToBall();
                break;
            case S:
                saveGame();
                break;
            // when the up button is pressed, shoot the ball, ball will always return to the break (paddle)
            //when it reaches the bottom of the screen and at the beginning of the game
            //case UP:
                //shootBall();
        }
    }

    float oldXBreak;

    private void move(final int direction) {
        new Thread(() -> {
            int sleepTime = 3;
            for (int i = 0; i < 30; i++) {
                if (xBreak == (sceneWidth - breakWidth) && direction == RIGHT) {
                    return;
                }
                if (xBreak == 0 && direction == LEFT) {
                    return;
                }
                if (direction == RIGHT) {
                    xBreak++;
                } else {
                    xBreak--;
                }
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


    protected static boolean goDownBall                  = true;
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
        //v = ((time - hitTime) / 1000.000) + 1.000;

        double vY = 1.000;
        if (goDownBall) {
            yBall += vY;
        } else {
            yBall -= vY;
        }

        if (goRightBall) {
            xBall += vX;
        } else {
            xBall -= vX;
        }

        if (yBall-ballRadius <= 0) {
            //vX = 1.000;
            resetColideFlags();
            goDownBall = true;
            return;
        }
        if (yBall+ballRadius >= sceneHeigt) {
            goDownBall = false;
            if (!isGoldStauts) {
                //TODO gameover
                heart--;
                score.show((double) sceneWidth / 2, (double) sceneHeigt / 2, -1, this);

                if (heart == 0) {
                    score.showGameOver(this);
                    engine.stop();
                }

            }
            //return;
        }

        if (yBall >= yBreak - ballRadius) {
            //System.out.println("Colide1");
            if (xBall+ballRadius >= xBreak && xBall <= xBreak + breakWidth) {
                hitTime = time;
                resetColideFlags();
                colideToBreak = true;
                goDownBall = false;

                double relation = (xBall - centerBreakX) / ((double) breakWidth / 2);

                if (Math.abs(relation) <= 0.3) {
                    //vX = 0;
                    vX = Math.abs(relation);
                } else if (Math.abs(relation) > 0.3 && Math.abs(relation) <= 0.7) {
                    vX = (Math.abs(relation) * 1.5) + (level / 3.500);
                    //System.out.println("vX " + vX);
                } else {
                    vX = (Math.abs(relation) * 2) + (level / 3.500);
                    //System.out.println("vX " + vX);
                }

                /*if (xBall - centerBreakX > 0) {
                    colideToBreakAndMoveToRight = true;
                } else {
                    colideToBreakAndMoveToRight = false;
                }*/
                //the above if else can be simplified to
                colideToBreakAndMoveToRight = xBall - centerBreakX > 0;
                //System.out.println("Colide2");
            }
        }

        if (xBall+ballRadius >= sceneWidth) {
            resetColideFlags();
            //vX = 1.000;
            colideToRightWall = true;
        }

        if (xBall-ballRadius <= 0) {
            resetColideFlags();
            //vX = 1.000;
            colideToLeftWall = true;
        }

        if (colideToBreak) {
            goRightBall = colideToBreakAndMoveToRight;
        }

        //Wall Colide

        if (colideToRightWall) {
            goRightBall = false;
        }

        if (colideToLeftWall) {
            goRightBall = true;
        }

        //Block Colide

        if (colideToRightBlock) {
            goRightBall = true;
        }

        if (colideToLeftBlock) {
            goRightBall = true;
        }

        if (colideToTopBlock) {
            goDownBall = false;
        }

        if (colideToBottomBlock) {
            goDownBall = true;
        }


    }


    private void checkDestroyedCount() {
        if (destroyedBlockCount == blocks.size()) {
            //TODO win level todo...
            System.out.println("You Win");
            score.showWin(this);
            engine.stop();
            nextLevel.setVisible(true);
            mainMenu.setVisible(true);
        }
    }

    private void saveGame() {
        new Thread(() -> {
            new File(savePathDir).mkdirs();
            File file = new File(savePath);
            ObjectOutputStream outputStream = null;

            try {
                outputStream = new ObjectOutputStream(new FileOutputStream(file));

                outputStream.writeInt(level);
                outputStream.writeInt(score.getScore());
                outputStream.writeInt(heart);
                outputStream.writeInt(destroyedBlockCount);


                outputStream.writeDouble(xBall);
                outputStream.writeDouble(yBall);
                outputStream.writeDouble(xBreak);
                outputStream.writeDouble(yBreak);
                outputStream.writeDouble(centerBreakX);
                outputStream.writeLong(time);
                outputStream.writeLong(goldTime);
                outputStream.writeDouble(vX);

                outputStream.writeBoolean(isExistHeartBlock);
                outputStream.writeBoolean(isGoldStauts);
                outputStream.writeBoolean(goDownBall);
                outputStream.writeBoolean(goRightBall);
                outputStream.writeBoolean(colideToBreak);
                outputStream.writeBoolean(colideToBreakAndMoveToRight);
                outputStream.writeBoolean(colideToRightWall);
                outputStream.writeBoolean(colideToLeftWall);
                outputStream.writeBoolean(colideToRightBlock);
                outputStream.writeBoolean(colideToBottomBlock);
                outputStream.writeBoolean(colideToLeftBlock);
                outputStream.writeBoolean(colideToTopBlock);

                ArrayList<BlockSerializable> blockSerializables = new ArrayList<>();
                for (Block block : blocks) {
                    if (block.isDestroyed) {
                        continue;
                    }
                    blockSerializables.add(new BlockSerializable(block.row, block.column, block.type));
                }

                outputStream.writeObject(blockSerializables);

                new Score().showMessage("Game Saved", Main.this);


            } catch (IOException e) {
                System.out.print("File IO access issue");
                return;
                //e.printStackTrace();
            } finally {
                try {
                    assert outputStream != null;
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    System.out.print("File IO access issue");
                    return;
                    //e.printStackTrace();
                }
            }
        }).start();

    }
/*
    private void loadGame() {

        LoadSave loadSave = new LoadSave();
        loadSave.read();

        isExistHeartBlock = loadSave.isExistHeartBlock;
        isGoldStauts = loadSave.isGoldStauts;
        goDownBall = loadSave.goDownBall;
        goRightBall = loadSave.goRightBall;
        colideToBreak = loadSave.colideToBreak;
        colideToBreakAndMoveToRight = loadSave.colideToBreakAndMoveToRight;
        colideToRightWall = loadSave.colideToRightWall;
        colideToLeftWall = loadSave.colideToLeftWall;
        colideToRightBlock = loadSave.colideToRightBlock;
        colideToBottomBlock = loadSave.colideToBottomBlock;
        colideToLeftBlock = loadSave.colideToLeftBlock;
        colideToTopBlock = loadSave.colideToTopBlock;
        level = loadSave.level;
        score = loadSave.score;
        heart = loadSave.heart;
        destroyedBlockCount = loadSave.destroyedBlockCount;
        xBall = loadSave.xBall;
        yBall = loadSave.yBall;
        xBreak = loadSave.xBreak;
        yBreak = loadSave.yBreak;
        centerBreakX = loadSave.centerBreakX;
        time = loadSave.time;
        goldTime = loadSave.goldTime;
        vX = loadSave.vX;

        blocks.clear();
        chocos.clear();

        for (BlockSerializable ser : loadSave.blocks) {
            int r = new Random().nextInt(200);
            blocks.add(new Block(ser.row, ser.j, colors[r % colors.length], ser.type));
        }*/
    //}


    private void nextLevel() {
        nextLevel.setVisible(false);
        mainMenu.setVisible(false);
        Platform.runLater(() -> {
            try {
                vX = 1.000;
                engine.stop();

                resetColideFlags();
                goDownBall = true;

                isGoldStauts = false;
                isExistHeartBlock = false;

                hitTime = 0;
                time = 0;
                goldTime = 0;

                engine.stop();
                blocks.clear();
                bonuses.clear();
                destroyedBlockCount = 0;
                start(primaryStage);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void restartGame() {
        try {
            level = 0;
            heart = 3;
            score.setScore(0);
            vX = 1.000;
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

            start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onUpdate() {

        Platform.runLater(() -> {
            if (!isGoldStauts){
                heartFreeze.setVisible(false);
                heartLabel.setVisible(true);
            }
            scoreLabel.setText("Score: " + score.getScore());
            heartLabel.setText("Heart : " + heart);
            heartFreeze.setText("FREEZE");

            rect.setX(xBreak);
            rect.setY(yBreak);
            ball.setCenterX(xBall);
            ball.setCenterY(yBall);

            for (Bonus choco : bonuses) {
                choco.choco.setY(choco.y);
            }
        });

        if (yBall-ballRadius >= Block.getPaddingTop() && yBall-ballRadius <= (Block.getHeight() * (level + 1)) + Block.getPaddingTop()) {
            for (final Block block : blocks) {
                int hitCode = block.checkHitToBlock(xBall, yBall);
                if (hitCode != Block.NO_HIT) {
                    score.incScore(1);

                    new Score().show(block.x, block.y, 1, this);

                    block.rect.setVisible(false);
                    block.isDestroyed = true;
                    destroyedBlockCount++;
                    //System.out.println("size is " + blocks.size());
                    resetColideFlags();

                    if (block.type == Block.BLOCK_CHOCO) {
                        final Bonus choco = new Bonus(block.row, block.column);
                        choco.timeCreated = time;
                        Platform.runLater(() -> root.getChildren().add(choco.choco));
                        bonuses.add(choco);
                    }

                    if (block.type == Block.BLOCK_STAR) {
                        goldTime = time;
                        ball.setFill(new ImagePattern(new Image("goldball.png")));
                        System.out.println("gold ball");
                        bgGold.setVisible(true);
                        //root.getChildren().addAll(bgGold);
                        isGoldStauts = true;
                        heartLabel.setVisible(false);
                        heartFreeze.setVisible(true);

                    }

                    if (block.type == Block.BLOCK_HEART) {
                        heart++;
                    }

                    if (hitCode == Block.HIT_RIGHT) {
                        colideToRightBlock = true;
                    } else if (hitCode == Block.HIT_BOTTOM) {
                        colideToBottomBlock = true;
                    } else if (hitCode == Block.HIT_LEFT) {
                        colideToLeftBlock = true;
                    } else if (hitCode == Block.HIT_TOP) {
                        colideToTopBlock = true;
                    }

                }
                //TODO hit to break and some work here....
                //System.out.println("Break in row:" + block.row + " and column:" + block.column + " hit");
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

        for (Bonus choco : bonuses) {
            if (choco.y > sceneHeigt || choco.taken) {
                continue;
            }
            if (choco.y >= yBreak && choco.y <= yBreak + breakHeight && choco.x >= xBreak && choco.x <= xBreak + breakWidth) {
                System.out.println("You Got it and +3 score for you");
                choco.taken = true;
                choco.choco.setVisible(false);
                score.incScore(3);
                score.show(choco.x, choco.y, 3, this);
            }
            choco.y += ((time - choco.timeCreated) / 1000.000) + 1.000;
        }

        //System.out.println("time is:" + time + " goldTime is " + goldTime);

    }


    @Override
    public void onTime(long time) {
        Main.time = time;
    }
}
