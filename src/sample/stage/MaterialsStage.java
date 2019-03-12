package sample.stage;

import javafx.stage.Stage;
import sample.controller.ExperimentController;
import sample.resource.Material;

import java.util.List;
import java.util.Map;

public class MaterialsStage extends Stage {
    public Map<Material, List<Double>> showAndReturn(ExperimentController controller) {
        super.showAndWait();
        return controller.getExperimentManager().getMaterialQualityMap();
    }
}