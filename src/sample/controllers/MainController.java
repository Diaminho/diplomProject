package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static java.lang.Thread.sleep;
//import javafx.scene.text.Text;

public class MainController {
    public static Stage primaryStage;

    public MainController(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/resources/fxml/menu.fxml"));
        //MainController.primaryStage = primaryStage;
        this.primaryStage.setTitle("Главное меню");
        this.primaryStage.setScene(new Scene(root));
        this.primaryStage.show();
    }

    public MainController() {}

    @FXML
    public void inputButtonAction(){
        primaryStage.close();
        try {
            new InputController(primaryStage);
        } catch(Exception e) {
            e.printStackTrace();
        }


    }

    @FXML
    public void start_buttonAction(ActionEvent event) {
        primaryStage.close();
        try {
            new ExperimentController(primaryStage);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onSamplingButton(ActionEvent event) {
        primaryStage.close();
        try {
            new SamplingController(primaryStage);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
