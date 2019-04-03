package sample.task;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import sample.Experiment;
import sample.resource.Material;

public class ExperimentTask {

    public Task FirstStageTask(Experiment experiment, Label label) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                //
                Material raw=new Material();
                while(raw!=null) {
                    raw = experiment.produceRawMaterial();
                    final String rawVolume = String.valueOf(raw.getVolume());
                    //
                    Thread.sleep(1000);
                    Platform.runLater(() -> label.setText(rawVolume));
                }
                return null;
            }
        };

        return task;
    }

}
