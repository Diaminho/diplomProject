package sample.task;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import sample.Experiment;
import sample.resource.Material;

public class ExperimentTask {

    public Task FirstStageTask(Experiment experiment, Label label, int oldCount) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                //need to fix
                //int count=oldCount;
                Material raw=new Material();
                while(raw!=null) {
                    raw = experiment.produceRawMaterial();
                    System.out.println(raw.getAvgQuality());
                    //final String rawVolume = String.valueOf(raw.getVolume());
                    //
                    Thread.sleep(10000);
                    //oldCount+=1;
                    Platform.runLater(() -> label.setText("1"));
                }
                return null;
            }
        };
        return task;
    }

}
