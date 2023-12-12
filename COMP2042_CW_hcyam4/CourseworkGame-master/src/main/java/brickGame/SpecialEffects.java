/**
 * handles all the animation and sound effects of the game
 */
package brickGame;

import javafx.animation.*;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.concurrent.atomic.AtomicBoolean;

public class SpecialEffects {
    private static SpecialEffects instance;
    private static ImageView debrisImage;
    private static ImageView heartImage;

    private SpecialEffects() {
        heartImage = new ImageView("heartImage.jpg");
    }
    /**
     * Gets the singleton instance of the SpecialEffects class.
     *
     * @return The singleton instance of the SpecialEffects class
     */
    public static SpecialEffects getInstance() {
        if (instance == null) {
            instance = new SpecialEffects();
        }
        return instance;
    }
    /**
     * Plays a scale and opacity animation for a label.
     * After the animation finishes, the label is removed from the specified Pane.
     *
     * @param labelToAnimate The label to be animated
     * @param root The Pane where the label is added
     */
    public static void playLabelAnimation(Label labelToAnimate, Pane root) {
        labelToAnimate.setVisible(true);
        labelToAnimate.setScaleX(0);
        labelToAnimate.setScaleY(0);
        labelToAnimate.setOpacity(1.0);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(500),
                        new KeyValue(labelToAnimate.scaleXProperty(), 3),
                        new KeyValue(labelToAnimate.scaleYProperty(), 3),
                        new KeyValue(labelToAnimate.opacityProperty(), 1))
        );
        timeline.setOnFinished(event -> removeLabel(labelToAnimate, root));
        timeline.play();
    }
    /**
     * Initializes an ImageView with the specified properties and adds it to the specified Pane.
     *
     * @param img The ImageView to be initialized
     * @param x The x-coordinate of the ImageView
     * @param y The y-coordinate of the ImageView
     * @param height The height of the ImageView
     * @param width The width of the ImageView
     */
    public static void initObj(ImageView img, double x, double y, int height, int width) {
        img.setVisible(false);
        img.setX(x);
        img.setY(y);
        img.setFitWidth(width);
        img.setFitHeight(height);
        img.setOpacity(1.0);
        addImageView(img, Main.root);
    }
    /**
     * Plays a heart animation using an ImageView.
     * The heart image moves from the specified coordinates to the heart label's position.
     * After the animation finishes, the ImageView is removed from the root Pane.
     *
     * @param x The starting x-coordinate of the heart animation
     * @param y The starting y-coordinate of the heart animation
     */
    public static void playHeartAnimation(double x, double y) {
        initObj(heartImage, x, y, 35, 35);
        heartImage.setVisible(true);
        Path path = new Path();
        path.getElements().add(new MoveTo(x, y));
        path.getElements().add(new LineTo(Main.displayView.heartLabel.getLayoutX(), Main.displayView.heartLabel.getLayoutY()));
        PathTransition pathTransition = new PathTransition();
        pathTransition.setNode(heartImage);
        pathTransition.setPath(path);
        pathTransition.setInterpolator(Interpolator.EASE_OUT);
        pathTransition.setCycleCount(1);
        ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setNode(heartImage);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(1.5);
        scaleTransition.setToY(1.5);
        scaleTransition.setInterpolator(Interpolator.EASE_OUT);
        scaleTransition.setCycleCount(3); //thrice for increase and decrease in size
        SequentialTransition sequentialTransition = new SequentialTransition(scaleTransition, pathTransition);
        sequentialTransition.setOnFinished(event -> removeImageView(heartImage, Main.root));
        sequentialTransition.play();
    }

    private static Timeline debrisTimeline;
    /**
     * Plays a block debris animation using an ImageView.
     * The debris image scales and fades out over time.
     * After the animation finishes, the ImageView is removed from the root Pane.
     *
     * @param x The x-coordinate of the debris animation
     * @param y The y-coordinate of the debris animation
     * @param root The root Pane where the debris ImageView is added
     */
    public static void playBlockDebris(int x, int y, Pane root) {
        debrisImage = new ImageView("debris.png");
        initObj(debrisImage, x, y, 64, 87);
        debrisImage.setVisible(true);
        debrisImage.setScaleX(0);
        debrisImage.setScaleY(0);
        debrisImage.setOpacity(1.0);
        AtomicBoolean removalStarted = new AtomicBoolean(false);
        debrisTimeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(debrisImage.scaleXProperty(), 0),
                        new KeyValue(debrisImage.scaleYProperty(), 0),
                        new KeyValue(debrisImage.opacityProperty(), 1.0)),
                new KeyFrame(Duration.millis(500),
                        new KeyValue(debrisImage.scaleXProperty(), 1),
                        new KeyValue(debrisImage.scaleYProperty(), 1),
                        new KeyValue(debrisImage.opacityProperty(), 0.5)),
                new KeyFrame(Duration.millis(1000),
                        event -> {
                            if (!removalStarted.get()) {
                                removalStarted.set(true);
                                debrisImage.setVisible(false);
                                removeImageView(debrisImage, root);
                                debrisTimeline.stop();
                            }
                        },
                        new KeyValue(debrisImage.scaleXProperty(), 0),
                        new KeyValue(debrisImage.scaleYProperty(), 0),
                        new KeyValue(debrisImage.opacityProperty(), 1))
        );
        debrisTimeline.setCycleCount(1);
        debrisTimeline.play();
    }
    /**
     * Removes an ImageView from the specified root Pane.
     *
     * @param imageView The ImageView to be removed
     * @param root The root Pane from which the ImageView is removed
     */
    private static void removeImageView(ImageView imageView, Pane root) {
        root.getChildren().remove(imageView);
    }
    /**
     * Removes a Label from the specified root Pane.
     *
     * @param animatedLabel The Label to be removed
     * @param root The root Pane from which the Label is removed
     */
    private static void removeLabel(Label animatedLabel, Pane root) {
        root.getChildren().remove(animatedLabel);
    }
    /**
     * Adds an ImageView to the specified root Pane.
     *
     * @param img The ImageView to be added
     * @param root The root Pane to which the ImageView is added
     */
    public static void addImageView(final ImageView img, Pane root) {
        root.getChildren().add(img);
    }
    /**
     * Sets up a MediaPlayer for a specified sound file.
     *
     * @param sound The sound file path
     * @return The initialized MediaPlayer
     */
    private static MediaPlayer setMediaPlayer(String sound) {
        String soundFile = Main.class.getResource(sound).toString();
        return new MediaPlayer(new Media(soundFile));
    }
    /**
     * Plays a sound using a MediaPlayer. The MediaPlayer is disposed after the sound is played.
     *
     * @param soundFile The sound file path
     */
    public static void playSound(String soundFile) {
        MediaPlayer mPlayer = setMediaPlayer(soundFile);
        mPlayer.play();
        mPlayer.setOnEndOfMedia(() -> mPlayer.dispose());
    }
}
