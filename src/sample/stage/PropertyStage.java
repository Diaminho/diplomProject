package sample.stage;

import javafx.stage.Stage;
import sample.controller.InputPropController;

    public class PropertyStage extends Stage {
        public String showAndReturn(InputPropController controll) {
            super.showAndWait();
            return InputPropController.getInputPropManager().getPropNameID().getText();
        }
    }

