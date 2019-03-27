package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.manager.SamplingChartManager;
import sample.resource.Material;
import sample.sampling.SamplingControl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SamplingChartController {
    public static Stage primaryStage;
    public static SamplingChartManager samplingChartManager;

    public SamplingChartController(Stage primaryStage,
                                   List<Double> listOfGeneratedSample,
                                   Map<Material, List<Double>> materialsMap,
                                   SamplingControl samplingControl) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/resource/fxml/samplingChart.fxml"));
        //MainController.primaryStage = primaryStage;
        SamplingChartController.primaryStage=primaryStage;
        primaryStage.setTitle("Выборочный контроль");
        SamplingChartController.samplingChartManager = new SamplingChartManager(root,
                listOfGeneratedSample,
                materialsMap,
                samplingControl);
        SamplingChartController.primaryStage.setScene(new Scene(root));
        SamplingChartController.primaryStage.show();
    }

    public SamplingChartController() {}



    @FXML
    public void onBackButton() {
        primaryStage.close();
        /*try {
            new MainController(primaryStage);
        } catch(Exception e) {
            e.printStackTrace();
        }*/
    }
}
