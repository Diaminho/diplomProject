package sample.stages;

import javafx.stage.Stage;
import sample.controllers.InputPropController;

    public class PropertyStage extends Stage {
        public String showAndReturn(InputPropController controll) {
            super.showAndWait();
            return InputPropController.getInputPropManager().getPropNameID().getText();
        }
    }

