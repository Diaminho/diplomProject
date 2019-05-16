package sample.task;

import javafx.application.Platform;
import javafx.scene.control.Label;
import sample.Experiment;
import sample.statistic.Statistic;

import java.util.concurrent.*;

public class ExperimentTask {

    private PausableScheduledThreadPoolExecutor executor = new PausableScheduledThreadPoolExecutor(4);

    public PausableScheduledThreadPoolExecutor getExecutor() {
        return executor;
    }

    public void addSuspendableTask(Runnable task, int delay, int period) {
        executor.scheduleWithFixedDelay(task, delay, period, TimeUnit.SECONDS);
    }

    public Runnable BlendingTask(Experiment experiment, Label label) {
         return new Runnable() {
            private int counter = 0;
            private boolean flag = true;

            @Override
            public void run() {
                //need to fix
                //int count=oldCount;
                //while(flag) {
                    //while (suspendFlag) {
                    //    wait();
                    //}
                    //Thread.sleep(10000);

                if (flag) {
                    flag = experiment.produceRawMaterial();
                    System.out.println("Blending count: " + counter++ + " Thread: " + Thread.currentThread().getName());
                    Platform.runLater(() -> label.setText("" + experiment.getRawList().size()));
                    //}
                }
            }
        };
    }

    public Runnable CuttingTask(Experiment experiment){
        return new Runnable() {
            private int counter = 0;
            private boolean flag = true;

            @Override
            public void run() {
                //need to fix
                //int count=oldCount;
                //Thread.sleep(1000);
                //while(flag) {
                    //while (suspendFlag) {
                    //    wait();
                    //}
                    //Thread.sleep(10000);
                if (flag) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    flag = experiment.doCutting();
                    //System.out.println(raw.getAvgQuality());
                    //final String rawVolume = String.valueOf(raw.getVolume());
                    //
                    System.out.println("Cutting " + counter++ + " Thread: " + Thread.currentThread().getName());
                    //oldCount+=1;
                    //Platform.runLater(() -> label.setText(""+experiment.getRawList().size()));
                }
            }
        };
    }

    public Runnable DryingTask(Experiment experiment){
        return new Runnable() {
            private int counter = 0;
            private boolean flag = true;

            @Override
            public void run() {
                //need to fix
                //int count=oldCount;
                //Thread.sleep(1000);
                //while(flag) {
                //while (suspendFlag) {
                //    wait();
                //}
                //Thread.sleep(10000);
                if (flag) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    flag = experiment.doDrying();
                //System.out.println(raw.getAvgQuality());
                //final String rawVolume = String.valueOf(raw.getVolume());
                //
                    System.out.println("Drying " + counter++ + " Thread: " + Thread.currentThread().getName());
                //oldCount+=1;
                //Platform.runLater(() -> label.setText(""+experiment.getRawList().size()));
                //}
                }
            }
        };
    }

    public Runnable BurningTask(Experiment experiment){
        return new Runnable() {
            private int counter = 0;
            private boolean flag = true;

            @Override
            public void run() {
                //need to fix
                //int count=oldCount;
                //Thread.sleep(1000);
                //while(flag) {
                //while (suspendFlag) {
                //    wait();
                //}
                //Thread.sleep(10000);
                if (flag) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    flag = experiment.doBurning();
                    //System.out.println(raw.getAvgQuality());
                    //final String rawVolume = String.valueOf(raw.getVolume());
                    //
                    System.out.println("Burning " + counter++ + " Thread: " + Thread.currentThread().getName());
                    Statistic.printBrickStat(experiment.getBrickList());
                    //oldCount+=1;
                    //Platform.runLater(() -> label.setText(""+experiment.getRawList().size()));
                    //}
                }
            }
        };
    }

    public Runnable LogisticTask(Experiment experiment){
        return new Runnable() {
            private int counter = 0;
            private boolean flag = true;

            @Override
            public void run() {
                //need to fix
                //int count=oldCount;
                //Thread.sleep(1000);
                //while(flag) {
                //while (suspendFlag) {
                //    wait();
                //}
                //Thread.sleep(10000);
                if (flag) {
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    flag = experiment.doLogistic();
                    //System.out.println(raw.getAvgQuality());
                    //final String rawVolume = String.valueOf(raw.getVolume());
                    //
                    System.out.println("Logistic " + counter++ + " Thread: " + Thread.currentThread().getName());
                    Statistic.printBrickStat(experiment.getLogisticBrickList());
                    //oldCount+=1;
                    //Platform.runLater(() -> label.setText(""+experiment.getRawList().size()));
                    //}
                }
            }
        };
    }
}
