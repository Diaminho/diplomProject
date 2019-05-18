package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import sample.Experiment;
import sample.resource.Material;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StatMethodsController {

    public static Stage primaryStage;
    private static Experiment experiment;
    //public static SamplingManager samplingManager;

    public StatMethodsController(Stage primaryStage, Experiment experiment) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/resource/fxml/statMethods.fxml"));
        //MainController.primaryStage = primaryStage;
        StatMethodsController.primaryStage = primaryStage;
        primaryStage.setTitle("Статистические методы контроля качества");
        StatMethodsController.experiment = experiment;
        StatMethodsController.primaryStage.setScene(new Scene(root));
        StatMethodsController.primaryStage.show();
    }

    public StatMethodsController() { }

    @FXML
    public void onIshikawaButton() {
        try {
            new IshikawaController(new Stage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onOperateButton() {
        if (onOperateButtonCan()==0) {
            try {
                Map materialsMap = experiment.getDefaultMaterialsQuality();
                new InputSampleParamsController(new Stage(), materialsMap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void onControlChartButton() {
        List<Boolean> qualityList = new ArrayList<>();
        for (Material m : experiment.getRawList()) {
            qualityList.add(m.getAvgQuality());
        }

        try {
            new ControlChartController(new Stage(), qualityList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Integer onOperateButtonCan(){
        if (experiment.getMaterialMap()==null) {
            showAlertDialog();
            return -1;
        }
        else {
            return 0;
        }
    }


    private void showAlertDialog(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Не были выбраны материалы");
        alert.showAndWait();
    }
}
