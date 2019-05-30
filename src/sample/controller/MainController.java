package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Experiment;

import java.io.IOException;
//import javafx.scene.text.Text;

public class MainController {
    public static Stage primaryStage;
    private static Experiment experiment = new Experiment();

    public MainController(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/resource/fxml/menu.fxml"));
        //MainController.primaryStage = primaryStage;
        this.primaryStage.setTitle("Главное меню");
        this.primaryStage.setScene(new Scene(root));
        this.primaryStage.show();



    }

    public MainController() {}

    @FXML
    public void onHelpButton(){
        //primaryStage.close();
        try {
            new HelpController(primaryStage);
        } catch(Exception e) {
            e.printStackTrace();
        }


    }

    @FXML
    public void onTechnologicalProcessButton() {
        primaryStage.close();
        try {
            new ExperimentController(primaryStage, experiment);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onSettingsButton() {
        //primaryStage.close();
        try {
            new SettingsController(new Stage(), experiment);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
