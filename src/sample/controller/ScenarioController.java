package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.manager.ScenarioManager;

import java.io.IOException;

public class ScenarioController {
    public static Stage primaryStage;
    public static ScenarioManager scenarioManager;
    private static Parent root;

    public ScenarioController() {}

    public ScenarioController(Stage primaryStage) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/sample/resource/fxml/scenario.fxml"));
        ScenarioController.primaryStage=primaryStage;
        primaryStage.setTitle("Редактор сценария");
        ScenarioController.scenarioManager = new ScenarioManager(root);
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

    public static ScenarioManager getScenarioManager() {
        return scenarioManager;
    }
}
