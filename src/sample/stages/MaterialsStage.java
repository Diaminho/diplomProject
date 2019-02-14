package sample.stages;

import javafx.stage.Stage;
import sample.controllers.MaterialsListController;
import sample.resources.Material;

import java.util.List;
import java.util.Map;

public class MaterialsStage extends Stage {
    public Map<Material, Integer> showAndReturn(MaterialsListController controller) {
        super.showAndWait();
        return controller.getMaterialsListManager().getSelectedMaterials();
    }
}
