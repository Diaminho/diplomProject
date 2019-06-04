package sample.manager;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import sample.Experiment;
import sample.resource.Material;
import sample.resource.helper.ConfigureQuality;
import sample.resource.helper.ScenarioStage;
import sample.resource.helper.StageQualityList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScenarioManager {
    //TODO добавить новую вкладку для мер воздействия на процесс Название, показатель качества после воздействия (случайно, нормальное распределение)
    private static Parent root;
    private Map<String, String> logStringsMap;

    TextArea logTextAreaId;

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
    //Обжиг
    TextField logisticQualityId;

    //Tab Сценарий
    Tab scenarioTabId;
    //Бригады
    ChoiceBox<Integer> scenarioBrigadeChoiceBoxId;
    Slider scenarioBrigadeSliderId;
    TextField scenarioBrigadeCountTurnsId;
    //Этапы
    ChoiceBox<String> scenarioStageChoiceBoxId;
    ChoiceBox<Integer> scenarioStageToolId;
    Slider scenarioStageSliderId;
    TextField scenarioStageTextFieldId;

    //Tab Меры воздействия
    ChoiceBox<String> configureObjectListChoiceBoixId;
    TextField configureDescriptionNameId;
    TextField configureQualityId;

    public static Experiment experiment;


    private int maxCountProcessIterations;

    public Experiment getExperiment() {
        return experiment;
    }

    public void setExperiment(Experiment experiment) {
        this.experiment = experiment;
    }

    public ScenarioManager(Parent root, Experiment experiment) {
        ScenarioManager.root = root;
        ScenarioManager.experiment = experiment;
        if (experiment.getDefaultMaterialsQuality().size() == 0) {
            for (Material o: experiment.getMaterialMap().keySet()){
                experiment.getDefaultMaterialsQuality().put(o,0.99);
            }
        }
        logStringsMap = new HashMap<>();
        init();
        initLog();
    }


    private void constructLogStringMaterials() {
        String info = "\n";
        for (Material m: experiment.getDefaultMaterialsQuality().keySet()) {
            info += "Материал " + m.getName() + " с показателем качества: " + experiment.getDefaultMaterialsQuality().get(m) * 100 + "% повлияет на возникновение вероятность возникновения дефектов на этапе Сушка с вероятностью: " + (1 - experiment.getDefaultMaterialsQuality().get(m)) * 25 + "%\n";
        }
        logStringsMap.put("Материалы", info);
    }


    private void constructLogStringDrying() {
        logStringsMap.put("Сушка", "Показатель надежности оборудования на стадии \"Сушка\": " + 100 * experiment.getStageQualityList().get(2).getStageToolQuality().get(0) + " % приведет к вероятности возникновения брака на этом этапе " + String.format("%.5f", 100 * experiment.getStagesInfluenceMap().get("Сушка").get(0)) + "%");
    }

    private void constructLogStringCutting() {
        logStringsMap.put("Формовка", "Показатель надежности оборудования на стадии \"Формовка\": " + 100 * experiment.getStageQualityList().get(1).getStageToolQuality().get(0) + " % приведет к вероятности возникновения брака на этом этапе " + String.format("%.5f", 100 * experiment.getStagesInfluenceMap().get("Формовка").get(0)) + "%");
    }

    private void constructLogStringBlending() {
        logStringsMap.put("Смешивание", "Показатель надежности оборудования на стадии \"Смешивание\": " + 100 * experiment.getStageQualityList().get(0).getStageToolQuality().get(0) + " % приведет к вероятности возникновения брака на этом этапе " + String.format("%.5f", 100 * experiment.getStagesInfluenceMap().get("Смешивание").get(0)) + "%");
    }

    private void constructLogStringBurning() {
        logStringsMap.put("Обжиг",
                "Показатель надежности оборудования на стадии \"Обжиг\": "
                        + 100 * experiment.getStageQualityList().get(3).getStageToolQuality().get(0) + "%" +
                        " приведет к вероятности возникновения дефектов: \"Трещины\" " +
                        String.format("%.5f", 100 * experiment.getStagesInfluenceMap().get("Обжиг").get(3)) + "%" +
                        ";  \"Цвет\" " + String.format("%.5f"  ,100 * experiment.getStagesInfluenceMap().get("Обжиг").get(0)) + "%" +
                        ";  \"Размеры\" " + String.format("%.5f"  ,100 * experiment.getStagesInfluenceMap().get("Обжиг").get(1)) + "%" +
                        ";  \"Структура\" " + String.format("%.5f"  , 100 * experiment.getStagesInfluenceMap().get("Обжиг").get(2)) + "%"
                );
    }

    private void constructLogStringLogistic() {
        logStringsMap.put("Логистика",
                "Показатель надежности оборудования на стадии \"Логистика\": "
                        + 100 * experiment.getStageQualityList().get(4).getStageToolQuality().get(0) + "%" +
                        " приведет к вероятности возникновения дефектов: \"Трещины\" " +
                        String.format("%.5f", 100 * experiment.getStagesInfluenceMap().get("Логистика").get(0)) + "%" +
                        ";  \"Размеры\" " + String.format("%.5f"  ,100 * experiment.getStagesInfluenceMap().get("Логистика").get(1)) + "%"
        );
    }

    private void constructLogStringBrigades() {
        String info = "Показатель профессионализма бригады повлияет на качество сырья, получаемого на этапе \"Формовка\": ";
        for (int i = 0; i < experiment.getBrigades().size(); i++) {
            info += "\n" + (i + 1) + " бригада. Показатель профессионализма: " + (100 * experiment.getBrigades().get(i)) + "% повлияет на вероятность появления дефектов сырья, полученного на этапе \"Формовка\"" + (1 - experiment.getBrigades().get(i)) * 50 + "%";
        }
        logStringsMap.put("Бригады", info);
    }

    private void initLog() {
        logTextAreaId = (TextArea) (((SplitPane) root).getItems().get(1)).lookup("#logTextAreaId");
        experiment.calculateInfluenceForStages();
        constructLogStringBlending();
        constructLogStringCutting();
        constructLogStringDrying();
        constructLogStringBurning();
        constructLogStringLogistic();
        constructLogStringBrigades();
        constructLogStringMaterials();


        logTextAreaId.setText("");
        for (String s: logStringsMap.keySet()) {
            logTextAreaId.setText(logTextAreaId.getText() + logStringsMap.get(s) + "\n");
        }
    }

    private void init(){
        //TODO ADD LOGS IN TEXTAREA and add scenario influence in Experiment

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
        ChangeListener<Integer> changeListener = new ChangeListener<Integer>() {

            @Override
            public void changed(ObservableValue<? extends Integer> observable, //
                                Integer  oldValue, Integer newValue) {
                if (newValue != null) {
                    blendingQualityId.setText("" + experiment.getStageQualityList().get(0).getStageToolQuality().get(newValue-1));
                }
            }
        };
        blendingChoiceBoxId.getSelectionModel().selectedItemProperty().addListener(changeListener);



        GridPane gridPaneCutting = (GridPane) (((SplitPane)stagesTabId.getContent()).getItems().get(1));
        cuttingQualityId = (TextField) gridPaneCutting.lookup("#cuttingQualityId");
        fillCutting();

        GridPane gridPaneDrying = (GridPane) (((SplitPane)stagesTabId.getContent()).getItems().get(2));
        dryingQualityId = (TextField) gridPaneDrying.lookup("#dryingQualityId");
        fillDrying();

        GridPane gridPaneBurning = (GridPane) (((SplitPane)stagesTabId.getContent()).getItems().get(3));
        burningQualityId = (TextField) gridPaneBurning.lookup("#burningQualityId");
        fillBurning();

        GridPane gridPaneLogistic = (GridPane) (((SplitPane)stagesTabId.getContent()).getItems().get(4));
        logisticQualityId = (TextField) gridPaneLogistic.lookup("#logisticQualityId");
        fillLogistic();

        //Сценарий
        GridPane gridPaneScenarioBrigade = (GridPane) (((SplitPane)scenarioTabId.getContent()).getItems().get(0));
        scenarioBrigadeChoiceBoxId = (ChoiceBox<Integer>) gridPaneScenarioBrigade.lookup("#scenarioBrigadeChoiceBoxId");
        scenarioBrigadeSliderId = (Slider) gridPaneScenarioBrigade.lookup("#scenarioBrigadeSliderId");
        scenarioBrigadeCountTurnsId = (TextField) gridPaneScenarioBrigade.lookup("#scenarioBrigadeCountTurnsId");
        fillScenarioBrigades();

        GridPane gridPaneScenarioStage = (GridPane) (((SplitPane)scenarioTabId.getContent()).getItems().get(1));
        scenarioStageChoiceBoxId = (ChoiceBox<String>) gridPaneScenarioStage.lookup("#scenarioStageChoiceBoxId");
        scenarioStageToolId = (ChoiceBox<Integer>) gridPaneScenarioStage.lookup("#scenarioStageToolId");
        scenarioStageSliderId = (Slider) gridPaneScenarioStage.lookup("#scenarioStageSliderId");
        scenarioStageTextFieldId = (TextField) gridPaneScenarioStage.lookup("#scenarioStageTextFieldId");
        fillScenarioStages();

        //Меры воздействия
        GridPane gridPaneConfigure = (GridPane) tabPane.getTabs().get(3).getContent();
        configureObjectListChoiceBoixId = (ChoiceBox<String>) gridPaneConfigure.lookup("#configureObjectListChoiceBoixId");
        configureDescriptionNameId = (TextField) gridPaneConfigure.lookup("#configureDescriptionNameId");
        configureQualityId = (TextField) gridPaneConfigure.lookup("#configureQualityId");
        fillConfigure();
    }

    private void fillSettingsMaterials() {
        materialsQualityId.setText("-");
        List<String> materialsNamesList = new ArrayList<>();
        for (Material m: experiment.getMaterialMap().keySet()) {
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
        brigadesCountId.setText("" + experiment.getBrigades().size());
        for (int i = 0; i < experiment.getBrigades().size(); i++) {
            brigadesChoiceBoxId.getItems().add(i + 1);
        }
        ChangeListener<Integer> changeListener = new ChangeListener<Integer>() {

            @Override
            public void changed(ObservableValue<? extends Integer> observable, //
                                Integer  oldValue, Integer newValue) {
                if (newValue != null) {
                    brigadesQualityId.setText(""+experiment.getBrigades().get(newValue - 1));
                }
            }
        };
        brigadesChoiceBoxId.getSelectionModel().selectedItemProperty().addListener(changeListener);
    }

    //Stages
    private void fillBlending() {
        blendingQualityId.setText("-");
        blendingChoiceBoxId.getItems().clear();
        blendingQuantityId.setText(""+experiment.getStageQualityList().get(0).getStageToolQuality().size());
        for (int i = 0; i < experiment.getStageQualityList().get(0).getStageToolQuality().size(); i++) {
            blendingChoiceBoxId.getItems().add(i + 1);
        }
    }

    private void fillCutting() {
        cuttingQualityId.setText("" + experiment.getStageQualityList().get(1).getStageToolQuality().get(0));
    }

    private void fillDrying() {
        dryingQualityId.setText("" + experiment.getStageQualityList().get(2).getStageToolQuality().get(0));
        dryingQualityId.textProperty().addListener(((observableValue, s, t1) -> {

        } ));
    }

    private void fillBurning() {
        burningQualityId.setText("" + experiment.getStageQualityList().get(3).getStageToolQuality().get(0));
    }

    private void fillLogistic() {
        logisticQualityId.setText("" + experiment.getStageQualityList().get(4).getStageToolQuality().get(0));
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
        stagesNameList.add("Логистика");

        scenarioStageChoiceBoxId.getItems().addAll(stagesNameList);
        //ChoiceBox
        ChangeListener<String> changeListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, //
                                String  oldValue, String newValue) {
                if (newValue != null) {
                    //TODO add choose tool for Stage
                    scenarioStageToolId.getItems().clear();
                    for (int i = 0; i < experiment.getStageQualityList().get(stagesNameList.indexOf(newValue)).getStageToolQuality().size(); i++) {
                        scenarioStageToolId.getItems().add(i + 1);
                    }
                    scenarioStageToolId.getSelectionModel().select(0);
                    scenarioStageTextFieldId.setText("" + experiment.getScenarioStagesList().get(stagesNameList.indexOf(newValue)).getScenarioStage().get(scenarioStageToolId.getSelectionModel().getSelectedIndex()));
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

    private void fillConfigure() {
        //fill List of Objects
        List<String> objList = new ArrayList<>();
        objList.add("Материалы");
        objList.add("Бригады");
        objList.add("Смешивание");
        objList.add("Формовка");
        objList.add("Сушка");
        objList.add("Обжиг");
        objList.add("Логистика");
        configureObjectListChoiceBoixId.getItems().clear();
        configureObjectListChoiceBoixId.getItems().addAll(objList);

        //fill List of items of Object
        configureObjectListChoiceBoixId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            int index = configureObjectListChoiceBoixId.getItems().indexOf(newValue);
            configureDescriptionNameId.setText(experiment.getConfigureQualityValuesList().get(index).getDescription());
            configureQualityId.setText("" + experiment.getConfigureQualityValuesList().get(index).getValue());
        });

    }


    public void onSaveButton(){
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
        initLog();
    }

    public void onSetBrigadeCountButton() {
        //experiment.getStageToolQuality().set(1, Double.parseDouble(cuttingQualityId.getText()));
        List<Double> brigades = new ArrayList<>();
        List<Integer> brigadesScenario = new ArrayList<>();
        brigadesChoiceBoxId.getItems().clear();
        for (int i = 0; i < Integer.parseInt(brigadesCountId.getText()); i++) {
            brigades.add(0.8);
            brigadesScenario.add(-1);
            brigadesChoiceBoxId.getItems().add(i + 1);
        }
        experiment.setBrigades(brigades);
        experiment.setScenarioBrigadesList(brigadesScenario);
    }

    public void onCancelButton(){

    }

    //Stages
    public void onOkStageBlendingButton() {
        StageQualityList blendingList = new StageQualityList();
        ScenarioStage scenariosList = new ScenarioStage();
        for (int i = 0; i < Integer.parseInt(blendingQuantityId.getText()); i++) {
            blendingList.getStageToolQuality().add(0.8d);
            scenariosList.getScenarioStage().add(-1);
        }
        experiment.getStageQualityList().set(0, blendingList);
        experiment.getScenarioStagesList().set(0, scenariosList);
        fillBlending();
    }

    public void onSaveBlendingButton() {
        experiment.getStageQualityList().get(0).getStageToolQuality().set(blendingChoiceBoxId.getValue() - 1, Double.parseDouble(blendingQualityId.getText()));
        initLog();
    }

    public void onSaveCuttingButton() {
        experiment.getStageQualityList().get(1).getStageToolQuality().set(0, Double.parseDouble(cuttingQualityId.getText()));
        initLog();
    }

    public void onSaveDryingButton() {
        experiment.getStageQualityList().get(2).getStageToolQuality().set(0, Double.parseDouble(dryingQualityId.getText()));
        initLog();
    }

    public void onSaveBurningButton() {
        experiment.getStageQualityList().get(3).getStageToolQuality().set(0, Double.parseDouble(burningQualityId.getText()));
        initLog();
    }

    public void onSaveLogisticButton() {
        experiment.getStageQualityList().get(4).getStageToolQuality().set(0, Double.parseDouble(logisticQualityId.getText()));
        initLog();
    }

    //Scenario
    public void onScenarioStageSaveButton() {
        experiment.getScenarioStagesList().
                get(scenarioStageChoiceBoxId.getSelectionModel().getSelectedIndex()).getScenarioStage().
                set(scenarioStageToolId.getSelectionModel().getSelectedIndex(), Integer.parseInt(scenarioStageTextFieldId.getText()));
    }

    public void onScenarioBrigadeSaveButton() {
        experiment.getScenarioBrigadesList().set(scenarioBrigadeChoiceBoxId.getSelectionModel().getSelectedIndex(), Integer.parseInt(scenarioBrigadeCountTurnsId.getText()));
    }

    //Configure
    public void onConfigureFixButton() {
        experiment.getConfigureQualityValuesList().set(configureObjectListChoiceBoixId.getSelectionModel().getSelectedIndex(),
                new ConfigureQuality(configureDescriptionNameId.getText(), Double.parseDouble(configureQualityId.getText())) );
    }
}
