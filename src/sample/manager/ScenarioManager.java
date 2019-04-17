package sample.manager;

import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import sample.Experiment;

import java.util.List;

public class ScenarioManager {
    private static Parent root;

    TextField defaultDefectId;
    ChoiceBox<Integer> countBrigadesId;
    //
    private Experiment experiment;

    public Experiment getExperiment() {
        return experiment;
    }

    public void setExperiment(Experiment experiment) {
        this.experiment = experiment;
    }

    public ScenarioManager(Parent root) {
        ScenarioManager.root=root;
        init();
    }

    private void init(){
        defaultDefectId=(TextField) root.lookup("#defaultDefectId");
        countBrigadesId=(ChoiceBox<Integer>) root.lookup("#countBrigadesId");
    }

    public void onSaveButton(){
        experiment=new Experiment();
        List<Double> qualityList=experiment.getStageQuality();
        qualityList.replaceAll(x -> Double.parseDouble(defaultDefectId.getText()));
    }

    public void onCancelButton(){

    }

}
