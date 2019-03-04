package sample.managers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

public class InputSampleParamsManager {

    private static Parent root;

    @FXML
    private TextField sampleSizeId;

    @FXML
    private TextField acId;

    @FXML
    private TextField sampleCountId;


    public InputSampleParamsManager(Parent root) {
        InputSampleParamsManager.root = root;
        init();
    }

    private void init() {
        sampleSizeId = (TextField) root.lookup("#sampleSizeId");
        acId =(TextField) root.lookup("#acId");
        sampleCountId = (TextField) root.lookup("#sampleCountId");
    }


    @FXML
    public void onBackButton(){

    }

    @FXML
    public void onGenerateButton(){

    }
}
