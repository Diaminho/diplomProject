package sample.stage;

import javafx.stage.Stage;
import sample.controller.MaterialsListController;
import sample.resource.Material;

import java.util.Map;

public class ChosenMaterialsStage extends Stage {
    public Map<Material, Integer> showAndReturn(MaterialsListController controller) {
        super.showAndWait();
        return controller.getMaterialsListManager().getSelectedMaterials();
    }
}
