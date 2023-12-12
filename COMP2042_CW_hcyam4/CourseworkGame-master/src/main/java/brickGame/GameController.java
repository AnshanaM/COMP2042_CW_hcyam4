package brickGame;

import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GameController {
    private static GameController instance;
    private static final int LEFT  = 1;
    private static final int RIGHT = 2;

    private GameController() {
        //nothing to initialise here
    }
    public static GameController getInstance(){
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    private static void handleExit(){
        try {
            Main.gameEngine.stop();
            Stage stage = (Stage)Main.primaryStage.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            System.out.println("Issue in handling exit");
        }
    }

    public static void handle(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
                movePaddle(LEFT);
                break;
            case RIGHT:
                movePaddle(RIGHT);
                break;
            case S:
                ArrayList<BlockSerializable> blockSerializables = new ArrayList<>();
                for (Block block : Main.blocks) {
                    if (block.isDestroyed) {
                        continue;
                    }
                    blockSerializables.add(new BlockSerializable(block.row, block.column, block.type));
                }
                Main.loadSave.saveGame(blockSerializables);
                break;
            case ESCAPE:
                handleExit();
                break;
            case P:
                Main.isPaused = !Main.isPaused;
                if (Main.isPaused) {
                Main.displayView.showMessage("GAME PAUSED");
            } else {
                Main.displayView.showMessage("GAME RESUMED");
            }
                break;
            case B:
                //remove tutorial bg and call gameReset
                Main.displayView.tutorialPage.setVisible(false);
                Main.retryFlag=0;
        }
    }
    private static void movePaddle(final int direction) {
        new Thread(() -> {
            int sleepTime = 3;
            for (int i = 0; i < 30; i++) {
                if (Main.xBreak == (Main.sceneWidth - Main.breakWidth) && direction == RIGHT) {
                    Main.wrapPaddle(true);
                    return;
                }
                if (Main.xBreak == 0 && direction == LEFT) {
                    Main.wrapPaddle(false);
                    return;
                }
                Main.xBreak = (direction == RIGHT) ? Main.xBreak + 1 : Main.xBreak - 1;
                Main.centerBreakX = Main.xBreak + Main.halfBreakWidth;

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
    protected void nextResetCommonSetup(int isRetry) {
        DisplayView.updateObjs(Main.level,Score.getScore(),Main.heart);
        Main.loadFromSave = false;
        Main.displayView.winMenu.setVisible(false);
        Main.displayView.gameOverMenu.setVisible(false);
        Main.displayView.mainMenu.setVisible(false);
        Main.vX = 2.000;
        Main.vY = 2.000;
        Main.destroyedBlockCount = 0;
        Main.resetColideFlags();
        Main.goDownBall = false;
        Main.xBall = Main.xBreak + Main.centerBreakX;
        Main.yBall = Main.yBreak;
        Main.isGoldStauts = false;
        Main.isExistHeartBlock = false;
        Main.time = 0;
        Main.goldTime = 0;
        Main.blocks.clear();
        Main.bonuses.clear();
        Main.root.getChildren().clear();
        if (isRetry == 0){//if going back to menu
            Main.level = 1;
            Main.retryFlag = 0;

        }else if (isRetry == 1){
            Main.retryFlag = 1;
            Main.score.setScore(0);
        }
        else{
            Main.level++;
            Main.retryFlag=-1;
        }
        Main.teleport =false;
        Main.heart = 3;
        System.out.printf("/nheart: %d",Main.heart);
    }

}
