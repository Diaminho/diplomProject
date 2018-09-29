package sample.managers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Experiment;
import sample.controllers.MainController;
import sample.resources.Cement;
import sample.resources.Clay;
import sample.resources.Sand;
import sample.resources.Water;

import java.io.IOException;


public class ExperimentManager {
    //Stage primaryStage;

    private static Parent root;

    @FXML
    private TextField cementID;
    @FXML
    private TextField sandID;
    @FXML
    private TextField clayID;
    @FXML
    private Text resultExperimentID;
    @FXML
    private Canvas canvasExperiment;
    @FXML
    private Text rawQualityID;
    @FXML
    private Text firstStageText;

    @FXML
    Stage primaryStage;

    private Experiment NewExperiment;

    public ExperimentManager(Parent root) {
        ExperimentManager.root = root;
        init();
    }



    private void init() {

        sandID = (TextField) root.lookup("#sandID");
        cementID = (TextField) root.lookup("#cementID");
        clayID = (TextField) root.lookup("#clayID");
        resultExperimentID = (Text) root.lookup("#resultExperimentID");
        canvasExperiment = (Canvas) root.lookup("#canvasExperiment");
        rawQualityID = (Text) root.lookup("#rawQualityID");
        firstStageText = (Text) root.lookup("#firstStageText");
    }




    @FXML
    public void onStartExperimentButton() throws IOException {
        Sand sand=new Sand((double)Integer.parseInt(sandID.getText()), 95.0, 12);
        Cement cement=new Cement((double)Integer.parseInt(cementID.getText()), 90.0, 20);
        Clay clay=new Clay((double)Integer.parseInt(clayID.getText()), 90.0);
        Water water=new Water();
        //Experiment NewExperiment=new Experiment((double)Integer.parseInt(sandID.getText()), (double)Integer.parseInt(cementID.getText()));
        NewExperiment=new Experiment(sand, cement,water, clay);
        System.out.println("SAND: "+NewExperiment.getSand().getVolume()+ ";  CEMENT: "+NewExperiment.getCement().getVolume());
        //NewExperiment.getBrickNumber();
        resultExperimentID.setText("Можно произвести: "+NewExperiment.getBrickNumber()+" единиц(ы) сырья");
        resultExperimentID.setVisible(true);
        //System.out.println("BRICKS: "+NewExperiment.brick);

        //GRAPHICS
        GraphicsContext gc=canvasExperiment.getGraphicsContext2D();
        gc.clearRect(0, 0, canvasExperiment.getWidth(), canvasExperiment.getHeight());
        double height=canvasExperiment.getHeight()/5;
        //double height=canvasExperiment.getHeight();
        //double width=canvasExperiment.getWidth();
        double lineWidth=2.0;
        gc.setLineWidth(lineWidth);
        //RECTANGLE FOR SAND
        NewExperiment.DrawRectangle(gc, Color.DARKGOLDENROD, 1,height, NewExperiment.getSand().getVolume(), "песок");
        //gc.setLineWidth(5);
        gc.setStroke(Color.DARKGOLDENROD);
        gc.strokeLine(48, height/2+35, 89, height*1.5+35);
        //RECTANGLE FOR CEMENT
        NewExperiment.DrawRectangle(gc, Color.GREY, 1,height*2, NewExperiment.getCement().getVolume(), "цемент");
        gc.setStroke(Color.GREY);
        gc.strokeLine(48, height*1.5+35, 89, height*1.5+35);

        //RECTANGLE FOR CLAY
        NewExperiment.DrawRectangle(gc, Color.web("#b66a50"), 1,height*3, NewExperiment.getClay().getVolume(), "глина");
        gc.setStroke(Color.web("#b66a50"));
        gc.strokeLine(48, height*3+10, 89, height*1.5+35);


        //RECTANGLE FOR RESULT AFTER 1 STAGE

/*
        double D = 3;  // diameter.

        DoubleProperty x  = new SimpleDoubleProperty();
        DoubleProperty y  = new SimpleDoubleProperty();


        gc.setStroke(Color.DARKGOLDENROD);


        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        new KeyValue(x, 48),
                        new KeyValue(y, height/2+35)
                ),
                new KeyFrame(Duration.seconds(3),
                        new KeyValue(x, 85),
                        new KeyValue(y, height*1.5+25)
                )
        );

        Timeline timeline2 = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        new KeyValue(x, 48),
                        new KeyValue(y, height*1.5+25)
                ),
                new KeyFrame(Duration.seconds(3),
                        new KeyValue(x, 85),
                        new KeyValue(y, height*1.5+25)
                )
        );

        timeline.setAutoReverse(true);
        timeline.setCycleCount(2);

        //timeline2.setAutoReverse(true);
        //timeline2.setCycleCount(3);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                //gc.setFill(Color.CORNSILK);
                //gc.fillRect(0, 0, W, H);
                gc.setStroke(Color.DARKGOLDENROD);
                gc.strokeOval(
                        x.doubleValue(),
                        y.doubleValue(),
                        D,
                        D
                );
            }
        };


        ParallelTransition pt = new ParallelTransition();
        pt.getChildren().add(timeline);
        pt.getChildren().add(timeline2);

        timer.start();
        pt.play();
        //timeline2.play();

*/
        while (NewExperiment.produceRawMaterial()==0) {
            //NewExperiment.produceBrick();
            gc.clearRect(90, height, height*3, height*3);
            //RECTANGLE FOR RAW MATERIAL AFTER 1 STAGE
            NewExperiment.DrawRectangle(gc, Color.SADDLEBROWN, 90, height*2, NewExperiment.getRaw().getVolume(), "сырье");

            rawQualityID.setText("Качество сырья:"+NewExperiment.getRaw().getQuality()+"%");
            rawQualityID.setVisible(true);
            firstStageText.setVisible(true);

        }


    }

    @FXML
    public void onGoMainButton() {
        try {
            new MainController(new Stage());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }



}
