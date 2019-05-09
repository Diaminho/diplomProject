package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Experiment;
import sample.manager.ExperimentManager;
import sample.resource.Material;
import sample.stage.ChosenMaterialsStage;
import sample.stage.ScenarioStage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExperimentController {

    private static Parent root;

    private static ExperimentManager experimentManager;

    private static Stage primaryStage;

    public ExperimentManager getExperimentManager() {
        return experimentManager;
    }

    private ControlChartController controlChartController;


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
            Map<Material, Integer> chosenMaterials=matStage.showAndReturn(new MaterialsListController(matStage));
            experimentManager.onChooseMaterialsButton(chosenMaterials);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onOperateButton(){
        if (experimentManager.onOperateButton()==0) {
            try {
                Map materialsMap = getExperimentManager().getMaterialQualityMap();
                new InputSampleParamsController(new Stage(), materialsMap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void onPauseButton(){
        experimentManager.onPauseButton();
    }

    @FXML
    public void onScenarioButton(){
        ScenarioStage sStage=new ScenarioStage();
        try {
            Experiment experiment =sStage.showAndReturn(new ScenarioController(sStage, new ArrayList<Material>(experimentManager.getMaterialQualityMap().keySet())));
            experimentManager.setNewExperiment(experiment);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
        try {
            new ScenarioController();
        } catch(Exception e) {
            e.printStackTrace();
        }*/
    }


    @FXML
    public void onControlChartButton() {
        List<Boolean> qualityList = new ArrayList<>();
        synchronized (experimentManager.getNewExperiment()) {
            for (Material m : experimentManager.getNewExperiment().getRawList()) {
                qualityList.add(m.getAvgQuality());
            }
        }
        try {
            controlChartController = new ControlChartController(new Stage(), qualityList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
