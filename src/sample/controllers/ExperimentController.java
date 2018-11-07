package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.managers.ExperimentManager;

import java.io.IOException;

public class ExperimentController {

    private static Parent root;

    private static ExperimentManager experimentManager;

    private static Stage primaryStage;

    public ExperimentController(Stage primaryStage) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/sample/resources/fxml/experiment.fxml"));
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
}
