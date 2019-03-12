package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.manager.InputSampleParamsManager;

import java.io.IOException;
import java.util.Map;

public class InputSampleParamsController {

    private static Parent root;

    private static InputSampleParamsManager inputSampleParamsManager;

    private static Stage primaryStage;

    public static InputSampleParamsManager getInputSampleParamsManager() {
        return inputSampleParamsManager;
    }

    public InputSampleParamsController(Stage primaryStage, Map materials) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/sample/resource/fxml/inputSampleParams.fxml"));
        InputSampleParamsController.primaryStage=primaryStage;
        primaryStage.setTitle("Окно ввода параметров для генерации выборки");
        inputSampleParamsManager = new InputSampleParamsManager(root, materials);
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
        inputSampleParamsManager.onGenerateButton();
        primaryStage.close();
        try{
            new SamplingChartController(primaryStage,inputSampleParamsManager.getSample(),inputSampleParamsManager.getMaterials());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
