package sample.task;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import sample.Experiment;
import sample.statistic.Statistic;

public class ExperimentTask {

    public SuspendableTask BlendingTask(Experiment experiment, Label label) {
        SuspendableTask task = new SuspendableTask(){
            boolean flag=true;
            @Override
            protected Void call() throws Exception {
                //need to fix
                //int count=oldCount;
                while(flag) {
                    while (suspendFlag) {
                        wait();
                    }
                    Thread.sleep(10000);
                    flag = experiment.produceRawMaterial();
                    System.out.println("Blending");
                    Platform.runLater(() -> label.setText(""+experiment.getRawList().size()));
                }
                return null;
            }
        };
        return task;
    }

    public SuspendableTask CuttingTask(Experiment experiment){
        SuspendableTask task = new SuspendableTask() {
            boolean flag=true;
            @Override
            protected Void call() throws Exception {
                //need to fix
                //int count=oldCount;
                Thread.sleep(1000);
                while(flag) {
                    while (suspendFlag) {
                        wait();
                    }
                    Thread.sleep(10000);
                    flag = experiment.doCutting();
                    //System.out.println(raw.getAvgQuality());
                    //final String rawVolume = String.valueOf(raw.getVolume());
                    //
                    System.out.println("Cutting");
                    //oldCount+=1;
                    //Platform.runLater(() -> label.setText(""+experiment.getRawList().size()));
                }
                return null;
            }
        };
        return task;
    }

    public SuspendableTask DryingTask(Experiment experiment){
        SuspendableTask task = new SuspendableTask() {
            boolean flag=true;
            @Override
            protected Void call() throws Exception {
                //need to fix
                //int count=oldCount;
                Thread.sleep(2000);
                while(flag) {
                    while (suspendFlag) {
                        wait();
                    }
                    Thread.sleep(10000);
                    flag = experiment.doDrying();
                    //System.out.println(raw.getAvgQuality());
                    //final String rawVolume = String.valueOf(raw.getVolume());
                    //
                    System.out.println("Drying");
                    //oldCount+=1;
                    //Platform.runLater(() -> label.setText(""+experiment.getRawList().size()));
                }
                return null;
            }
        };
        return task;
    }

    public SuspendableTask BurningTask(Experiment experiment){
        SuspendableTask task = new SuspendableTask() {
            boolean flag=true;
            @Override
            protected Void call() throws Exception {
                //need to fix
                //int count=oldCount;
                Thread.sleep(4000);
                while(flag) {
                    while (suspendFlag) {
                        wait();
                    }
                    Thread.sleep(10000);
                    flag = experiment.doBurning();
                    //System.out.println(raw.getAvgQuality());
                    //final String rawVolume = String.valueOf(raw.getVolume());
                    //
                    System.out.println("Burning");
                    Statistic.printBrickStat(experiment.getBrickList());
                    //System.out.println(experiment.ge);
                    //oldCount+=1;
                    //Platform.runLater(() -> label.setText(""+experiment.getRawList().size()));
                }
                return null;
            }
        };
        return task;
    }
}
