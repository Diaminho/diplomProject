package sample.managers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Experiment;
import sample.animation.AnimationFunctions;
import sample.controllers.MainController;
import sample.resources.*;
import sample.sampling.SamplingControl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


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

    Thread animationThread;

    private Experiment newExperiment;

    private List<Material> materialsList;

    public ExperimentManager(Parent root) {
        ExperimentManager.root = root;
        animationThread=new Thread();
        init();
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
    public void onStartExperimentButton() throws IOException {
        //Material material=new Material("Цемент");
        //material.setMaterialImage(new Image("/sample/images/cement.jpg"));
        //GraphicsContext gc=canvasExperiment.getGraphicsContext2D();


        if (materialsList!=null){
            //GRAPHICS
            //gc.clearRect(0, 0, canvasExperiment.getWidth(), canvasExperiment.getHeight());
            int pos=0;
            for (Material i:materialsList) {
                AnimationFunctions.doAnimation(experimentPane,80,40+pos,150, Color.SANDYBROWN);
                ImageView iv=setImageViewProperties(i.getMaterialImage(),experimentPane.getWidth()/5,experimentPane.getHeight()/5,0,pos);
                experimentPane.getChildren().add(iv);
                //gc.strokeText(Double.toString(i.getVolume()),30,110+pos);
                pos+=120;
            }
        }



        newExperiment=new Experiment(materialsList);
        newExperiment.produceRawMaterial();
        double rawVolume=newExperiment.getRaw().getVolume();
        System.out.println("RAW VOLUME: "+rawVolume);


        //BLENDING
        if (newExperiment.getRaw()!=null){
            //GRAPHICS
            AnimationFunctions.doBlendingStageAnimation(animationThread,experimentPane,200,80,100,100);
            ImageView iv=setImageViewProperties(newExperiment.getStages().get(0),150,200,experimentPane.getWidth()/4,experimentPane.getHeight()/7);
            experimentPane.getChildren().add(iv);
            //GraphicsContext gc=canvasExperiment.getGraphicsContext2D();
            //gc.clearRect(0, 0, canvasExperiment.getWidth(), canvasExperiment.getHeight());
            //gc.drawImage(newExperiment.getRaw().getMaterialImage(), 200, 80, 100, 100);
            //gc.strokeText(Double.toString(newExperiment.getRaw().getVolume()),250,200);
        }


        //RAW TO CUT
        AnimationFunctions.doEndlessBrick(experimentPane,200,200,Color.FIREBRICK);


        //CUTTING
        ImageView iv=setImageViewProperties(newExperiment.getStages().get(1),60,60,experimentPane.getWidth()/2,0);
        //experimentPane.getChildren().add(iv);
        AnimationFunctions.doCuttingStageAnimation(experimentPane,iv,200);

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
    public void onChooseMaterialsButton(List materials) {
        materialsList=new ArrayList<>();
        System.out.println("Были выбраны следующие материалы для эксперимента:");
        for (Object o:materials){
            System.out.println(((Material)o).getName());
            materialsList.add((Material)o);
            //TEMP
            //materialsList.get(materialsList.size()-1).setVolume(11);
        }

    }

    @FXML
    public void onSamplingControlButton(){
        SamplingControl sc=new SamplingControl();
        //getting properties
        List<Double> qualityList=new ArrayList<>();
        for (Material mat:materialsList){
            mat.setAvgQuality();
            qualityList.add(mat.getAvgQuality());
            System.out.println("Качество материала: "+mat.getName()+" "+mat.getAvgQuality());
        }
        sc.check1StepSamplingControl(qualityList,0.8f);
    }


    @FXML
    synchronized public void onPauseButton(){
        AnimationFunctions.doPause(pauseButton, animationThread);
    }
}
