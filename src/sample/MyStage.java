package sample;

import javafx.stage.Stage;
import sample.controllers.InputPropController;

    public class MyStage extends Stage {
        public String showAndReturn(InputPropController controll) {
            super.showAndWait();
            return InputPropController.getInputPropManager().getPropNameID().getText();
        }
    }

