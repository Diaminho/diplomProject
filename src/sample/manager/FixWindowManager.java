package sample.manager;

import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import sample.Experiment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FixWindowManager {
    static Parent root;
    Experiment experiment;

    ChoiceBox<String> elementChoiceBoxId;
    ChoiceBox<String> indexChoiceBoxId;
    TextArea descriptionId;

    public FixWindowManager(Parent root, Experiment experiment) {
        FixWindowManager.root = root;
        this.experiment = experiment;
        init();
    }

    private void init() {
        elementChoiceBoxId = (ChoiceBox<String>) root.lookup("#elementChoiceBoxId");
        List<String> elementList = new ArrayList<>();
        elementList.add("Материалы");
        elementList.add("Бригады");
        elementList.add("Смешивание");
        elementList.add("Формовка");
        elementList.add("Сушка");
        elementList.add("Обжиг");
        elementList.add("Логистика");
        elementChoiceBoxId.getItems().addAll(elementList);
        elementChoiceBoxId.getSelectionModel().selectedIndexProperty().addListener(((observable, oldValue, newValue) -> {
            indexChoiceBoxId.getItems().clear();
            int index = elementChoiceBoxId.getSelectionModel().getSelectedIndex();
            List<String> indexList = new ArrayList<>();
            int size;
            if (index == 0) {
                size = experiment.getMaterialMap().size();
                indexList.addAll((Collection<String>)(Collection<?>) experiment.getMaterialMap().keySet());
            }
            else if (index == 1) {
                size = experiment.getBrigades().size();
            }
            else {
                size = experiment.getStageQualityList().get(index - 2).getStageToolQuality().size();
            }
            if (indexList.size() == 0) {
                for (int i = 1; i <= size; i++) {
                    if (index == 0) {
                    }
                    indexList.add("" + i);
                }
            }
            indexChoiceBoxId.getItems().addAll(indexList);

            descriptionId.setText(experiment.getConfigureQualityValuesList().get(elementChoiceBoxId.getSelectionModel().getSelectedIndex()).getDescription());
        }));

        indexChoiceBoxId = (ChoiceBox<String>) root.lookup("#indexChoiceBoxId");

        descriptionId = (TextArea) root.lookup("#descriptionId");
    }

    public void onFixButton() {
        int elementId = elementChoiceBoxId.getSelectionModel().getSelectedIndex();
        int indexId = indexChoiceBoxId.getSelectionModel().getSelectedIndex();
        //TODO need to add material fix
        if (elementId == 0) {
            if (experiment.getBrigades().get(indexId) < experiment.getAcceptableQuality()) {
                experiment.getBrigades().set(indexId, experiment.getConfigureQualityValuesList().get(elementId).getValue());
                return;
            }
        }
        else if (elementId == 1) {
            if (experiment.getBrigades().get(indexId) < experiment.getAcceptableQuality()) {
                experiment.getBrigades().set(indexId, experiment.getConfigureQualityValuesList().get(elementId).getValue());
                return;
            }
        }
        else  {
            if (experiment.getStageQualityList().get(elementId - 2).getStageToolQuality().get(indexId) < experiment.getAcceptableQuality()) {
                experiment.getStageQualityList().get(elementId - 2).getStageToolQuality().set(indexId, experiment.getConfigureQualityValuesList().get(elementId - 1).getValue());
                return;
            }
        }
    }
}
