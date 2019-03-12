package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.manager.ExperimentManager;
import sample.stage.ChosenMaterialsStage;

import java.io.IOException;
import java.util.Map;

public class ExperimentController {

    private static Parent root;

    private static ExperimentManager experimentManager;

    private static Stage primaryStage;

    public ExperimentManager getExperimentManager() {
        return experimentManager;
    }

    public ExperimentController(Stage primaryStage) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/sample/resource/fxml/experiment.fxml"));
        ExperimentController.primaryStage=primaryStage;
        primaryStage.setTitle("Производство кирпичей");
        ExperimentController.experimentManager = new ExperimentManager(root);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public ExperimentController() {}

    @FXML
    public void onStartExperimentButton(ActionEvent event) throws IOException {
        experimentManager.onStartExperimentButton();
    }

    @FXML
    public void onGoMainButton(ActionEvent event) {
        primaryStage.close();
        experimentManager.onGoMainButton();
    }

    @FXML
    public void onChooseMaterialsButton() {
        ChosenMaterialsStage matStage=new ChosenMaterialsStage();
        try {
            Map chosenMaterials=matStage.showAndReturn(new MaterialsListController(matStage));
            experimentManager.onChooseMaterialsButton(chosenMaterials);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onOperateButton(){
        /*experimentManager.onOperateButton();
        try {
            SamplingChartController samplingChartController=new SamplingChartController(new Stage(), experimentManager.getMaterialQualityMap());
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

        //primaryStage.close();
        try {
            Map materialsMap=getExperimentManager().getMaterialQualityMap();
            new InputSampleParamsController(new Stage(), materialsMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onPauseButton(){
        experimentManager.onPauseButton();
    }
}
