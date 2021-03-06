package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.manager.InputManager;

import java.io.IOException;

public class InputController {

    private static Parent root;

    private static InputManager inputManager;

    private static Stage primaryStage;

    public InputController(Stage primaryStage) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/sample/resource/fxml/input.fxml"));
        InputController.primaryStage=primaryStage;
        primaryStage.setTitle("Добавление материалов");
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


}
