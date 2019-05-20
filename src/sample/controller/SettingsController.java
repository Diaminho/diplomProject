package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import sample.Experiment;
import sample.stage.ChosenMaterialsStage;
import sample.stage.ScenarioStage;

import java.io.IOException;

public class SettingsController {
    public static Parent root;
    public static Experiment experiment = new Experiment();
    public static Stage primaryStage;


    public SettingsController(Stage primaryStage, Experiment experiment) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/sample/resource/fxml/settings.fxml"));
        SettingsController.primaryStage = primaryStage;
        primaryStage.setTitle("Настройки");
        primaryStage.setScene(new Scene(root));
        SettingsController.experiment = experiment;
        primaryStage.show();
    }

    public SettingsController() {
    }

    @FXML
    public void onCreateMaterialButton() {
        //primaryStage.close();
        try {
            new InputController(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onChooseMaterialButton() {
        ChosenMaterialsStage matStage = new ChosenMaterialsStage();
        try {
            experiment.setMaterialMap(matStage.showAndReturn(new MaterialsListController(matStage)));
            experiment.fillNeededMaterials();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onScenarioButton() {
        ScenarioStage sStage = new ScenarioStage();
        try {
            sStage.showAndReturn(new ScenarioController(sStage, experiment));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void showAlertDialog(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Не были выбраны материалы");
        alert.showAndWait();
    }
}
