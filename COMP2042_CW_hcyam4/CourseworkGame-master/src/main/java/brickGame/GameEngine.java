/**
 * The GameEngine class manages the game loop and provides a framework for handling game events
 * such as updating, physics calculations, initialization, and time tracking.
 */
package brickGame;

import java.io.IOException;

public class GameEngine {
    private static GameEngine instance;
    private OnAction onAction;
    private int fps;
    private Thread updateThread;
    private Thread physicsThread;
    private boolean isStopped;
    private long time = 0;
    private Thread timeThread;

    private GameEngine() {
        fps = 15;
        isStopped = true;
    }
    /**
     * Gets the singleton instance of the GameEngine class.
     *
     * @return The GameEngine instance.
     */
    public static GameEngine getInstance() {
        if (instance == null) {
            instance = new GameEngine();
        }
        return instance;
    }
    /**
     * Sets the OnAction interface to handle game events.
     *
     * @param onAction The OnAction instance to handle game events.
     */
    public void setOnAction(OnAction onAction) {
        this.onAction = onAction;
    }

    /**
     * Set fps and convert it to milliseconds.
     *
     * @param fps Frames per second
     */
    public void setFps(int fps) {
        this.fps = (int) 1000 / fps;
    }
    /**
     * Starts the update thread. Calls the onUpdate method of the OnAction interface and sleeps
     * for a specified duration.
     */
    private synchronized void update() {
        updateThread = new Thread(() -> {
            while (!updateThread.isInterrupted()) {
                try {
                    onAction.onUpdate();
                    Thread.sleep(fps);
                } catch (InterruptedException e) {
                    return;
                    // e.printStackTrace();
                }
            }
        });
        updateThread.start();
    }
    /**
     * Initializes the game engine by calling the onInit method of the OnAction interface.
     */
    private void initialize() {
        onAction.onInit();
    }
    /**
     * Starts the physics calculation thread. Calls the onPhysicsUpdate method of the OnAction interface
     * and sleeps for a specified duration. Handles InterruptedException and IOException.
     */
    private synchronized void physicsCalculation() {
        physicsThread = new Thread(() -> {
            while (!physicsThread.isInterrupted()) {
                try {
                    onAction.onPhysicsUpdate();
                    Thread.sleep(fps);
                } catch (InterruptedException | IOException e) {
                    return;
                    // e.printStackTrace();
                }
            }
        });
        physicsThread.start();
    }
    /**
     * Starts the game engine by initializing, updating, performing physics calculations, and tracking time.
     */
    public void start() {
        time = 0;
        initialize();
        update();
        physicsCalculation();
        timeStart();
        isStopped = false;
    }
    /**
     * Stops the game engine by interrupting update, physics, and time threads.
     */
    public void stop() {
        if (!isStopped) {
            isStopped = true;
            updateThread.interrupt(); // changed from updateThread.stop()
            physicsThread.interrupt(); // same for this
            timeThread.interrupt(); // same for this too
        }
    }
    /**
     * Starts the time tracking thread. Increments time in milliseconds and notifies the OnAction
     * interface about the elapsed time.
     */
    private void timeStart() {
        timeThread = new Thread(() -> {
            try {
                while (true) {
                    time++;
                    onAction.onTime(time);
                    Thread.sleep(1);
                }
            } catch (InterruptedException e) {
                return;
                // e.printStackTrace();
            }
        });
        timeThread.start();
    }
    /**
     * The OnAction interface defines methods to handle game events.
     */
    public interface OnAction {
        /**
         * Called for each frame to perform update logic.
         */
        void onUpdate();
        /**
         * Called during initialization of the game engine.
         */
        void onInit();
        /**
         * Called for each frame to perform physics calculations.
         *
         * @throws IOException If an I/O error occurs during physics calculations.
         */
        void onPhysicsUpdate() throws IOException;
        /**
         * Called for each millisecond to track time.
         *
         * @param time The elapsed time in milliseconds.
         */
        void onTime(long time);
    }
}
