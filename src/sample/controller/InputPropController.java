package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.manager.InputPropManager;

import java.io.IOException;

public class InputPropController {
    private static Parent root;

    private static InputPropManager inputPropManager;

    private static Stage primaryStage;


    public static InputPropManager getInputPropManager() {
        return inputPropManager;
    }

    public InputPropController(Stage primaryStage) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/sample/resource/fxml/inputPropForm.fxml"));
        InputPropController.primaryStage=primaryStage;
        primaryStage.setTitle("Окно ввода свойств материала");
        InputPropController.inputPropManager = new InputPropManager(root);
        primaryStage.setScene(new Scene(root));
        //primaryStage.show();
    }

    public InputPropController() {}

    @FXML
    public void onAddPropButton(ActionEvent event) throws IOException {
        primaryStage.close();
        inputPropManager.onAddPropButton();
    }

    @FXML
    public void onBackButton(ActionEvent event) throws IOException {
        primaryStage.close();
        inputPropManager.onBackButton();
    }


}
