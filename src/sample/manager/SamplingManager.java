package sample.manager;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

public class SamplingManager {
    private static Parent root;

    TextField sizeID;
    TextField medID;
    TextField dispID;
    TextField qualID;

    public SamplingManager(Parent root) {
        SamplingManager.root = root;
        init();
    }

    private void init() {
        sizeID= (TextField) root.lookup("#sizeID");
        dispID = (TextField) root.lookup("#dispID");
        medID = (TextField) root.lookup("#medID");
        qualID = (TextField) root.lookup("#qualID");
    }

    @FXML
    public void onGenerateButton(){
        /*float[] res=SampleGenerator.generateSample(Integer.parseInt(sizeID.getText()),Float.parseFloat(dispID.getText()),Float.parseFloat(medID.getText()));
        System.out.println("Сгенерирована следующая выборка");
        for (float item:res){
            System.out.print(item+" ");
        }
        System.out.println();

        SamplingControl sc=new SamplingControl(0.05f, 0.051f);
        sc.setC(4);
        System.out.println(sc.check1StepSamplingControl(res,Float.parseFloat(qualID.getText())));
        */
    }
}
