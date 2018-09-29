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
        Parent root = FXMLLoader.load(getClass().getResource("../resources/fxml/sample.fxml"));
        //MainController.primaryStage = primaryStage;
        primaryStage.setTitle("Главное меню");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public MainController() {}

    @FXML
    public void inputButtonAction(){
        primaryStage.close();
        try {
            new InputController(new Stage());
        } catch(Exception e) {
            e.printStackTrace();
        }


    }

    @FXML
    public void start_buttonAction(ActionEvent event) {
        primaryStage.close();
        try {
            new ExperimentController(new Stage());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
