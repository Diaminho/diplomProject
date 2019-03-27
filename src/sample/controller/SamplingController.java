package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.manager.SamplingManager;

import java.io.IOException;

public class SamplingController {
    public static Stage primaryStage;
    public static SamplingManager samplingManager;

    public SamplingController(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/resource/fxml/sampling.fxml"));
        //MainController.primaryStage = primaryStage;
        SamplingController.primaryStage=primaryStage;
        primaryStage.setTitle("Выборочный контроль");
        SamplingController.samplingManager = new SamplingManager(root);
        this.primaryStage.setScene(new Scene(root));
        this.primaryStage.show();
    }

    public SamplingController() {}

    @FXML
    public void onGenerateButton(){
        samplingManager.onGenerateButton();
    }

    @FXML
    public void onMenuButton(ActionEvent event) {
        primaryStage.close();
        try {
            new MainController(primaryStage);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
