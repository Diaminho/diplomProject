package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Experiment;
import sample.manager.ScenarioManager;

import java.io.IOException;

public class ScenarioController {
    public static Stage primaryStage;
    public static ScenarioManager scenarioManager;
    private static Parent root;


    public ScenarioController() {}



    public ScenarioController(Stage primaryStage, Experiment experiment) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/sample/resource/fxml/scenario.fxml"));
        ScenarioController.primaryStage = primaryStage;
        primaryStage.setTitle("Редактор сценария");
        ScenarioController.scenarioManager = new ScenarioManager(root, experiment);
        primaryStage.setScene(new Scene(root));
        //primaryStage.show();
    }

    @FXML
    public void onSaveButton(){
        scenarioManager.onSaveButton();
        primaryStage.close();
    }

    @FXML
    public void onCancelButton(){
        scenarioManager.onCancelButton();
        primaryStage.close();
    }

    @FXML
    public void onSetMaterialButton() {
        scenarioManager.onSetMaterialButton();
    }

    @FXML
    public void onSetBrigadeButton() {
        scenarioManager.onSetBrigadeButton();
    }

    @FXML
    public void onSetBrigadeCountButton() {
        scenarioManager.onSetBrigadeCountButton();
    }

    public static ScenarioManager getScenarioManager() {
        return scenarioManager;
    }


    //Tab Stages
    @FXML
    public void onOkStageBlendingButton() {
        scenarioManager.onOkStageBlendingButton();
    }

    @FXML
    public void onSaveBlendingButton() {
        scenarioManager.onSaveBlendingButton();
    }

    @FXML
    public void onSaveCuttingButton() {
        scenarioManager.onSaveCuttingButton();
    }

    @FXML
    public void onSaveDryingButton() {
        scenarioManager.onSaveDryingButton();
    }

    @FXML
    public void onSaveBurningButton() {
        scenarioManager.onSaveBurningButton();
    }

    @FXML
    public void onSaveLogisticButton() {
        scenarioManager.onSaveLogisticButton();
    }

    //Tab Scenario
    @FXML
    public void onScenarioStageSaveButton() {
        scenarioManager.onScenarioStageSaveButton();
    }

    @FXML
    public void onScenarioBrigadeSaveButton() {
        scenarioManager.onScenarioBrigadeSaveButton();
    }

    @FXML
    public void onConfigureFixButton() { scenarioManager.onConfigureFixButton();}
}
