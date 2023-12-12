/**
 * The GameController class manages user input events and game control actions.
 * It provides methods for handling keyboard events, paddle movement, and common setup for game resets.
 */
package brickGame;

import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GameController{
    private static GameController instance;
    private static final int LEFT  = 1;
    private static final int RIGHT = 2;

    private GameController() {
        //nothing to initialise here
    }
    /**
     * Gets the singleton instance of the GameController class.
     *
     * @return The GameController instance.
     */
    public static GameController getInstance(){
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }
    /**
     * Handles the exit action by stopping the game engine and closing the application window.
     */
    private static void handleExit(){
        try {
            Main.gameEngine.stop();
            Stage stage = (Stage)Main.primaryStage.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            System.out.println("Issue in handling exit");
        }
    }
    /**
     * Handles keyboard events, including paddle movement, game saving, exiting, pausing, and resuming.
     *
     * @param event The KeyEvent representing the keyboard event.
     */
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
        }
    }
    /**
     * Moves the paddle in the specified direction (LEFT or RIGHT).
     *
     * @param direction The direction of paddle movement (LEFT or RIGHT).
     */
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
    /**
     * Common setup for the next game reset, including updating display objects, resetting game parameters,
     * clearing the game board, and handling level progression.
     *
     * @param isRetry Flag indicating whether the reset is due to a retry or moving back to the main menu.
     */
    protected void nextResetCommonSetup(int isRetry) {
        DisplayView.updateObjs(Main.level,Score.getScore(),Main.heart);
        Main.loadFromSave = false;
//        Main.displayView.winMenu.setVisible(false);
//        Main.displayView.gameOverMenu.setVisible(false);
//        Main.displayView.mainMenu.setVisible(false);
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
        if (isRetry == Main.BACK){//if going back to menu
            Main.level = 1;
            Main.retryFlag = Main.BACK;

        }else if (isRetry == Main.RETRY){
            Main.retryFlag = Main.RETRY;
            Main.score.setScore(0);
        }
        else{
            Main.level++;
            Main.retryFlag= Main.NEXT;
        }
        Main.teleport =false;
        Main.heart = 3;
        System.out.printf("/nheart: %d",Main.heart);
    }
    /**
     * Checks if the player has finished all levels and displays appropriate messages and options.
     *
     * @param main The Main class instance for accessing game state and UI elements.
     */
    protected void checkFinishAllLevels(Main main){
        if (main.level>1){
                main.displayView.showMessage("Level Up!");
        }
        if (main.level > 7) {
            main.displayView.backToMenu.setOnAction(event -> main.gameReset(main.BACK));
            main.displayView.winMenu.getChildren().removeAll(main.displayView.nextLevel,Main.displayView.backToMenu);
            main.displayView.winMenu.getChildren().add(main.displayView.backToMenu);
            main.displayView.winMenu.setVisible(true);
        }
    }
    /**
     * Starts the game engine with the specified frame rate and sets the action event handler.
     *
     * @param main The Main class instance for accessing game state and UI elements.
     */
    protected void startGameEngine(Main main){
        main.gameEngine.getInstance();
        main.gameEngine.setOnAction(main);
        main.gameEngine.setFps(150);
        main.gameEngine.start();
    }

}
