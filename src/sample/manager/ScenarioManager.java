package sample.manager;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.util.Map;

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
    TextField brigadesCountId;
    ChoiceBox<Integer> brigadesChoiceBoxId;
    TextField brigadesQualityId;

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
        experiment = new Experiment();
        for (Material m: materialList) {
            experiment.getDefaultMaterialsQuality().put(m, 0.9);
        }

        settingsTabId = ((TabPane) root).getTabs().get(0);

        GridPane gridPaneMaterials = (GridPane) (((SplitPane) ((SplitPane)settingsTabId.getContent()).getItems().get(0)).getItems().get(0) );
        materialsQualityId = (TextField) gridPaneMaterials.lookup("#materialsQualityId");
        materialsChoiceBoxId = (ChoiceBox<String>)  gridPaneMaterials.lookup("#materialsChoiceBoxId");
        fillSettingsMaterials();

        GridPane gridPaneBrigades = (GridPane) (((SplitPane) ((SplitPane)settingsTabId.getContent()).getItems().get(0)).getItems().get(1));
        brigadesCountId = (TextField) gridPaneBrigades.lookup("#brigadesCountId");
        brigadesChoiceBoxId = (ChoiceBox<Integer>) gridPaneBrigades.lookup("#brigadesChoiceBoxId");
        brigadesQualityId = (TextField) gridPaneBrigades.lookup("#brigadesQualityId");
        fillSettingsBrigades();
    }

    private void fillSettingsMaterials() {
        materialsQualityId.setText("-");
        List<String> materialsNamesList = new ArrayList<>();
        for (Material m: materialList) {
            materialsNamesList.add(m.getName());
        }
        materialsChoiceBoxId.getItems().addAll(materialsNamesList);
        ChangeListener<String> changeListener = new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, //
                                String  oldValue, String newValue) {
                if (newValue != null) {
                    Material m = findMaterialByName(newValue);
                    materialsQualityId.setText(experiment.getDefaultMaterialsQuality().get(m).toString());
                }
            }
        };
        materialsChoiceBoxId.getSelectionModel().selectedItemProperty().addListener(changeListener);
    }

    private void fillSettingsBrigades() {
        brigadesQualityId.setText("-");
        brigadesCountId.setText(""+experiment.getBrigades().size());
        List<Double> brigadesList = experiment.getBrigades();
        for (int i = 0; i < experiment.getBrigades().size(); i++) {
            brigadesChoiceBoxId.getItems().add(i);
        }
        ChangeListener<Integer> changeListener = new ChangeListener<Integer>() {

            @Override
            public void changed(ObservableValue<? extends Integer> observable, //
                                Integer  oldValue, Integer newValue) {
                if (newValue != null) {
                    brigadesQualityId.setText(""+brigadesList.get(newValue));
                }
            }
        };
        brigadesChoiceBoxId.getSelectionModel().selectedItemProperty().addListener(changeListener);
    }

    public void onSaveButton(){
        List<Double> qualityList=experiment.getStageQuality();
        //qualityList.replaceAll(x -> Double.parseDouble(defaultDefectId.getText()));
    }

    public void onSetMaterialButton() {
        Map<Material, Double> defaultMap = experiment.getDefaultMaterialsQuality();
        String materialName = materialsChoiceBoxId.getSelectionModel().getSelectedItem();
        Material m = findMaterialByName(materialName);
        if (m != null) {
            defaultMap.replace(m, Double.parseDouble(materialsQualityId.getText()));
        }
    }

    private Material findMaterialByName(String name) {
        for (Material m: experiment.getDefaultMaterialsQuality().keySet()) {
            if (m.getName().compareTo(name)==0) {
                return m;
            }
        }
        return null;
    }

    public void onSetBrigadeButton() {
        experiment.getBrigades().set(brigadesChoiceBoxId.getSelectionModel().getSelectedIndex(), Double.parseDouble(brigadesQualityId.getText()));
    }

    public void onSetBrigadeCountButton() {

    }

    public void onCancelButton(){

    }

}
