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

import java.io.IOException;
import java.util.Map;

public class ExperimentController {

    private static Parent root;

    private static ExperimentManager experimentManager;

    private static Stage primaryStage;

    Map<Material, Integer> chosenMaterials;

    public ExperimentManager getExperimentManager() {
        return experimentManager;
    }

    private ControlChartController controlChartController;


    public ExperimentController(Stage primaryStage, Experiment experiment) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/sample/resource/fxml/experiment.fxml"));
        ExperimentController.primaryStage=primaryStage;
        primaryStage.setTitle("Производство кирпичей");
        ExperimentController.experimentManager = new ExperimentManager(root , experiment);
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
    public void onPauseButton(){
        experimentManager.onPauseButton();
    }

    @FXML
    public void onFixButton() {
        try {
            new FixWindowController(new Stage(), experimentManager.getNewExperiment());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onStatMethodsButton() {
        try {
            new StatMethodsController(new Stage(), experimentManager.getNewExperiment());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
