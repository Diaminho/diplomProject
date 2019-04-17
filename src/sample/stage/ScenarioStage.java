package sample.stage;

import javafx.stage.Stage;
import sample.Experiment;
import sample.controller.ScenarioController;

public class ScenarioStage extends Stage {
    public Experiment showAndReturn(ScenarioController controller) {
        super.showAndWait();
        return controller.getScenarioManager().getExperiment();
    }
}
