package sample.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.manager.IshikawaManager;

import java.io.IOException;

public class IshikawaController {

    private static Parent root;
    private static IshikawaManager ishikawaManager;
    private static Stage primaryStage;


    public IshikawaController(Stage primaryStage) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/sample/resource/fxml/ishikawa.fxml"));
        IshikawaController.primaryStage = primaryStage;
        primaryStage.setTitle("Диграмма Ишикавы");
        IshikawaController.ishikawaManager = new IshikawaManager(root);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public IshikawaController() {}
}
