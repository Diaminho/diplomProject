package sample.managers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Experiment;
import sample.controllers.MainController;
import sample.resources.Material;

import java.io.IOException;
import java.util.List;


public class InputManager {
    //Stage primaryStage;
    List<Material> materialsList;


    private static Parent root;


    @FXML
    private TextField textFieldPropName;
    @FXML
    private TextField textFieldMaterialName;


    @FXML
    Stage primaryStage;

    private Experiment NewExperiment;

    public InputManager(Parent root) {
        InputManager.root = root;
        init();
    }



    private void init() {
        textFieldPropName = (TextField) root.lookup("#textFieldPropName");
        textFieldMaterialName = (TextField) root.lookup("#textFieldMaterialName");
    }




    @FXML
    public void onSaveInputButton() throws IOException {

    }


    @FXML
    public void onAddMaterialButton() throws IOException {

        materialsList.add(new Material(textFieldMaterialName.getText()));
        //materialsList.get(material)
    }


    @FXML
    public void onAddPropButton() throws IOException {

    }


    //////////
    public int searchMaterial()






    @FXML
    public void onGoToMenuButton() {
        try {
            new MainController(new Stage());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
