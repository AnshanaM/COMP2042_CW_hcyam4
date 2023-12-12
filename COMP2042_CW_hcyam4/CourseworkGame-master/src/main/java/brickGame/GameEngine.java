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

    public static GameEngine getInstance() {
        if (instance == null) {
            instance = new GameEngine();
        }
        return instance;
    }

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

    private void initialize() {
        onAction.onInit();
    }

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

    public void start() {
        time = 0;
        initialize();
        update();
        physicsCalculation();
        timeStart();
        isStopped = false;
    }

    public void stop() {
        if (!isStopped) {
            isStopped = true;
            updateThread.interrupt(); // changed from updateThread.stop()
            physicsThread.interrupt(); // same for this
            timeThread.interrupt(); // same for this too
        }
    }

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

    public interface OnAction {
        void onUpdate();

        void onInit();

        void onPhysicsUpdate() throws IOException;

        void onTime(long time);
    }
}
