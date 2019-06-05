package sample.manager;

import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.Experiment;
import sample.animation.AnimationFunctions;
import sample.controller.MainController;
import sample.resource.Material;
import sample.task.ExperimentTask;
import sample.task.MarshallConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ExperimentManager {
    //Stage primaryStage;

    private static Parent root;

    private AnchorPane experimentPane;

    private TextArea logAreaId;

    private Button startExperimentButton;

    private Button fixButton;

    private Button pauseButton;


    Stage primaryStage;
    public static Experiment newExperiment;
    Label rawVolumeLabel;
    ExperimentTask experimentTask;
    private List<Timeline> timelineList;
    private List<Runnable> taskList=new ArrayList<>();



    public Experiment getNewExperiment() {
        return newExperiment;
    }

    public void setNewExperiment(Experiment newExperiment) {
        this.newExperiment = newExperiment;
    }

    public ExperimentManager(Parent root, Experiment experiment) {
        ExperimentManager.root = root;
        init();
        timelineList=new ArrayList<>();

        if (experiment.getMaterialMap() == null) {
            ExperimentManager.newExperiment = MarshallConverter.marshalingToExperiment();
            if (ExperimentManager.newExperiment == null) {
                startExperimentButton.setDisable(true);
                fixButton.setDisable(true);
                pauseButton.setDisable(true);
            }
        }
        else {
            ExperimentManager.newExperiment = experiment;
        }

    }

    private void init() {
        experimentPane = (AnchorPane) root.lookup("#experimentPane");
        rawVolumeLabel = new Label();
        logAreaId = (TextArea) root.lookup("#logAreaId");
        startExperimentButton = (Button) root.lookup("#startExperimentButton");
        fixButton = (Button) root.lookup("#fixButton");
        pauseButton = (Button) root.lookup("#pauseButton");
    }

    private ImageView setImageViewProperties(Image image, double width, double height, double x, double y){
        ImageView iv=new ImageView(image);
        iv.setFitWidth(width);
        iv.setFitHeight(height);
        iv.setX(x);
        iv.setY(y);
        return iv;
    }

    @FXML
    public Integer onStartExperimentButton() throws IOException {
        //Material material=new Material("Цемент");
        //material.setMaterialImage(new Image("/sample/image/cement.jpg"));
        //GraphicsContext gc=canvasExperiment.getGraphicsContext2D();
        //startMyPlayer();

        //test LOAD EXPERIMENT FROM XML
        /*
        try {
            MarshallConverter.marshalingToXML(newExperiment);
        } catch (JAXBException e) {
            e.printStackTrace();
        }*/

        /**
        test thread with pause
        **/
        //myThread=new MyThread("testThread");

        Timeline timeline;
        ImageView iv=new ImageView();
        newExperiment.generatePreData();

        if (newExperiment.getMaterialMap() !=null){
            //GRAPHICS
            //gc.clearRect(0, 0, canvasExperiment.getWidth(), canvasExperiment.getHeight());
            int pos=70;
            for (Material i: newExperiment.getMaterialMap().keySet()) {
                //timeline=AnimationFunctions.doAnimation(experimentPane,80,40+pos,150, Color.SANDYBROWN);
                //timelineList.add(timeline);
                Label newLabel = new Label(i.getName());
                newLabel.setFont(new Font(12));
                newLabel.setTranslateX(5);
                newLabel.setTranslateY(pos - 12);
                newLabel.setVisible(true);
                experimentPane.getChildren().add(newLabel);
                iv=setImageViewProperties(i.getMaterialImage(),60,60,0,pos);
                experimentPane.getChildren().add(iv);
                iv=setImageViewProperties(new Image("/sample/image/right_arrow.png"),130,60,60, pos);
                experimentPane.getChildren().add(iv);
                //gc.strokeText(Material.toString(i.getVolume()),30,110+pos);
                pos+=120;
            }
        }
        else {
            showAlertDialog();
            return -1;
        }
        
        experimentTask=new ExperimentTask();
        experimentTask.setDate(new Date());
        Runnable t=experimentTask.BlendingTask(newExperiment, rawVolumeLabel);
        //taskList.add(t);
        //t.start();
        experimentTask.addSuspendableTask(t,5, 5);


        //BRIGADES

        iv=setImageViewProperties(new Image("/sample/image/brigade.png"),70,70,0,300);
        Label brigadeTitleLabel = new Label("Бригады");
        brigadeTitleLabel.setFont(new Font(14));
        brigadeTitleLabel.setTranslateX(5);
        brigadeTitleLabel.setTranslateY(275);
        brigadeTitleLabel.setVisible(true);
        experimentPane.getChildren().add(brigadeTitleLabel);

        Label brigadeLabel=new Label();
        brigadeLabel.setTranslateX(80);
        brigadeLabel.setTranslateY(330);
        brigadeLabel.setVisible(true);
        brigadeLabel.setText(""+newExperiment.getBrigades().size());
        brigadeLabel.setFont(Font.font("Cambria", 32));
        experimentPane.getChildren().add(iv);
        experimentPane.getChildren().add(brigadeLabel);
        //



        //System.out.println(thread.getName());
        //newExperiment.produceRawMaterial();
        //NEED TO SLEEP THREAD AFTER PRODUCING TO MODEL REAL PRODUCING

        //
        //
        //double rawVolume=newExperiment.getRaw().getVolume();
        //System.out.println("RAW VOLUME: "+rawVolume);

        ///CREATE LABEL FOR RAW VOLUME
        //rawVolumeLabel.setLabelFor(iv);
        ///


        Image image;
        //BLENDING
        Label blendingLabel = new Label("Смешивание");
        blendingLabel.setFont(new Font(20));
        blendingLabel.setTranslateX(185);
        blendingLabel.setTranslateY(30);
        blendingLabel.setVisible(true);
        experimentPane.getChildren().add(blendingLabel);
        if (newExperiment.getRawList() != null){
            //GRAPHICS
            int size = newExperiment.getStageQualityList().get(0).getStageToolQuality().size();
            int width = 150;
            int height = 200;
            if (size > 1) {
                width *= 0.7;
                height *= 0.7;
            }
            timeline=AnimationFunctions.doBlendingStageProgress(experimentPane,190,(width + 20) * size + 60, 100);
            timelineList.add(timeline);


            rawVolumeLabel.setText("0");
            rawVolumeLabel.setVisible(true);
            rawVolumeLabel.setTranslateX(225);
            rawVolumeLabel.setTranslateY((width + 20) * size + 80);
            experimentPane.getChildren().add(rawVolumeLabel);


            image = newExperiment.findImageByMaterialName("Blending");
            for (int i = 0; i < size; i++) {
                iv = setImageViewProperties(image, width, height, 190, ((width + 20) * i) + experimentPane.getHeight() / 7);
                experimentPane.getChildren().add(iv);
            }
            //GraphicsContext gc=canvasExperiment.getGraphicsContext2D();
            //gc.clearRect(0, 0, canvasExperiment.getWidth(), canvasExperiment.getHeight());
            //gc.drawImage(newExperiment.getRaw().getMaterialImage(), 200, 80, 100, 100);
            //gc.strokeText(Material.toString(newExperiment.getRaw().getVolume()),250,200);
        }


        //RAW TO CUT
        //timeline=AnimationFunctions.doEndlessBrick(experimentPane,200,200,Color.FIREBRICK);
        //timelineList.add(timeline);



        iv=setImageViewProperties(new Image("/sample/image/right_arrow.png"),110,60,310, 160);
        experimentPane.getChildren().add(iv);

        //CUTTING
        image=newExperiment.findImageByMaterialName("Cutting");
        Label cuttingLabel = new Label("Формовка");
        cuttingLabel.setFont(new Font(20));
        cuttingLabel.setTranslateX(430);
        cuttingLabel.setTranslateY(110);
        cuttingLabel.setVisible(true);
        experimentPane.getChildren().add(cuttingLabel);

        //iv=setImageViewProperties(image,60,60,experimentPane.getWidth()/2,0);
        //experimentPane.getChildren().add(iv);
        timeline=AnimationFunctions.doCuttingStageAnimation(experimentPane, 410,150);
        timelineList.add(timeline);
        Label newLabel = new Label("0");
        newLabel.setFont(new Font(16));
        newLabel.setTranslateX(465);
        newLabel.setTranslateY(240);
        newLabel.setVisible(true);
        experimentPane.getChildren().add(newLabel);
        //
        //newExperiment.doCutting();

        Runnable t2=experimentTask.CuttingTask(newExperiment, newLabel);
        //taskList.add(t2);
        //t2.start();
        experimentTask.addSuspendableTask(t2,6, 6);

        //DRYING
        //ImageView iv2=setImageViewProperties(newExperiment.getStages().get(2),60,30,experimentPane.getWidth()/2+100,0);
        iv=setImageViewProperties(new Image("/sample/image/right_arrow.png"),100,70,550, 150);
        experimentPane.getChildren().add(iv);
        image=newExperiment.findImageByMaterialName("Drying");
        Label dryingLabel = new Label("Сушка");
        dryingLabel.setFont(new Font(20));
        dryingLabel.setTranslateX(725);
        dryingLabel.setTranslateY(70);
        dryingLabel.setVisible(true);
        experimentPane.getChildren().add(dryingLabel);
        iv=setImageViewProperties(image,200,150,660,100);
        experimentPane.getChildren().add(iv);
        //
        Label newLabelDrying = new Label("0");
        newLabelDrying.setFont(new Font(16));
        newLabelDrying.setTranslateX(740);
        newLabelDrying.setTranslateY(250);
        newLabelDrying.setVisible(true);
        experimentPane.getChildren().add(newLabelDrying);
        Runnable t3=experimentTask.DryingTask(newExperiment, newLabelDrying);
        taskList.add(t3);
        //t3.start();
        experimentTask.addSuspendableTask(t3,7, 6);

        //BURNING
        //SOME ANIMATION
        //
        iv=setImageViewProperties(new Image("/sample/image/right_arrow.png"),100,70,850, 150);
        experimentPane.getChildren().add(iv);
        image=newExperiment.findImageByMaterialName("Burning");
        Label burningLabel = new Label("Обжиг");
        burningLabel.setFont(new Font(20));
        burningLabel.setTranslateX(1030);
        burningLabel.setTranslateY(100);
        burningLabel.setVisible(true);
        experimentPane.getChildren().add(burningLabel);

        iv=setImageViewProperties(image,200,100,950,130);
        experimentPane.getChildren().add(iv);
        //TASK
        Label newLabelBurning = new Label("0");
        newLabelBurning.setFont(new Font(16));
        newLabelBurning.setTranslateX(1030);
        newLabelBurning.setTranslateY(230);
        newLabelBurning.setVisible(true);
        experimentPane.getChildren().add(newLabelBurning);
        Runnable t4=experimentTask.BurningTask(newExperiment, newLabelBurning);
        //taskList.add(t4);
        //t4.start();
        experimentTask.addSuspendableTask(t4,8, 6);

        //TASK
        iv=setImageViewProperties(new Image("/sample/image/right_arrow.png"),80,50,1120, 150);
        experimentPane.getChildren().add(iv);
        image=newExperiment.findImageByMaterialName("Logistic");
        Label logisticLabel = new Label("Логистика");
        logisticLabel.setFont(new Font(20));
        logisticLabel.setTranslateX(1230);
        logisticLabel.setTranslateY(100);
        logisticLabel.setVisible(true);
        experimentPane.getChildren().add(logisticLabel);
        iv=setImageViewProperties(image,160,80,1200,130);
        experimentPane.getChildren().add(iv);

        Label newLabelLogistic = new Label("0");
        newLabelLogistic.setFont(new Font(16));
        newLabelLogistic.setTranslateX(1270);
        newLabelLogistic.setTranslateY(200);
        newLabelLogistic.setVisible(true);
        experimentPane.getChildren().add(newLabelLogistic);
        Runnable t5=experimentTask.LogisticTask(newExperiment, logAreaId, newLabelLogistic);
        //taskList.add(t4);
        //t4.start();
        experimentTask.addSuspendableTask(t5,9, 6);
        return 0;
    }

    @FXML
    public void onGoMainButton() {
        try {
            new MainController(new Stage());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }



    @FXML
    public void onPauseButton(){
        if (timelineList.get(0).getStatus().equals(Animation.Status.RUNNING)) {
            for (Timeline timeline:timelineList) {
                timeline.pause();
            }
            //pause();
            for (Runnable t: taskList){
                //if (!t.isSuspendFlag()) {
                    //t.suspend();
                //}
            }
            //TRY EXECUTOR
            experimentTask.getExecutor().pause();
        }
        else {
            for (Timeline timeline:timelineList) {
                timeline.stop();
                timeline.play();
            }
            //experimentTask.resume();
            for (Runnable t: taskList){
                //t.cancel(false);
                //if (t.isSuspendFlag()) {
                //    t.resume();
                //}
                //Thread th=new Thread(t);
                //th.run();
            }
            //TRY EXECUTOR
            experimentTask.getExecutor().resume();

        }
    }

    public void onFixButton() {

    }


    private void showAlertDialog(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Не были выбраны материалы");
        alert.showAndWait();
    }

}
