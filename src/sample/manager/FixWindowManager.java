package sample.manager;

import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import sample.Experiment;

import java.util.ArrayList;
import java.util.List;

public class FixWindowManager {
    static Parent root;
    Experiment experiment;

    ChoiceBox<String> elementChoiceBoxId;
    ChoiceBox<Integer> indexChoiceBoxId;

    public FixWindowManager(Parent root, Experiment experiment) {
        FixWindowManager.root = root;
        this.experiment= experiment;
        init();
    }

    private void init() {
        elementChoiceBoxId = (ChoiceBox<String>) root.lookup("#elementChoiceBoxId");
        List<String> elementList = new ArrayList<>();
        elementList.add("Бригады");
        elementList.add("Смешивание");
        elementList.add("Формовка");
        elementList.add("Сушка");
        elementList.add("Обжиг");
        elementChoiceBoxId.getItems().addAll(elementList);
        elementChoiceBoxId.getSelectionModel().selectedIndexProperty().addListener(((observable, oldValue, newValue) -> {
            indexChoiceBoxId.getItems().clear();
            List<Integer> indexList = new ArrayList<>();
            int size;
            if (newValue.intValue() == 0) {
                size = experiment.getBrigades().size();
            }
            else {
                size = experiment.getStageQuality().get(newValue.intValue() - 1).size();
            }
            for (int i = 1; i <= size; i++) {
                indexList.add(i);
            }
            indexChoiceBoxId.getItems().addAll(indexList);
        }));

        indexChoiceBoxId = (ChoiceBox<Integer>) root.lookup("#indexChoiceBoxId");
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
