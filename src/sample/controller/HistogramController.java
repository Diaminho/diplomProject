package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Experiment;
import sample.manager.HistogramManager;

import java.io.IOException;

public class HistogramController {
    private static Parent root;
    private static Stage primaryStage;
    private static HistogramManager histogramManager;

    public HistogramController(Stage primaryStage, Experiment experiment) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/sample/resource/fxml/histogram.fxml"));
        HistogramController.primaryStage = primaryStage;
        primaryStage.setTitle("Гистограмма");
        HistogramController.histogramManager = new HistogramManager(root, experiment);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public HistogramController() {
    }

    @FXML
    public void onAppyFilterButton() {
        histogramManager.onAppyFilterButton();
    }

    @FXML
    public void onBuildButton() {
        histogramManager.onBuildButton();
    }
}
