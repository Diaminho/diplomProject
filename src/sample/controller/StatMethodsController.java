package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sample.Experiment;
import sample.resource.Brick;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StatMethodsController {

    public static Stage primaryStage;
    private static Experiment experiment;

    @FXML
    Button controlCharButton;
    @FXML
    Button histogramButton;
    @FXML
    Button paretoButton;
    @FXML
    Button samplingControlButton;
    //public static SamplingManager samplingManager;

    public StatMethodsController(Stage primaryStage, Experiment experiment) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/resource/fxml/statMethods.fxml"));
        //MainController.primaryStage = primaryStage;
        StatMethodsController.primaryStage = primaryStage;
        primaryStage.setTitle("Статистические методы контроля качества");
        init(root);

        if (experiment == null) {
            controlCharButton.setDisable(true);
            histogramButton.setDisable(true);
            paretoButton.setDisable(true);
            samplingControlButton.setDisable(true);
        }

        StatMethodsController.experiment = experiment;
        StatMethodsController.primaryStage.setScene(new Scene(root));
        StatMethodsController.primaryStage.show();
    }

    public StatMethodsController() { }

    private void init(Parent root) {
        controlCharButton = (Button) root.lookup("#controlCharButton");
        histogramButton = (Button) root.lookup("#histogramButton");
        paretoButton = (Button) root.lookup("#paretoButton");
        samplingControlButton = (Button) root.lookup("#samplingControlButton");
    }

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
                new InputSampleParamsController(new Stage(), experiment);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void onControlChartButton() {
        List<Boolean> qualityList = new ArrayList<>();
        for (Brick m : experiment.getLogisticBrickList()) {
            qualityList.add(m.getAvgQuality());
        }

        try {
            new ControlChartController(new Stage(), qualityList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void onHistogramButton() {
        try {
            new HistogramController(new Stage(), experiment);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onParetoButton() {
        try {
            new ParetoController(new Stage(), experiment);
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
