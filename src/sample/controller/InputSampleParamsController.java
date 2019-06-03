package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Experiment;
import sample.manager.InputSampleParamsManager;

import java.io.IOException;

public class InputSampleParamsController {

    private static Parent root;

    private static InputSampleParamsManager inputSampleParamsManager;

    private static Stage primaryStage;

    public static InputSampleParamsManager getInputSampleParamsManager() {
        return inputSampleParamsManager;
    }

    public InputSampleParamsController(Stage primaryStage, Experiment experiment) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/sample/resource/fxml/inputSampleParams.fxml"));
        InputSampleParamsController.primaryStage=primaryStage;
        primaryStage.setTitle("Генерация плана");
        inputSampleParamsManager = new InputSampleParamsManager(root, experiment);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public InputSampleParamsController() {}



    @FXML
    public void onBackButton(){
        inputSampleParamsManager.onBackButton();
        primaryStage.close();
        /*try {
            new ExperimentController(primaryStage);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @FXML
    public void onGenerateButton(){
        if (inputSampleParamsManager.onGenerateButton()==0) {
            //primaryStage.close();
            try {
                new SamplingChartController(new Stage(),
                        inputSampleParamsManager.getSample(),
                        inputSampleParamsManager.getExperiment().getDefaultMaterialsQuality(),
                        inputSampleParamsManager.getSamplingControl(),
                        inputSampleParamsManager.getChoiceStepId().getSelectionModel().getSelectedItem());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void onDoSamplingControlButton() {
        inputSampleParamsManager.onDoSamplingControlButton();
    }

    @FXML
    public void onSaveButton() {
        inputSampleParamsManager.onSaveButton();
    }

}
