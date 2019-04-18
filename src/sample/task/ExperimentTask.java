package sample.task;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import sample.Experiment;
import sample.resource.Material;

public class ExperimentTask {

    public Task BlendingTask(Experiment experiment, Label label) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                //need to fix
                //int count=oldCount;
                boolean flag=true;
                while(flag) {
                    Thread.sleep(10000);
                    flag = experiment.produceRawMaterial();
                    System.out.println("1");
                    //final String rawVolume = String.valueOf(raw.getVolume());
                    //
                    //oldCount+=1;
                    Platform.runLater(() -> label.setText(""+experiment.getRawList().size()));
                }
                return null;
            }
        };
        return task;
    }

    public Task CuttingTask(Experiment experiment){
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                //need to fix
                //int count=oldCount;
                boolean flag=true;
                while(flag) {
                    Thread.sleep(10000);
                    flag = experiment.doCutting();
                    //System.out.println(raw.getAvgQuality());
                    //final String rawVolume = String.valueOf(raw.getVolume());
                    //
                    System.out.println("2");
                    //oldCount+=1;
                    //Platform.runLater(() -> label.setText(""+experiment.getRawList().size()));
                }
                return null;
            }
        };
        return task;
    }

}
