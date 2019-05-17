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
        this.experiment= experiment;
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
                size = experiment.getStageQuality().get(index - 2).size();
            }
            if (indexList.size() == 0) {
                for (int i = 1; i <= size; i++) {
                    if (index == 0) {
                    }
                    indexList.add("" + i);
                }
            }
            indexChoiceBoxId.getItems().addAll(indexList);

            descriptionId.setText(experiment.getConfigureQualityValuesList().get(elementChoiceBoxId.getSelectionModel().getSelectedIndex()).getKey());
        }));

        indexChoiceBoxId = (ChoiceBox<String>) root.lookup("#indexChoiceBoxId");

        descriptionId = (TextArea) root.lookup("#descriptionId");
    }

    public void onFixButton() {
        int elementId = elementChoiceBoxId.getSelectionModel().getSelectedIndex();
        int indexId = indexChoiceBoxId.getSelectionModel().getSelectedIndex();
        if (elementId == 0) {
            if (experiment.getBrigades().get(indexId) < experiment.getAcceptableQuality()) {
                experiment.getBrigades().set(indexId, 0.95);
                return;
            }
        }
        else  {
            if (experiment.getStageQuality().get(elementId - 1).get(indexId) < experiment.getAcceptableQuality()) {
                experiment.getStageQuality().get(elementId - 1).set(indexId, 0.95);
                return;
            }
        }
    }
}
