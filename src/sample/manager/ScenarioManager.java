package sample.manager;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import sample.Experiment;
import sample.resource.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScenarioManager {
    private static Parent root;
    private Map<Material, Integer> materialQuantityMap;

    //Tab Настройки
    //Materials
    Tab settingsTabId;
    TextField materialsQualityId;
    ChoiceBox<String> materialsChoiceBoxId;
    //Brigades
    TextField brigadesCountId;
    ChoiceBox<Integer> brigadesChoiceBoxId;
    TextField brigadesQualityId;

    //Tab Этапы
    //Смешивание
    Tab stagesTabId;
    TextField blendingQuantityId;
    ChoiceBox<Integer> blendingChoiceBoxId;
    TextField blendingQualityId;
    //Формовка
    TextField cuttingQualityId;
    //Сушка
    TextField dryingQualityId;
    //Обжиг
    TextField burningQualityId;

    //Tab Сценарий
    Tab scenarioTabId;
    //Бригады
    ChoiceBox<Integer> scenarioBrigadeChoiceBoxId;
    Slider scenarioBrigadeSliderId;
    TextField scenarioBrigadeCountTurnsId;
    //Этапы
    ChoiceBox<String> scenarioStageChoiceBoxId;
    Slider scenarioStageSliderId;
    TextField scenarioStageTextFieldId;

    private Experiment experiment;


    private int maxCountProcessIterations;

    public Experiment getExperiment() {
        return experiment;
    }

    public void setExperiment(Experiment experiment) {
        this.experiment = experiment;
    }

    public ScenarioManager(Parent root, Map<Material, Integer> materialQuantityMap) {
        ScenarioManager.root = root;
        this.materialQuantityMap = new HashMap<>(materialQuantityMap);
        init();
    }

    private void init(){
        //TODO ADD LOGS IN TEXTAREA and add scenario influence in Experiment
        experiment = new Experiment(materialQuantityMap);
        for (Material m: materialQuantityMap.keySet()) {
            experiment.getDefaultMaterialsQuality().put(m, 0.9);
        }

        maxCountProcessIterations = -1;
        Material neededMaterial;
        for (Material i: experiment.getMaterialMap().keySet()) {
            if (experiment.getMaterialMap().get(i) > -1) {
                neededMaterial = experiment.findMaterialByName(i.getName(), experiment.getNeededMaterials().keySet());
                maxCountProcessIterations = experiment.getMaterialMap().get(i) / experiment.getNeededMaterials().get(neededMaterial);
            }
        }

        TabPane tabPane = (TabPane) ((SplitPane) root).getItems().get(0);

        settingsTabId = tabPane.getTabs().get(0);
        stagesTabId = tabPane.getTabs().get(1);
        scenarioTabId = tabPane.getTabs().get(2);

        //Настройки
        GridPane gridPaneMaterials = (GridPane) (((SplitPane)settingsTabId.getContent()).getItems().get(0));
        materialsQualityId = (TextField) gridPaneMaterials.lookup("#materialsQualityId");
        materialsChoiceBoxId = (ChoiceBox<String>)  gridPaneMaterials.lookup("#materialsChoiceBoxId");
        fillSettingsMaterials();

        GridPane gridPaneBrigades = (GridPane) (((SplitPane)settingsTabId.getContent()).getItems().get(1));
        brigadesCountId = (TextField) gridPaneBrigades.lookup("#brigadesCountId");
        brigadesChoiceBoxId = (ChoiceBox<Integer>) gridPaneBrigades.lookup("#brigadesChoiceBoxId");
        brigadesQualityId = (TextField) gridPaneBrigades.lookup("#brigadesQualityId");
        fillSettingsBrigades();

        //Этапы
        GridPane gridPaneBlending = (GridPane) (((SplitPane)stagesTabId.getContent()).getItems().get(0));
        blendingQuantityId = (TextField) gridPaneBlending.lookup("#blendingQuantityId");
        blendingChoiceBoxId = (ChoiceBox<Integer>) gridPaneBlending.lookup("#blendingChoiceBoxId");
        blendingQualityId = (TextField) gridPaneBlending.lookup("#blendingQualityId");
        fillBlending();

        GridPane gridPaneCutting = (GridPane) (((SplitPane)stagesTabId.getContent()).getItems().get(1));
        cuttingQualityId = (TextField) gridPaneCutting.lookup("#cuttingQualityId");
        fillCutting();

        GridPane gridPaneDrying = (GridPane) (((SplitPane)stagesTabId.getContent()).getItems().get(2));
        dryingQualityId = (TextField) gridPaneDrying.lookup("#dryingQualityId");
        fillDrying();

        GridPane gridPaneBurning = (GridPane) (((SplitPane)stagesTabId.getContent()).getItems().get(3));
        burningQualityId = (TextField) gridPaneBurning.lookup("#burningQualityId");
        fillBurning();

        //Сценарий
        GridPane gridPaneScenarioBrigade = (GridPane) (((SplitPane)scenarioTabId.getContent()).getItems().get(0));
        scenarioBrigadeChoiceBoxId = (ChoiceBox<Integer>) gridPaneScenarioBrigade.lookup("#scenarioBrigadeChoiceBoxId");
        scenarioBrigadeSliderId = (Slider) gridPaneScenarioBrigade.lookup("#scenarioBrigadeSliderId");
        scenarioBrigadeCountTurnsId = (TextField) gridPaneScenarioBrigade.lookup("#scenarioBrigadeCountTurnsId");
        fillScenarioBrigades();

        GridPane gridPaneScenarioStage = (GridPane) (((SplitPane)scenarioTabId.getContent()).getItems().get(1));
        scenarioStageChoiceBoxId = (ChoiceBox<String>) gridPaneScenarioStage.lookup("#scenarioStageChoiceBoxId");
        scenarioStageSliderId = (Slider) gridPaneScenarioStage.lookup("#scenarioStageSliderId");
        scenarioStageTextFieldId = (TextField) gridPaneScenarioStage.lookup("#scenarioStageTextFieldId");
        fillScenarioStages();
    }

    private void fillSettingsMaterials() {
        materialsQualityId.setText("-");
        List<String> materialsNamesList = new ArrayList<>();
        for (Material m: materialQuantityMap.keySet()) {
            materialsNamesList.add(m.getName());
        }
        materialsChoiceBoxId.getItems().addAll(materialsNamesList);
        ChangeListener<String> changeListener = new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable,
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

    //Stages
    private void fillBlending() {
        blendingQualityId.setText("-");
        //TODO add parallel blending
        blendingQuantityId.setText("1");
        List<Double> blendingList = new ArrayList<>();
        blendingList.add(experiment.getStageQuality().get(0));
        //for (int i = 0; i < ; i++) {
            blendingChoiceBoxId.getItems().add(1);
        //}
        ChangeListener<Integer> changeListener = new ChangeListener<Integer>() {

            @Override
            public void changed(ObservableValue<? extends Integer> observable, //
                                Integer  oldValue, Integer newValue) {
                if (newValue != null) {
                    blendingQualityId.setText(""+blendingList.get(newValue-1));
                }
            }
        };
        blendingChoiceBoxId.getSelectionModel().selectedItemProperty().addListener(changeListener);
    }

    private void fillCutting() {
        cuttingQualityId.setText("" + experiment.getStageQuality().get(1));
    }

    private void fillDrying() {
        dryingQualityId.setText("" + experiment.getStageQuality().get(2));
    }

    private void fillBurning() {
        burningQualityId.setText("" + experiment.getStageQuality().get(3));
    }

    private void fillScenarioStages() {
        scenarioStageTextFieldId.setText("-");
        scenarioStageTextFieldId.textProperty().addListener((observable, oldValue, newValue) -> {
            if (Integer.parseInt(newValue) > maxCountProcessIterations) {
                newValue = "" + maxCountProcessIterations;
            }
            scenarioStageSliderId.setValue(Double.parseDouble(newValue));
        });


        List<String> stagesNameList = new ArrayList<>();
        stagesNameList.add("Смешивание");
        stagesNameList.add("Формовка");
        stagesNameList.add("Сушка");
        stagesNameList.add("Обжиг");

        scenarioStageChoiceBoxId.getItems().addAll(stagesNameList);
        //ChoiceBox
        ChangeListener<String> changeListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, //
                                String  oldValue, String newValue) {
                if (newValue != null) {
                    scenarioStageTextFieldId.setText("" + experiment.getScenarioStagesList().get(stagesNameList.indexOf(newValue)));
                }
            }
        };
        scenarioStageChoiceBoxId.getSelectionModel().selectedItemProperty().addListener(changeListener);

        scenarioStageSliderId.setMax(maxCountProcessIterations);
        scenarioStageSliderId.setMin(-1);
        scenarioStageSliderId.setShowTickLabels(true);
        scenarioStageSliderId.setShowTickMarks(true);
        scenarioStageSliderId.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, //
                                Number oldValue, Number newValue) {
                scenarioStageTextFieldId.setText("" + newValue.intValue());
            }
        });

    }

    private void fillScenarioBrigades() {
        scenarioBrigadeCountTurnsId.setText("-");
        scenarioBrigadeCountTurnsId.textProperty().addListener((observable, oldValue, newValue) -> {
            if (Integer.parseInt(newValue) > maxCountProcessIterations) {
                newValue = "" + maxCountProcessIterations;
            }
            scenarioBrigadeSliderId.setValue(Integer.parseInt(newValue));
        });

        List<Integer> brigadesList = new ArrayList<>();
        for (int i = 0; i < experiment.getBrigades().size(); i++) {
            brigadesList.add(i + 1);
        }

        scenarioBrigadeChoiceBoxId.getItems().addAll(brigadesList);
        //ChoiceBox
        ChangeListener<Integer> changeListener = new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, //
                                Integer  oldValue, Integer newValue) {
                if (newValue != null) {
                    scenarioBrigadeCountTurnsId.setText("" + experiment.getScenarioBrigadesList().get(newValue - 1));
                }
            }
        };
        scenarioBrigadeChoiceBoxId.getSelectionModel().selectedItemProperty().addListener(changeListener);

        scenarioBrigadeSliderId.setMax(maxCountProcessIterations);
        scenarioBrigadeSliderId.setMin(-1);
        scenarioBrigadeSliderId.setShowTickLabels(true);
        scenarioBrigadeSliderId.setShowTickMarks(true);
        scenarioBrigadeSliderId.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, //
                                Number oldValue, Number newValue) {
                scenarioBrigadeCountTurnsId.setText("" + newValue.intValue());
            }
        });
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
        experiment.getStageQuality().set(1, Double.parseDouble(cuttingQualityId.getText()));
    }

    public void onCancelButton(){

    }

    //Stages
    public void onSaveBlendingButton() {
        experiment.getStageQuality().set(0, Double.parseDouble(blendingQualityId.getText()));
    }

    public void onSaveCuttingButton() {
        experiment.getStageQuality().set(1, Double.parseDouble(cuttingQualityId.getText()));
    }

    public void onSaveDryingButton() {
        experiment.getStageQuality().set(2, Double.parseDouble(dryingQualityId.getText()));
    }

    public void onSaveBurningButton() {
        experiment.getStageQuality().set(3, Double.parseDouble(burningQualityId.getText()));
    }

    //Scenario
    public void onScenarioStageSaveButton() {
        experiment.getScenarioStagesList().set(scenarioStageChoiceBoxId.getSelectionModel().getSelectedIndex(), Integer.parseInt(scenarioStageTextFieldId.getText()));
    }

    public void onScenarioBrigadeSaveButton() {
        experiment.getScenarioBrigadesList().set(scenarioBrigadeChoiceBoxId.getSelectionModel().getSelectedIndex(), Integer.parseInt(scenarioBrigadeCountTurnsId.getText()));
    }
}
