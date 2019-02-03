package sample.animation;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
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


    public static void doAnimation(AnchorPane ap, double x1, double y1, double x2, Color color){

        Rectangle rectBasicTimeline = new Rectangle(x1, y1, 25, 25);
        rectBasicTimeline.setFill(color);
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

    public static void doBlendingStageProgress(Thread thread, AnchorPane ap, double x1, double y1){
        Task task = new Task<Void>() {
            @Override public Void call() {
                final int max = 100;
                for (int i = 1; i <= max; i++) {
                    updateProgress(i, max);
                }
                return null;
            }
        };

        ProgressBar bar = new ProgressBar();
        bar.setTranslateX(x1);
        bar.setTranslateY(y1);
        bar.progressProperty().bind(task.progressProperty());
        ap.getChildren().add(bar);
        thread.start();
    }

    public static void doPause(Button button, Thread thread){
        try {
            thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void doCuttingStageAnimation(AnchorPane ap, ImageView imageView, double y2){
        ap.getChildren().add(imageView);
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        //timeline.setAutoReverse(true);
        final KeyValue kv = new KeyValue(imageView.yProperty(), y2*0.7);
        final KeyFrame kf = new KeyFrame(Duration.millis(2000), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setDelay(Duration.millis(10000));
        timeline.play();

    }

    public static void doEndlessBrick(AnchorPane ap, double x1, double y1, Color color){

        Rectangle rect = new Rectangle(x1+135, y1-20, 10, 25);
        rect.setFill(color);
        final Timeline timeline = new Timeline();
        //timeline.setCycleCount(Timeline.INDEFINITE);
        //timeline.setAutoReverse(true);
        KeyFrame initial = new KeyFrame(Duration.millis(1000), e -> ap.getChildren().addAll(rect));
        //
        final KeyValue kv = new KeyValue(rect.widthProperty(), 100);
        final KeyFrame kf = new KeyFrame(Duration.millis(10000), kv);
        timeline.getKeyFrames().add(initial);
        timeline.getKeyFrames().add(kf);
        timeline.setDelay(Duration.millis(1000));
        timeline.play();
        //ap.getChildren().add(rect);
    }

    public static void doDryingAnimation(AnchorPane ap, double x1, double y1){
        ImageView dryingImageView=new ImageView();
        dryingImageView.setX(x1);
        dryingImageView.setY(y1);
        List<Image> dryingImagesList=new ArrayList<>();

        //IMAGES FOR ANIMATION
        dryingImagesList.add(new Image("/sample/images/stages/drying0.png", 100,100,true,true));
        dryingImagesList.add(new Image("/sample/images/stages/drying1.png",100,100,true,true));
        dryingImagesList.add(new Image("/sample/images/stages/drying.png",100,100,true,true));
        //
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        //timeline.setAutoReverse(true);
        for (int i=0;i<dryingImagesList.size();i++){
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(i), new KeyValue(dryingImageView.imageProperty(), dryingImagesList.get(i))));
        }
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(dryingImagesList.size()), new KeyValue(dryingImageView.imageProperty(), null)));
        timeline.setDelay(Duration.millis(15000));
        timeline.play();
        //dryingImageView.setImage(dryingImagesList.get(0));
        ap.getChildren().add(dryingImageView);
    }

}
