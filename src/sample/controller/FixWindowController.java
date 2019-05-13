package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Experiment;
import sample.manager.FixWindowManager;

import java.io.IOException;

public class FixWindowController {
    private static Parent root;

    private static FixWindowManager fixWindowManager;

    private static Stage primaryStage;

    public FixWindowController(Stage primaryStage, Experiment experiment) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/sample/resource/fxml/fixWindow.fxml"));
        FixWindowController.primaryStage = primaryStage;
        primaryStage.setTitle("Окно воздействия на технологический процесс");
        FixWindowController.fixWindowManager = new FixWindowManager(root, experiment);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public FixWindowController() {}

    @FXML
    public void onFixButton() {
        fixWindowManager.onFixButton();
    }
}
