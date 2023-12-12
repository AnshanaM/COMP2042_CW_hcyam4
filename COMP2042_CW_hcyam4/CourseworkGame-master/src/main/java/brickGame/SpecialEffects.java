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

    public static SpecialEffects getInstance() {
        if (instance == null) {
            instance = new SpecialEffects();
        }
        return instance;
    }

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

    public static void initObj(ImageView img, double x, double y, int height, int width) {
        img.setVisible(false);
        img.setX(x);
        img.setY(y);
        img.setFitWidth(width);
        img.setFitHeight(height);
        img.setOpacity(1.0);
        addImageView(img, Main.root);
    }

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

    private static void removeImageView(ImageView imageView, Pane root) {
        root.getChildren().remove(imageView);
    }

    private static void removeLabel(Label animatedLabel, Pane root) {
        root.getChildren().remove(animatedLabel);
    }

    public static void addImageView(final ImageView img, Pane root) {
        root.getChildren().add(img);
    }

    private static MediaPlayer setMediaPlayer(String sound) {
        String soundFile = Main.class.getResource(sound).toString();
        return new MediaPlayer(new Media(soundFile));
    }

    public static void playSound(String soundFile) {
        MediaPlayer mPlayer = setMediaPlayer(soundFile);
        mPlayer.play();
        mPlayer.setOnEndOfMedia(() -> mPlayer.dispose());
    }
}
