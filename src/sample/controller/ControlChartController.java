package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.manager.ControlChartManager;
import sample.manager.SamplingChartManager;
import sample.resource.Material;
import sample.sampling.SamplingControl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ControlChartController {
    public static Stage primaryStage;
    public static ControlChartManager controlChartManager;

    public ControlChartController(Stage primaryStage,
                                  List<Boolean> qualityList) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/resource/fxml/controlChart.fxml"));
        //MainController.primaryStage = primaryStage;
        ControlChartController.primaryStage=primaryStage;
        primaryStage.setTitle("Контрольная картя");
        ControlChartController.controlChartManager = new ControlChartManager(root, qualityList);
        ControlChartController.primaryStage.setScene(new Scene(root));
        ControlChartController.primaryStage.show();
    }

    public ControlChartController() {}



    @FXML
    public void onBackButton() {
        primaryStage.close();
        /*try {
            new MainController(primaryStage);
        } catch(Exception e) {
            e.printStackTrace();
        }*/
    }

    @FXML
    public void onBuildButton() {
        //controlChartManager.updateChart();
        controlChartManager.onBuildButton();
    }
}
