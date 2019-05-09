package sample.manager;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import sample.Experiment;
import sample.resource.Material;

import javax.xml.soap.Text;
import java.util.ArrayList;
import java.util.List;

import static java.rmi.Naming.lookup;

public class ScenarioManager {
    private static Parent root;
    private List<Material> materialList;

    //Tab Настройки
    //Materials
    Tab settingsTabId;
    TextField materialsQualityId;
    ChoiceBox<String> materialsChoiceBoxId;
    //Brigades

    private Experiment experiment;


    public Experiment getExperiment() {
        return experiment;
    }

    public void setExperiment(Experiment experiment) {
        this.experiment = experiment;
    }

    public ScenarioManager(Parent root, List<Material> materialList) {
        ScenarioManager.root=root;
        this.materialList = new ArrayList<>(materialList);
        init();
    }

    private void init(){
        settingsTabId = ((TabPane) root).getTabs().get(0);

        materialsQualityId = (TextField) (((SplitPane) ((SplitPane)settingsTabId.getContent()).getItems().get(0)).getItems().get(0) ).lookup("#materialsQualityId");
        materialsChoiceBoxId = (ChoiceBox<String>)  (((SplitPane)((SplitPane)settingsTabId.getContent()).getItems().get(0)).getItems().get(0)).lookup("#materialsChoiceBoxId");

        materialsQualityId.setText("11");
        fillSettingsMaterials();
    }

    private void fillSettingsMaterials() {
        List<String> materialsNamesList = new ArrayList<>();
        for (Material m: materialList) {
            materialsNamesList.add(m.getName());
        }
        materialsChoiceBoxId.getItems().addAll(materialsNamesList);

        //materialsChoiceBoxId.getSelectionModel().getSelectedItem();
    }

    public void onSaveButton(){
        //experiment = new Experiment();
        List<Double> qualityList=experiment.getStageQuality();
        //qualityList.replaceAll(x -> Double.parseDouble(defaultDefectId.getText()));
    }

    public void onCancelButton(){

    }

}
