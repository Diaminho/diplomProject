package sample.animation;

import javafx.animation.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class AnimationFunctions {


    public static Timeline doAnimation(AnchorPane ap, double x1, double y1, double x2, Color color){

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
        return timeline;
        //ap.getChildren().add(timeline);

    }

    public static Timeline doBlendingStageProgress(AnchorPane ap, double x1, double y1, int delay){
        ProgressBar bar = new ProgressBar();
        ap.getChildren().add(bar);
        bar.setTranslateX(x1);
        bar.setTranslateY(y1);
        IntegerProperty seconds = new SimpleIntegerProperty();
        bar.progressProperty().bind(seconds.divide(10.0));
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(seconds, 0)),
                new KeyFrame(Duration.seconds(10), e-> {
                    // do anything you need here on completion...
                    //System.out.println("Смешивание завершено");
                }, new KeyValue(seconds, 10))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.setDelay(Duration.millis(delay));
        timeline.play();
        return timeline;
    }

    public static void doPause(Button button, Thread thread){
        try {
            thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static Timeline doCuttingStageAnimation(AnchorPane ap, double x1, double y1){

        /////


        ImageView cuttingImageView=new ImageView();
        cuttingImageView.setX(x1);
        cuttingImageView.setY(y1);
        List<Image> cuttingImagesList=new ArrayList<>();

        //IMAGES FOR ANIMATION
        cuttingImagesList.add(new Image("/sample/image/stage/cutting/cutting.png", 150,150,true,true));
        cuttingImagesList.add(new Image("/sample/image/stage/cutting/cutting1.png", 150,150,true,true));
        cuttingImagesList.add(new Image("/sample/image/stage/cutting/cutting2.png",150,150,true,true));
        cuttingImagesList.add(new Image("/sample/image/stage/cutting/cutting3.png",150,150,true,true));
        //
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        //timeline.setAutoReverse(true);
        for (int i=0;i<cuttingImagesList.size();i++){
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(i*2), new KeyValue(cuttingImageView.imageProperty(), cuttingImagesList.get(i))));
        }
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(cuttingImagesList.size()*2), new KeyValue(cuttingImageView.imageProperty(), null)));
        timeline.play();
        //dryingImageView.setImage(dryingImagesList.get(0));
        ap.getChildren().add(cuttingImageView);
        return timeline;
    }

    public static Timeline doEndlessBrick(AnchorPane ap, double x1, double y1, Color color){

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
        return timeline;
        //ap.getChildren().add(rect);
    }

    public static Timeline doDryingAnimation(AnchorPane ap, double x1, double y1){
        ImageView dryingImageView=new ImageView();
        dryingImageView.setX(x1);
        dryingImageView.setY(y1);
        List<Image> dryingImagesList=new ArrayList<>();

        //IMAGES FOR ANIMATION
        dryingImagesList.add(new Image("/sample/image/stage/drying0.png", 100,100,true,true));
        dryingImagesList.add(new Image("/sample/image/stage/drying1.png",100,100,true,true));
        dryingImagesList.add(new Image("/sample/image/stage/drying.png",100,100,true,true));
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
        return timeline;
    }

}
