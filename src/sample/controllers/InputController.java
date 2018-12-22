package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.stages.PropertyStage;
import sample.managers.InputManager;

import java.io.IOException;

public class InputController {

    private static Parent root;

    private static InputManager inputManager;

    private static Stage primaryStage;

    public InputController(Stage primaryStage) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/sample/resources/fxml/input.fxml"));
        InputController.primaryStage=primaryStage;
        primaryStage.setTitle("Ввод начальных данных");
        InputController.inputManager = new InputManager(root);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public InputController() {}

    @FXML
    public void onSaveInputButton(ActionEvent event) throws IOException {
        inputManager.onSaveInputButton();
    }

    @FXML
    public void onGoToMenuButton(ActionEvent event) {
        primaryStage.close();
        inputManager.onGoToMenuButton();
    }

    @FXML
    public void onAddMaterialButton(ActionEvent event) throws IOException {
        inputManager.onAddMaterialButton();
    }

    @FXML
    public void onAddPropertyButton(ActionEvent event) throws IOException {
        PropertyStage propertyStage =new PropertyStage();
        String propName= propertyStage.showAndReturn(new InputPropController(propertyStage));
        System.out.println(propName);
        if(propName.compareTo("")!=0 && propName.charAt(0)!=' '){
            inputManager.onAddPropertyButton(propName);
        }
    }

}
