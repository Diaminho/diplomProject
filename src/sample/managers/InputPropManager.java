package sample.managers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class InputPropManager {

    private static Parent root;

    @FXML
    private TextField propNameID;

    @FXML
    Stage primaryStage;


    public InputPropManager(Parent root) {
        InputPropManager.root = root;
        init();
    }

    private void init() {
        propNameID = (TextField) root.lookup("#propNameID");
    }


    public TextField getPropNameID() {
        return propNameID;
    }

    @FXML
    public void onAddPropButton() throws IOException {
        //primaryStage.close();
    }

    @FXML
    public void onBackButton() throws IOException {
        //primaryStage.close();
    }


}
