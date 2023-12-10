package brickGame;

import javafx.animation.*;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class Animation {

    public static void playAnimation(Label labelToAnimate, Pane main) {
        labelToAnimate.setVisible(true);
        labelToAnimate.setScaleX(0);
        labelToAnimate.setScaleY(0);
        labelToAnimate.setOpacity(1.0);

        //timeline for the animation
        Timeline timeline = new Timeline();
        Duration duration = Duration.millis(500); //animation duration in ms

        //keyframe definitions
        KeyValue scaleXValue = new KeyValue(labelToAnimate.scaleXProperty(), 3);
        KeyValue scaleYValue = new KeyValue(labelToAnimate.scaleYProperty(), 3);
        KeyValue opacityValue = new KeyValue(labelToAnimate.opacityProperty(), 1);

        //adding key frames to timeline
        KeyFrame keyFrame = new KeyFrame(duration, scaleXValue, scaleYValue, opacityValue);
        timeline.getKeyFrames().add(keyFrame);

        //set up callback for when the animation finishes
        timeline.setOnFinished(event -> removeLabel(labelToAnimate, main));

        //play animation
        timeline.play();
    }
    public static void playHeartAnimation(ImageView imageViewToAnimate, double x, double y, Pane main) {
        imageViewToAnimate.setVisible(true);
        imageViewToAnimate.setX(x);
        imageViewToAnimate.setY(y);
        imageViewToAnimate.setFitWidth(40);
        imageViewToAnimate.setFitHeight(40);
        imageViewToAnimate.setOpacity(1.0);
        //animation path
        Path path = new Path();
        path.getElements().add(new MoveTo(x, y));
        path.getElements().add(new LineTo(Main.displayView.heartLabel.getLayoutX(),Main.displayView.heartLabel.getLayoutY())); // Adjust the distance
        //path transition
        PathTransition pathTransition = new PathTransition();
        pathTransition.setNode(imageViewToAnimate);
        pathTransition.setPath(path);
        pathTransition.setInterpolator(Interpolator.EASE_OUT);
        pathTransition.setCycleCount(1);
        //scale transition
        ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setNode(imageViewToAnimate);
        scaleTransition.setFromX(1.0); // Initial scale X
        scaleTransition.setFromY(1.0); // Initial scale Y
        scaleTransition.setToX(1.5);   // Target scale X
        scaleTransition.setToY(1.5);   // Target scale Y
        scaleTransition.setInterpolator(Interpolator.EASE_OUT);
        scaleTransition.setCycleCount(3); //thrice for increase and decrease in size
        //sequential transition
        SequentialTransition sequentialTransition = new SequentialTransition(scaleTransition, pathTransition);
        //callback for when animation finishes
        sequentialTransition.setOnFinished(event -> removeImageView(imageViewToAnimate, main));
        //play animation
        sequentialTransition.play();
    }




    private static void removeImageView(ImageView imageView, Pane main) {
        main.getChildren().remove(imageView);
    }

    private static void removeLabel(Label animatedLabel, Pane main) {
        //remove label from the pane
        main.getChildren().remove(animatedLabel);
    }
    public static void addImage(final ImageView img, Pane main){
        // Remove the img from the scene after the animation
        main.getChildren().remove(img);
    }
}
