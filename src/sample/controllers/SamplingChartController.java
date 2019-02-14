package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.managers.SamplingChartManager;

import java.io.IOException;

public class SamplingChartController {
    public static Stage primaryStage;
    public static SamplingChartManager samplingChartManager;

    public SamplingChartController(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/resources/fxml/samplingChart.fxml"));
        //MainController.primaryStage = primaryStage;
        SamplingChartController.primaryStage=primaryStage;
        primaryStage.setTitle("Выборочный контроль");
        SamplingChartController.samplingChartManager = new SamplingChartManager(root);
        SamplingChartController.primaryStage.setScene(new Scene(root));
        SamplingChartController.primaryStage.show();
    }

    public SamplingChartController() {}



    @FXML
    public void onBackButton() {
        primaryStage.close();
        try {
            new MainController(primaryStage);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
