package sample.managers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Experiment;
import sample.controllers.MainController;
import sample.resources.*;

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
    private Canvas canvasExperiment;
    @FXML
    private Text rawQualityID;
    @FXML
    private Text firstStageText;

    @FXML
    Stage primaryStage;

    private Experiment NewExperiment;

    private List<Material> materialsList;

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

        canvasExperiment=(Canvas) root.lookup("#canvasExperiment");
    }




    @FXML
    public void onStartExperimentButton() throws IOException {
        //Material material=new Material("Цемент");
        //material.setMaterialImage(new Image("/sample/images/cement.jpg"));

        if (materialsList!=null){
            //GRAPHICS
            GraphicsContext gc=canvasExperiment.getGraphicsContext2D();
            gc.clearRect(0, 0, canvasExperiment.getWidth(), canvasExperiment.getHeight());
            gc.drawImage(materialsList.get(0).getMaterialImage(), 10, 10, 100, 100);
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

    @FXML
    public void onChooseMaterialsButton(List materials) {
        materialsList=new ArrayList<>();
        System.out.println("Были выбраны следующие материалы для эксперимента:");
        for (Object o:materials){
            System.out.println(((Material)o).getName());
            materialsList.add((Material)o);
        }

    }



}
