package sample.animation;

import javafx.animation.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class AnimationFunctions {


    public static void doAnimation(AnchorPane ap, double x1, double y1, double x2, double y2){

        Rectangle rectBasicTimeline = new Rectangle(x1, y1, 25, 25);
        rectBasicTimeline.setFill(Color.SANDYBROWN);
        ap.getChildren().add(rectBasicTimeline);
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        //timeline.setAutoReverse(true);
        final KeyValue kv = new KeyValue(rectBasicTimeline.xProperty(), x2);
        final KeyFrame kf = new KeyFrame(Duration.millis(5000), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
        //ap.getChildren().add(timeline);

    }

    public static void doSecondStageAnimation(AnchorPane ap, double x1, double y1, double x2, double y2){


    }

}
