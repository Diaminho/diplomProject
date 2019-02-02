package sample.stages;

import javafx.stage.Stage;
import sample.controllers.MaterialsListController;
import sample.resources.Material;

import java.util.List;

public class MaterialsStage extends Stage {
    public List<Material> showAndReturn(MaterialsListController controller) {
        super.showAndWait();
        return controller.getMaterialsListManager().getSelectedMaterials();
    }
}
