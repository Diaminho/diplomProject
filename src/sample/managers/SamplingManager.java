package sample.managers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import sample.sampling.SampleGenerator;

public class SamplingManager {
    private static Parent root;

    TextField sizeID;
    TextField medID;
    TextField dispID;

    public SamplingManager(Parent root) {
        SamplingManager.root = root;
        init();
    }

    private void init() {
        sizeID= (TextField) root.lookup("#sizeID");
        dispID = (TextField) root.lookup("#dispID");
        medID = (TextField) root.lookup("#medID");
    }

    @FXML
    public void onGenerateButton(){
        float[] res=SampleGenerator.generateSample(Integer.parseInt(sizeID.getText()),Float.parseFloat(dispID.getText()),Float.parseFloat(medID.getText()));
        System.out.println("Сгенерирована следующая выборка");
        for (float item:res){
            System.out.print(item+" ");
        }
    }
}
