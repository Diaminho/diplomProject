package sample.managers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Experiment;
import sample.controllers.MainController;

import java.io.IOException;


public class InputManager {
    //Stage primaryStage;

    private static Parent root;

    @FXML
    private TextField textFieldPropValue;
    @FXML
    private TextField textFieldPropName;
    @FXML
    private TextField textFieldMaterialName;
    @FXML
    private TextField textFieldMaterialPropCount;

    @FXML
    Stage primaryStage;

    private Experiment NewExperiment;

    public InputManager(Parent root) {
        InputManager.root = root;
        init();
    }



    private void init() {
        textFieldPropValue= (TextField) root.lookup("#textFieldPropValue");
        textFieldPropName = (TextField) root.lookup("#textFieldPropName");
        textFieldMaterialName = (TextField) root.lookup("#textFieldMaterialName");
        textFieldMaterialPropCount=(TextField) root.lookup("#textFieldMaterialPropCount");
    }




    @FXML
    public void onSaveInputButton() throws IOException {

    }

    @FXML
    public void onGoToMenuButton() {
        try {
            new MainController(new Stage());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
