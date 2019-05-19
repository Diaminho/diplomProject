package sample.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Experiment;
import sample.manager.ParetoManager;

import java.io.IOException;

public class ParetoController {
    public static Stage primaryStage;
    public static ParetoManager paretoManager;

    public ParetoController(Stage primaryStage, Experiment experiment) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/resource/fxml/pareto.fxml"));
        //MainController.primaryStage = primaryStage;
        ParetoController.primaryStage=primaryStage;
        primaryStage.setTitle("Диаграмма Парето");
        ParetoController.paretoManager = new ParetoManager(root, experiment);
        ParetoController.primaryStage.setScene(new Scene(root));
        ParetoController.primaryStage.show();
    }

    public ParetoController() {
    }
}
