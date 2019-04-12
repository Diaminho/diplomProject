package sample.manager;

import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jdk.nashorn.internal.runtime.regexp.joni.constants.TargetInfo;
import sample.Experiment;
import sample.animation.AnimationFunctions;
import sample.controller.MainController;
import sample.resource.Material;
import sample.sampling.SampleGenerator;
import sample.task.ExperimentTask;

import java.io.IOException;
import java.util.*;


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
    private AnchorPane experimentPane;
    @FXML
    private Text rawQualityID;
    @FXML
    private Text firstStageText;
    @FXML
    private Button pauseButton;

    @FXML
    Stage primaryStage;

    private Thread animationThread;

    private Experiment newExperiment;

    private Map<Material, Integer> materialIntegerMap;

    private Map<Material, List<Double>> materialQualityMap;

    Label rawVolumeLabel;

    private List<Timeline> timelineList;

    public ExperimentManager(Parent root) {
        ExperimentManager.root = root;
        init();
        timelineList=new ArrayList<>();
    }

    public Map<Material, List<Double>> getMaterialQualityMap() {
        return materialQualityMap;
    }

    public void setMaterialQualityMap(Map<Material, List<Double>> materialQualityMap) {
        this.materialQualityMap = materialQualityMap;
    }

    private void init() {

        sandID = (TextField) root.lookup("#sandID");
        cementID = (TextField) root.lookup("#cementID");
        clayID = (TextField) root.lookup("#clayID");
        resultExperimentID = (Text) root.lookup("#resultExperimentID");
        experimentPane = (AnchorPane) root.lookup("#experimentPane");
        rawQualityID = (Text) root.lookup("#rawQualityID");
        firstStageText = (Text) root.lookup("#firstStageText");
        pauseButton = (Button) root.lookup("#pauseButton");
        rawVolumeLabel=new Label();
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
        Timeline timeline;
        ImageView iv=new ImageView();

        if (materialIntegerMap !=null){
            //GRAPHICS
            //gc.clearRect(0, 0, canvasExperiment.getWidth(), canvasExperiment.getHeight());
            int pos=0;
            for (Material i: materialIntegerMap.keySet()) {
                timeline=AnimationFunctions.doAnimation(experimentPane,80,40+pos,150, Color.SANDYBROWN);
                timelineList.add(timeline);
                iv=setImageViewProperties(i.getMaterialImage(),60,60,0,pos);
                experimentPane.getChildren().add(iv);
                //gc.strokeText(Material.toString(i.getVolume()),30,110+pos);
                pos+=120;
            }
        }
        else {
            showAlertDialog();
            return -1;
        }


        //NEED TO MOVE TO ANOTHER THREAD
        newExperiment=new Experiment(materialIntegerMap);
        ExperimentTask experimentTask=new ExperimentTask();
        Thread thread=new Thread(experimentTask.FirstStageTask(newExperiment, rawVolumeLabel,1));
        thread.start();

        //System.out.println(thread.getName());
        //newExperiment.produceRawMaterial();
        //NEED TO SLEEP THREAD AFTER PRODUCING TO MODEL REAL PRODUCING

        //
        //
        //double rawVolume=newExperiment.getRaw().getVolume();
        //System.out.println("RAW VOLUME: "+rawVolume);

        ///CREATE LABEL FOR RAW VOLUME
        //rawVolumeLabel.setLabelFor(iv);
        rawVolumeLabel.setText("0");
        rawVolumeLabel.setVisible(true);
        rawVolumeLabel.setTranslateX(260);
        rawVolumeLabel.setTranslateY(270);
        experimentPane.getChildren().add(rawVolumeLabel);
        ///


        Image image;
        //BLENDING
        if (newExperiment.getRawList().size()>0){
            //GRAPHICS
            timeline=AnimationFunctions.doBlendingStageProgress(experimentPane,220,250, 100);
            timelineList.add(timeline);

            image=newExperiment.findImageByMaterialName("Blending");

            iv=setImageViewProperties(image,150,200,experimentPane.getWidth()/4,experimentPane.getHeight()/7);
            experimentPane.getChildren().add(iv);
            //GraphicsContext gc=canvasExperiment.getGraphicsContext2D();
            //gc.clearRect(0, 0, canvasExperiment.getWidth(), canvasExperiment.getHeight());
            //gc.drawImage(newExperiment.getRaw().getMaterialImage(), 200, 80, 100, 100);
            //gc.strokeText(Material.toString(newExperiment.getRaw().getVolume()),250,200);
        }


        //RAW TO CUT
        timeline=AnimationFunctions.doEndlessBrick(experimentPane,200,200,Color.FIREBRICK);
        timelineList.add(timeline);
        //CUTTING
        image=newExperiment.findImageByMaterialName("Cutting");
        iv=setImageViewProperties(image,60,60,experimentPane.getWidth()/2,0);
        //experimentPane.getChildren().add(iv);
        timeline=AnimationFunctions.doCuttingStageAnimation(experimentPane,iv,200);
        timelineList.add(timeline);
        //
        newExperiment.doCutting(0.9);
        //



        //DRYING
        //ImageView iv2=setImageViewProperties(newExperiment.getStages().get(2),60,30,experimentPane.getWidth()/2+100,0);
        timeline=AnimationFunctions.doDryingAnimation(experimentPane,500,100);
        timelineList.add(timeline);
        //



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
    public void onChooseMaterialsButton(Map<Material, Integer> materials) {
        if (materials!=null) {
            materialIntegerMap = new HashMap<>(materials);
            //
            Double quality;
            List<Double> qualityList = new ArrayList<>();
            materialQualityMap = new HashMap<>();
            for (Object o : materialIntegerMap.keySet()) {
                //set material properties
                //((Material)o).addProperty("","0.9");
                //
                qualityList = SampleGenerator.generateSample(materialIntegerMap.get(o), 0.05f, 0.88f);
                materialQualityMap.put((Material) o, qualityList);
            }
        }
        //
        //SampleGenerator.generateSample();
        //

    }

    @FXML
    public Integer onOperateButton(){
        if (materialIntegerMap==null) {
            showAlertDialog();
            return -1;
        }
        else {
            return 0;
        }
    }


    @FXML
    public void onPauseButton(){
        if (timelineList.get(0).getStatus().equals(Animation.Status.RUNNING)) {
            for (Timeline timeline:timelineList) {
                timeline.pause();
            }
        }
        else {
            for (Timeline timeline:timelineList) {
                timeline.play();
            }
        }
    }


    private void showAlertDialog(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Не были выбраны материалы");
        alert.showAndWait();
    }


    //////////TEST THREADS



    public void startMyPlayer() {
        System.out.println("PLAYING NOW...");
        animationThread.start();
    }

    public void pauseMyPlayer() {
        System.out.println("PAUSED NOW...");
        synchronized (animationThread) {
            try {
                animationThread.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void resumeMyPlayer() {
        System.out.println("RESUMING NOW...");
        synchronized (animationThread) {
            animationThread.notify();
        }
    }

    public void setRawVolumeLabel(String text){

    }

}
