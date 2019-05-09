package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import sample.Experiment;
import sample.manager.ScenarioManager;
import sample.resource.Material;

import java.io.IOException;
import java.util.List;

public class ScenarioController {
    public static Stage primaryStage;
    public static ScenarioManager scenarioManager;
    private static Parent root;


    public ScenarioController() {}



    public ScenarioController(Stage primaryStage, List<Material> materialsList) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/sample/resource/fxml/scenario.fxml"));
        ScenarioController.primaryStage = primaryStage;
        primaryStage.setTitle("Редактор сценария");
        ScenarioController.scenarioManager = new ScenarioManager(root, materialsList);
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
}
