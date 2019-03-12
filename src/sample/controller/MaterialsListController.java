package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.manager.MaterialsListManager;

import java.io.IOException;

public class MaterialsListController {
    private static Stage primaryStage;

    private  MaterialsListManager materialsListManager;

    public  MaterialsListManager getMaterialsListManager() {
        return materialsListManager;
    }

    public MaterialsListController(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/resource/fxml/materialsList.fxml"));
        //MainController.primaryStage = primaryStage;
        this.primaryStage=primaryStage;
        primaryStage.setTitle("Список материалов");
        this.materialsListManager = new MaterialsListManager(root);
        this.primaryStage.setScene(new Scene(root));
        //this.primaryStage.show();
    }

    public MaterialsListController() {}

    @FXML
    public void onCloseButton(){
        primaryStage.close();
        //materialsListManager.onCloseButton();
    }


}
