package sample.manager;

import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Experiment;
import sample.animation.AnimationFunctions;
import sample.controller.MainController;
import sample.resource.Material;
import sample.sampling.SampleGenerator;
import sample.task.ExperimentTask;
import sample.task.SuspendableTask;

import java.io.IOException;
import java.util.*;


public class ExperimentManager {
    //Stage primaryStage;

    private static Parent root;

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
    ExperimentTask experimentTask;
    private List<Timeline> timelineList;
    private List<SuspendableTask> taskList=new ArrayList<>();



    public Experiment getNewExperiment() {
        return newExperiment;
    }

    public void setNewExperiment(Experiment newExperiment) {
        this.newExperiment = newExperiment;
    }

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

        /**
        test thread with pause
        **/
        //myThread=new MyThread("testThread");

        Timeline timeline;
        ImageView iv=new ImageView();

        if (materialIntegerMap !=null){
            //GRAPHICS
            //gc.clearRect(0, 0, canvasExperiment.getWidth(), canvasExperiment.getHeight());
            int pos=70;
            for (Material i: materialIntegerMap.keySet()) {
                //timeline=AnimationFunctions.doAnimation(experimentPane,80,40+pos,150, Color.SANDYBROWN);
                //timelineList.add(timeline);
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


        //NEED TO MOVE TO ANOTHER THREAD
        if (newExperiment!=null) {
            newExperiment.setMaterialMap(materialIntegerMap);
            newExperiment.fillNeededMaterials();
        }
        else{
            newExperiment = new Experiment(materialIntegerMap);
        }
        experimentTask=new ExperimentTask();
        SuspendableTask t=experimentTask.BlendingTask(newExperiment, rawVolumeLabel);
        taskList.add(t);
        t.start();




        //BRIGADES
        iv=setImageViewProperties(new Image("/sample/image/brigade.png"),70,70,0,300);
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
        rawVolumeLabel.setText("0");
        rawVolumeLabel.setVisible(true);
        rawVolumeLabel.setTranslateX(240);
        rawVolumeLabel.setTranslateY(270);
        experimentPane.getChildren().add(rawVolumeLabel);
        ///


        Image image;
        //BLENDING
        if (newExperiment.getRawList()!=null){
            //GRAPHICS
            timeline=AnimationFunctions.doBlendingStageProgress(experimentPane,200,250, 100);
            timelineList.add(timeline);

            image=newExperiment.findImageByMaterialName("Blending");

            iv=setImageViewProperties(image,150,200,180,experimentPane.getHeight()/7);
            experimentPane.getChildren().add(iv);
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
        //iv=setImageViewProperties(image,60,60,experimentPane.getWidth()/2,0);
        //experimentPane.getChildren().add(iv);
        timeline=AnimationFunctions.doCuttingStageAnimation(experimentPane, 430,150);
        timelineList.add(timeline);
        //
        //newExperiment.doCutting();

        SuspendableTask t2=experimentTask.CuttingTask(newExperiment);
        taskList.add(t2);
        t2.start();

        //DRYING
        //ImageView iv2=setImageViewProperties(newExperiment.getStages().get(2),60,30,experimentPane.getWidth()/2+100,0);
        timeline=AnimationFunctions.doDryingAnimation(experimentPane,500,100);
        timelineList.add(timeline);
        //
        SuspendableTask t3=experimentTask.DryingTask(newExperiment);
        taskList.add(t3);
        t3.start();

        //BURNING
        //SOME ANIMATION
        //

        //TASK
        SuspendableTask t4=experimentTask.BurningTask(newExperiment);
        taskList.add(t4);
        t4.start();


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
            //pause();
            for (SuspendableTask t: taskList){
                if (!t.isSuspendFlag()) {
                    t.suspend();
                }
            }
        }
        else {
            for (Timeline timeline:timelineList) {
                timeline.play();
            }
            //experimentTask.resume();
            for (SuspendableTask t: taskList){
                //t.cancel(false);
                if (t.isSuspendFlag()) {
                    t.resume();
                }
                //Thread th=new Thread(t);
                //th.run();
            }

        }
    }


    private void showAlertDialog(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Не были выбраны материалы");
        alert.showAndWait();
    }
}
