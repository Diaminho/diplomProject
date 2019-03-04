package sample.stages;

import javafx.stage.Stage;
import sample.controllers.ExperimentController;
import sample.resources.Material;

import java.util.List;

public class MaterialsStage extends Stage {
    public List<List<Material>> showAndReturn(ExperimentController controller) {
        super.showAndWait();
        return controller.getExperimentManager().getListOfMaterialsList();
    }
}